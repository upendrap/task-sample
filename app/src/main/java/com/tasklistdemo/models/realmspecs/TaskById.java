package com.tasklistdemo.models.realmspecs;

import com.tasklistdemo.models.RealmSpecs;
import com.tasklistdemo.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * This query will return task for a particular id
 */
public class TaskById implements RealmSpecs<Task> {
    public final String id;

    public TaskById(String id) {
        this.id = id;
    }

    @Override
    public RealmResults<Task> query(Realm realm) {
        return realm.where(Task.class).equalTo("taskId", id).findAll();
    }
}
