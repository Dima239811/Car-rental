# Бэкенд часть приложения сервис аренды авто

## Рабочая ветка  feature1

### Описание работы

С коллекцией запросов можно ознакомиться по ссылке (обратить внимание можно на вкладки авторизации, body и т.д.)
https://dimacekalin59-9294930.postman.co/workspace/%D0%94%D0%B8%D0%BC%D0%B0-%D0%A7%D0%B5%D0%BA%D0%B0%D0%BB%D0%B8%D0%BD's-Workspace~779638f5-db15-457a-8bea-966c6a4e669c/folder/52655520-9e986137-28a6-4cf6-80c7-a9c324274200?action=share&source=copy-link&creator=52655520&ctx=documentation


## Порты по умолчанию

Бэкенд : `8080` \
БД: `5432`

## Пояснения по структуре:

Статусы аренд: [enum](src/main/java/com/infy/enums/RentalStatus.java)

Роли пользователей системы: [enum](src/main/java/com/infy/enums/Role.java)

При первом запуске автоматически создается админ с данными: \
логин: admin \
пароль admin123

Скрипт инициализации в файле [script](src/main/java/com/infy/config/DataInitializer.java)

При необходимости править CORS настройки необходимо перейти в файл [cors](src/main/java/com/infy/config/CorsConfig.java)

В файле [Handler](src/main/java/com/infy/exception/GlobalExceptionHandler.java) можно ознакомиться с кодами,
которые вернуться в HTTP запросе в случае возникновения исключения (при необходимости узнать номер кода,
нажать на Return(HttpStatus...) и перекинет в джавовский файл с енумами кодов

## Конфигурация
В файле [properties](src/main/resources/application.properties) лежат настройки бд (подключение,
логировнаие и т.д) \
При первом запуске можно включить флаги: \
spring.sql.init.mode=never \
spring.jpa.defer-datasource-initialization=false \

Тогда создадутся тестовые данный и таблицы бд.

## Сборка проекта
`mnc clean ` \
`mvn package ` 

После чего в вашей среде разработки запустить проект

