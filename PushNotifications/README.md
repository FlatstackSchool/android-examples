# Пример push notifications

## Настройка
#### [Официальный путь](https://developers.google.com/cloud-messaging/android/client) - просто пошагово выполнять.
#### Если в официальном пути возникают трудности:
1. Идем на https://console.developers.google.com/apis/ логинимся под нужной почтой.
2. Создаем проект
3. В левом табе выбираем Обзор -> Mobile API -> Google Cloud Messaging включить
4. Учетные данные -> Создать учетные данные -> Ключ API -> Ключ для Android. Вводим название. Снизу жмем "Добавить ресурс "название пакета и контрольная сумма"". Создать.
5. Появится диалог с каким-то ключем, он нам не нужен, просто ОК.
6. Жмем в правом верхнем углу на иконку с тремя вертикальными точками -> Сведения о проекте. Копируем номер проекта, он нужен для нашего android проекта.
7. Учетные данные -> Создать учетные данные -> Ключ API -> Ключ для сервер. Вводим любое название. Создать. Полученные ключ сохраняем и отдаем server guys.

![instruction](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/steps.gif)   

#### Ограничения
Пуши не будут приходить на устройства без google play services. Это не проблема, если ты распространяешь приложение только в google play.
Для amazon также есть своя альтернатива GCM пушам (тут это не рассматривается).
Можно написать свой push сервер, но это не самая простая задача (хотя telegram, whatsapp, facebook это сделали, но где они, а где ты?)
Все альтернативы построены на использовании под капотом gcm и являются лишь обертками.

#### Topics
GCM позволяет слать пуши не только конкретным юзерам, но и группам пользователей. Появляется такое понятие как topics.

После получения пуш токена можно подписать юзера на определенный топик - название ты задаешь сам.

Пример - можно разделить юзеров по половому признаку. Допустим, известен пол.
Мужчин подписываем на топик men. Женщин - women.
И на 8 марта делаем рассылку в women, на 23 февраля в man.
В примере ниже описано как делать такую рассылку.

#### Советы
* Вступление. Для пуш уведомления есть две иконки - большая и маленькая. Маленькая отображается в статус баре и потом отображается в развернутом пуше.
Большая отображается только в развернутом пуше.
Маленькая до 5.0 могла быть любого цвета, с 5.0 на неё применяется фильтр и все непрозрачные цвета становятся белыми.
Это может привести к тому, что у тебя есть цветная картинка на каком-то непрозрачном фоне и после фильтра она станет белым квадратом.
Тут 2 пути, либо иметь 2 набора иконок, либо сразу сделать иконку в белом цвете вне зависимости от sdk

![expanded_push_old](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/expanded_push_old.png) ![expanded_push_new](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/expanded_push_new.png)   

* Для развернутого пуш уведомления можно подсунуть кастомную вьюшку с любым расположением элементов (смотри класс `PushNotificationManager#createCustomView)
Нужно учесть, что на девайсах до 5.0 (или до 4.4, не помню), фон у вьюшки должен быть темный, а текст белый, иначе она будет смотреться иначе. А на девайсах выше, наоборот - белый фон и черный текст.

![push_white](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/push_white.png) ![push_black](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/push_black.jpg)

#### Как проверить

1. Устанавливаем пример на девайс, нажимаем "Register push token", копируем token из logcat.

2. Делаем POST запрос на [https://gcm-http.googleapis.com/gcm/send](https://gcm-http.googleapis.com/gcm/send) со след параметрами:

```
HEADERS:
Content-Type:application/json
Authorization:key=your_server_api_key (см Настройки п.7)
```

Далее добавляет в тело запроса след инфу:

Для пуша на конкретное устройство

```json
{
  "to": "device_push_token",
  "data": {
    "message": "Your message",
   }
}
```
`device_push_token` берем в п.1 из logcat

Для пуша группе юзеров

```json
{
  "to": "/topics/your_topic",
  "data": {
    "message": "Your message",
   }
}
```

[Офиц гайд по тестирования пушей на конкретное устройство](https://developers.google.com/cloud-messaging/topic-messaging#sending_topic_messages_from_the_server)

[Офиц гайд по топикам](https://developers.google.com/cloud-messaging/topic-messaging)

