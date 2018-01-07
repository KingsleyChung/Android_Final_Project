package cn.kingsleychung.final_project.remote;

import java.util.List;
import java.util.Map;

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
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhoug on 2017/12/23.
 */

public interface APIService {

//    @Multipart
//    @POST("api/user/register")
//    Observable<UserClass> registerPost(@Part("photo\"; filename=\"test.png\"") RequestBody img,
//                                       @Part("userName") String userName,
//                                       @Part("nickName") String nickName,
//                                       @Part("password") String password,
//                                       @Part("phone") String phone,
//                                       @Part("email") String email,
//                                       @Part("qq") String qq,
//                                       @Part("wechat") String wechat
//    );

//    @Multipart
//    @POST("api/user/register")
//    Observable<UserClass> registerPost(@PartMap Map<String ,RequestBody> params);

    @POST("api/user/register")
    Observable<UserClass> registerPost(@Body UserClass user);

    @POST("api/user/login")
    @FormUrlEncoded
    Observable<UserClass> loginPost(@Field("userName") String userName, @Field("password") String password);

    @POST("api/user/update")
    Observable<UserClass> updatePost(@Body UserClass user);

    @POST("api/user/res/user")
    @FormUrlEncoded
    Observable<UserClass> getUserInformationPost(@Field("userName") String userName);

    @POST("api/user/friend/add")
    @FormUrlEncoded
    Observable<UserClass> addFriendPost(@Field("userId") String userId, @Field("friendName") String friendName);

    @POST("api/user/friend/delete")
    @FormUrlEncoded
    Observable<UserClass> deleteFriendPost(@Field("userId") String userId, @Field("friendName") String friendName);

    @POST("api/user/money")
    @FormUrlEncoded
    Observable<UserClass> rechargePost(@Field("userId") String userId, @Field("money") int friendName);

    @POST("api/task/accept")
    @FormUrlEncoded
    Observable<UserClass> acceptTaskPost(@Field("userId") String userId, @Field("taskId") String taskId);

    @POST("api/task/cancel")
    @FormUrlEncoded
    Observable<UserClass> cancelTaskPost(@Field("userId") String userId, @Field("taskId") String taskId);

    @POST("api/task/res/task")
    @FormUrlEncoded
    Observable<Task> getTaskPost(@Field("taskId") String taskId);

    @POST("api/task/add")
    Observable<UserClass> addTaskPost(@Body Task task);

    @POST("api/task/delete")
    @FormUrlEncoded
    Observable<Task> deleteTaskPost(@Field("taskId") String taskId);

    @POST("api/task/tag")
    Observable<Task> updateTaskTagPost(@Body Task task);

    @POST("api/task/reward")
    Observable<Task> updatTaskRewardPost(@Body Task task);

    @POST("api/task/update")
    Observable<Task> updateTaskInformationPost(@Body Task task);

    @POST("api/task/finish")
    @FormUrlEncoded
    Observable<Task> finishTaskPost(@Field("taskId") String taskId);

    @POST("api/task/near")
    Observable<List<Task>> getNearTaskPost(@Body UserClass user);

    @POST("api/task/res/pushtask")
    @FormUrlEncoded
    Observable<List<Task>> pushTaskPost(@Field("userName") String userName);

    @POST("api/task/res/actask")
    @FormUrlEncoded
    Observable<List<Task>> actTaskPost(@Field("userName") String userName);

    @POST("api/task/res/inftask")
    @FormUrlEncoded
    Observable<List<Task>> infTaskPost(@Field("userName") String userName);

    @Multipart
    @POST("api/user/upload")
    Observable<UserClass> uploadPhotoPost(@Part("photo\"; filename=\"test.jpg\"") RequestBody img );

    @GET("/public/image/{photoName}")
    Observable<ResponseBody> downloadPicFromNet(@Path("photoName") String photoName);
}


