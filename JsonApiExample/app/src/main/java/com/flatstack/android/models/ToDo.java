package com.flatstack.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

/**
 * Created by ereminilya on 24/3/17.
 */
@JsonApi(type = "todo-items")
public class ToDo extends Resource implements Parcelable {

    private String title;
    private String text;
    private Status status;

    public ToDo() {
    }

    public ToDo(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setCompleted(boolean completed) {
        status = completed ? Status.COMPLETED : Status.CREATED;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    public boolean isCompleted() {
        return status == Status.COMPLETED;
    }

    protected ToDo(Parcel in) {
        this.setId(in.readString());

        this.title = in.readString();
        this.text = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : Status.values()[tmpStatus];
    }

    public static final Creator<ToDo> CREATOR = new Creator<ToDo>() {
        @Override public ToDo createFromParcel(Parcel source) {
            return new ToDo(source);
        }

        @Override public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };
}