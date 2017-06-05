package com.example.revern.ormlite.database.dao;

import com.example.revern.ormlite.models.Cat;
import com.example.revern.ormlite.models.Food;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Revern on 30.03.2017.
 */

public class FoodDAO extends BaseDaoImpl<Food, Integer> {
    public FoodDAO(ConnectionSource connectionSource, Class<Food> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insertListForCat(List<Food> foodList, Cat cat) throws SQLException {
        for(Food food : foodList){
            food.setCat(cat);
            create(food);
        }
    }

    public Food getFoodByName(String name) throws SQLException {
        return queryBuilder().where().eq(Food.COLUMN_NAME, name).queryForFirst();
    }
}
