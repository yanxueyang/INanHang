package www.nuaa.edu.cn.inanhang.Model;

import java.util.List;

/**
 * 用户
 *
 * @author sun
 * @version 15/9/11
 */
public final class User {
    private String username;
    private String password;
    private String personID;
    private String nickname;
    private String portrait;
    private boolean msgPush;
    private String jobType;
    private String job;
    private int credits;
    private String cardNumber;
    private BasicInfo basicInfo;
    private List<House> houses;
    private String signature;

    private List<Advertisement> advertisements;

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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isMsgPush() {
        return msgPush;
    }

    public void setMsgPush(boolean msgPush) {
        this.msgPush = msgPush;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getCredits() {
        return credits + 40;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
    public String getSignature(){
        return signature;
    }
    public void setSignature(String signature){
        this.signature=signature;
    }
    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", personID='" + personID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", jobType='" + jobType + '\'' +
                ", job='" + job + '\'' +
                ", portrait='" + portrait + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", basicInfo='" + basicInfo + '\'' +
                ", houses=" + houses +
                '}';
    }
}
