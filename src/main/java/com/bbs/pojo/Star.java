package com.bbs.pojo;

public class Star {
    private Integer id;
    private String userId;
    private String articleId;
    private String starTime;
    private Article article;
    private Member member;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }
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
                ", starTime='" + starTime + '\'' +
                ", article=" + article +
                ", member=" + member +
                '}';
    }
}
