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

import cn.kingsleychung.final_project.User.ResponseUser;
import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

/**
 * Created by Kings on 2017/12/15.
 */

public class TaskFragment extends Fragment {
    private View TaskView;
    private List<Task> receiveTask;
    private List<Task> sendTask;
    private RecyclerView receiveTaskView;
    private RecyclerView sendTaskView;
    private UserManagement userManagement;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TaskView = inflater.inflate(R.layout.task, container, false);
        slidingTabLayout = TaskView.findViewById(R.id.tablayout);
        viewPager = TaskView.findViewById(R.id.viewpager);
        sendTaskActicity sendTaskActicity = new sendTaskActicity();
        receiveTaskActivity receiveTaskActivity = new receiveTaskActivity();
//        fragmentList.add(sendTaskActicity);
//        fragmentList.add(receiveTaskActivity);
//        init();
//        //receiveTaskView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        sendTaskView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//
//        userManagement = UserManagement.getInstance();
//        userManagement.getPushTask("1", new Subscriber<List<Task>>() {
//            @Override
//            public void onCompleted() {
//                Log.d("d", "1");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("d", "2");
//            }
//
//            @Override
//            public void onNext(List<Task> tasks) {
//                Log.d("d", "3");
//                final TaskListAdapter taskListAdapter = new TaskListAdapter(tasks);
//                sendTaskView.setAdapter(taskListAdapter);
//            }
//        });
        return TaskView;
    }

//    private void init() {
//        receiveTaskView = TaskView.findViewById(R.id.receiveList);
//        sendTaskView = TaskView.findViewById(R.id.sendList);
//    }
}