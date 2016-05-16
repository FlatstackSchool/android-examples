package com.ilyaeremin.activeandroidexample.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ilya Eremin on 5/13/16.
 */
@Table(name = "blocks")
public class Block extends Model {

    public static final String FOREIGN_KEY = "articleId";

    @Column private String text;
    @Column private String imageUrl;

    @Column(name = FOREIGN_KEY, onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public long articleId;

    public Block() {
        super();
    }

    public Block(long articleId, String text, String imageUrl) {
        super();
        this.articleId = articleId;
        this.text = text;
        this.imageUrl = imageUrl;
    }
}
