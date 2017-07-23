package com.tasklistdemo.models;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by upenp on 7/23/2017.
 * task repository implementation
 */

public class TaskRepository implements Repository<Task, RealmSpecs> {
    private Realm realm;

    private static TaskRepository INSTANCE;

    private TaskRepository(Realm realm) {
        this.realm = realm;
    }

    public static TaskRepository getInstance(Realm realm) {
        if (null == INSTANCE) {
            INSTANCE = new TaskRepository(realm);
        }
        return INSTANCE;
    }

    @Override
    public void add(final Task data) {
        data.setSynched(false);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @Override
    public void add(final List<Task> data) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @Override
    public List<Task> query(RealmSpecs specs) {
        return specs.query(realm);
    }

    @Override
    public void update(final Task data) {
        data.setSynched(false);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @Override
    public void remove(final Task data) {
        //marking as deleted as this data will also need to be deleted from the server
        data.setDeleted(true);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });

    }

    @Override
    public void remove(RealmSpecs specs) {
        RealmResults results = specs.query(realm);
        Task t;
        for (int i = 0; i < results.size(); i++) {
            t = (Task) results.get(i);
            t.setDeleted(true);
            update(t);
        }

    }
}
