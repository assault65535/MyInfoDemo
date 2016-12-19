package com.tnecesoc.MyInfoDemo.Bean;

import java.io.Serializable;

/**
 * Created by Tnecesoc on 2016/11/20.
 */
public class ProfileBean implements Serializable {

    public enum Category {
        FRIEND, FOLLOW, FOLLOWER, ARBITRARY
    }

    public enum Gender {
        MALE, FEMALE, SECRET
    }

    private String community, phone, nickname, username, email, address, motto, gender;

    public ProfileBean() {}

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public boolean isEmpty() {
        boolean ans = community.isEmpty();
        ans &= nickname == null || nickname.isEmpty();
        ans &= phone == null || phone.isEmpty();
        ans &= username == null || username.isEmpty();
        ans &= email == null || email.isEmpty();
        ans &= gender == null || gender.isEmpty();
        ans &= address == null || address.isEmpty();
        ans &= motto == null || motto.isEmpty();
        return ans;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof ProfileBean) {

            ProfileBean other = (ProfileBean) o;
            boolean ans = community.equals(other.community);
            ans &= nickname.equals(other.nickname);
            ans &= phone.equals(other.phone);
            ans &= username.equals(other.username);
            ans &= email.equals(other.email);
            ans &= address.equals(other.address);
            ans &= gender.equals(other.gender);
            ans &= motto.equals(other.motto);

            return ans;

        } else {
            return false;
        }

    }
}

