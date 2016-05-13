package com.ilyaeremin.activeandroidexample.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Ilya Eremin on 27.04.2016.
 */
@Table(name = "favorites")
public class Article extends Model {
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    long _id;
    @Column                 String       name;
    @Column                 String       imageUrl;
    @Column                 SharingLinks links;
    @Column                 List<Block>  blocks;
    @Column(name = "block") Block        block;

    public void setBlock(Block block) {
        this.block = block;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
