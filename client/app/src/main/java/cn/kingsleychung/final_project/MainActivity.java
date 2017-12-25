package cn.kingsleychung.final_project;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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

    Toolbar toolbar;
    FrameLayout root;
    View openMenu, closeMenu;
    MapFragment mapFragment;
    TaskFragment taskFragment;
    LinearLayout map, task, profile, setting;
    View guillotineMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.i("SHA1", sHA1(this));
        initViews();
        initMenu();
        initPermission();
        initClickListener();

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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mapFragment = new MapFragment();
        transaction.replace(R.id.content_fragment, mapFragment);
        transaction.commit();
    }

    private void initClickListener() {
        map.setOnClickListener(this);
        task.setOnClickListener(this);
        profile.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.map_group:
                if (mapFragment == null) mapFragment = new MapFragment();
                transaction.replace(R.id.content_fragment, mapFragment);
                closeMenu.callOnClick();
                break;
            case R.id.task_group:
                if (taskFragment == null) taskFragment = new TaskFragment();
                transaction.replace(R.id.content_fragment, taskFragment);
                closeMenu.callOnClick();
                break;
            case R.id.profile_group:
                break;
            case R.id.setting_group:
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

    private void initPermission() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

        if (Build.VERSION.SDK_INT >= 23) {
            //如果超过6.0才需要动态权限，否则不需要动态权限
            //如果同时申请多个权限，可以for循环遍历
            int check = ContextCompat.checkSelfPermission(this,permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check == PackageManager.PERMISSION_GRANTED) {
                //写入你需要权限才能使用的方法
                initFragment();
            } else {
                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
                requestPermissions(new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
                initFragment();
            }
        } else {
            initFragment();
        }

    }
}