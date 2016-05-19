package com.ilyaeremin.activeandroidexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.ilyaeremin.activeandroidexample.models.Block;

import java.util.List;

/**
 * Created by Ilya Eremin on 5/19/16.
 */

public class AAOddityScreen extends AppCompatActivity {

    private Block block;
    private Toast toast;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_aa_oddity);

        final TextView dbContentTv = (TextView) findViewById(R.id.content);

        findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                block = new Block(123, "blockText", "https://funni-images.com/haha.jpg");
                showToast("New Object Instantiated");
            }
        });

        findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (block == null) {
                    showToast("You must instansiate object first");
                } else {
                    showToast("Object saved with id: " + block.save());
                }
            }
        });

        findViewById(R.id.clean_db).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                new Delete().from(Block.class).execute();
                // even if you call block.delete() it will not gonna work properly
                showToast("Block tables cleared");
            }
        });

        findViewById(R.id.show_db_content).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                List<Model> blocks = new Select().from(Block.class).execute();
                dbContentTv.setText(Deps.getInstance().getMapper().toJson(blocks));
            }
        });

    }

    private void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(AAOddityScreen.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }


}
