package cn.kingsleychung.final_project;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by weimumu on 2017-12-31.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder>{
    private List<Task> myTask;

    public TaskListAdapter(List<Task> myTask) {
        this.myTask = myTask;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskbriefitem,parent,false);
        MyViewHolder vh  = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return myTask.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView username;
        public TextView title;
        public TextView time;
        public TextView brief;
        public MyViewHolder(View view){
            super(view);
        }
    }
}
