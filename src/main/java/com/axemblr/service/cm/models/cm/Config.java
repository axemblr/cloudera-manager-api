/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Model for a configuration parameter. When an entry's value property is not available, it means the entry is not
 * configured. This means that the default value for the entry, if any, will be used. Setting a value to null
 * also can be used to unset any previously set value for the parameter, reverting to the default value (if any).
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_config.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiConfig.html
 */
public class Config {
  private final String name;
  private final String value;
  private final boolean required;
  private final String defaultView;
  private final String displayName;
  private final String description;
  private final String relatedName;
  private final ValidationState validationState;
  private final String validationMessage;

  public Config(String name, String value) {
    this(name, value, false, null, null, null, null, null, null);
  }

  @JsonCreator
  public Config(@JsonProperty("name") String name,
                @JsonProperty("value") String value,
                @JsonProperty("required") boolean required,
                @JsonProperty("default") String defaultView,
                @JsonProperty("displayName") String displayName,
                @JsonProperty("description") String description,
                @JsonProperty("relatedName") String relatedName,
                @JsonProperty("validationState") ValidationState validationState,
                @JsonProperty("validationMessage") String validationMessage) {
    this.name = name;
    this.value = value;
    this.required = required;
    this.defaultView = defaultView;
    this.displayName = displayName;
    this.description = description;
    this.relatedName = relatedName;
    this.validationState = validationState;
    this.validationMessage = validationMessage;
  }

  /**
   * Readonly. The canonical name that identifies this configuration parameter.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * The user-defined value. When absent, the default value (if any) will be used.
   *
   * @return
   */
  public String getValue() {
    return value;
  }

  /**
   * Readonly. Requires "full" view. Whether this configuration is required for the object.
   * If any required configuration is not set, operations on the object may not work.
   *
   * @return
   */
  @JsonIgnore
  public boolean isRequired() {
    return required;
  }

  /**
   * Readonly. Requires "full" view. The default value.
   *
   * @return
   */
  @JsonIgnore
  public String getDefaultView() {
    return defaultView;
  }

  /**
   * Readonly. Requires "full" view. A user-friendly name of the parameters, as would have been shown in the web UI.
   *
   * @return
   */
  @JsonIgnore
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Readonly. Requires "full" view. A textual description of the parameter.
   *
   * @return
   */
  @JsonIgnore
  public String getDescription() {
    return description;
  }

  /**
   * Readonly. Requires "full" view. If applicable, contains the related configuration variable used by the source project.
   *
   * @return
   */
  @JsonIgnore
  public String getRelatedName() {
    return relatedName;
  }

  /**
   * Readonly. Requires "full" view. State of the configuration parameter after validation.
   *
   * @return
   */
  @JsonIgnore
  public ValidationState getValidationState() {
    return validationState;
  }

  /**
   * Readonly. Requires "full" view. A message explaining the parameter's validation state.
   *
   * @return
   */
  @JsonIgnore
  public String getValidationMessage() {
    return validationMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Config)) return false;

    Config config = (Config) o;

    if (name != null ? !name.equals(config.name) : config.name != null) return false;
    if (value != null ? !value.equals(config.value) : config.value != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Config{" +
        "name='" + name + '\'' +
        ", value='" + value + '\'' +
        ", required=" + required +
        ", defaultView='" + defaultView + '\'' +
        ", displayName='" + displayName + '\'' +
        ", description='" + description + '\'' +
        ", relatedName='" + relatedName + '\'' +
        ", validationState=" + validationState +
        ", validationMessage='" + validationMessage + '\'' +
        '}';
  }
}
