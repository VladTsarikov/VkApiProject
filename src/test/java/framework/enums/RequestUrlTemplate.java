package framework.enums;

import framework.VkApiUtils;

public enum RequestUrlTemplate {

    TEXT_POST(new StringBuilder(VkApiUtils.getVkApiRequestUrl()).append("wall.post?owner_id=%s&message=%s&access_token=%s&v=").append(VkApiUtils.getVkApiRequestVersion()).toString()),
    PHOTO_UPLOADER(new StringBuilder(VkApiUtils.getVkApiRequestUrl()).append("photos.getWallUploadServer?user_id=%s&access_token=%s&v=").append(VkApiUtils.getVkApiRequestVersion()).toString()),
    PHOTO_SAVER(new StringBuilder(VkApiUtils.getVkApiRequestUrl()).append("photos.saveWallPhoto?user_id=%s&photo=%s&server=%s&hash=%s&access_token=%s&v=").append(VkApiUtils.getVkApiRequestVersion()).toString()),
    COMMENT_ADDER(new StringBuilder(VkApiUtils.getVkApiRequestUrl()).append("wall.createComment?owner_id=%s&post_id=%s&message=%s&access_token=%s&v=").append(VkApiUtils.getVkApiRequestVersion()).toString()),
    LIKE_CHECKER(new StringBuilder(VkApiUtils.getVkApiRequestUrl()).append("likes.isLiked?user_id=%s&type=post&owner_id=%s&item_id=%s&access_token=%s&v=").append(VkApiUtils.getVkApiRequestVersion()).toString()),
    POST_EDITOR(new StringBuilder(VkApiUtils.getVkApiRequestUrl()).append("wall.edit?owner_id=%s&post_id=%s&message=%s&attachments=photo%s_%s&access_token=%s&v=").append(VkApiUtils.getVkApiRequestVersion()).toString()),
    POST_DELETER(new StringBuilder(VkApiUtils.getVkApiRequestUrl()).append("wall.delete?owner_id=%s&post_id=%s&access_token=%s&v=").append(VkApiUtils.getVkApiRequestVersion()).toString());

    private String requestUrl;

    RequestUrlTemplate(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestTemplate() {
        return requestUrl;
    }
}