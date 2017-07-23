package com.tasklistdemo.tasklisting;

import com.tasklistdemo.models.Task;

import java.util.List;

public interface TaskListingContract {
    public interface ITaskListingView {
        void loading(boolean showOrHide);

        void showTasks(List<Task> tasks);
    }

    public interface ITaskListingPresenter {
        void loadTasks();

        void onDestroy();
    }
}
