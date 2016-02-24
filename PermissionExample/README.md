 Начиная с Android 6.0 вводится новая система разрешений. Появилась группа опасных разрешений(*Dangerous permissions*),
 использование которых нужно спрашивать во время работы приложения. 

Вот список опасных разрешений(*Dangerous permissions*) разбитые по группам:

1. CALENDAR	(READ_CALENDAR ,WRITE_CALENDAR)
2. CAMERA	(CAMERA)
3. CONTACTS (READ_CONTACTS, WRITE_CONTACTS, GET_ACCOUNTS)
4. LOCATION	(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
5. MICROPHONE (RECORD_AUDIO)
6. PHONE (READ_PHONE_STATE, CALL_PHONE, READ_CALL_LOG, WRITE_CALL_LOG, ADD_VOICEMAIL, USE_SIP, PROCESS_OUTGOING_CALLS)
7. SENSORS	(BODY_SENSORS)
8. SMS (SEND_SMS, RECEIVE_SMS, READ_SMS, RECEIVE_WAP_PUSH, RECEIVE_MMS)
9. STORAGE (READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)

Фактически **единственных необходимым**, что нужно сделать, чтобы начать использовать новую систему разрешений нужно использовать SDK версии > 23
```java
android {
    compileSdkVersion 23
    ...
 
    defaultConfig {
        ...
        targetSdkVersion 23
        ...
    }
```

![](https://github.com/fs/android-examples/blob/master/PermissionExample/assert/perm_visual.gif?raw=true)

Более подробную информаю можно прочитать [тут](http://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en)
и [документации](http://developer.android.com/intl/ru/guide/topics/security/permissions.html)
