package com.axemblr.service.cm;

import com.axemblr.service.cm.util.StreamGobbler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class LiveTestBase {

  private static final Logger LOGGER = Logger.getLogger(LiveTestBase.class.getName());

  protected static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
  protected static final ProcessBuilder processBuilder = new ProcessBuilder();
  protected static final String VAGRANT_CONFIG_PATH = "src/test/resources/vagrant";
  protected static final String VAGRANT_CMD = "vagrant";

  protected final String TARGET_HOST_NAME = "lucid64";
  protected final String DFS_NAME_DIR_LIST = "dfs_name_dir_list";
  protected final String FS_CHECKPOINT_DIR_LIST = "fs_checkpoint_dir_list";
  protected final String DFS_DATA_DIR_LIST = "dfs_data_dir_list";

  protected static String CM_SERVICE_IP = "192.168.33.10";
  protected static int CM_SERVICE_PORT = 7180;
  protected static URI ENDPOINT = URI.create(String.format("http://%s:%d", CM_SERVICE_IP, CM_SERVICE_PORT));

  /**
   * Given a {@link ProcessBuilder} instance, runs the given command and waits the specified
   * amount of time for the process to finish.
   * Designed to start/stop VM with VAGRANT_CMD.
   * <p/>
   * Lessons from: http://kylecartmell.com/?p=9
   *
   * @param pBuilder      the process builder
   * @param minutesToWait time to wait, in minutes
   * @param commands      the list of commands
   */
  protected static void runAndWait(ProcessBuilder pBuilder, long minutesToWait, String... commands) throws IOException {
    pBuilder.command(commands);
    pBuilder.redirectErrorStream(true);
    Timer timer = null;
    Process process = null;
    try {
      final Thread currentThread = Thread.currentThread();
      timer = new Timer(true);
      // interrupt the current thread so we avoid waiting indefinitely for Process.waitFor();
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          currentThread.interrupt();
        }
      }, TimeUnit.MINUTES.toMillis(minutesToWait));
      process = pBuilder.start();
      EXECUTOR.execute(new StreamGobbler(process.getInputStream(), "INPUT"));
      process.waitFor();
    } catch (InterruptedException interruptedException) {
      LOGGER.severe("Timeout exceeded. Killing the process as it probably hanged.");
      process.destroy();
    } catch (Exception e) {
      LOGGER.severe("Exception starting vagrant VM");
      LOGGER.severe(e.getMessage());
    } finally {
      // If the process returns within the timeout period, we have to stop the interrupter
      // so that it does not unexpectedly interrupt some other code later.
      timer.cancel();
      // We need to clear the interrupt flag on the current thread just in case
      // interrupter executed after waitFor had already returned but before timer.cancel
      // took effect.
      //
      // Oh, and there's also Sun bug 6420270 to worry about here.
      Thread.interrupted();
    }
  }

  /**
   * Check to see if host:port is alive.
   *
   * @param uri
   * @return
   */
  public static boolean isEndpointActive(URI uri) {
    String host = uri.getHost();
    int port = uri.getPort();
    Socket socket = null;
    try {
      socket = new Socket();
      socket.setReuseAddress(true);
      SocketAddress socketAddress = new InetSocketAddress(host, port);
      socket.connect(socketAddress, 3000);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
        }
      }
    }
    return false;
  }

}
