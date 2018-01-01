package cn.kingsleychung.final_project;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        holder.usernameTexT.setText(myTask.get(position).getUserName());
        holder.titleText.setText("标题：" + myTask.get(position).getTitle());
        holder.timeText.setText("截止时间：" + myTask.get(position).getDate());
        holder.briefText.setText("目的地：" + myTask.get(position).getTgPosName());
    }

    @Override
    public int getItemCount() {
        return myTask.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView usernameTexT;
        public TextView titleText;
        public TextView timeText;
        public TextView briefText;
        public LinearLayout linearLayout;
        public MyViewHolder(View view){
            super(view);
            usernameTexT = view.findViewById(R.id.briefusername);
            titleText = view.findViewById(R.id.brieftitle);
            timeText = view.findViewById(R.id.brieftime);
            briefText = view.findViewById(R.id.brief);
            linearLayout = view.findViewById(R.id.noLine);
        }
    }
}
