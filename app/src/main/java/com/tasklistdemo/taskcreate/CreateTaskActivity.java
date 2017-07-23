package com.tasklistdemo.taskcreate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.tasklistdemo.R;
import com.tasklistdemo.models.Task;
import com.tasklistdemo.models.TaskRepository;

import java.util.UUID;

import io.realm.Realm;

public class CreateTaskActivity extends AppCompatActivity implements CreateTaskContract.ICreateTaskView {
    public static final String TAG = CreateTaskActivity.class.getSimpleName();
    public static final String EXTRA_TASK = "Task";

    private TaskPresenter mPresenter;
    private Task task;

    private EditText etTasks;
    private ProgressDialog progressDialog;
    private MenuItem menuItemDelete;

    public static Intent getIntent(Context src) {
        return new Intent(src, CreateTaskActivity.class);
    }

    public static Intent getIntent(Context src, Task task) {
        Intent intent = new Intent(src, CreateTaskActivity.class);
        intent.putExtra(EXTRA_TASK, task);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        etTasks = (EditText) findViewById(R.id.etTaskDetails);

        if (null != getIntent() && getIntent().hasExtra(EXTRA_TASK)) {
            task = getIntent().getParcelableExtra(EXTRA_TASK);
            etTasks.setText(task.getTaskDetails());
            getSupportActionBar().setTitle(R.string.title_task_update);
        }
        mPresenter = new TaskPresenter(this, TaskRepository.getInstance(Realm.getDefaultInstance()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_create, menu);
        menuItemDelete = menu.findItem(R.id.actionDelete);
        menuItemDelete.setVisible(null != task);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionDone) {
            String taskDetails = etTasks.getText().toString();
            if (validate(taskDetails)) {
                if (null == task)
                    task = new Task(taskDetails, UUID.randomUUID().toString());
                task.setTaskDetails(taskDetails);
                mPresenter.saveTask(task);
            } else {
                Toast.makeText(this, R.string.error_empty_task_details, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.actionDelete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.delete_task);
            builder.setMessage(R.string.are_you_sure);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.delete(task);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.setCancelable(false);
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
        super.onDestroy();
    }

    private boolean validate(String text) {
        return !TextUtils.isEmpty(text);
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
    public void onTaskDone() {
        finish();
    }

    @Override
    public void onTaskDeleted() {
        Toast.makeText(this, R.string.task_deleted, Toast.LENGTH_SHORT).show();
        onTaskDone();
    }
}

