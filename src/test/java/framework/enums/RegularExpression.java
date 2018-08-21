package framework.enums;

public enum RegularExpression {

    USER_ID("\\/id(.*)"),
    BRACKETS_REPLACER("^.|.$"),
    PHOTO_COMPARE_ID("com\\/photo\\d*_(.*)");

    private String regExp;

    RegularExpression(String regExp) {
        this.regExp = regExp;
    }

    public String getRegExp() {
        return regExp;
    }
}