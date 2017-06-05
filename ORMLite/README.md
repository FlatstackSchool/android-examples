### Хранение данных с помощью ORMLite
##### Или что делать, если наткнулись на легаси код

#### Что это?
ORMite - это обертка над SQLite для его более удобного использования.

[Официальная документация](http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_1.html#Getting-Started)

#### Добавлене в проект
Добавляем в `app/build.gradle`
```groovy
 compile 'com.j256.ormlite:ormlite-android:5.0'
```

#### Модели/Таблицы
Для создания таблицы, необходимо создать класс (модель) с аннотацией `@DatabaseTable(tableName = "yourTableName"), в котором необхоимо задать параметр `tableName` именем таблицы. Для создания полей, создаем переменные, которые помечаем аннотацией `@DatabaseTable()`, внутри которого можно задавать свойства поля ([полный список](http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Class-Setup)):
```java
@DatabaseTable(tableName = "cats")
 public class Cat {
     public static final String COLUMN_ID = "id";
     public static final String COLUMN_NAME = "name";

     @DatabaseField(columnName = COLUMN_ID, generatedId = true)
     private int id;

     @DatabaseField(columnName = COLUMN_NAME, canBeNull = false, unique = true)
     private String name;
 }```

#### Настройка DAO и инициализация
Создаем DAO класс путем наследования от `BaseDaoImpl<ModelClass, Integer>`, в котором и будут прописаны методы для общения с базой данных:
```java
public class CatDAO extends BaseDaoImpl<Cat, Integer> {
    public CatDAO(ConnectionSource connectionSource, Class<Cat> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
```

Создаем класс хелпер, который будет создавать/обновлять базу данных посредством вызовов статичных методов класса `TableUtils` и хранить в себе DAO, который наследуется от OrmLiteSqliteOpenHelper, который, в свою очередь, наследуется от SQLiteOpenHelper:
```java
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "db";
    private static final int DATABASE_VERSION = 1;

    private CatDAO catDAO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Cat.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        List<String> allSql = new ArrayList<String>();
        switch(oldVersion)
        {
            case 1:
                allSql.add("alter table Cats add column `new_col` VARCHAR");
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

    //calling when close app
    @Override public void close() {
        super.close();
        catDAO = null;
    }
}
```

И создаем фактори класс для хранения хелпера, использую паттерн синглтон(в принцпе, можно использовать Dagger, но в оффициальной документации рекомендуется, именно, этот способ, что не удивительно, ведь ORMLite не поддерживается с 2013 года, так что, если наткнемся на проект с этой ормкой, то, скорее всего, увидем, именно, такую реализацию:
```java
public class DbHelperFactory {
    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void setHelper(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }
}
```

И, наконец-то, сама инициализация в `Application`
```java
public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        DbHelperFactory.setHelper(this);
    }

    @Override public void onTerminate() {
        DbHelperFactory.releaseHelper();
        super.onTerminate();
    }
}
```

#### Общение с базой данных
Наконец-то, самое главное. Общение с бд происходит посредством построения билдера для запроса из DAO исполнением его:
```java
//inside DAO class
public Cat getCatByName(String name) throws SQLException {
    return queryBuilder().where().eq(Cat.COLUMN_NAME, name).queryForFirst();
}
```
Как мы видим, мы, просто, пишем почти тот же SQL запрос, на языке Java, только select пишется в конце, а не в начале методами `.query()` (возвращает `List` экземпляров класса модели) и `.queryForFist()` (возвращает первый экземпляр класса модели).

а в бизнеслогике приложение метод вызывается следующим образом, сначала инициализируем DAO:
```java
 private CatDAO catDAO;
 private void initDAOs() throws SQLException {
     catDAO = DbHelperFactory.getHelper().getCatDAO();
 }
```
после вызываем метод:
```java
Cat cat = catDAO.getCatByName(name);
```
а так же в DAO изначально существуют методы CRUD:
```java
//create
catDAO.create(cat); //add one cat
catDAO.create(catsList); //add list of cats
//read
Cat cat = catDAO.queryForId(cat_id);
List<Cat> cats = catDAO.queryForAll();
//update
catDAO.update(cat); //update by id inside cat
catDAO.update(cat, id); //replace cat with id to new cat
//destroy
catDAO.delete(cat); //delete cat
catDAO.delete(catsList); //delete list of cats
catDAO.deleteById(id); //delete cat by id
catDAO.deleteIds(idsList); //delete list of cats by id
```

#### Недостатки
Не поддерживается с 2013 года, хотя вышло одно обновление в 2016 году, но, все же, одно обновление в 3 года не есть хорошо.
#### Количество методов
```
methods_count:598
dependencies_methods_count: 2342
total_methods_count: 2940

jar_size: 71
dependencies_jar_size: 326
total_jar_size: 397
```