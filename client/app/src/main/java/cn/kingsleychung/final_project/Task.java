package cn.kingsleychung.final_project;

import java.util.List;

/**
 * Created by zhoug on 2017/12/24.
 */

public class Task {
    private boolean success;
    private String message ;
    private String taskId;
    private String userName;
    private String title;
    private String content;
    private String taskPosName;
    private double [] taskPosLoc;
    private String tgPosName;
    private double [] tgPosLoc;
    private double reward;
    private Boolean kind;
    private int taskState;
    private String srcUser;
    private List<String> tag;
    private List<String> desUser;
    private String date;  //格式：2017-06-12 06:51:20

    private String userId; //只用于序列化
    private List<String> user; //只用于序列化

    public Task(String userName, String userId, String title, String content, String taskPosName, String tgPosName, double reward, boolean kind, List<String> tag, List<String> desUser, double [] taskPosLoc, double [] tgPosLoc ,String date) {
        this.success = false;
        this.message = "";
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.taskPosName = taskPosName;
        this.tgPosName = tgPosName;
        this.reward = reward;
        this.kind = kind;
        this.tag = tag;
        this.desUser = desUser;
        this.srcUser = userId;
        this.tgPosLoc = tgPosLoc;
        this.taskPosLoc = taskPosLoc;
        this.date = date;
        this.userId = userId;
        this.user = desUser;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTaskPosName() {
        return taskPosName;
    }

    public String getTgPosName() {
        return tgPosName;
    }

    public double getReward() {
        return reward;
    }

    public Boolean getKind() {
        return kind;
    }

    public int getTaskState() {
        return  taskState;
    }

    public String getMessage() {
        return message;
    }

    public String getSrcUser() {
        return srcUser;
    }

    public boolean getSuccess() {
        return success;
    }

    public double [] getTaskPosLoc() {
        return  taskPosLoc;
    }
    public double []getTgPosLoc() {
        return tgPosLoc;
    }
    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }
}


