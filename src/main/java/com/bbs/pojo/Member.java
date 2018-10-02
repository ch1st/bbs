package com.bbs.pojo;

import java.io.Serializable;

public class Member implements Serializable {
    private String id;
    private String name;
    private String username;
    private String password;
    private String code;
    private String regIP;
    private String loginIP;
    private String email;
    private Integer status;
    private String regTime;
    private String loginTime;
    private String avatar;
    private String city;
    private String signature;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRegTime() {
        return regTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Member() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegIP() {
        return regIP;
    }

    public void setRegIP(String regIP) {
        this.regIP = regIP;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", regIP='" + regIP + '\'' +
                ", loginIP='" + loginIP + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", regTime='" + regTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", avatar='" + avatar + '\'' +
                ", city='" + city + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
