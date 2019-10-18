package com.silence.sdk.ykcore.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;


import com.silence.sdk.ykcore.common.YKGameSdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Describe :  文件帮助
 */

public class FileUtil {


    /**
     * @param path 路径
     * @return 文件是否存在
     */
    private static boolean isExist(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        return file.exists() && file.length() > 0;
    }

    /**
     * @param file 文件
     * @return 文件是否存在
     */
    public static boolean isExist(File file) {
        return file != null && isExist(file.getAbsolutePath());
    }

    public static File saveWxCode2File(byte[] buffer) throws IOException {
        File saveFile = new File(YKGameSdk.options().getCacheDir(), System.currentTimeMillis() + "_code.jpg");
        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(saveFile));
        BitmapUtil.recyclerBitmaps(bitmap);
        buffer = null;
        return saveFile;
    }

}


