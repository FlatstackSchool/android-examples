### Firebase Cloud Messaging
Firebase Cloud Messaging [(FCM)](https://firebase.google.com/docs/cloud-messaging/) - это межплатформенное решение для обмена сообщениями, которое позволяет надежно доставлять сообщения без каких-либо затрат.

#### Добавление в Firebase Console
Заходим в [Firebase Console](https://console.firebase.google.com),
Созаем новый проект, жмем `Add Firebase to your Android app`.
Заполняем поле `Android package name` именем пакета своего приложения.
Так же можно заполнить (необязательно) `App nickname` и `SHA1 ключ`
Жмем `Register App`, появляется окно с предложением скачать `google-services.json` файл.
Скачиваем этот файл (либо можно будет скачать позже, просто, нажав соответсвующуюю кнопку в приложении в консоли Firebase)
Сверху имеется вкладка `CLOUD MESSAGING`, нажав на который, можно найти `Server Key` и `Legacy Server Key`(Google рекомендует использовать `Server Key`, но и `Legacy Server Key` тоже будет работать) и `Sender ID`, которые нужны для отправки нотификаций с сервера (отдать бэкендчикам).

#### Добавление в проект
Добавить следующий код в `/.build.gradle`:
```groovy
buildscript {
  dependencies {
    classpath 'com.google.gms:google-services:3.1.0'
  }
}
```

затем в `/app/build.gradle` добавим следующее:
```groovy
dependencies {
    compile 'com.google.firebase:firebase-messaging:10.2.6'
}
...
apply plugin: 'com.google.gms.google-services'
```

И копируем ранее скаченный `google-services.json` файл в директорию `/app`

#### Создаем сервисы для принятия нотификаций
Создаем класс наследуюийся от 'FirebaseInstanceIdService' и переопределяем метод `onTokenRefresh()`, который будет срабатывать каждый раз, когда будет меняться токен (здесь необходимо отправлять новый токен на сервер):
```java
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // sendRegistrationToServer(refreshedToken);
    }
}
```

Теперь создаем класс наследующийся от `FirebaseMessagingService` и переопределяем метод `onMessageReceived(RemoteMessage remoteMessage)`, который и будет принимать сообщения от сервера(здесь необходимо обработать полученныое сообщение и показать его как нотификацию):
```java
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getTitle(remoteMessage))
                .setContentText(getMessage(remoteMessage))
                .setAutoCancel(true)
                .setSound(RINGTONE_URI)
                .setVibrate(new long[]{0, 200, 200, 200})
                .setContentIntent(getContentIntent());
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    ...
}
```

И, конечно же, не забываем добавить сервисы в `AndroidManifest`:
```xml
<application
    ...
    <service android:name=".pushes.MyFirebaseInstanceIDService"
        android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
        </intent-filter>
    </service>

    <service android:name=".pushes.MyFirebaseMessagingService"
        android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>
</application>
```

#### Виды приходящих сообщений
В методе `onMessageReceived(RemoteMessage remoteMessage)` все данные о нотификации хранятся в `remoteMessage` и они могут быть двух типов:
1) Стандартная нотификации от Firebase, получаемая методом `remoteMessage.getNotification()` (такие нотификации почти не используются)
2) Мап данных (Map<String, String>) заданные сервером, получаемые методом `remoteMessage.getData()`, которые можно легко распарсить в модель спомощью `Gson`:
```java
@NonNull private NotificationModel getNotificationModel(@Nullable Map<String, String> map) {
    JsonElement json = gson.toJsonTree(map);
    return gson.fromJson(json, NotificationModel.class);
}
```

#### Получение токена
```java
String token = FirebaseInstanceId.getInstance().getToken();
```

#### Отправка нотфиикаций
Для отправки нотификаций необходимо выполнить следующий запрос с сервера (эти знания уже необходимо передавать бекэндщикам):
`https://fcm.googleapis.com/fcm/send`

headers:
```
Content-Type = application/json
Authorization = Server Key, который мы получили в консоли Firebase
```

body:
```
{
  "to": "token",
  "data": {
    "message": "hello"
   }
}
```
где token - это токен девайса.

#### Топики
[Оффициальная документация](https://firebase.google.com/docs/cloud-messaging/android/send-multiple)
Можно програмно подписать устройство на топики:
```java
FirebaseMessaging.getInstance().subscribeToTopic("cats");
```

Либо отписать от топикаметодом:
```java
FirebaseMessaging.getInstance().unsubscribeFromTopic("cats");
```
Рассылаются сообщения в топики ем же запросом, но другим телом:
```
{
  "to": "/topics/cats",
  "data": {
    "message": "hello"
   }
}
```
Или сразу нескольким топикам:
```
{
  "condition": "'dogs' in topics || 'cats' in topics",
  "data": {
    "message": "hello"
   }
}
```

#### Группы
Создаются группы следующим запросом:
`https://android.googleapis.com/gcm/notification`

headers:

```
Content-Type = application/json
Authorization = Server Key, который мы получили в консоли Firebase
project_id = Sender ID, который мы получили в консоли Firebase
```

body:
```
{
   "operation": "create",
   "notification_key_name": "Group1",
   "registration_ids": [token1, token2]
}
```
Где tokens это токены девайсов

И ответом но этот запрос получаем `notification_key` (id группы), который необходимо вставить в поле `"to"` в теле сообщения.

Для добавления новых девайсов в группу осуществляем тот же запрос со следующим телом:
```
{
   "operation": "add",
   "notification_key_name": "Group1",
   "notification_key": notification_key(который получили при создании группы),
   "registration_ids": [token3, token4]
}
```