package com.example.revern.ormlite.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Revern on 29.03.2017.
 */

@DatabaseTable(tableName = "food")
public class Food {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CAT = "cat";

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(canBeNull = false, unique = true)
    String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Cat cat;

    public Food() {
    }

    public Food(String name) {
        this.name = name;
    }

    public Food(String name, Cat cat) {
        this.name = name;
        this.cat = cat;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return name;
    }
}
