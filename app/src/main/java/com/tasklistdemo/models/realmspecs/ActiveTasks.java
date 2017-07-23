package com.tasklistdemo.models.realmspecs;

import com.tasklistdemo.models.RealmSpecs;
import com.tasklistdemo.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * This query will return all the tasks which are not deleted
 */
public class ActiveTasks implements RealmSpecs<Task> {

    @Override
    public RealmResults<Task> query(Realm realm) {
        return realm.where(Task.class).equalTo("isDeleted", false).findAll();
    }
}
