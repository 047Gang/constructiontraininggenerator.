@echo off
REM Construction Training Generator - Windows Startup Script
REM Генератор документов обучения - скрипт запуска для Windows

echo.
echo 🚀 Запуск Construction Training Generator...
echo.

REM Проверка наличия Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java не установлена. Пожалуйста, установите Java JDK 17+
    pause
    exit /b 1
)

echo ✅ Java найдена
echo.
echo 📦 Загрузка зависимостей и сборка проекта...
echo Это может занять несколько минут при первом запуске...
echo.

REM Использование Maven Wrapper если он есть
if exist "mvnw.cmd" (
    call mvnw.cmd clean spring-boot:run
) else (
    REM Если Maven Wrapper не найден, попытаемся использовать обычный mvn
    mvn clean spring-boot:run
)

if errorlevel 1 (
    echo.
    echo ❌ Ошибка при запуске приложения
    echo Убедитесь, что установлены Java и Maven
    pause
    exit /b 1
)

pause
