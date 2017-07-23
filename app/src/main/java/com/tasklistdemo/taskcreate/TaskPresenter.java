package com.tasklistdemo.taskcreate;

import com.tasklistdemo.models.Task;
import com.tasklistdemo.models.TaskRepository;

/**
 * Created by upenp on 7/23/2017.
 */

public class TaskPresenter implements CreateTaskContract.ICreateTaskPresenter {
    private CreateTaskContract.ICreateTaskView mView;
    private TaskRepository mRepository;

    public TaskPresenter(CreateTaskContract.ICreateTaskView mView, TaskRepository mRepository) {
        this.mView = mView;
        this.mRepository = mRepository;
    }

    @Override
    public void saveTask(Task t) {
        if (null != mView) mView.loading(true);
        if (null != mRepository) mRepository.add(t);
        if (null != mView) {
            mView.loading(false);
            mView.onTaskDone();
        }
    }

    void onDestroy() {
        mRepository = null;
        mView = null;
    }

    public void delete(Task task) {
        if (null != mView) mView.loading(true);
        if (null != mRepository) mRepository.remove(task);
        if (null != mView) {
            mView.loading(false);
            mView.onTaskDone();
        }
    }
}
