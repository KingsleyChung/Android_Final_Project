package cn.kingsleychung.final_project;


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

public class SendTaskActivity extends Fragment {
    private View TaskView;
    private List<ListTask> resultTask;
    private RecyclerView sendTaskView;
    private UserManagement userManagement;
    private TaskListAdapter taskListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        TaskView =  inflater.inflate(R.layout.sendtask, container, false);
        sendTaskView = TaskView.findViewById(R.id.sendList);
        sendTaskView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        userManagement = UserManagement.getInstance();
        userManagement.getPushTask(userManagement.getUser().getUserName(), new Subscriber<List<Task>>() {
            @Override
            public void onCompleted() {
                Log.d("d", "1");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("d", "2");
                System.out.println(e);
            }

            @Override
            public void onNext(List<Task> tasks) {
                Log.d("d", "3");
                resultTask = transform(tasks);
                taskListAdapter = new TaskListAdapter(resultTask);
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
                            sendTaskView.setAdapter(taskListAdapter);
                        }
                    });
                }
            }
        });
        return TaskView;
    }

    private List<ListTask> transform(List<Task> tasks) {
        List<ListTask> result = new ArrayList<>();
        for(int i = 0; i < tasks.size(); i++) {
            ListTask item = new ListTask(tasks.get(i).getUserName(), tasks.get(i).getTitle(), tasks.get(i).getDate(), tasks.get(i).getTaskPosName(), tasks.get(i).getAcUser(), null);
            result.add(item);
        }
        return result;
    }
}
