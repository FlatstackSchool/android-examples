package com.example.revern.ormlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.revern.ormlite.database.dao.CatDAO;
import com.example.revern.ormlite.database.dao.FoodDAO;
import com.example.revern.ormlite.models.Cat;
import com.example.revern.ormlite.models.Food;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Revern on 29.03.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "db";
    private static final int DATABASE_VERSION = 1;

    private CatDAO catDAO;
    private FoodDAO foodDAO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Cat.class);
            TableUtils.createTable(connectionSource, Food.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        List<String> allSql = new ArrayList<String>();
        switch(oldVersion)
        {
            case 1:
                //allSql.add("alter table AdData add column `new_col` VARCHAR");
                //allSql.add("alter table AdData add column `new_col2` VARCHAR");
        }
        for (String sql : allSql) {
            database.execSQL(sql);
        }
    }

    public CatDAO getCatDAO() throws SQLException {
        if (catDAO == null) {
            catDAO = new CatDAO(getConnectionSource(), Cat.class);
        }
        return catDAO;
    }

    public FoodDAO getFoodDAO() throws SQLException {
        if (foodDAO == null) {
            foodDAO = new FoodDAO(getConnectionSource(), Food.class);
        }
        return foodDAO;
    }

    //calling when close app
    @Override
    public void close() {
        super.close();
        catDAO = null;
        foodDAO = null;
    }
}
