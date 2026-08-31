#!/bin/bash

# Construction Training Generator - Startup Script
# Скрипт для быстрого запуска приложения

echo "🚀 Запуск Construction Training Generator..."
echo ""

# Проверка наличия Maven
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven не установлен. Пожалуйста, установите Maven."
    exit 1
fi

echo "📦 Сборка проекта..."
mvn clean install -q

if [ $? -ne 0 ]; then
    echo "❌ Ошибка при сборке проекта"
    exit 1
fi

echo "✅ Сборка завершена!"
echo ""
echo "▶️  Запуск приложения на http://localhost:8080"
echo ""

mvn spring-boot:run
