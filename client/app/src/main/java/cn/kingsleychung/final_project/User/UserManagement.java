package cn.kingsleychung.final_project.User;

import java.io.File;
import java.util.List;
import cn.kingsleychung.final_project.Task;
import cn.kingsleychung.final_project.remote.APIService;
import cn.kingsleychung.final_project.remote.ApiUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhoug on 2017/12/23.
 */

public class UserManagement {

    private UserClass user;
    private APIService apiService;
    private Task tempTask;

    public static UserManagement userManagement;

    public UserManagement() {
            apiService = ApiUtils.getAPIService();
            user = null;
    }

    public static UserManagement getInstance() {
        if (userManagement == null) {
            userManagement = new UserManagement();
        }
        return userManagement;
    }

    public void storeUser(UserClass myUser) {
        user = myUser;
        if (myUser.getMessage() == null) {
            user.setSuccess(true);
            user.setMessage("successful");
        }
    }

    public UserClass getUser() {
        return user;
    }

    public Task getTempTask() {
        return tempTask;
    }

    public void setTempTask(Task task) {
        tempTask = task;
    }

    public void register(final UserClass user, Subscriber<UserClass> subscriber) {
        apiService.registerPost(user)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void login(String userName, String password, Subscriber<UserClass> subscriber) {
        apiService.loginPost(userName, password)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void updateUser(UserClass user, Subscriber<UserClass> subscriber) {
        user.setUserId(getUser().getUserId());
        apiService.updatePost(user)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void getUserInformation(String useName, Subscriber<UserClass> subscriber) {
        apiService.getUserInformationPost(useName)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void addFriend(String friendName, Subscriber<UserClass> subscriber) {
        apiService.addFriendPost(getUser().getUserId(), friendName)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void deleteFriend(String friendName, Subscriber<UserClass> subscriber) {
        apiService.deleteFriendPost(getUser().getUserId(), friendName)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void rechargeMoney(int money, Subscriber<UserClass> subscriber) {
        apiService.rechargePost(getUser().getUserId(),money)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void acceptTask(String taskId, Subscriber<UserClass> subscriber) {
        apiService.acceptTaskPost(getUser().getUserId(),taskId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void cancelTask(String taskId, Subscriber<UserClass> subscriber) {
        apiService.cancelTaskPost(getUser().getUserId(),taskId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getTask(String taskId, Subscriber<Task> subscriber) {
        apiService.getTaskPost(taskId)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void addTask(Task task, Subscriber<UserClass> subscriber) {
        apiService.addTaskPost(task)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(subscriber);
    }

    public void deleteTask(Task task, Subscriber<UserClass> subscriber) {
        apiService.addTaskPost(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void updateTaskTag(Task task, Subscriber<Task> subscriber) {
        apiService.updateTaskTagPost(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void updateTaskReward(Task task, Subscriber<Task> subscriber) {
        apiService.updatTaskRewardPost(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void updateTask(Task task, Subscriber<Task> subscriber) {
        apiService.updateTaskInformationPost(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void finishTask(String taskId, Subscriber<Task> subscriber) {
        apiService.finishTaskPost(taskId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //一定要先给用户设定一个位置才可以调用。
    public void getNearTask(Subscriber<List<Task>> subscriber) {
        apiService.getNearTaskPost(getInstance().getUser())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getPushTask(String userName, Subscriber<List<Task>> subscriber) {
        apiService.pushTaskPost(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getacTask(String userName, Subscriber<List<Task>> subscriber) {
        apiService.actTaskPost(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getInfTask(String userName, Subscriber<List<Task>> subscriber) {
        apiService.infTaskPost(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void uploadPhoto(String path, Subscriber<UserClass> subscriber) {
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        apiService.uploadPhotoPost(requestBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void pollingGetMyInformation(Subscriber<UserClass> subscriber) {
        String userName;
        if (user == null) {
            userName = "1";
        } else {
            userName = getUser().getUserName();
        }
        apiService.getUserInformationPost(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}

