package ru.t1.gladun.tests.entity;

public enum TaskStatus {
    rejected("Отменена"),
    on_verification("На проверке"),
    for_execution("К выполнению"),
    in_proccess("В работе"),
    completed("Выполнена"),
    closed("Завершено");

    TaskStatus(String status) {
    }
}
