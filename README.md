# AppsGeyser
Тестовое задание для компании AppsGeyser
Navigation Drawer имеет два пункта:
### 1) Новый текст
### 2) История
![ScreenShot](https://github.com/Onotole1/AppsGeyser/blob/master/Screenshot%20from%202017-04-22%2003-01-28.png)

### Экран «Новый текст» содержит многострочное текстовое поле и Floating Action Button.
![ScreenShot](https://github.com/Onotole1/AppsGeyser/blob/master/Screenshot%20from%202017-04-22%2003-04-42.png)
### По нажатию на кнопку, должно появиться диалоговое окно, сообщающее язык на котором введен текст (или выводить причину ошибки распознавания).
### Успех:
![ScreenShot](https://github.com/Onotole1/AppsGeyser/blob/master/Screenshot%20from%202017-04-22%2003-26-37.png)
### Ошибка:
![ScreenShot](https://github.com/Onotole1/AppsGeyser/blob/master/Screenshot%20from%202017-04-22%2003-40-53.png)

###Экран «История» содержит список введенных фраз на странице «Новый текст» и их распознанный язык. Фразы в список попадают после нажатия на FAB экрана «Новый текст», если удалось распознать текст.
![ScreenShot](https://github.com/Onotole1/AppsGeyser/blob/master/Screenshot%20from%202017-04-22%2003-01-54.png)

### Для распознавания использован сервис http://www.alchemyapi.com/api/language-detection Приложение поддерживает смену ориентации экрана. Результат распознавания попадает в историю, даже если процесс Activity остановлен до прихода ответа от веб-сервиса или произошла смена ориентации экрана. Обновление списка на странице «История» происходит в режиме реально времени, как только получен успешный результат от веб-сервиса.
