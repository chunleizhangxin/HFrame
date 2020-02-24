package com.android.styy.common.dao;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "user")
public class UserEntity implements Serializable{

    private static final long serialVersionUID = -1016435626233210305L;

    @Id(autoincrement = true)
    private long uid;
    private String id;
    private String name;
    private String registerEmail;
    private String areaCode;
    private String areaName;
    private String contacts;
    private String contactsTel;
    private String responsePerson;
    private String leaderEmail;
    private String officialWebsite;
    private String address;
    private String introduce;
    private String level;
    private String complaintTel;
    private String consultTel;
    private String userId;
    private String createdUserId;
    private String createdTime;
    private String updateUserId;
    private String updateTime;
    private String userType;
    private String nickName;
    private String status;
    private String username;
    private boolean logout;

    @Generated(hash = 1628294289)
    public UserEntity(long uid, String id, String name, String registerEmail,
            String areaCode, String areaName, String contacts, String contactsTel,
            String responsePerson, String leaderEmail, String officialWebsite,
            String address, String introduce, String level, String complaintTel,
            String consultTel, String userId, String createdUserId,
            String createdTime, String updateUserId, String updateTime,
            String userType, String nickName, String status, String username,
            boolean logout) {
        this.uid = uid;
        this.id = id;
        this.name = name;
        this.registerEmail = registerEmail;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.contacts = contacts;
        this.contactsTel = contactsTel;
        this.responsePerson = responsePerson;
        this.leaderEmail = leaderEmail;
        this.officialWebsite = officialWebsite;
        this.address = address;
        this.introduce = introduce;
        this.level = level;
        this.complaintTel = complaintTel;
        this.consultTel = consultTel;
        this.userId = userId;
        this.createdUserId = createdUserId;
        this.createdTime = createdTime;
        this.updateUserId = updateUserId;
        this.updateTime = updateTime;
        this.userType = userType;
        this.nickName = nickName;
        this.status = status;
        this.username = username;
        this.logout = logout;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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

    public String getRegisterEmail() {
        return registerEmail;
    }

    public void setRegisterEmail(String registerEmail) {
        this.registerEmail = registerEmail;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getResponsePerson() {
        return responsePerson;
    }

    public void setResponsePerson(String responsePerson) {
        this.responsePerson = responsePerson;
    }

    public String getLeaderEmail() {
        return leaderEmail;
    }

    public void setLeaderEmail(String leaderEmail) {
        this.leaderEmail = leaderEmail;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getComplaintTel() {
        return complaintTel;
    }

    public void setComplaintTel(String complaintTel) {
        this.complaintTel = complaintTel;
    }

    public String getConsultTel() {
        return consultTel;
    }

    public void setConsultTel(String consultTel) {
        this.consultTel = consultTel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public boolean getLogout() {
        return this.logout;
    }
}
