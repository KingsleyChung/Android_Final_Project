package cn.kingsleychung.final_project;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.kingsleychung.final_project.Other.ListTask;
import cn.kingsleychung.final_project.User.UserManagement;
import rx.Subscriber;

/**
 * Created by weimumu on 2018-1-1.
 */

public class ReceiveTaskActivity extends Fragment {
    private View TaskView;
    private List<ListTask> resultTask;
    private List<Task> taskList;
    private RecyclerView receiveTaskView;
    private UserManagement userManagement;
    private TaskListAdapter taskListAdapter;
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
                resultTask = transform(tasks);
                taskList = tasks;
                taskListAdapter = new TaskListAdapter(resultTask);
                taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.v("debug", "click");
                        showDetailActivity(position);
                    }

                    @Override
                    public void OnLongItemClick(View view, int position) {
                        Log.v("debug", "longclick");
                    }
                });
                for(int i = 0; i < resultTask.size(); i++) {
                    final int index = i;
                    UserManagement.getInstance().getPhoto(tasks.get(i).getPhoto(), new Subscriber<Bitmap>() {
                        @Override
                        public void onCompleted() {
                            Log.d("debug", "14");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("debug", "12");
                        }

                        @Override
                        public void onNext(Bitmap bitmap) {
                            resultTask.get(index).setImage(bitmap);
                            receiveTaskView.setAdapter(taskListAdapter);
                        }
                    });
                }
            }
        });
        return TaskView;
    }

    private List<ListTask> transform(List<Task> tasks) {
        final List<ListTask> result = new ArrayList<>();
        for(int i = 0; i < tasks.size(); i++) {
            ListTask item = new ListTask(tasks.get(i).getUserName(), tasks.get(i).getTitle(), tasks.get(i).getDate(), tasks.get(i).getTaskPosName(), tasks.get(i).getAcUser(), null);
            result.add(item);
        }
        return result;
    }

    private void showDetailActivity(int index) {
        Intent intent = new Intent(getActivity(), DetailTask.class);
        Bundle bundle = new Bundle();
        Task sendTask = taskList.get(index);
        bundle.putString("Mode", "ShowDetail");
        bundle.putString("TaskTitle", sendTask.getTitle());
        bundle.putString("TaskContent", sendTask.getContent());
        bundle.putString("TaskExpire", sendTask.getDate());
        bundle.putDouble("StartLatitude", sendTask.getTaskPosLoc()[1]);
        bundle.putDouble("StartLogitude", sendTask.getTaskPosLoc()[0]);
        if (sendTask.getTgPosLoc() != null) {
            bundle.putDouble("EndLatitude", sendTask.getTgPosLoc()[1]);
            bundle.putDouble("EndLogtitude", sendTask.getTgPosLoc()[0]);
        }
        bundle.putString("AcceptUser", sendTask.getAcUser());
        bundle.putBoolean("Kind", sendTask.getKind());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
