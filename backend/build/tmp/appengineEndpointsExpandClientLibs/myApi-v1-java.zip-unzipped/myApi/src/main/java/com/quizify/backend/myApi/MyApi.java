/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2015-11-16 19:10:01 UTC)
 * on 2015-11-21 at 05:46:41 UTC 
 * Modify at your own risk.
 */

package com.quizify.backend.myApi;

/**
 * Service definition for MyApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link MyApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class MyApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.21.0 of the myApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "myApi/v1/user/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public MyApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  MyApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "insertNewUser".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link InsertNewUser#execute()} method to invoke the remote operation.
   *
   * @param userName
   * @param email
   * @param facebookId
   * @param gcmId
   * @return the request
   */
  public InsertNewUser insertNewUser(java.lang.String userName, java.lang.String email, java.lang.String facebookId, java.lang.String gcmId) throws java.io.IOException {
    InsertNewUser result = new InsertNewUser(userName, email, facebookId, gcmId);
    initialize(result);
    return result;
  }

  public class InsertNewUser extends MyApiRequest<com.quizify.backend.myApi.model.User> {

    private static final String REST_PATH = "{userName}/{email}/{facebookId}/{gcmId}";

    /**
     * Create a request for the method "insertNewUser".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link InsertNewUser#execute()} method to invoke the remote operation. <p>
     * {@link InsertNewUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientR
     * equest)} must be called to initialize this instance immediately after invoking the constructor.
     * </p>
     *
     * @param userName
     * @param email
     * @param facebookId
     * @param gcmId
     * @since 1.13
     */
    protected InsertNewUser(java.lang.String userName, java.lang.String email, java.lang.String facebookId, java.lang.String gcmId) {
      super(MyApi.this, "POST", REST_PATH, null, com.quizify.backend.myApi.model.User.class);
      this.userName = com.google.api.client.util.Preconditions.checkNotNull(userName, "Required parameter userName must be specified.");
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.facebookId = com.google.api.client.util.Preconditions.checkNotNull(facebookId, "Required parameter facebookId must be specified.");
      this.gcmId = com.google.api.client.util.Preconditions.checkNotNull(gcmId, "Required parameter gcmId must be specified.");
    }

    @Override
    public InsertNewUser setAlt(java.lang.String alt) {
      return (InsertNewUser) super.setAlt(alt);
    }

    @Override
    public InsertNewUser setFields(java.lang.String fields) {
      return (InsertNewUser) super.setFields(fields);
    }

    @Override
    public InsertNewUser setKey(java.lang.String key) {
      return (InsertNewUser) super.setKey(key);
    }

    @Override
    public InsertNewUser setOauthToken(java.lang.String oauthToken) {
      return (InsertNewUser) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertNewUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertNewUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertNewUser setQuotaUser(java.lang.String quotaUser) {
      return (InsertNewUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertNewUser setUserIp(java.lang.String userIp) {
      return (InsertNewUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String userName;

    /**

     */
    public java.lang.String getUserName() {
      return userName;
    }

    public InsertNewUser setUserName(java.lang.String userName) {
      this.userName = userName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public InsertNewUser setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String facebookId;

    /**

     */
    public java.lang.String getFacebookId() {
      return facebookId;
    }

    public InsertNewUser setFacebookId(java.lang.String facebookId) {
      this.facebookId = facebookId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String gcmId;

    /**

     */
    public java.lang.String getGcmId() {
      return gcmId;
    }

    public InsertNewUser setGcmId(java.lang.String gcmId) {
      this.gcmId = gcmId;
      return this;
    }

    @Override
    public InsertNewUser set(String parameterName, Object value) {
      return (InsertNewUser) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link MyApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link MyApi}. */
    @Override
    public MyApi build() {
      return new MyApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link MyApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setMyApiRequestInitializer(
        MyApiRequestInitializer myapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(myapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}