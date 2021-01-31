package com.example.namlibrary.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.HandlerThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThread {
    public interface ITFexecute<T> {
        T run();
    }

    public interface ITFexecute_pd<T1, T2> {
        T2 run(T1 t1);
    }

    public interface ITFResult<T> {
        void onSuccess(T result);

        void onFailed(Exception e);
    }

    public static <T> void execute(Activity activity, ITFexecute<T> itf, ITFResult<T> itfResult) {
        new HandlerThread("UIHandler") {
            @Override
            public void run() {
                // xử lý các sự kiện liên quan tới mạng và luồng tại đây
                T r = itf.run();

                activity.runOnUiThread(() -> {
                    // xử lý các sự kiện liên quan tới getFromDb dữ liệu, set view... tại đây
                    if (r == null) itfResult.onFailed(new NullPointerException());
                    else itfResult.onSuccess(r);
                });
            }
        }.start();
    }

    // nguyên mẫu hàm
    // hàm này không được phép gọi trong hàm run(), chỉ được gọi trong 1 hàm bình thường như runOnUiThread, ...
    public static <T1, T2> void execute_with_pd(final Activity activity, final ArrayList<T1> ts, String dialogMessage, final MultiThread.ITFexecute_pd<T1, T2> itf, MultiThread.ITFResult<ArrayList<T2>> itfResult) {
        final ProgressDialog prgDialog;
        final int[] x = {0};
        prgDialog = new ProgressDialog(activity);
        prgDialog.setMessage(dialogMessage);
        prgDialog.setIndeterminate(false);
        prgDialog.setMax(ts.size());
        prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prgDialog.setCancelable(false);
        prgDialog.show();
        final HashMap<Object, Object> hm = new HashMap<>();
        new HandlerThread("UIHandler") {
            @Override
            public void run() {
                ExecutorService es = Executors.newCachedThreadPool();
                for (final T1 t1 : ts) {
                    es.execute(() -> {
                        final CountDownLatch latch = new CountDownLatch(1);
                        new HandlerThread("UIHandler") {
                            @Override
                            public void run() {
                                hm.put(t1, itf.run(t1));
                                latch.countDown();
                            }
                        }.start();
                        try {
                            latch.await();
                            prgDialog.setProgress(++x[0]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
                es.shutdown();
                try {
                    es.awaitTermination(10, TimeUnit.MINUTES);
                } catch (InterruptedException ignored) {
                }
                activity.runOnUiThread(() -> {
                    ArrayList<T2> r = new ArrayList<>();
                    for (T1 t1 : ts) {
                        T2 value = (T2) hm.get(t1);
                        if (value != null)
                            r.add(value);
                    }
                    prgDialog.dismiss();
                    if (r.size() == 0) itfResult.onFailed(new ArrayIndexOutOfBoundsException());
                    else itfResult.onSuccess(r);
                });
            }
        }.start();
    }

    public static <T1, T2> void execute_with_pd_in_bg(final Activity activity, final ArrayList<T1> ts, final MultiThread.ITFexecute_pd<T1, T2> itf, MultiThread.ITFResult<ArrayList<T2>> itfResult) {
        final HashMap<Object, Object> hm = new HashMap<>();
        new HandlerThread("UIHandler") {
            @Override
            public void run() {
                ExecutorService es = Executors.newCachedThreadPool();
                for (final T1 t1 : ts) {
                    es.execute(() -> {
                        final CountDownLatch latch = new CountDownLatch(1);
                        new HandlerThread("UIHandler") {
                            @Override
                            public void run() {
                                hm.put(t1, itf.run(t1));
                                latch.countDown();
                            }
                        }.start();
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
                es.shutdown();
                try {
                    es.awaitTermination(10, TimeUnit.MINUTES);
                } catch (InterruptedException ignored) {
                }
                activity.runOnUiThread(() -> {
                    ArrayList<T2> r = new ArrayList<>();
                    for (T1 t1 : ts) {
                        T2 value = (T2) hm.get(t1);
                        if (value != null)
                            r.add(value);
                    }
                    if (r.size() == 0) itfResult.onFailed(new ArrayIndexOutOfBoundsException());
                    else itfResult.onSuccess(r);
                });
            }
        }.start();
    }
}
