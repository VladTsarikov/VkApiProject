package framework.enums;

public enum RegularExpression {

    USER_ID("\\/id(.*)"),
    POST_ID("post_id\":(.*\\d)"),
    UPLOAD_URL("url\":\"(.*)\","),
    UPLOAD_URL_FIRST_PART("https:\\\\\\/\\\\\\/(.*com)\\\\"),
    UPLOAD_URL_SECOND_PART("com\\\\\\/(.*)\\\\"),
    UPLOAD_URL_THIRD_PART("com\\\\\\/.*\\\\\\/(.*)\","),
    PHOTO_SERVER1("com\\\\\\/c(\\d*)"),
    PHOTO_COMPARE_ID("com\\/photo(.*)"),
    PHOTO_SERVER("server\":(\\d*),"),
    PHOTO_ID1("wallphoto=(.*)\","),
    PHOTO_ID("photo\":\"(\\[\\{.*\\}\\])"),
   // photo":"\[\{(.*)\}\]
    //photo":"(\[\{.*\}\])
    PHOTO_HASH1("&hash(.*)&r"),
    PHOTO_HASH("\"hash\":\"(.*)\""),
    UPLOAD_PHOTO_OWNER_ID("owner_id\":(.*),\"siz"),
    UPLOAD_PHOTO_ID("id\":(.*),\"alb"),
    LIKE_INDIKATOR("liked\":(\\d),");


    private String regExp;

    RegularExpression(String regExp) {
        this.regExp = regExp;
    }

    public String getRegExp() {
        return regExp;
    }
}