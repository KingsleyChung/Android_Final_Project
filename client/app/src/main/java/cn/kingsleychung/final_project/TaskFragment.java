package cn.kingsleychung.final_project;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kings on 2017/12/15.
 */

public class TaskFragment extends Fragment {
    private View TaskView;
    private RecyclerView receiveTaskView;
    private RecyclerView sendTaskView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TaskView = inflater.inflate(R.layout.task, container, false);

        return TaskView;
    }

    private void init() {
        receiveTaskView = TaskView.findViewById(R.id.receiveList);
        sendTaskView = TaskView.findViewById(R.id.sendList);
    }
}