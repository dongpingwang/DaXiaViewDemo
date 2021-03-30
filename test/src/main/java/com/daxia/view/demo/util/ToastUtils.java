package com.daxia.view.demo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Dongping Wang
 * date 3/29/2021
 */
public final class ToastUtils {

    private static volatile Toast toast;

    private ToastUtils() {

    }

    public synchronized static void show(Context context, String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
