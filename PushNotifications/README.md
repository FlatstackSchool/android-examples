# Пример push notifications

## Настройка
###### [Официальный путь](https://developers.google.com/cloud-messaging/android/client) - просто пошагово выполнять.
###### Если в официальном пути возникают трудности:
1. Идем на https://console.developers.google.com/apis/ логинимся под нужной почтой.
2. Создаем проект
3. В левом табе выбираем Обзор -> Mobile API -> Google Cloud Messaging включить
4. Учетные данные -> Создать учетные данные -> Ключ API -> Ключ для Android. Вводим название. Снизу жмем "Добавить ресурс "название пакета и контрольная сумма"". Создать.
5. Появится диалог с каким-то ключем, он нам не нужен, просто ОК.
6. Жмем в правом верхнем углу на иконку с тремя вертикальными точками -> Сведения о проекте. Копируем номер проекта, он нужен для нашего android проекта.
7. Учетные данные -> Создать учетные данные -> Ключ API -> Ключ для сервер. Вводим любое название. Создать. Полученные ключ сохраняем и отдаем server guys.

![instruction](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/steps.gif)   

###### Ограничения
Пуши не будут приходить на устройства без google play services. Это не проблема, если ты распространяешь приложение только в google play.
Для amazon также есть своя альтернатива GCM пушам (тут это не рассматривается).
Можно написать свой push сервер, но это не самая простая задача (хотя telegram, whatsapp, facebook это сделали, но где они, а где ты?)
Все альтернативы построены на использовании под капотом gcm и являются лишь обертками.

###### Советы
* Вступление. Для пуш уведомления есть две иконки - большая и маленькая. Маленькая отображается в статус баре и потом отображается в развернутом пуше.
Большая отображается только в развернутом пуше.
Маленькая до 5.0 могла быть любого цвета, с 5.0 на неё применяется фильтр и все непрозрачные цвета становятся белыми.
Это может привести к тому, что у тебя есть цветная картинка на каком-то непрозрачном фоне и после фильтра она станет белым квадратом.
Тут 2 пути, либо иметь 2 набора иконок, либо сразу сделать иконку в белом цвете вне зависимости от sdk

![expanded_push_old](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/expanded_push_old.png) ![expanded_push_new](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/expanded_push_new.png)   

* Для развернутого пуш уведомления можно подсунуть кастомную вьюшку с любым расположением элементов (смотри класс `PushNotificationManager#createCustomView)
Нужно учесть, что на девайсах до 5.0 (или до 4.4, не помню), фон у вьюшки должен быть темный, а текст белый, иначе она будет смотреться иначе. А на девайсах выше, наоборот - белый фон и черный текст.

![push_white](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/push_white.png) ![push_black](https://raw.githubusercontent.com/fs/android-examples/push_notifications/PushNotifications/arts/push_black.jpg)

###### Как проверить
Для проверки используем онлайн php консоль

1. Устанавливаем пример на девайс, нажимаем "Register push token", копируем token из logcat.

2. Берем этот код: https://gist.github.com/IlyaEremin/f15711a5dfb8b0dc28f29cab319ae4b9 и вставляем сюда http://phpfiddle.org/

3. Подставляем туда свои значения server api key (см Настройки п.7) и token устройства (тот что скопировал в п.1 из logcat).

4. Нажимаем Run - F9 и получаем пуш на устройство.

