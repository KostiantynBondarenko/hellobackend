[![Build Status](https://travis-ci.org/KostiantynBondarenko/hellobackend.svg?branch=master)](https://travis-ci.org/KostiantynBondarenko/hellobackend)

Hello back-end
=============== 
REST сервис HelloBackEnd

Для работы приложения необходима **PostgreSQL БД test (username: postgres, password: postgres)**.
Настройки доступа расположены в файле /resources/application.yml.
Инициализация и заполнение выполняются автоматически при старте приложения.

Пример запроса к таблице **contacts**:
__http://localhost:8080/hello/contacts?nameFilter=^A.*$&offset=0&limit=20__

Запрос  возвращает контакты из таблицы БД. Массив контактов возвращается в json формате.

Параметр **nameFilter** обязателен. В него передаётся регулярное выражение. 

Параметры **offset** и **limit** добавлен для исключения единовременной загрузки большого количества данных из БД и порционной выборки данных, данные параметры не обязателен в строке запроса, при их отсутсвии они равны 0 и 20 соответственно.

Для запуска приложения нужно выполнить команды **mvn package** и **java -jar target/hellobackend-0.0.1-SNAPSHOT.jar**.