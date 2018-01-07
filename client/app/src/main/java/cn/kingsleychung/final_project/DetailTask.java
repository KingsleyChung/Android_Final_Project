package cn.kingsleychung.final_project;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.kingsleychung.final_project.Other.GetRecordsPagerAdapter;
import cn.kingsleychung.final_project.User.UserClass;
import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

/**
 * Created by kingsley on 3/1/2018.
 */

public class DetailTask extends FragmentActivity {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private GetRecordsPagerAdapter pagerAdapter;
    private TextView mTitle, mExpireDate;
    private EditText mContent;
    private ImageView mSubmit;
    private Calendar mCalendar;
    private MapView mMapView;
    private AMap mAMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_task);

        initView();
        initStatus();
        initTabView();
        initDatePicker();
        initClickListener();
    }

    private void initView() {
        mTitle = findViewById(R.id.detail_task_title);
        mContent = findViewById(R.id.detail_content);
        slidingTabLayout = findViewById(R.id.detail_tab_layout);
        viewPager = findViewById(R.id.detail_view_pager);
        mExpireDate = findViewById(R.id.detail_expire_date);
        mSubmit = findViewById(R.id.detail_submit);
    }

    private void initStatus() {
        Bundle bundle = getIntent().getExtras();
        mTitle.setText(bundle.getString("TaskTitle"));
        mContent.setText(bundle.getString("TaskContent"));
    }

    private void initTabView() {
        fragmentList.add(new PublicTaskInfoFragment());
        fragmentList.add(new DirectionalTaskInfoFragment());
        pagerAdapter = new GetRecordsPagerAdapter(this.getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        slidingTabLayout.setViewPager(viewPager, new String[]{"非指定", "指定"});
    }

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
                Task tempTask = new Task(UserManagement.getInstance().getUser().getUserName(),
                        UserManagement.getInstance().getUser().getUserId(),
                        mTitle.getText().toString(),
                        mContent.getText().toString(),
                        "-1", "-1",
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
    }


}
