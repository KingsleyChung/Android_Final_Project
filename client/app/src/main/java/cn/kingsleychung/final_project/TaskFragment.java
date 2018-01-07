package cn.kingsleychung.final_project;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.kingsleychung.final_project.Other.GetRecordsPagerAdapter;

/**
 * Created by Kings on 2017/12/15.
 */

public class TaskFragment extends Fragment {
    private View TaskView;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private GetRecordsPagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TaskView = inflater.inflate(R.layout.task, container, false);
        slidingTabLayout = TaskView.findViewById(R.id.tablayout);
        viewPager = TaskView.findViewById(R.id.viewpager);
        SendTaskActivity sendTaskActivity = new SendTaskActivity();
        ReceiveTaskActivity receiveTaskActivity = new ReceiveTaskActivity();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(sendTaskActivity);
        fragmentList.add(receiveTaskActivity);
        pagerAdapter = new GetRecordsPagerAdapter(this.getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        slidingTabLayout.setViewPager(viewPager, new String[]{"已发布任务", "已接受任务"});
        return TaskView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}