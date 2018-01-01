package cn.kingsleychung.final_project;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.kingsleychung.final_project.Other.GetRecordsPagerAdapter;
import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

/**
 * Created by Kings on 2017/12/15.
 */

public class TaskFragment extends Fragment {
    private View TaskView;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private GetRecordsPagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TaskView = inflater.inflate(R.layout.task, container, false);
        slidingTabLayout = TaskView.findViewById(R.id.tablayout);
        viewPager = TaskView.findViewById(R.id.viewpager);
        sendTaskActicity sendTaskActicity = new sendTaskActicity();
        receiveTaskActivity receiveTaskActivity = new receiveTaskActivity();
        fragmentList.add(sendTaskActicity);
        fragmentList.add(receiveTaskActivity);
        pagerAdapter = new GetRecordsPagerAdapter(this.getFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        slidingTabLayout.setViewPager(viewPager, new String[]{"已发布任务", "已接受任务"});
        return TaskView;
    }
}