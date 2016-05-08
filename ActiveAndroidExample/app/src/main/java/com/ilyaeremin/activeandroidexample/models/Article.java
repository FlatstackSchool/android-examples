package com.ilyaeremin.activeandroidexample.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ilya Eremin on 27.04.2016.
 */
@Table(name = "favorites")
public class Article extends Model {
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    long _id;
    @Column String       name;
    @Column String       imageUrl;
    @Column SharingLinks links;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
