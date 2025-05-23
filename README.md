﻿# NotesBook
# Java Project 2: Knowledge Base Manager

## 1. Архитектура проекта

Проект реализован как простое JavaFX-приложение с возможностью сохранять заметки, искать по тегам, удалять и восстанавливать их. Он включает в себя:

### 1.1 Пользовательский интерфейс (Frontend):
- JavaFX: визуальный интерфейс с элементами управления.
- Поля ввода: текст заметки и теги.
- Кнопки: Add Note, Save, Load, Undo, Delete.
- ListView для отображения сохранённых заметок и тегов.

### 1.2 Backend (Логика приложения):
- Работа с коллекциями (`List`, `Stack`).
- Хранение и управление заметками (`Note`) и тегами (`Tag`).
- Поддержка Undo/Redo с помощью стека (`History`, `redoStack`).
- Поиск по тегу.
- Использование сериализации (`ObjectOutputStream`, `ObjectInputStream`) для работы с файлами.

---

## 2. Используемые методы

### Main.java
- start(Stage) — создание UI, логика кнопок
- updateNotesList(...) — обновление интерфейса (отображение всех/фильтрованных заметок)
- showAlert(...) — всплывающие сообщения

### KnowledgeBase.java
- addNote(text, tags) — добавление заметки
- getNotes() — вернуть все заметки
- deleteNote(index) — удаление заметки
- saveNotesToFile(fileName) — сохранение всех заметок в файл
- loadNotesFromFile(fileName) — загрузка заметок из файла
- undo() — отмена последнего действия

### FileManager.java
- saveNotesToFile(notes, fileName) — запись объектов в файл
- loadNotesFromFile(fileName) — чтение объектов из файла

### Note.java
- createNote(text, tags) — фабричный метод создания заметки
- getText() — получить текст
- getTags() — получить теги
- deleteNote() — удалить текст и очистить теги

### Tag.java
- createTag(name) — фабричный метод (альтернатива конструктору)
- getName() — получить имя тега

### History.java
- saveState(note) — сохранить состояние (для undo)
- undo() — вернуть последнее состояние

---

## 3. Как запустить проект

1. Убедись, что у тебя установлен JavaFX и он подключён в конфигурации.
2. Запусти файл Main.java.
3. Добавляй заметки, вводи теги, сохраняй, ищи и восстанавливай действия!

---

## 4. Хранилище

- Файл notes.dat используется для хранения заметок через сериализацию.
- Временная история действий хранится в памяти (`Stack`).

---
