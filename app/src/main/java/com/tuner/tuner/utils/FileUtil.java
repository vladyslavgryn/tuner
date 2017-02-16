package com.tuner.tuner.utils;

import android.os.Environment;

import java.io.File;

public class FileUtil {

    public static final String FILE_EXT = ".pdf";
    public static final String FILE_MIME_TYPE = "application/pdf";
    private static final String DIRECTORY = "tuner";

    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getApplicationPath() {
        return new File(Environment.getExternalStorageDirectory() + File.separator + DIRECTORY);
    }

    public static File getApplicationFilePath(String fileName) {
        return new File(Environment.getExternalStorageDirectory() + File.separator + DIRECTORY + File.separator + fileName);
    }
}
