package com.spitchenko.appsgeyser.base.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 16:08
 *
 * @author anatoliy
 *
 * Имплементация базового контроллера для декомпозиции активности на представление и контроллер
 */
public interface BaseActivityController {
    /**
     * Метод содержит логику по инициализации элементов представления
     * @param savedInstanceState - сохранённое состояние при повороте экрана
     */
    void updateOnCreate(@Nullable final Bundle savedInstanceState);
    /**
     * Метод вызывается при прохождении жизненного цикла активности для сохранения состояния
     * элементов представления
     * @param outState - объект для записи состояния
     */
    void updateOnSaveInstanceState(@NonNull final Bundle outState);
    /**
     * Метод вызывается для восстановления состояния элементов представления
     */
    void updateOnRestoreInstanceState(@NonNull final Bundle savedInstanceState);
    /**
     * Данный метод вызывается соответственно очередности жизненного цикла активности
     * и содержит логику по подписке на широковещательные сообщения (аналог конструктора)
     */
    void updateOnResume();

    /**
     * Данный метод вызывается соответственно очередности жизненного цикла активности
     * и содержит логику по отписыванию от широковещательных сообщения (аналог деструктора)
     */
    void updateOnPause();
}
