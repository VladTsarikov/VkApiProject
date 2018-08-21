package framework.enums;

public enum ResponseParamName {

    POST_ID("post_id"),
    RESPONSE("response"),
    PHOTO("photo"),
    HASH("hash"),
    UPLOAD_URL("upload_url"),
    OWNER_ID("owner_id"),
    ID("id"),
    LIKED("liked"),
    SERVER("server");

    private String paramName;

    ResponseParamName(String paramName) { this.paramName = paramName; }

    public String getParamName() { return paramName; }
}
