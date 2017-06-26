package com.example.revern.ormlite.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Revern on 29.03.2017.
 */

@DatabaseTable(tableName = "cats")
public class Cat {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BIRDTHDAY = "birthday";
    public static final String COLUMN_FAVORITE_FOOD = "favorite_food";
    public static final String COLUMN_EATING_FOOD = "eating_food";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME, canBeNull = false, unique = true)
    private String name;

    @DatabaseField(columnName = COLUMN_BIRDTHDAY, dataType = DataType.DATE)
    private Date birthday;

    @DatabaseField(columnName = COLUMN_FAVORITE_FOOD, foreign = true, foreignAutoRefresh = true)
    private Food favoriteFood;

    @ForeignCollectionField(columnName = COLUMN_EATING_FOOD, eager = true)
    private ForeignCollection<Food> eatingFood;

    public Cat() {
    }

    public Cat(String name, Date birthDate, Food favoriteFood) {
        this.name = name;
        this.birthday = birthDate;
        this.favoriteFood = favoriteFood;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Food getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(Food favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public Collection<Food> getEatingFood() {
        return eatingFood;
    }

    public List<Food> getEatingFoodList() {
        List<Food> list = new ArrayList<>();
        if (eatingFood != null) {
            Iterator<Food> iterator = eatingFood.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        }
        return list;
    }

    public void setEatingFood(ForeignCollection<Food> eatingFood) {
        this.eatingFood = eatingFood;
    }

    @Override
    public String toString() {
        return "id = " + id
                + ", name is " + name
                + ", born in " + birthday.toString()
                + ", favorite food is " + favoriteFood
                + ", eating food: " + getEatingFoodList().toString()
                ;
    }
}
