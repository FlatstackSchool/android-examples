# Пример push notifications

## Настройка
#### [Официальный путь](https://developers.google.com/cloud-messaging/android/client) - просто пошагово выполнять.
#### Если в официальном пути возникают трудности:
1. Идем на https://console.developers.google.com/apis/ логинимся под нужной почтой.
2. Создаем проект
3. В левом табе выбираем Обзор -> Mobile API -> Google Cloud Messaging включить
4. **(Можно пропустить этот пункт - он нужен для того, чтобы пуши могло получать только наше приложение)**
Учетные данные -> Создать учетные данные -> Ключ API -> Ключ для Android. Вводим название. Снизу жмем "Добавить ресурс "название пакета и контрольная сумма"". Создать. Появится диалог с каким-то ключем, он нам не нужен, просто ОК.
[Как генерить SHA1 ключ](http://stackoverflow.com/questions/27609442/how-to-get-the-sha1-fingerprint-certificate-in-android-studio-for-debug-mode)
5. Жмем в правом верхнем углу на иконку с тремя вертикальными точками -> Сведения о проекте. Копируем номер проекта, он нужен для нашего android проекта.
6. Учетные данные -> Создать учетные данные -> Ключ API -> Ключ для сервер. Вводим любое название. Создать. Полученные ключ сохраняем и отдаем server guys.

![instruction](https://raw.githubusercontent.com/fs/android-examples/PushNotifications/arts/steps.gif)   

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
* Для пуш уведомления есть две иконки - большая и маленькая. Маленькая отображается в статус баре и потом отображается в развернутом пуше.
Большая отображается только в развернутом пуше.
Маленькая до 5.0 могла быть любого цвета, с 5.0 на неё применяется фильтр и все непрозрачные цвета становятся белыми.
Это может привести к тому, что у тебя есть цветная картинка на каком-то непрозрачном фоне и после фильтра она станет белым квадратом.
Тут 2 пути, либо иметь 2 набора иконок, либо сразу сделать иконку в белом цвете вне зависимости от sdk

| Pre 5.0        |     5.0+ |
|:--------------:|:--------:|
|![expanded_push_old](https://raw.githubusercontent.com/fs/android-examples/PushNotifications/arts/expanded_push_old.png) | ![expanded_push_new](https://raw.githubusercontent.com/fs/android-examples/PushNotifications/arts/expanded_push_new.png) |
   

* Для развернутого пуш уведомления можно подсунуть кастомную вьюшку с любым расположением элементов (смотри класс `PushNotificationManager#createCustomView`)
Чтобы твои уведомление совпадали с общим стилем системы учти, что на девайсах до 5.0, фон у вьюшки должен быть темный, а текст белый, и наоборот на 5.0+.
Я бы не рекомендовал переопределять цвет шрифта и бэкграунда, чтобы система сама выбирали нужные. Потому что ситуация с фонами может меняться от производителя к производителю.

|   Pre 5.0      |  5.0+    |
|:--------------:|:--------:|
|![push_black](https://raw.githubusercontent.com/fs/android-examples/PushNotifications/arts/push_black.jpg) | ![push_white](https://raw.githubusercontent.com/fs/android-examples/PushNotifications/arts/push_white.png) |

* [Официальный гайд внешнему виду пушей](http://www.google.com/design/spec/patterns/notifications.html)

#### Отправляем пуши на девайс

1. Устанавливаем пример на девайс, нажимаем "Register push token", копируем token из logcat.

2. Делаем POST запрос на [https://gcm-http.googleapis.com/gcm/send](https://gcm-http.googleapis.com/gcm/send) со след параметрами:

```
HEADERS:
Content-Type:application/json
Authorization:key=your_server_api_key
```
`your_server_api_key` Если генерели json файл настроек, то заходим в google apis console -> учетные записи -> ключи API -> находим server key (auto created by Google Service) с типом Сервер и копируем ключ

Если шли кастомным путем, то берем из пункта 6 из Настроек.

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

