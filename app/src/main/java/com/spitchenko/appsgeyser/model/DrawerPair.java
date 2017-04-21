package com.spitchenko.appsgeyser.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Date: 21.04.17
 * Time: 10:03
 *
 * @author anatoliy
 *
 * Объекты данного класса используются для заполнения drawerListViewAdapter
 */
public class DrawerPair {
    private @Getter @Setter int imageId;
    private @Getter @Setter String description;

    public DrawerPair(final int imageId, final String description) {
        this.imageId = imageId;
        this.description = description;
    }
}
