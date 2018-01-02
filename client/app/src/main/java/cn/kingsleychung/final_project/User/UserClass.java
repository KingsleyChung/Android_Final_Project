package cn.kingsleychung.final_project.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoug on 2018/1/1.
 */

public class UserClass {

        @Expose
        @SerializedName("success")
        private boolean success;

        @Expose
        @SerializedName("message")
        private String message;

        @Expose
        @SerializedName("location")
        private double [] location;

        @Expose
        @SerializedName("userId")
        private String userId;

        @Expose
        @SerializedName("userName")
        private String userName;

        @Expose
        @SerializedName("nickName")
        private String nickName;

        @Expose
        @SerializedName("password")
        private String password;

        @Expose
        @SerializedName("phone")
        private String phone;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("photo")
        private String photo;

        @Expose
        @SerializedName("qq")
        private String qq;

        @Expose
        @SerializedName("wechat")
        private String wechat;

        @Expose
        @SerializedName("money")
        private int money;

        @Expose
        @SerializedName("friend")
        private List<String> friend;

        @Expose
        @SerializedName("group")
        private List<String> group;

        @Expose
        @SerializedName("pushTask")
        private List<String> pushTask;

        @Expose
        @SerializedName("pullTask")
        private List<String> pullTask;

        @Expose
        @SerializedName("acTask")
        private List<String> acTask;

        @Expose
        @SerializedName("infTask")
        private List<String> infTask;

        public UserClass(String userName, String nickName, String password, String phone, String email,String qq, String wechat, String photo) {
            this.success = true;
            this.message = "default is successful";
            this.userId = "";
            this.userName = userName;
            this.nickName = nickName;
            this.password = password;
            this.phone = phone;
            this.email = email;
            this.qq = qq;
            this.wechat = wechat;
            this.photo = photo;
            this.money = 100;
            this.friend = new ArrayList<String>();
            this.group = new ArrayList<String>();
            this.pushTask = new ArrayList<String>();
            this.pullTask = new ArrayList<String>();
            this.acTask = new ArrayList<String>();
            this.infTask = new ArrayList<String>();
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() { return password; }

        public String getNickName() {
            return nickName;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getQq() {
            return qq;
        }

        public String getWechat() {
            return wechat;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public void setSuccess(boolean temp) {
            success = temp;
        }

        public void setMessage(String myMessage) {
            message = myMessage;
        }
        public double [] getLocation() {
            return location;
        }
        public void setLocation(double [] location) {
            this.location = location;
        }

        public String getIconName() { return photo; }

}
