package com.tasklistdemo.models.realmspecs;

import com.tasklistdemo.models.RealmSpecs;
import com.tasklistdemo.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * This query will return all the unsynched tasks
 */
public class UnsynchedTasks implements RealmSpecs<Task> {

    @Override
    public RealmResults<Task> query(Realm realm) {
        return realm.where(Task.class).equalTo("isSynched", false).findAll();
    }
}
