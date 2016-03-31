package com.mobileappsco.training.mymovies.traditionalproviders;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 3/31/2016.
 */
public class TestUtilities extends AndroidTestCase {

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    public static ContentValues getMockMovie() {
        ContentValues testValues = new ContentValues();
        testValues.put("poster_path", "/lWaksjJw8e7hdLPSxAoBTxTehY3.jpg");
        testValues.put("adult", false);
        testValues.put("overview", "Em 2002, em “Identidade Desconhecida (The Bourne Identity)”, ele tentou descobrir quem era. Em 2004, com “Supremacia (The Bourne Supremacy), vingou-se daquilo que lhe tinham feito. Agora, ele está de regresso a casa... e lembra-se de tudo... Matt Damon encarna mais uma vez, a personagem de Jason Bourne para a última parte da história em \"Ultimato\".");
        testValues.put("release_date", "2007-08-03");
        testValues.put("id", 2503);
        testValues.put("original_title", "The Bourne Ultimatum");
        testValues.put("original_language", "en");
        testValues.put("title", "O Ultimato Bourne");
        testValues.put("backdrop_path", "/6WpDOqkZFmhNJ0rwuLJiZVKlZi1.jpg");
        testValues.put("popularity", 5.820295);
        testValues.put("vote_count", 1717);
        testValues.put("video", false);
        testValues.put("vote_average", 7.14);
        return testValues;
    }

    public static ContentValues getMockTrailer() {
        ContentValues testValues = new ContentValues();
        testValues.put("id", "533ec653c3a3685448000258");
        testValues.put("iso_639_1", "en");
        testValues.put("iso_3166_1", "US");
        testValues.put("key", "8iHrEOiuoyw");
        testValues.put("name", "Trailer");
        testValues.put("site", "YouTube");
        testValues.put("size", 720);
        testValues.put("type", "Trailer");
        return testValues;
    }

    public static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

}
