package framework.enums;

public enum RequestUrlTemplate {

    TEXT_POST("https://api.vk.com/method/wall.post?owner_id=%s&message=%s&access_token=%s&v=5.80"),
    POST_EDITOR("https://api.vk.com/method/photos.getWallUploadServer?owner_id=%s&access_token=%s&v=5.80"),
    PHOTO_SAVER("https://api.vk.com/method/photos.saveWallPhoto?user_id=%s&photo=%s&server=%s&hash=%s&access_token=%s&v=5.80"),
    COMMENT_ADDER("https://api.vk.com/method/wall.createComment?owner_id=%s&post_id=%s&message=%s&access_token=%s&v=5.80"),
    LIKE_CHECKER("https://api.vk.com/method/likes.isLiked?user_id=%s&type=post&owner_id=%s&item_id=%s&access_token=%s&v=5.80"),
    POST_DELETER("https://api.vk.com/method/wall.delete?owner_id=%s&post_id=%s&access_token=%s&v=5.80");


    private String requestUrl;
    //private String requestParametrs;

    RequestUrlTemplate(String requestUrl) {
        this.requestUrl = requestUrl;
        //this.requestParametrs = requestParametrs;

    }

    public String getRequest() {
        return requestUrl;
    }
}
