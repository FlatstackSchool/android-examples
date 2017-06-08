# RealmExample

В данном примере показано как просто реализовать с помощью Realm книгу контактов. Функционал:
- Добавление контакта
- Изменение контакта
- Удаление контакта
- Поиск по имени
- Принадлежность группе (users, managers, tops)

#### [Realm](https://realm.io/docs/java/latest/) - это мобильная база данных и замена SQLite. В Realm не используется SQLite, он написан на C++. Realm сохраняет данные в универсальном, основанном на таблицах формате с помощью ядра C ++. Это позволяет Realm разрешать доступ к данным с любыми символами.

#### Преимущества над SQLite
- до 10 раз быстрее
- простота в использовании
- удобство для создания и хранения данных на лету
- поддержка, активное развитие

#### Добавление в проект
Добавить следующий код в `/.build.gradle`:
```groovy
apply plugin: 'realm-android'

....

buildscript {
  dependencies {
    classpath 'io.realm:realm-gradle-plugin:3.2.0'
  }
}
```

## Примеры:
### Объект для БД
```java
public class User extends RealmObject {
   private String name;
   @PrimaryKey
   private String phone;
...
}
```
### Аннотации
- @PrimaryKey - признак первичного ключа для поля
- @Required - признак обязательного заполнения поля
- @Index - признак поискового индекса для поля

### Миграция
В данном примере текущая версия БД - 4. Если у пользователя устаревшая версия приложения, то необходимо привести БД к актуальной версии. Это возможно с помощью миграции.
```java
RealmConfiguration config = new RealmConfiguration.Builder()
    .schemaVersion(4) // При изменении схемы объектов меняем на +1
    .migration(migration) // Процесс миграции
    .build()
....
RealmMigration migration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();
            // Добавляем объект User, поля name и phone (первичный ключ)
            if (oldVersion == 0) {
                schema.create("User")
                    .addField("name", String.class)
                    .addField("phone", String.class, FieldAttribute.PRIMARY_KEY);
                oldVersion++;
            }
            // Добавляем к User еще поле surname
            if (oldVersion == 1) {
                schema.get("User")
                    .addField("surname", String.class);
                oldVersion++;
            }
            // Добавляем объект Group, поля name и массив users (тип User)
            if (oldVersion == 2) {
                schema.create("Group")
                    .addField("name", String.class)
                    .addRealmListField("users", schema.get("User"));
                oldVersion++;
            }
            // Добавляем к User поле groupId.
            // Добавляем к Group поле id (первичный ключ)
            if (oldVersion == 3) {
                schema.get("User")
                    .addField("groupId", int.class);
                schema.get("Group")
                    .addField("id", int.class, FieldAttribute.PRIMARY_KEY);
            }
        }
    };
```
### Авто-обновление
Realm позволяет обновлять объекты, ссылаясь на них прямо в БД.
```java
realm.executeTransaction(new Realm.Transaction() {
    @Override public void execute(Realm realm) { 
        User user = realm.createObject(User.class); 
        user.setName("Yaroslav"); 
        user.setSurname("Sudnik"); 
    } 
});
```
### Запрос
```java
RealmResults<User> getUsers(String name) {
    return realm.where(User.class).contains("name", name).findAll();
}
```
