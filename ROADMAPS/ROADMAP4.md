## План выполнения четвертого набора требований

1. Прочитать требования (5 мин.)

2. Провести исследование - (80 мин.) 
    1. Gradle
    2. Spek
    3. JaCoCo
    4. ktlint
    5. Statteic code report

3. Переструктурировать проект, чтобы соответствовал Grdle'у - (30 мин.) **2 ч.**
    1. Переструктурировать
    2. Заинициализировать Gradle `gradle init`
    3. Заполнить зависимости, плагины, репозитории в build.gradle.kts

4. Написать Spek-тесты - (2 ч.)
    1. Обновить зависимости
    2. Написать тесты
    3. Добавить таски в грейдл

5. Подключить проверку стиля кода ktlint - (90 мин.) **15 мин.**

6. Настроить генерацию отчета по статическому анализу кода - (30 мин.) **30 мин.**
    1. Подключить `detekt`
    2. Исправить ошибки
    3. Настроить файл конфигурации

7. Обновить README.md - (5 мин.)

### Исследовательские задачи

1. Gradle
    1. [What s gradle?](https://docs.gradle.org/current/userguide/what_is_gradle.html)
    2. [Gradle structure](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html)
    3. [Dependencies](https://docs.gradle.org/current/userguide/viewing_debugging_dependencies.html)

2. [Spek](https://www.spekframework.org/)

3. [JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/)

4. [ktlint](https://github.com/pinterest/ktlint)

5. [Static code report](https://arturbosch.github.io/detekt/)