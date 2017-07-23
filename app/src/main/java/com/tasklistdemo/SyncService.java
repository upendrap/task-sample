package com.tasklistdemo;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.tasklistdemo.models.Task;
import com.tasklistdemo.models.TaskRepository;
import com.tasklistdemo.models.realmspecs.UnsynchedTasks;

import java.util.List;

import io.realm.Realm;

public class SyncService extends JobService {
    public SyncService() {
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        //we can sync data here
        List<Task> unsynchedTasks = TaskRepository.getInstance(Realm.getDefaultInstance()).query(new UnsynchedTasks());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
