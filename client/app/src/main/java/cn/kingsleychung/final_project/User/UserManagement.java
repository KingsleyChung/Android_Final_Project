package cn.kingsleychung.final_project.User;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kingsleychung.final_project.Task;
import cn.kingsleychung.final_project.remote.APIService;
import cn.kingsleychung.final_project.remote.ApiUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
//          File file = new File(path);
//        Map<String,RequestBody> params = new HashMap<>();
//        params.put("userName", toRequestBody(user.getUserName()));
//        params.put("nickName", toRequestBody(user.getNickName()));
//        params.put("password", toRequestBody(user.getPassword()));
//        params.put("phone", toRequestBody(user.getPhone()));
//        params.put("email", toRequestBody(user.getEmail()));
//        params.put("qq", toRequestBody(user.getQq()));
//        params.put("wechat", toRequestBody(user.getWechat()));
//        params.put("photo\"; filename=\"test.png\"", RequestBody.create(MediaType.parse("image/png"), file));
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
//        apiService.registerPost(requestBody,user.getUserName(), user.getNickName(), user.getPassword(),user.getPhone(), user.getEmail(), user.getQq(), user.getWechat())
//                  .subscribeOn(Schedulers.newThread())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribe(subscriber);
//        apiService.registerPost(params)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);

    }

//    private RequestBody toRequestBody(String value) {
//        String temp;
//        if (value == null)
//            temp = "-1";
//        else
//            temp = value;
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), temp);
//        return requestBody;
//    }

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
        System.out.println(getInstance().getUser().getLocation()[1]);
        System.out.println(getInstance().getUser().getLocation()[0]);
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

    //订阅者要将拿到的UserClass对象中的photo字符串存储起来,然后发到后台更新
    public void uploadPhoto(String path, Subscriber<UserClass> subscriber) {
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        apiService.uploadPhotoPost(requestBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getPhoto(String photoName, Subscriber<Bitmap> subscriber) {

        apiService.downloadPicFromNet(photoName)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody responseBody) {
                        return BitmapFactory.decodeStream(responseBody.byteStream());
                    }
                })
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

