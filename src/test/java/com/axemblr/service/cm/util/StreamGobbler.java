/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.util;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.io.Closeables;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Sends streams to output in background. Closes the stream in case of exception.
 * <p/>
 * Thanks to: http://www.javaworld.com/jw-12-2000/jw-1229-traps.html?page=4
 */
public class StreamGobbler implements Runnable {
  private InputStream is;
  private String type;
  private PrintStream out;

  public StreamGobbler(InputStream is, String type) {
    this(is, type, System.out);
  }

  StreamGobbler(InputStream is, String type, PrintStream out) {
    this.is = checkNotNull(is);
    this.type = checkNotNull(type);
    this.out = checkNotNull(out);
  }

  public void run() {
    try {
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null)
        out.println(type + ">" + line);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      Closeables.closeQuietly(is);
    }
  }
}