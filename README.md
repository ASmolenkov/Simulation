# Simulation
Симуляция мира хищников и травоядных
Описание проекта
Это консольная симуляция экосистемы, где взаимодействуют хищники, травоядные и растения. Проект реализован на Java с использованием многопоточности для обработки пользовательского ввода и обновления состояния мира.

Особенности
🌍 Динамически генерируемый мир с существами и растениями

🐺 Хищники, которые охотятся на травоядных

🐇 Травоядные, которые ищут растения для пропитания

🌱 Растения, которые периодически появляются на карте

🔄 Бесконечный цикл симуляции с управлением через консоль

🖥️ Визуализация мира в консоли с цветовым кодированием

Управление
Программа поддерживает следующие команды:

start - запустить симуляцию

stop - приостановить симуляцию

step - Произвести один ход

exit - завершить программу


Требования
Java JDK 11 или выше

Maven для сборки (опционально)

Установка и запуск
Клонируйте репозиторий:

bash
git clone https://github.com/yourusername/ecosystem-simulation.git
cd ecosystem-simulation
Соберите проект:

bash
mvn package
Запустите программу:

bash
java -jar target/ecosystem-simulation.jar
Или запустите напрямую из IDE, открыв проект как Maven-проект.

Лицензия
Этот проект распространяется под лицензией MIT. Подробнее см. в файле LICENSE.