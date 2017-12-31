package cn.kingsleychung.final_project.remote;

import java.util.List;

import cn.kingsleychung.final_project.Task;
import cn.kingsleychung.final_project.User.UserClass;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhoug on 2017/12/23.
 */

public interface APIService {

    @POST("user/register")
    Observable<UserClass> registerPost(@Body UserClass user);

    @POST("user/login")
    @FormUrlEncoded
    Observable<UserClass> loginPost(@Field("userName") String userName, @Field("password") String password);

    @POST("user/update")
    Observable<UserClass> updatePost(@Body UserClass user);

    @POST("user/res/user")
    @FormUrlEncoded
    Observable<UserClass> getUserInformationPost(@Field("userName") String userName);

    @POST("user/friend/add")
    @FormUrlEncoded
    Observable<UserClass> addFriendPost(@Field("userId") String userId, @Field("friendName") String friendName);

    @POST("user/friend/delete")
    @FormUrlEncoded
    Observable<UserClass> deleteFriendPost(@Field("userId") String userId, @Field("friendName") String friendName);

    @POST("user/money")
    @FormUrlEncoded
    Observable<UserClass> rechargePost(@Field("userId") String userId, @Field("money") int friendName);

    @POST("task/accept")
    @FormUrlEncoded
    Observable<UserClass> acceptTaskPost(@Field("userId") String userId, @Field("taskId") String taskId);

    @POST("task/cancel")
    @FormUrlEncoded
    Observable<UserClass> cancelTaskPost(@Field("userId") String userId, @Field("taskId") String taskId);

    @POST("task/res/task")
    @FormUrlEncoded
    Observable<Task> getTaskPost(@Field("taskId") String taskId);

    @POST("task/add")
    Observable<UserClass> addTaskPost(@Body Task task);

    @POST("task/delete")
    @FormUrlEncoded
    Observable<Task> deleteTaskPost(@Field("taskId") String taskId);

    @POST("task/tag")
    Observable<Task> updateTaskTagPost(@Body Task task);

    @POST("task/reward")
    Observable<Task> updatTaskRewardPost(@Body Task task);

    @POST("task/update")
    Observable<Task> updateTaskInformationPost(@Body Task task);

    @POST("task/finish")
    @FormUrlEncoded
    Observable<Task> finishTaskPost(@Field("taskId") String taskId);

    @POST("task/near")
    Observable<List<Task>> getNearTaskPost(@Body UserClass user);
}


