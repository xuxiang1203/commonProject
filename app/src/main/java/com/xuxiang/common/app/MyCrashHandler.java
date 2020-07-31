package com.xuxiang.common.app;

import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 全局异常处理器
 */
public class MyCrashHandler implements UncaughtExceptionHandler {
    private static MyCrashHandler myCrashHandler;
    private Context context;

    private MyCrashHandler() {

    }

    public static synchronized MyCrashHandler getInstance() {
        if (myCrashHandler != null) {
            return myCrashHandler;
        } else {
            myCrashHandler = new MyCrashHandler();
            return myCrashHandler;
        }
    }

    public void init(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread t, Throwable th) {
        try {
            String errorinfo = getErrorInfo(th);
            String headstring = "";
            int nIndex = -1;
            if ((nIndex = errorinfo.indexOf("Caused by:")) >= 0) {
                String ssString = errorinfo.substring(nIndex);
                String[] ss = ssString.split("\n\t");
                headstring = ss[0] + "\n\t" + ss[1] + "\n\t" + ss[2] + "\n\t";
            }
            String stacktrace =  headstring + errorinfo;
            Log.e("CrashHandler", headstring + errorinfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getErrorInfo(Throwable t) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        t.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

}