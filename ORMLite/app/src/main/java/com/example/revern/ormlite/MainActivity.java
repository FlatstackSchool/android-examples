package com.example.revern.ormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.revern.ormlite.database.DbHelperFactory;
import com.example.revern.ormlite.database.dao.CatDAO;
import com.example.revern.ormlite.database.dao.FoodDAO;
import com.example.revern.ormlite.models.Cat;
import com.example.revern.ormlite.models.Food;
import com.example.revern.ormlite.utils.StringUtils;

import java.sql.SQLException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.cat_name) EditText uiCatName;
    @Bind(R.id.fav_food_id) EditText uiFoodId;
    @Bind(R.id.food) EditText uiFoodName;
    @Bind(R.id.cat_id) EditText uiCatId;

    private FoodDAO foodDAO;
    private CatDAO catDAO;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try {
            initDAOs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initDAOs() throws SQLException {
        foodDAO = DbHelperFactory.getHelper().getFoodDAO();
        catDAO = DbHelperFactory.getHelper().getCatDAO();
    }

    public void onInsertCatClick(View view) throws SQLException {
        String name = uiCatName.getText().toString();
        int food_id = Integer.valueOf(uiFoodId.getText().toString());
        if (StringUtils.isEmpty(name)) return;

        Food favorite_food = foodDAO.queryForId(food_id);

        Cat cat = new Cat(name, new Date(), favorite_food);
        catDAO.create(cat);

        Toast.makeText(this, cat.toString() + " CREATED!", Toast.LENGTH_LONG).show();
    }

    public void onQueryCatClick(View view) throws SQLException {
        String name = uiCatName.getText().toString();
        if (StringUtils.isEmpty(name)) return;

        Cat cat = catDAO.getCatByName(name);

        Toast.makeText(this, cat.toString() + " QUERY!", Toast.LENGTH_LONG).show();
    }

    public void onDeleteCatClick(View view) throws SQLException {
        String name = uiCatName.getText().toString();
        if (StringUtils.isEmpty(name)) return;

        Cat cat = catDAO.getCatByName(name);
        catDAO.delete(cat);

        Toast.makeText(this, name + " DELETED!", Toast.LENGTH_LONG).show();
    }

    public void onInsertFoodClick(View view) throws SQLException {
        String name = uiFoodName.getText().toString();
        int cat_id = Integer.valueOf(uiFoodId.getText().toString());
        if (StringUtils.isEmpty(name)) return;

        Cat cat = catDAO.queryForId(cat_id);
        Food food = new Food(name, cat);
        foodDAO.create(food);

        Toast.makeText(this, food.toString() + " CREATED!", Toast.LENGTH_LONG).show();
    }

    public void onQueryFoodClick(View view) throws SQLException {
        String name = uiFoodName.getText().toString();
        if (StringUtils.isEmpty(name)) return;

        Food food = foodDAO.getFoodByName(name);
        Toast.makeText(this, food.toString() + " QUERY!", Toast.LENGTH_LONG).show();
    }

    public void onDeleteFoodClick(View view) throws SQLException {
        String id = uiFoodName.getText().toString();
        if (StringUtils.isEmpty(id)) return;

        foodDAO.deleteById((Integer.parseInt(id)));

        Toast.makeText(this, id + " DELETED!", Toast.LENGTH_LONG).show();
    }
}
