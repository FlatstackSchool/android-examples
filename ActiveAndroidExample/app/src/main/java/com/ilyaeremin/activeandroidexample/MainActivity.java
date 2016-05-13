package com.ilyaeremin.activeandroidexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.ilyaeremin.activeandroidexample.models.Article;
import com.ilyaeremin.activeandroidexample.models.Block;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);

        final List<Article> articles = new ArrayList<Article>();
        for (int i = 0; i < 1000; i++) {
            Article article = new Article();
            article.set_id(i);
            article.setBlocks(new ArrayList<Block>() {{
                add(new Block("some text", "url"));
                add(new Block("some text2", "url2"));
            }});
            article.setBlock(new Block("separate block", "some url"));
            articles.add(article);
        }

        findViewById(R.id.write_with_transaction).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                long before = System.currentTimeMillis();
                ActiveAndroid.beginTransaction();
                try {
                    for (Article article : articles) {
                        article.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
                long after = System.currentTimeMillis();
                info.setText("writing 1000 objects with transation complete on: " + (after - before) + " millis");
            }
        });
        findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                long before = System.currentTimeMillis();
                for (Article article : articles) {
                    article.save();
                }
                long after = System.currentTimeMillis();
                info.setText("writing 1000 objects without transaction complete on: " + (after - before) + " millis");
            }
        });
        findViewById(R.id.write_10_articles).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    articles.get(i).save();
                }
            }
        });
        findViewById(R.id.clear_db).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                long before = System.currentTimeMillis();
                new Delete().from(Article.class).execute();
                long after = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "Done with " + (after - before) + " millis", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
