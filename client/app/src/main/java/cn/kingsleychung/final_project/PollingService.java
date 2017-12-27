package cn.kingsleychung.final_project;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.Timer;
import java.util.TimerTask;

import cn.kingsleychung.final_project.User.UserManagement;

public class PollingService extends Service {

    private IBinder myBinder = new MyBinder();

    public PollingService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                UserManagement.getInstance().pollingGetMyInformation(SubscriberManagement.getPollingSubscriber());
            }
        }, 200000 , 5000);
    }

    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    start();
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
