package com.example.weatherapp;

import android.provider.BaseColumns;

public class FeedReaderContract {

    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static String COLUMN_NAME_PUBDATE;
        public static String COLUMN_NAME_IMAGEMAIN;
        public static String COLUMN_NAME_XMLLINK;
        public static String COLUMN_NAME_MAINCATEGORY;
    }
}
