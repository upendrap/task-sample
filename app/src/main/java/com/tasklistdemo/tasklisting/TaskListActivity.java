package com.tasklistdemo.tasklisting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.tasklistdemo.R;
import com.tasklistdemo.SyncService;
import com.tasklistdemo.models.Task;
import com.tasklistdemo.taskcreate.CreateTaskActivity;
import com.tasklistdemo.utils.RecyclerViewItemClickListener;

import java.util.List;

/**
 * this will show the user list of all the tasks which are not deleted.
 * A sync operation will be tried every 30 seconds if internet connection is available.
 */
public class TaskListActivity extends AppCompatActivity implements RecyclerViewItemClickListener<Task>, TaskListingContract.ITaskListingView {
    public static final String TAG = TaskListActivity.class.getSimpleName();

    private TaskListingContract.ITaskListingPresenter mPresenter;
    private RecyclerView rvTasks;
    private ProgressDialog progressDialog;

    private FirebaseJobDispatcher dispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvTasks = (RecyclerView) findViewById(R.id.rvTasks);
        setSupportActionBar(toolbar);
        rvTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTasks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskListActivity.this, CreateTaskActivity.class));
            }
        });
        mPresenter = new TaskListingPresenter(this);
    }

    private void scheduleSync() {
        //will try sync every 30 seconds
        Job.Builder sync = dispatcher.newJobBuilder()
                .setService(SyncService.class)
                .setLifetime(Lifetime.FOREVER)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(0, 30))
                .setTag(TaskListActivity.TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK);
        dispatcher.mustSchedule(sync.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        scheduleSync();
        mPresenter.loadTasks();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void loading(boolean showOrHide) {
        if (null == progressDialog) {
            progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait), true, false);
        }
        if (showOrHide) progressDialog.show();
        else progressDialog.dismiss();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        rvTasks.setAdapter(new TaskRecyclerViewAdapter(tasks, this));
    }

    @Override
    public void onItemClick(Task data, int position) {
        startActivity(CreateTaskActivity.getIntent(this, data));
    }
}

