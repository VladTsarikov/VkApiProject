package framework.enums;

public enum RegularExpression {

    POST_ID("post_id\":(.*\\d)"), PHOTO_SERVER("com\\\\\\/c(\\d*)"),PHOTO_ID("wallphoto=(.*)\","),PHOTO_HASH("&hash(.*)&r"), LIKE_INDIKATOR("liked\":(\\d),");

    private String regExp;

    RegularExpression(String regExp) {
        this.regExp = regExp;
    }

    public String getRegExp() {
        return regExp;
    }
}
