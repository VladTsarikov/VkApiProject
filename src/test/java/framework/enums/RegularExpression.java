package framework.enums;

public enum RegularExpression {

    POST_ID("post_id\":(.*\\d)"), PHOTO_URL("upload_url\":(.*)\","), LIKE_INDIKATOR("liked\": (\\d),");

    private String regExp;

    RegularExpression(String regExp) {
        this.regExp = regExp;
    }

    public String getRegExp() {
        return regExp;
    }
}
