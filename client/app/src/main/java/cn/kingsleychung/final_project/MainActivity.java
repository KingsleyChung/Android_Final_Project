package cn.kingsleychung.final_project;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final long RIPPLE_DURATION = 0;

    private Toolbar toolbar;
    private FrameLayout root;
    private View openMenu, closeMenu;
    private MapFragment mapFragment;
    private TaskFragment taskFragment;
    private LinearLayout map, task, profile, setting;
    private View guillotineMenu;

    //服务
    private IBinder mBinder;
    private ServiceConnection mConnection;

    private int selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("SHA1", sHA1(this));
        initViews();
        initMenu();
        //initPermission();
        initFragment();
        initClickListener();

        /*测试用*/
        //UserManagement.getInstance().getTask("5a3e6fd7d6028c09d1f75da3", SubscriberManagement.getTaskSubscriber(this));
        //UserManagement.getInstance().getUserInformation("1", SubscriberManagement.getUserSubscriber(this));
//        List<String> temp = new ArrayList<String>();
//        temp.add("1");
//        temp.add("2");
//        List<String> temp2 = new ArrayList<String>();
//        temp2.add("5a45c59e48b3173902c9774f");
//        temp2.add("5a45c5a148b3173902c97750");
//        Task task = new Task("5a45c59948b3173902c9774e", "zhougb3text","laji", "suse", "house", 11.5,true, temp, temp2, new double[] {12,12}, new double[] {13,13},"2015-01-12 06:51:20");
//        UserManagement.getInstance().addTask(task, SubscriberManagement.getUserSubscriber(this));
//        UserManagement.getInstance().login("1429", "1429",loginSubscriber);
        //initService();
        //startService(); 服务的启动要在用户登录之后，不能紧跟初始化服务之后

//        UserManagement.getInstance().uploadPhoto("/data/data/cn.kingsleychung.final_project/1.png", SubscriberManagement.getUserSubscriber(this));
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        root = findViewById(R.id.root);
        openMenu = findViewById(R.id.content_hamburger);
    }

    private void initMenu() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), openMenu)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        map = guillotineMenu.findViewById(R.id.map_group);
        task = guillotineMenu.findViewById(R.id.task_group);
        profile = guillotineMenu.findViewById(R.id.profile_group);
        setting = guillotineMenu.findViewById(R.id.setting_group);
        closeMenu = guillotineMenu.findViewById(R.id.guillotine_hamburger);
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mapFragment = new MapFragment();
        transaction.replace(R.id.content_fragment, mapFragment);
        transaction.commit();
        selectedFragment = 0;
    }

    private void initClickListener() {
        map.setOnClickListener(this);
        task.setOnClickListener(this);
        profile.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.map_group:
                if (mapFragment == null) mapFragment = new MapFragment();
                transaction.replace(R.id.content_fragment, mapFragment);
                selectedFragment = 0;
                closeMenu.callOnClick();
                break;
            case R.id.task_group:
                if (taskFragment == null) taskFragment = new TaskFragment();
                transaction.replace(R.id.content_fragment, taskFragment);
                selectedFragment = 1;
                closeMenu.callOnClick();
                break;
            case R.id.profile_group:
                selectedFragment = 2;
                break;
            case R.id.setting_group:
                selectedFragment = 3;
                break;
        }

        transaction.commit();
    }

    //for testing
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    void initService() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mConnection = null;
            }
        };
        Intent mIntent = new Intent(this,PollingService.class);
        startService(mIntent);
        bindService(mIntent,mConnection, Context.BIND_AUTO_CREATE);
    }

    void startService() {
        try{
            int code = 1;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            mBinder.transact(code,data,reply,0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        //unbindService(mConnection);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (selectedFragment == 0) {
            moveTaskToBack(true);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content_fragment, mapFragment);
            transaction.commit();
            selectedFragment = 0;
        }
    }
}
