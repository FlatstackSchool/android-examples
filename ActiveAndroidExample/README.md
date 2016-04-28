## Храним данные легко и весело с Active Android
###### Временами не оч весело

#### Причины
ActiveAndroid работает на sqlite. Он дает удобную оболочку для работы с базой.
Это подходит для приложений не требующих сложных запросов и максимальной скорости - то есть для большинства.
Также стоит обратить внимание на Realm, SQLBrite (еще одна обертка над sqlite) и sqlite.


Это описание является выжимкой из [официального гайда](https://github.com/pardom/ActiveAndroid/wiki/Getting-started).
И результатом моего опыта работы с ActiveAndroid

#### Требования

Добавляем

```groovy
repositories {
     mavenCentral()
     maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
 }

 compile 'com.michaelpardo:activeandroid:3.1.0-SNAPSHOT'
```

И перед использованием инициализируем (обычно в `Application.onCreate()`)

```java
Configuration.Builder configBuilder = new Configuration.Builder(app);
configBuilder.setDatabaseName("example.db");
configBuilder.setDatabaseVersion(1);
ActiveAndroid.initialize(configBuilder.create());
```

Добавить

###### При обычной инициализации при старте ищутся классы, 
которые наследуются от `com.activeandroid.Model` 
и `com.activeandroid.TypeSerializer` - это не самая быстрая операция. Поэтому есть другой способ инициализации - напрямую указать какие классы надо добавить.


```java
Configuration.Builder configBuilder = new Configuration.Builder(app);
configBuilder.setDatabaseName("example.db");
configBuilder.setDatabaseVersion(1);
configBuilder.addModelClasses(Article.class, ArticleDetails.class);
configBuilder.addTypeSerializers(ImageSerializer.class, SharingBarsSerializer.class, SharingSerializer.class);
ActiveAndroid.initialize(configBuilder.create());

```

###### TypeSerializer
Из коробки ActiveAndroid умеет записывать в базу все примитивы и String.
Не умеет работать с массивами. Например, для `int[]` необходимо писать свой сериалайзер.
Также, если у тебя есть объект, в котором есть вложенные объекты, которые не наследуются от `Model`,
то тебе надо писать для них Serializer. Вкратце, надо написать правила для сериализации и десериализации.
Хранить данные можно в любых примитивных форматах или в String. В этом проекте есть несколько примеров `TypeSerializer`.


###### Предостережения
Если объекты, которые наследуются от `Model` также сериализуются в json, то для Gson'а надо задать правило, не сериализовать 'final' поля.
При работе с gson все равно нельзя иметь final поля в своих объектах. В целом, это оборачивается головной болью использовать два способа хранения.

###### Тесты
При использовании в тестах объектов с `extends Model` может возникнуть ошибка ХХХ которая говорит, что AA не инициализирован.
В этом проекте обработан этот случай. См класс:

###### И наконец, как это использовать.
Нужные классы наследуем от `Model` и добавляем к каждому аннотацию `@Table(name = "table name")`
Далее помечаем каждое необходимое поле аннотацией `@Column`. _Важно!_ Не иметь полей с именем `id`, такое поле уже есть внутри `Model`.
Если хочешь использовать свой id, то помечай поле `@Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)`
Внутри `@Column` вообще много всяких настроек, мне пригождались то, что я тут описываю.
```java
@Table(name = "favorites")
public class Article extends Model {
    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE) long _id;
    @Column String title;
    String imageUrl;
}
```

######Запись в базу
1 объект
```java
Article article = new Article();
article.set_id(123);
article.save();
```

Для записи списков и массивов используй транзакции для ускорения:
```java
List<Article> articles = getArticles();
ActiveAndroid.beginTransaction();
try {
    for (Article article : articles) {
        article.save();
    }
    ActiveAndroid.setTransactionSuccessful();
} finally {
    ActiveAndroid.endTransaction();
}
```
######Получение данных:
```java
List<Article> = new Select().from(Article.class).execute();
```
Можно сделать разные сложные выборки. По сути, все что доступно для sqlite доступно и через AA.
######Удаление
```java
// 1 объект
article.delete();
// очистить всю таблицу
new Delete().from(Article.class).execute();
```

###### [Миграция](https://github.com/pardom/ActiveAndroid/wiki/Schema-migrations)
Тут придется испачкать руки в sql запросах :)