package com.ilyaeremin.activeandroidexample;

import com.activeandroid.Cache;
import com.activeandroid.query.Select;
import com.ilyaeremin.activeandroidexample.models.Article;
import com.ilyaeremin.activeandroidexample.models.Block;

import java.util.List;

/**
 * Created by Ilya Eremin on 15.05.2016.
 */
public class DbHelper {
    public static void saveArticle(Article article) {
        for (Block block : article.getBlocks()) {
            block.save();
        }
        article.save();
    }

    public static Article getArticleById(long id) {
        Article article = new Select().from(Article.class).where("_id=?", id).executeSingle();
        List<Block> blocks = new Select()
            .from(Block.class)
            .where(Cache.getTableName(Block.class) + "." + Block.FOREIGN_KEY + "=?", article.get_id()).execute();
        article.setBlocks(blocks);
        return article;
    }
}
