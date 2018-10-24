package com.bbs.pojo;

public class Word {
    private String id;
    private String user;
    private String replyContent;
    private Integer star;
    private String date;
    private String tId;
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", star=" + star +
                ", date='" + date + '\'' +
                ", tId='" + tId + '\'' +
                ", member=" + member +
                '}';
    }
}
