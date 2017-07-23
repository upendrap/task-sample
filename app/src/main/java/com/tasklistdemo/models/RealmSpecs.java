package com.tasklistdemo.models;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by upenp on 7/23/2017.
 * Realm specific base interface for querying
 */

public interface RealmSpecs<T extends RealmModel> extends QuerySpecs {
    public RealmResults<T> query(Realm realm);
}

