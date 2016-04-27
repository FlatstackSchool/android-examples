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

`ActiveAndroid.initialize(context);`

###### При обычной инициализации при старте ищутся классы, 
которые наследуются от `com.activeandroid.Model` 
и `com.activeandroid.TypeSerializer` - это не самая быстрая операция. Поэтому есть другой способ инициализации - напрямую указать какие классы надо добавить.


```java
Configuration.Builder configBuilder = new Configuration.Builder(app);
configBuilder.addModelClasses(Article.class, ArticleDetails.class);
configBuilder.addTypeSerializers(ImageSerializer.class, SharingBarsSerializer.class, SharingSerializer.class);
ActiveAndroid.initialize(configBuilder.create());

```

###### TypeSerializer
Из коробки ActiveAndroid умеет записывать в базу: все примитивы и String. 
Не умеет работать с массивами. Например, для `int[]` необходимо писать свой сериалайзер.


