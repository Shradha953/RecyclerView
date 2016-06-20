package com.example.bitjini.recyclerviewwithimages;

import android.graphics.Bitmap;

/**
 * Created by bitjini on 16/6/16.
 */
public class ItemList {
    private Bitmap thumbnailURL;
    /**
     * Just for the sake of internal reference so that we can identify the item.
     */
    long id;

    /**
     *
     * @param id
     * @param thumbnailURL
     */
    public ItemList(long id,Bitmap thumbnailURL) {
        this.id = id;

        this.thumbnailURL = thumbnailURL;
    }

    public ItemList(Bitmap bm1) {

    }


    public long getID() {
        return id;
    }

    public Bitmap getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(Bitmap thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}