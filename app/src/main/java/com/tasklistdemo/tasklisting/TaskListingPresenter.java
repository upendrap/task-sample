package com.tasklistdemo.tasklisting;

import com.tasklistdemo.models.TaskRepository;
import com.tasklistdemo.models.realmspecs.ActiveTasks;

import io.realm.Realm;

/**
 * Created by upenp on 7/23/2017.
 */

public class TaskListingPresenter implements TaskListingContract.ITaskListingPresenter {
    private TaskListingContract.ITaskListingView mView;

    public TaskListingPresenter(TaskListingContract.ITaskListingView mView) {
        this.mView = mView;
    }

    @Override
    public void loadTasks() {
        if (null != mView) mView.loading(true);

        if (null != mView) {
            mView.loading(false);
            mView.showTasks(TaskRepository.getInstance(Realm.getDefaultInstance()).query(new ActiveTasks()));
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
