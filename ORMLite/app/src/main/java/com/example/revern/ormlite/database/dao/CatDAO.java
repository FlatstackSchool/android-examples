package com.example.revern.ormlite.database.dao;

import com.example.revern.ormlite.models.Cat;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by Revern on 30.03.2017.
 */

public class CatDAO extends BaseDaoImpl<Cat, Integer> {
    public CatDAO(ConnectionSource connectionSource, Class<Cat> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Cat getCatByName(String name) throws SQLException {
        return queryBuilder().where().eq(Cat.COLUMN_NAME, name).queryForFirst();
    }
}
