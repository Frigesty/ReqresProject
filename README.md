# Проект по автоматизации тестирования API для сайта [Reqres](https://reqres.in/)
<p align="center"><a href="https://reqres.in/"><img src="images/icons/reqres.png" align="center"  height="150"  alt="reqres"/></a></p>

> Reqres - сервис, предоставляющий публичный API для практики тестирования и разработки

## Содержание:

- [Технологии и инструменты](#tools)
- [Тестовое покрытие](#cases)
- [Локальный запуск тестов](#localrun)
- [Запуск тестов в Jenkins](#remoterun)
- [Allure отчёт](#report)
- [Интеграция с Allure TestOps](#testops)
- [Интеграция с Jira](#jira)
- [Уведомления в Telegram](#telegram)


<a id="tools"></a>

## Технологии и инструменты:

<div align="center">
<a href="https://www.jetbrains.com/idea/"><img alt="InteliJ IDEA" height="50" src="images/icons/intellij-original.svg" width="50"/></a>
<a href="https://github.com/"><img alt="GitHub" height="50" src="images/icons/github-original.svg" width="50"/></a>  
<a href="https://www.java.com/"><img alt="Java" height="50" src="images/icons/java-original.svg" width="50"/></a>
<a href="https://gradle.org/"><img alt="Gradle" height="50" src="images/icons/gradle-original.svg" width="50"/></a>  
<a href="https://junit.org/junit5/"><img alt="JUnit 5" height="50" src="images/icons/junit-original.svg" width="50"/></a>
<a href="https://rest-assured.io/"><img alt="RestAssured" height="50" src="images/icons/restAssured.png" width="50"/></a>
<a href="https://www.jenkins.io/"><img alt="Jenkins" height="50" src="images/icons/jenkins-original.svg" width="50"/></a>
<a href="https://github.com/allure-framework/"><img alt="Allure Report" height="50" src="images/icons/allureReports.png" width="50"/></a>
<a href="https://qameta.io/"><img alt="Allure TestOps" height="50" src="images/icons/allureTestops.svg" width="50"/></a>
<a href="https://www.atlassian.com/software/jira"><img alt="Jira" height="50" src="images/icons/jira.webp" width="50"/></a>  
<a href="https://telegram.org/"><img alt="Telegram" height="50" src="images/icons/telegram.webp" width="50"/></a>
</div>

<p></p>

Автотесты для данного проекта написаны на <code>Java</code> с использованием библиотеки <code>REST-assured</code> для взаимодействия с REST api.

В качестве фреймворка для запуска тестов используется <code>Junit5</code>, а в качестве сборщика проекта - <code>Gradle</code>. Произведена настройка CI в <code>Jenkins</code>.

По результатам каждого тестового прогона создаётся <code>Allure</code> отчёт для визуализации результатов прогона.

Для тест-менеджмента настроена интеграция с <code>Allure TestOps</code>, которая, в свою очередь, интегрирована с таск-трекером <code>Jira</code>

После прогона тестов <code>Telegram</code> бот присылает сообщение с информацией о прошедшем прогоне

<a id="cases"></a>

## Тестовое покрытие:
### Регистрация пользователя
🔎 Тест на успешную регистрацию пользователя

🔎 Тест на провальную регистрацию без пароля

### Работа с ресурсами
🔎 Тест на проверка корректности данных в List Resource

🔎 Тест на получение цвета по ID

🔎 Тест на то что цвет не найден


### Авторизация пользователя
🔎 Тест на успешный логин пользователя

🔎 Тест на провальный логин без пароля


### Получение, редактирование, удаление пользователя
🔎 Тест на проверку корректности данных пользователей

🔎 Тест на получение пользователя по ID

🔎 Тест на проверку, что пользователь не найден

🔎 Тест на создание нового пользователя

🔎 Тест на обновление данных пользователя

🔎 Тест на удаление пользователя

🔎 Тест на проверку корректности данных пользователей на первой странице с задержкой

<a id="localrun"></a>

## Локальный запуск тестов
Для локального запуска тестов из IDE или из терминала необходимо выполнить следующую команду

```
gradle clean test
```

<a id="remoterun"></a>

## Запуск тестов в [Jenkins](https://jenkins.autotests.cloud/job/C27-przh17-ApiTests_unit23/)
Для запуска тестов в Jenkins нужно нажать на кнопку Build with Parameters

<p align="center">
<img src="images/screenshots/jenkins-screen.png">
</p>

<a id="report"></a>

## [Allure отчёт](https://jenkins.autotests.cloud/job/C27-przh17-ApiTests_unit23/allure/)
### Графики

<p align="center">
<img src="images/screenshots/allure-graphs.png">
</p>

### Тесты

<p align="center">
<img src="images/screenshots/allure-tests.png">
</p>

<a id="testops"></a>

## Интеграция с [Allure TestOps](https://allure.autotests.cloud/project/4207/test-cases)

<p align="center">
<img src="images/screenshots/allureTestops-screen.png">
</p>

<a id="jira"></a>

## Интеграция с [Jira](https://jira.autotests.cloud/browse/HOMEWORK-1209)

<p align="center">
<img src="images/screenshots/jira-screen.png">
</p>

<a id="telegram"></a>

## Уведомление в Telegram

По результатам каждого прогона тестов в Jenkins отправляется сообщение в Telegram. Сообщение содержит информацию о прогоне, а также диаграмму со статистикой прохождения тестов.

<p align="center">
<img src="images/screenshots/telegram-screen.png" width="400">
</p>