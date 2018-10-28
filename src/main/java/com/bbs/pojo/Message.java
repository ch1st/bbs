package com.bbs.pojo;

public class Message {
    private String id;
    private String user;
    private String articleId;
    private int status;
    private String date;
    private Member member;
    private Article article;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", articleId='" + articleId + '\'' +
                ", status=" + status +
                ", date='" + date + '\'' +
                ", member=" + member +
                ", article=" + article +
                '}';
    }
}
