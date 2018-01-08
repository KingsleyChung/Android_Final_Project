package cn.kingsleychung.final_project;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.kingsleychung.final_project.Other.GetRecordsPagerAdapter;
import cn.kingsleychung.final_project.Other.MapContainer;
import cn.kingsleychung.final_project.User.UserClass;
import cn.kingsleychung.final_project.User.UserManagement;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * Created by kingsley on 3/1/2018.
 */

public class DetailTask extends FragmentActivity {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private GetRecordsPagerAdapter pagerAdapter;
    private TextView mTitle, mExpireDate, mSubmit, mUsername, mClear, mAccept;
    private EditText mContent;
    private ImageView mBack, mHelp;
    private CircleImageView mIcon;
    private Calendar mCalendar;
    private MapView mMapView;
    private AMap mAMap;
    private UiSettings mUiSettings;
    private String mTaskID;
    Bundle bundle;
    String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_task);

        mMapView = findViewById(R.id.detail_map);
        mMapView.onCreate(savedInstanceState);

        initView();
        initMap();
        initStatus();
//        initTabView();
        initDatePicker();
        initClickListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private void initView() {
        mTitle = findViewById(R.id.detail_task_title);
        mContent = findViewById(R.id.detail_content);
//        slidingTabLayout = findViewById(R.id.detail_tab_layout);
//        viewPager = findViewById(R.id.detail_view_pager);
        mExpireDate = findViewById(R.id.detail_expire_date);
        mBack = findViewById(R.id.detail_back);
        mHelp = findViewById(R.id.detail_help);
        mUsername = findViewById(R.id.detail_name);
        mIcon = findViewById(R.id.detail_icon);
        mSubmit = findViewById(R.id.detail_submit);
        mAccept = findViewById(R.id.detail_accept);
        mClear = findViewById(R.id.detail_clear);
        mTaskID = null;
        bundle = getIntent().getExtras();
        userName = bundle.getString("Username");
    }

    private void initMap() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(1000));
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);

        Bundle bundle = getIntent().getExtras();
        LatLng startLocation = new LatLng(bundle.getDouble("StartLatitude"), bundle.getDouble("StartLongitude")), endLocation = null;
        if (bundle.containsKey("EndLatitude")) {
            endLocation = new LatLng(bundle.getDouble("EndLatitude"), bundle.getDouble("EndLongitude"));
        }
        Marker startMarker = mAMap.addMarker(new MarkerOptions().position(startLocation).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start_pin_down)))),
                endMarker = null;
        if (endLocation != null) {
            endMarker = mAMap.addMarker(new MarkerOptions().position(endLocation).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.end_pin_down))));
            double distance = AMapUtils.calculateLineDistance(startLocation, endLocation);
        }
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(startLocation));
    }

    private void initStatus() {
        mTitle.setText(bundle.getString("TaskTitle"));
        mContent.setText(bundle.getString("TaskContent"));
        mUsername.setText(userName);
        Subscriber<Bitmap> getIconSubscriber = (new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onNext(Bitmap bitmap) {
                mIcon.setImageBitmap(bitmap);
            }
        });
        UserManagement.getInstance().getPhoto(userName + ".jpg", getIconSubscriber);
        if (bundle.getString("Mode").equals("ShowDetail")) {
            mExpireDate.setText(bundle.getString("TaskExpire"));
            mTaskID = bundle.getString("TaskID");
            if (userName.equals(UserManagement.getInstance().getUser().getUserName())) {
                mAccept.setVisibility(View.GONE);
                mClear.setVisibility(View.GONE);
                mSubmit.setVisibility(View.GONE);
            } else {
                mAccept.setVisibility(View.VISIBLE);
                mClear.setVisibility(View.GONE);
                mSubmit.setVisibility(View.GONE);
            }
        } else if (bundle.getString("Mode").equals("AddTask")) {
            mAccept.setVisibility(View.GONE);
            mClear.setVisibility(View.VISIBLE);
            mSubmit.setVisibility(View.VISIBLE);
        }

    }

//    private void initTabView() {
//        fragmentList.add(new PublicTaskInfoFragment());
//        fragmentList.add(new DirectionalTaskInfoFragment());
//        pagerAdapter = new GetRecordsPagerAdapter(this.getSupportFragmentManager(), fragmentList);
//        viewPager.setAdapter(pagerAdapter);
//        slidingTabLayout.setViewPager(viewPager, new String[]{"非指定", "指定"});
//    }

    private void initDatePicker() {
        mCalendar = Calendar.getInstance();
        final int year = mCalendar.get(Calendar.YEAR);
        final int month = mCalendar.get(Calendar.MONTH);
        final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        mExpireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        String dis_year = year + "",
                                dis_month = ++month + "",
                                dis_day = day + "";
                        if (month < 10) {
                            dis_month = "0" + month;
                        }
                        if (day < 10) {
                            dis_day = "0" + day;
                        }
                        mExpireDate.setText(dis_year + "-" + dis_month + "-" + dis_day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(DetailTask.this, 0, listener, year, month, day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
            }
        });
    }

    private void initClickListener() {
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subscriber<UserClass> uploadTaskSubscriber = (new Subscriber<UserClass>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e);

                        System.out.println(getIntent().getExtras().getDouble("StartLatitude"));
                        System.out.println(getIntent().getExtras().getDouble("StartLongitude"));
                    }


                    @Override
                    public void onNext(UserClass userClass) {
                        UserManagement.getInstance().storeUser(userClass);
                        Toast.makeText(DetailTask.this, "Upload successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                System.out.println(getIntent().getExtras().getDouble("StartLatitude"));
                System.out.println(getIntent().getExtras().getDouble("StartLongitude"));

                Bundle bundle = getIntent().getExtras();
                Task tempTask = new Task(UserManagement.getInstance().getUser().getUserName(),
                        UserManagement.getInstance().getUser().getUserId(),
                        mTitle.getText().toString(),
                        mContent.getText().toString(),
                        bundle.getString("StartLocationText"),
                        bundle.getString("EndLocationText"),
                        1000, false, new ArrayList<String>(),
                        new ArrayList<String>(),
                        new double[] { getIntent().getExtras().getDouble("StartLongitude"), getIntent().getExtras().getDouble("StartLatitude") },
                        new double[] {},
                        mExpireDate.getText().toString() + " 00:00:00");
//                Task(String userName, String userId, String title, String content, String taskPosName,
//                        String tgPosName, double reward, boolean kind, List<String> tag, List<String> desUser,
//                double [] taskPosLoc, double [] tgPosLoc ,String date)

                UserManagement.getInstance().addTask(tempTask, uploadTaskSubscriber);
            }
        });

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subscriber<UserClass> acceptTaskSubscriber = (new Subscriber<UserClass>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e);
                    }

                    @Override
                    public void onNext(UserClass userClass) {

                    }
                });
                if (mTaskID != null) {
                    UserManagement.getInstance().acceptTask(mTaskID, acceptTaskSubscriber);
                    Toast.makeText(getApplicationContext(), getString(R.string.accept_task_successfully), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpireDate.setText("");
                mContent.setText("");
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
