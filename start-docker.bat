@echo off
REM Construction Training Generator - Docker Startup
REM Запуск через Docker (самый простой способ)

echo.
echo 🐳 Запуск приложения через Docker...
echo.

REM Проверка Docker
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker не установлен
    echo Скачайте Docker Desktop: https://www.docker.com/products/docker-desktop
    pause
    exit /b 1
)

echo ✅ Docker найден
echo.
echo 📦 Создание и запуск контейнера...
echo.

docker-compose up --build

pause
