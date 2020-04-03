## План выполнения третьего набора требований

1. Прочитать требования (5 мин.)

2. Провести исследование
    1. Провести исследование библиотеки [log4j](https://logging.apache.org/log4j/kotlin/)(**R3.1**) - 30 мин  
    2. Провести исследование[h2db](http://www.h2database.com/html/main.html ) (**R3.3**) - 30 мин  
    3. Провести исследование библиотеки для исключения SQLI 
    [PreparedStatement](http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html)(**R3.7**) - 30 мин
    4. Проовести исследование принципов [SOLID](https://clean-code.org/how-to-write-code-solidno.html)(**R3.10**) - 2 часа
    5. Провести исследование и узнать что такое [переменные среды](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getenv-java.lang.String-)(**R3.11**) - 30 мин  
    6. Провести исследование и узнать о [доменных моделях](https://stackoverflow.com/questions/1674209/what-is-the-difference-between-business-class-and-domain-class-what-is-meant-by) 
    и [DAO](https://en.wikipedia.org/wiki/Data_access_object) (**R3.9**) - 1 час  - 30 мин

3. Добавить в программу библиотеку log4j логирование:  
    1. Приложение логирует в стандартный поток вывода(stdout) все введенные параметры, процесс принятия решения с пояснением причины, возникающие исключения со стректрейсом.  
    2. Добавить файл aaa.log, для того чтобы приложение логировало в него тоже самое что и в stdout

4. 

### Исследовательские задачи
1. log4j (**R3.1, R3.2**)
    1. Ознакомиться с 
    [документацией](http://logging.apache.org/log4j/2.x/manual/configuration.html)
    
 