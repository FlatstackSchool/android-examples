package com.ilyaeremin.activeandroidexample.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Ilya Eremin on 5/13/16.
 */
@Table(name = "blocks")
public class Block extends Model {

    @Column private long articleId;

    @Column private String text;
    @Column private String imageUrl;

    public Block() {
        super();
    }

    public Block(String text, String imageUrl) {
        super();
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public List<Article> items(){
        return getMany(Article.class, "block");
    }
}
