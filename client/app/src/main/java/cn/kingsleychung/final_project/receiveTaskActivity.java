package cn.kingsleychung.final_project;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

/**
 * Created by weimumu on 2018-1-1.
 */

public class receiveTaskActivity extends Fragment {
    private View TaskView;
    private List<Task> receiveTask;
    private RecyclerView receiveTaskView;
    private UserManagement userManagement;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        TaskView = inflater.inflate(R.layout.receivetask, container, false);
        receiveTaskView = TaskView.findViewById(R.id.receiveList);
        receiveTaskView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        userManagement = UserManagement.getInstance();
        userManagement.getacTask(userManagement.getUser().getUserName(), new Subscriber<List<Task>>() {
            @Override
            public void onCompleted() {
                Log.d("d", "4");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("d", "5");
            }

            @Override
            public void onNext(List<Task> tasks) {
                Log.d("d", "6");
                final TaskListAdapter taskListAdapter = new TaskListAdapter(tasks);
                receiveTaskView.setAdapter(taskListAdapter);
            }
        });
        return TaskView;
    }
}
