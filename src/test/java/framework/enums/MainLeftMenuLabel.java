package framework.enums;

public enum MainLeftMenuLabel {

    MY_PAGE("pr"), NEWS("nwsf"), POSTS("msg");

    private String category;

    MainLeftMenuLabel(String category) {
        this.category = category;
    }

    public String getMainLeftMenuLabelCategory() {
        return category;
    }

}
