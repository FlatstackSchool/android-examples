## Храним данные легко и весело с Active Android
###### Временами не оч весело

#### Причины
ActiveAndroid (далее AA) дает удобную оболочку над sqlite.
Он подходит для приложений не требующих сложных запросов и очень быстрых ответов - то есть для большинства.
См также: стоит обратить внимание на [Realm](https://realm.io/docs/java/latest/),
[SQLBrite](https://github.com/square/sqlbrite) (тоже обертка над sqlite от square && JackWharton),
[GreenDAO](http://greenrobot.org/greendao/) (от тех же ребят что сделали известным всем ивент бас)
и sqlite.

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
configBuilder.setDatabaseName("your_db_name.db");
configBuilder.setDatabaseVersion(1);
ActiveAndroid.initialize(configBuilder.create());
```

###### При такой инициализации при старте ищутся классы,
которые наследуются от `com.activeandroid.Model` 
и `com.activeandroid.TypeSerializer` - это не самая быстрая операция и это здорово увеличивает cold startup time (до нескольких секунд с проектом в ~100 классов).
Чтобы не делать этого, напрямую указываем какие классы необходимы для работы БД.

```java
Configuration.Builder configBuilder = new Configuration.Builder(app);
configBuilder.setDatabaseName("example.db");
configBuilder.setDatabaseVersion(1);
<b>configBuilder.addModelClasses(Article.class, Block.class);
configBuilder.addTypeSerializers(SharingLinksSerializer.class, StringArraySerializer.class, ListSerializer.class);</b>
ActiveAndroid.initialize(configBuilder.create());
```
[Эту инфу можно также указывать в `AndroidManifest.xml`](https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model#speeding-up-application-startup)

###### TypeSerializer
Из коробки ActiveAndroid умеет записывать в базу все примитивы и String.
Не умеет работать с массивами. Например, для `int[]` необходимо писать свой сериалайзер.
То есть, если у тебя есть объект с любыми полями отличными от них и которые не наследуются от `Model`, то тебе надо писать для них Serializer.
Хранить данные можно в любых примитивных форматах или в String. См примеры в `/serializers`.

###### Поля, которые наследуются от `Model`
На них для записи необходимо явно вызывать `save()` (см пример `DbHelper`)
При чтении нужно самостоятельно сделать выборку (см пример `DbHelper`) и записать поля в объект.

###### Парсинг в json
`extends Model` дает дополнительные поля, которые могут сломать парсинг из\в json.
Для Gson'а надо задать правило, не сериализовать\десериализовать 'final' поля.
При работе с gson все равно нельзя иметь final поля в своих объектах, если только ты не используешь кастомный парсер.

###### Тесты
При юнит тестировании с использованием объектов, которые `extends Model` возникнет ошибка `NPE at com.activeandroid.Cache.getTableInfo(Cache.java:148)` из-за того, что AA не инициализирован.
В этом проекте приведено решение на основе Robolectirc, см пакет test. Особое внимание обрати на test/resources - туда вынесены параметры, которые бы иначе пришлось добавлять к каждому классу в виде
`@Config(...)` и на использование именно `RobolectricGradleTestRunner`, а не обычного `RobolectricTestRunner`.

###### И наконец, как это использовать.
Нужные классы наследуем от `Model` и добавляем к каждому аннотацию `@Table(name = "table name")`
Далее помечаем каждое необходимые для записи поля аннотацией `@Column`. _Важно!_ Не иметь полей с именем `id`, такое поле уже есть внутри `Model`.
Если хочешь поле как уникальный идентификатор объекта, то помечай его `@Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)`
Для аннотации `@Column` можно задать множество настроек. Например, `@Column(name = some_name) long someName` для смены имени столбца.
Если не указать имя, то оно будет таким же, к

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

Для записи большого количества объектов используй транзакции (ускорение до 10 раз, запусти пример, проверь):
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

######Ограничения
Данная библиотека имеет дикий недостаток: она не умеет из коробки работать с полями типа `List`
Почему это недостаток? Можно написать кастомный сериалайзер для листов,
но чтобы написать сериалайзер надо указать в одном из методов тип сериализуемого объекта,
а из-за java type erasure мы этого сделать не можем (нельзя получить класс от `List<SomeType>`)
Возможные выходы:
1. Хранить коллекции объектов в виде массивов и писать для каждого типа массивов сериалайзер (см. `StringArraySerializer`)
(по сути здесь ничего страшного, но могут возникнуть непредвиденные проблемы позже).
2. Написать сериалайзер для списков, с сохранением типов (учти, чтобы все не испортилось при переименовании объектов).
Вывод - оба этих решения не очень и заставляют отказаться от использования ActiveAndroid, потому что `List<T>` - частый случай
(см `ListSerializer`).
3. Если это `List<? extends Model>` то для сохранения такого поля надо можно написать хелпер (см `DbHelper`),
который бы вызывал `save()` на каждом элементе.

###### [Миграция](https://github.com/pardom/ActiveAndroid/wiki/Schema-migrations)
Тут придется испачкать руки в sql запросах :)

###### Рекомендации
Не стоит иметь поля, которые `extends Model` - объекты лучше просто сериализовать,
сложные базы по всем правилам и со всеми отношениями редко требуются в мобильном приложении.

## TODO
* Подробней рассмотреть вариант `A extends Model`, который содержит `B extends Model` - что происходит на самом деле?