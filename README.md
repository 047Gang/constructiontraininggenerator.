# Construction Training Generator

## 🔧 Генератор документов обучения для строительных инструментов

Минималистичное веб-приложение на Java Spring Boot для генерации сертификатов о прохождении инструктажа по безопасной работе со строительными инструментами.

### ✨ Возможности

- ✅ Генерация PDF сертификатов
- ✅ 50+ строительных инструментов в базе
- ✅ Красивый минималистичный интерфейс
- ✅ Быстрая работа
- ✅ Русский язык
- ✅ Работает через Docker (без установки ничего!)

### 🚀 Быстрый старт

#### Вариант 1: Docker (РЕКОМЕНДУЕТСЯ - самый простой!)

Если у тебя установлен **Docker Desktop**:

**На Windows:**
```bash
start-docker.bat
```

**На Linux/Mac:**
```bash
bash start-docker.sh
```

Или вручную:
```bash
docker-compose up --build
```

Приложение откроется на: **http://localhost:8080** 🎉

#### Вариант 2: Java + Maven

Требования:
- Java 17+
- Maven 3.6+

**На Windows:**
```bash
run.bat
```

**На Linux/Mac:**
```bash
bash run.sh
```

Или вручную:
```bash
git clone https://github.com/047Gang/construction-training-generator.git
cd construction-training-generator
mvn clean spring-boot:run
```

### 📋 Как использовать

1. Откройте приложение в браузере: **http://localhost:8080**
2. Введите имя сотрудника
3. Выберите инструмент из списка
4. (опционально) Введите имя инструктора
5. Нажмите "Скачать сертификат"
6. PDF будет загружен на компьютер ✅

### 🐳 Требования для Docker версии

- **Docker Desktop** (скачать: https://www.docker.com/products/docker-desktop)

Это всё! Docker автоматически установит Java, Maven и всё остальное.

### 🛠️ Требования для обычной версии

- **Java JDK 17+** (скачать: https://adoptium.net/)
- **Maven 3.6+** (скачать: https://maven.apache.org/download.cgi)

### 📦 Проектная структура

```
src/main/
├── java/com/construction/
│   ├── TrainingGeneratorApplication.java
│   ├── controller/
│   │   └── TrainingController.java
│   ├── model/
│   │   ├── Tool.java
│   │   └── TrainingCertificate.java
│   └── service/
│       ├── ToolService.java
│       └── CertificateService.java
└── resources/
    ├── templates/
    │   └── index.html
    └── application.properties
```

### 📄 Инструменты в базе

Приложение включает 50 основных строительных инструментов:
- Дрели, УШМ, пилы
- Молотки, гвоздезабиватели
- Электроинструменты
- Измерительные инструменты
- И многое другое...

Каждый инструмент содержит:
- Описание
- Советы по безопасности
- Правила использования

### 🔒 Безопасность

Каждый сертификат содержит:
- Уникальный номер
- Дату обучения
- Имя сотрудника
- Имя инструктора
- Подпись инструктора

### 🛠️ Технологический стек

- **Backend**: Java 17, Spring Boot 3.1.5
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **PDF**: iText 7
- **Build**: Maven
- **Containerization**: Docker

### 📝 Лицензия

MIT License - см. LICENSE файл

### 👨‍💻 Автор

047Gang

### 🤝 Контрибьютинг

Приветствуются pull requests! Можно:
- Добавлять новые инструменты
- Улучшать дизайн
- Добавлять новые функции

### 🆘 Помощь

Если что-то не работает:

1. **Docker версия не запускается:**
   ```bash
   docker-compose down
   docker-compose up --build
   ```

2. **Maven версия не запускается:**
   Убедитесь, что Java установлена:
   ```bash
   java -version
   ```

3. **Приложение не открывается на http://localhost:8080:**
   - Подождите 30-60 секунд после запуска (первая сборка долгая)
   - Проверьте, нет ли других приложений на порту 8080
   - Перезагрузите страницу браузера (Ctrl+Shift+R)

---

**Сделано с ❤️ для безопасности на работе**
