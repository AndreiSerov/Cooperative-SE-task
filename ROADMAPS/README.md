## Этап 3 - Rev 2

R3.1 Приложение должно логировать в stdout все введенные параметры, 
процесс принятия решения с пояснением причины, возникающие исключения со 
стректрейсом
Можно использовать библиотеку [log4j](http://logging.apache.org/log4j/2.x/manual/configuration.html)   
Вывод вида "Пароль ABC для пользователя XYZ неверный"

R3.2 Приложение должно логировать в файл aaa.log в директории запуска все введенные параметры, процесс принятия решения с пояснением причины, возникающие исключения со стректрейсом


R3.3 Приложение должно хранить данные во встраиваемой СУБД
Вы можете выбрать любую встраиваемую СУБД, т.е. такую которая включается внутрь jar-файла, не требует установки и настройки  
В качестве БД можно использовать [h2](http://www.h2database.com/html/main.html) 


R3.4 Схема данных должна включать по крайней мере 3 таблицы, 3 внешних ключа, один уникальный ключ (логин), один индекс (пользователь, роль)


R3.5 При старте приложение должно файл с БД aaa.db (в зависимости от выбранной СУБД расширение может отличаться) в директории запуска и инициализировать схему если БД не найдена
Для инициализации схемы используйте [flyway](http://flywaydb.org/getstarted/firststeps/api.html)

R3.6 При старте приложение должно заполнять тестовыми данными через миграцию, если БД не найдена в папке с приложением
UPD 3 данные должны заполняться в отдельной миграции


R3.7 Выборка данных о пользователе и ролях из базы должна исключать SQLI
Используйте для выборки данных [PreparedStatement](http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html) 


R3.8 Вставка данных о доступе должна исключать SQLI

R3.9 Вся работа с данными из базы должна осуществляться через DAL и доменные объекты (Anemic Data Model)
UPD 2 добавил про DAL


R3.10 Архитектура приложения должна удовлетворять принципам SOLID
UPD [1](https://ru.wikipedia.org/wiki/SOLID_(%D0%BE%D0%B1%D1%8A%D0%B5%D0%BA%D1%82%D0%BD%D0%BE-%D0%BE%D1%80%D0%B8%D0%B5%D0%BD%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%BD%D0%BE%D0%B5_%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5))


R3.10 Доменные классы должны находиться в отдельном пакете, DAO-класс если более одного тоже должны находиться в отдельном пакете
UPD3


R3.11 Настройки БД (url, login, pass) должны передаваться через переменные окружения
System.getenv()

## Этап 4 - Rev 2

R4.1 Структура проекта должна соответствовать проекту gradle

R4.2 Проект должен собираться при помощи gradle

R4.3 Все зависимые библиотеки должны подставляться из репозитория

R4.4 При сборке все зависимости должны упаковываться в один исполняемый файл

R4.5 Приложение должно тестироваться через Spek
[1](https://www.spekframework.org/)
[2](http://habrahabr.ru/post/120101/)
[3](http://site.mockito.org)
подмена реализации класса

R4.6 Gradle должен генерировать отчет по покрытию тестами JaCoCo

R4.7 Стиль кода должен проверяться при помощи 
[ktlint](https://github.com/pinterest/ktlint)

R4.8 Gradle должен генерировать отчет по статическому анализу кода
https://arturbosch.github.io/detekt/

R4.9 Документация должна быть обновлена для соответствия новой системе сборки


## Этап 5 - Server

R5.1 Проект должен быть преобразован в web-приложение
[mkyong](http://www.mkyong.com/maven/how-to-create-a-web-application-project-with-maven/)

R5.2 Проект должен запускаться через [сервлет контейнер](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html) 
Не забудьте добавить jetty-servlet в зависимости
Запускать нужно через mvn org.eclipse.jetty:jetty-maven-plugin:run

R5.3  Создайте один сервлет слушающий /echo/* и переопределите методы doGet и doPost. Проверяйте URL из request: если запрос не /echo/get или /echo/post возвращайте ответ 404

R5.4 Проект должен содержать сервлет слушающий по адресу /echo/get который принимает GET запрос вида ?id=X и выводящий в ответ значение X
http://www.mkyong.com/servlet/a-simple-servlet-example-write-deploy-run/

R5.5 Проект должен содержать сервлет слушающий по адресу /echo/post который принимает POST запрос с текстом и делает редирект на /echo/get?id=X где X поле введенное в форму

R5.6 Проект должен содержать страницу index.html с формой, одним полем и кнопкой submit, форма отправляет post запрос на /echo/post сервлет. Сделать ссылку на GET-сервлет с каким-нибудь параметром.

R5.7 Сделать иерархический pom-проект
Eclipse Plugin - Multiple Module Project with Eclipse 
В родительском pov-файле должны быть прописаны два дочерних модуля. А в каждом дочернем модуле должна быть ссылка на родителя.

R5.8 Задеплоить war проект на heroku
Требовани добавлено 16 Апреля. 
[1](https://devcenter.heroku.com/articles/getting-started-with-java#introduction) 
[2](https://devcenter.heroku.com/articles/deploy-a-java-web-application-that-launches-with-jetty-runner) 

R5.9 Настроить автоматический деплой war проекта на heroku
Требовани добавлено 16 Апреля.
https://devcenter.heroku.com/articles/github-integration 

R5.10 Для генерации результата get страницы из шаблона используйте GSP
Требовани добавлено 16 Апреля.
Запрос приходит на get-сервлет, он заполняет переменную и вызывает 
[getRequestDispatcher("...gsp").forward(...)](http://java-course.ru/student/book1/jsp/) 

## Этап 6 - Guice
R6.1 Пути должны конфигурироваться через guice
https://github.com/google/guice/wiki/ServletModule

R6.2 Логгеры в серверной части должны инжектиться в сервлеты через guice
https://github.com/google/guice/wiki/CustomInjections

R6.3 В GuiceServletConfig должен быть прописан UserServlet который будет работать с User /ajax/user
Для этих сервлетов переопределяйте метод service(request, responce)

R6.4 В GuiceServletConfig должен быть прописан AuthorityServlet который будет работать с Authority /ajax/authority

R6.5 В GuiceServletConfig должен быть прописан ActivityServlet который будет работать с Activity /ajax/activity

## Этап 7 - GSON

R7.1 В сервлетах объекты должны сериализоваться при помощи 
[GSON](https://github.com/google/gson/blob/master/UserGuide.md)

R7.2 Для получения сериализатора должен использоваться провайдер
https://github.com/google/guice/wiki/InjectingProviders

R7.3 В UserServlet должен инжектиться провайдер GSON

R7.4 В AuthorityServlet должен инжектиться провайдер GSON

R7.5 В ActivityServlet должен инжектиться провайдер GSON

R7.6 Выполнение http get по адресу /ajax/user должен возвращать json список пользователей
UPD 26.04 Для получения списка пользователей нужно использовать соответствующий Dao из проекта client

R7.7 Выполнение http get по адресу /ajax/user?id=xxx должен возвращать json пользователя с указанным идентификатором

R7.8 Выполнение http get по адресу /ajax/authority должен возвращать json список прав доступа
UPD 26.04 Для получения списка прав доступа нужно использовать соответствующий Dao из проекта client

R7.9 Выполнение http get по адресу /ajax/authority?id=xxx должен возвращать json право пользователя с указанным идентификатором

R7.10 Выполнение http get по адресу /ajax/authority?userId=xxx должен возвращать json права указанного пользователя

R7.11 Выполнение http get по адресу /ajax/activity должен возвращать json список действий
UPD 26.04 Для получения списка действий нужно использовать соответствующи Dao из проекта client

R7.12 Выполнение http get по адресу /ajax/activity?id=xxx должен возвращать 
json действие с указанным идентификатором

R7.13 Выполнение http get по адресу /ajax/activity?authorityId=xxx должен 
возвращать json действия с указанными правами доступа

R7.14 Сериализоваться должны только поля помеченные 
(@Expose)[https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/annotations/Expose.html]

R7.15 В объекте User не должны сериализоваться пароль и хеш

R7.18 В объекте Authority не должны сериализоваться User

R7.19 В объекте Activity не должны сериализваться Authority

R7.20 Коннект к базе данных данных должен инжектиться в Dao классы при помощи DI
UPD 26.04 Миграцию бд можно провести в DI конфиге



## Этап 8 - ORM

R8.1 Для работы с базой должен использоваться ORM
https://github.com/google/guice/wiki/GuicePersist 

R8.2 Доменные объекты должны быть помечены как JPA сущности при помощи аннотаций
https://docs.jboss.org/hibernate/annotations/3.5/reference/en/html/entity.html#entity-mapping

R8.3 Схема должна быть приведена к совместимой с ORM через миграции
http://www.studytrails.com/java/json/java-google-json-parse-json-to-java.jsp

R8.4 Каждый объект должен содержать версию для оптимистической блокировки
http://www.objectdb.com/java/jpa/entity/fields#Version_Field_

R8.5 ID поля должны быть автогенерируемыми
http://www.objectdb.com/java/jpa/entity/id

R8.6 Работа с сущностью User должна вестись через UserDao

R8.7 В UserDao должен инжектиться EntityManager
https://github.com/google/guice/wiki/JPA

R8.8 В UserServler должен инжектиться UserDao

R8.9 Работа с сущностью User должна вестись через AuthorityDao

R8.10 В AuthorityDao должен инжектиться EntityManager
https://github.com/google/guice/wiki/JPA

R8.11 В AuthorityServler должен инжектиться AuthorityDao

R8.12 Работа с сущностью User должна вестись через ActivityDao

R8.13 В ActivityDao должен инжектиться EntityManager
https://github.com/google/guice/wiki/JPA

R8.14 В ActivityServlet должен инжектиться ActivityDao

R8.15 Приложение должно получать параметры коннекта к БД через переменные окружения
UPD 26.04 при этом тестовое окружение должно продолжать использовать H2.

R8.16 Адаптируйте миграции к PostgreSQL
UPD 26.04 переведите H2 в режим совместимости с PostgreSQL http://www.h2database.com/html/features.html#compatibility

R8.17 Используйте PostgreSQL предоставляемую сервисом Heroku
UPD 26.04 https://www.heroku.com/postgres

R8.18 Используйте пулл коннектов c3p0
UPD 26.04 http://javacore.ru/topic/3-jdbc-spring-c3p0.htm



