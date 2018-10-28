package com.bbs.pojo;

public class Reset {
    private Integer id;
    private String user;
    private String resetTime;
    private String token;
    private Member member;


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getResetTime() {
        return resetTime;
    }

    public void setResetTime(String resetTime) {
        this.resetTime = resetTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Reset{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", resetTime='" + resetTime + '\'' +
                ", token='" + token + '\'' +
                ", member=" + member +
                '}';
    }
}
