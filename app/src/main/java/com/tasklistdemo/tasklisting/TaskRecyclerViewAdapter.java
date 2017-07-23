package com.tasklistdemo.tasklisting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tasklistdemo.R;
import com.tasklistdemo.models.Task;
import com.tasklistdemo.utils.RecyclerViewItemClickListener;

import java.text.DateFormat;
import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {
    private List<Task> mTasks;
    private RecyclerViewItemClickListener mListener;

    public TaskRecyclerViewAdapter(List<Task> mTasks, RecyclerViewItemClickListener listener) {
        this.mTasks = mTasks;
        this.mListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_task, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bindData(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvDate, tvDetails;
        private View viewStatus;
        private RecyclerViewItemClickListener<Task> mListener;
        private Task mTask;

        public TaskViewHolder(View itemView, RecyclerViewItemClickListener listener) {
            this(itemView);
            mListener = listener;
        }

        public TaskViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvDate = (TextView) itemView.findViewById(R.id.tvTaskDate);
            tvDetails = (TextView) itemView.findViewById(R.id.tvTaskDetails);
        }

        public void bindData(Task task) {
            mTask = task;
            tvDate.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(task.getCreatedTime()));
            tvDetails.setText(task.getTaskDetails());
        }

        @Override
        public void onClick(View v) {
            if (null != mListener) mListener.onItemClick(mTask, getAdapterPosition());
        }
    }
}
