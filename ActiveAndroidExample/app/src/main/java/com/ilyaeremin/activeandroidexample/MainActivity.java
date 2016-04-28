package com.ilyaeremin.activeandroidexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.ilyaeremin.activeandroidexample.models.Article;

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
        for (int i = 0; i < 10000; i++) {
            Article article = new Article();
            article.set_id(i);
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
                info.setText("writing complete onL: " + (after - before) + "seconds");
            }
        });
    }
}
