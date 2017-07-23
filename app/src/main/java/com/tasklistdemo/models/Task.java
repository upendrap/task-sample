package com.tasklistdemo.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by upenp on 7/23/2017.
 */

public class Task extends RealmObject implements Parcelable {
    //defaults to uuid if created offline
    private String localId;
    @PrimaryKey
    //will be generated on server
    private String taskId;
    private String taskDetails;
    private Date createdTime;
    private boolean isSynched;
    private boolean isDeleted;

    public Task() {
    }

    public String getLocalId() {
        return localId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public boolean isSynched() {
        return isSynched;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Task(String taskDetails, String id) {
        this.taskDetails = taskDetails;
        localId = id;
        taskId = id;
        isSynched = false;
        isDeleted = false;
        createdTime = new Date();
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    void setSynched(boolean isSynched) {
        this.isSynched = isSynched;
    }

    protected Task(Parcel in) {
        localId = in.readString();
        taskId = in.readString();
        taskDetails = in.readString();
        long tmpCreatedTime = in.readLong();
        createdTime = tmpCreatedTime != -1 ? new Date(tmpCreatedTime) : null;
        isSynched = in.readByte() != 0x00;
        isDeleted = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localId);
        dest.writeString(taskId);
        dest.writeString(taskDetails);
        dest.writeLong(createdTime != null ? createdTime.getTime() : -1L);
        dest.writeByte((byte) (isSynched ? 0x01 : 0x00));
        dest.writeByte((byte) (isDeleted ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }
}
