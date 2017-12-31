package cn.kingsleychung.final_project;

import android.content.Context;
import android.widget.Toast;

import cn.kingsleychung.final_project.User.ResponseUser;
import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

/**
 * Created by zhoug on 2017/12/24.
 */

public class SubscriberManagement {

    public static Subscriber<ResponseUser> getUserSubscriber(final Context context) {
        Subscriber<ResponseUser> temp = (new Subscriber<ResponseUser>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"网络出错，请检查您的网络连接！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseUser user) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                UserManagement.getInstance().storeResponseUser(user);
                Toast.makeText(context,user.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        return temp;
    }

    public static Subscriber<ResponseUser> getUserLoginSubscriber(final Context context) {
        Subscriber<ResponseUser> temp = (new Subscriber<ResponseUser>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"网络出错，请检查您的网络连接！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseUser user) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                UserManagement.getInstance().storeResponseUser(user);


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

    public static Subscriber<ResponseUser> getPollingSubscriber() {
        Subscriber<ResponseUser> temp = (new Subscriber<ResponseUser>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseUser user) {
                //这里是将返回的json数据用来更新用户的本地信息，并不一定都使用，如getUserInformation返回的不是用户本人信息，则不可用。
                UserManagement.getInstance().storeResponseUser(user);
            }
        });
        return temp;
    }
}
