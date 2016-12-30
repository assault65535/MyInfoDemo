package com.tnecesoc.MyInfoDemo.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Tnecesoc on 2016/11/20.
 */
public class Profile implements Parcelable {

    public enum Category {
        FRIEND, FOLLOW, FOLLOWER, ARBITRARY, UNKNOWN
    }

    public enum Gender {
        MALE, FEMALE, SECRET
    }

    private String community, phone, nickname, username, email, address, motto, gender;

    public Profile() {
    }

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

//    public boolean isEmpty() {
//        boolean ans = community == null || community.isEmpty();
//        ans &= nickname == null || nickname.isEmpty();
//        ans &= phone == null || phone.isEmpty();
//        ans &= username == null || username.isEmpty();
//        ans &= email == null || email.isEmpty();
//        ans &= gender == null || gender.isEmpty();
//        ans &= address == null || address.isEmpty();
//        ans &= motto == null || motto.isEmpty();
//        return ans;
//    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Profile) {

            Profile other = (Profile) o;

            return Objects.equals(username, other.username);

        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(community);
        dest.writeString(nickname);
        dest.writeString(username);
        dest.writeString(address);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(motto);
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Profile.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            Profile ans = new Profile();

            ans.community = source.readString();
            ans.nickname = source.readString();
            ans.username = source.readString();
            ans.address = source.readString();
            ans.gender = source.readString();
            ans.phone = source.readString();
            ans.email = source.readString();
            ans.motto = source.readString();

            return ans;
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }

    };



}

