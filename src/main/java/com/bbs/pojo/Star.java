package com.bbs.pojo;

public class Star {
    private Integer id;
    private String userId;
    private String articleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "Star{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}
