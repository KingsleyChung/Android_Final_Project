package cn.kingsleychung.final_project.remote;

import java.util.List;

import cn.kingsleychung.final_project.Task;
import cn.kingsleychung.final_project.User.UserClass;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("task/res/pushtask")
    @FormUrlEncoded
    Observable<List<Task>> pushTaskPost(@Field("userName") String userName);

    @POST("task/res/actask")
    @FormUrlEncoded
    Observable<List<Task>> actTaskPost(@Field("userName") String userName);

    @POST("task/res/inftask")
    @FormUrlEncoded
    Observable<List<Task>> infTaskPost(@Field("userName") String userName);

    @Multipart
    @POST("user/upload")
    Observable<UserClass> uploadPhotoPost(@Part("photo\"; filename=\"test.jpg\"") RequestBody img );

    @GET("/20140509/4746986_145156378323_2.jpg")
    Observable<ResponseBody> downloadPicFromNet();
}


