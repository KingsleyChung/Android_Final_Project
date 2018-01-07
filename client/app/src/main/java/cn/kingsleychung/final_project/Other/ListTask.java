package cn.kingsleychung.final_project.Other;


import android.graphics.Bitmap;

/**
 * Created by weimumu on 2018-1-7.
 */

public class ListTask {
    private String UserName;
    private String Title;
    private String Date;
    private String TgPosName;
    private String acUser;
    private Bitmap image;


    public ListTask(String UserName, String Title, String Date, String TgPosName, String acUser, Bitmap image) {
        this.UserName = UserName;
        this.Title = Title;
        this.Date = Date;
        this.TgPosName = TgPosName;
        this.acUser = acUser;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getAcUser() {
        return acUser;
    }

    public String getDate() {
        return Date;
    }

    public String getTgPosName() {
        return TgPosName;
    }

    public String getTitle() {
        return Title;
    }

    public String getUserName() {
        return UserName;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
