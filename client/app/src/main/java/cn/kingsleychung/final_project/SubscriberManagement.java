package cn.kingsleychung.final_project;

import android.content.Context;
import android.widget.Toast;

import cn.kingsleychung.final_project.User.UserClass;
import cn.kingsleychung.final_project.User.UserManagement;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by zhoug on 2017/12/24.
 */

public class SubscriberManagement {

    public static Subscriber<UserClass> getUserSubscriber(final Context context) {
        Subscriber<UserClass> temp = (new Subscriber<UserClass>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"网络出错，请检查您的网络连接！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(UserClass user) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                //UserManagement.getInstance().storeResponseUser(user);
                Toast.makeText(context,user.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        return temp;
    }

    public static Subscriber<UserClass> getUserLoginSubscriber(final Context context) {
        Subscriber<UserClass> temp = (new Subscriber<UserClass>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"网络出错，请检查您的网络连接！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(UserClass user) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                UserManagement.getInstance().storeUser(user);


            }
        });
        return temp;
    }

    public static Subscriber<Task> getTaskSubscriber(final Context context) {
        Subscriber<Task> temp = (new Subscriber<Task>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"网络出错，请检查您的网络连接！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Task task) {
                UserManagement.getInstance().setTempTask(task);
                Toast.makeText(context,UserManagement.getInstance().getTempTask().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        return temp;
    }

    public static Subscriber<UserClass> getPollingSubscriber() {
        Subscriber<UserClass> temp = (new Subscriber<UserClass>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserClass user) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                UserManagement.getInstance().storeUser(user);
            }
        });
        return temp;
    }

    public static Subscriber<ResponseBody> getImageSubscriber() {
        Subscriber<ResponseBody> temp = (new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseBody body) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                //UserManagement.getInstance().storeResponseUser(user);
            }
        });
        return temp;
    }
}
