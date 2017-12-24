package cn.kingsleychung.final_project;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by zhoug on 2017/12/24.
 */

public class Task {
    private boolean success;
    private String message ;
    private String taskId;
    private String title;
    private String content;
    private String taskPos;
    private String tgPos;
    private double reward;
    private Boolean kind;
    private int taskState;
    private String srcUser;
    private List<String> tag;
    private List<String> desUser;

    private String userId; //只用于序列化
    private List<String> user; //只用于序列化

    public Task(String userId, String title, String content, String taskPos, String tgPos, double reward, boolean kind, List<String> tag, List<String> desUser) {
        this.success = false;
        this.message = "";
        this.title = title;
        this.content = content;
        this.taskPos = taskPos;
        this.tgPos = tgPos;
        this.reward = reward;
        this.kind = kind;
        this.tag = tag;
        this.desUser = desUser;
        this.srcUser = userId;

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

    public String getTaskPos() {
        return taskPos;
    }

    public String getTgPos() {
        return tgPos;
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

    public boolean getSuccess() {
        return success;
    }

}


