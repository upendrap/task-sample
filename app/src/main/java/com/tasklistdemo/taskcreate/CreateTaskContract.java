package com.tasklistdemo.taskcreate;

import com.tasklistdemo.models.Task;

public interface CreateTaskContract {
    public interface ICreateTaskView {
        void loading(boolean showOrHide);

        void onTaskDone();

        void onTaskDeleted();
    }

    public interface ICreateTaskPresenter {
        void saveTask(Task t);
    }
}
