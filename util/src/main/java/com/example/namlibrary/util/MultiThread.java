package com.example.namlibrary.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

    public static <T> void execute(ITFexecute<T> itf, ITFResult<T> itfResult) {
        new MyAsync<>(itf, itfResult).execute();
    }

    public static <T1, T2> void execute_with_pd_in_bg(ArrayList<T1> ts, ITFexecute_pd<T1, T2> itf, ITFResult<ArrayList<T2>> itfResult) {
        new MyAsync2<>(ts, itf, itfResult).execute();
    }

    // nguyên mẫu hàm
    // hàm này không được phép gọi trong hàm run(), chỉ được gọi trong 1 hàm bình thường như runOnUiThread, ...
    public static <T1, T2> void execute_with_pd(Context context, ArrayList<T1> ts, String dialogMessage, ITFexecute_pd<T1, T2> itf, ITFResult<ArrayList<T2>> itfResult) {
        new MyAsync3<>(context, ts, dialogMessage, itf, itfResult).execute();
    }

    static class MyAsync<T> extends AsyncTask<Void, Void, T> {
        ITFexecute<T> itf;
        ITFResult<T> itfResult;

        public MyAsync(ITFexecute<T> itf, ITFResult<T> itfResult) {
            this.itf = itf;
            this.itfResult = itfResult;
        }

        @Override
        protected T doInBackground(Void... voids) {
            return itf.run();
        }

        @Override
        protected void onPostExecute(T t) {
            super.onPostExecute(t);
            if (t == null) itfResult.onFailed(new NullPointerException());
            else itfResult.onSuccess(t);
        }
    }

    static class MyAsync2<T1, T2> extends AsyncTask<Void, Void, Void> {
        ArrayList<T1> ts;
        ITFexecute_pd<T1, T2> itf;
        ITFResult<ArrayList<T2>> itfResult;
        HashMap<T1, T2> hm = new HashMap<>();

        public MyAsync2(ArrayList<T1> ts, ITFexecute_pd<T1, T2> itf, ITFResult<ArrayList<T2>> itfResult) {
            this.ts = ts;
            this.itf = itf;
            this.itfResult = itfResult;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExecutorService es = Executors.newCachedThreadPool();
            for (T1 t1 : ts) {
                es.execute(() -> {
                    CountDownLatch latch = new CountDownLatch(1);
                    new HandlerThread("UIHandler") {
                        @Override
                        public void run() {
                            hm.put(t1, itf.run(t1));
                            latch.countDown();
                        }
                    }.start();
                    try {
                        latch.await();
                    } catch (InterruptedException ignored) {
                    }
                });
            }
            es.shutdown();
            try {
                es.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<T2> r = new ArrayList<>();
            for (T1 t1 : ts) {
                T2 value = hm.get(t1);
                if (value != null) r.add(value);
            }
            if (r.size() == 0) itfResult.onFailed(new ArrayIndexOutOfBoundsException());
            else itfResult.onSuccess(r);
        }
    }

    static class MyAsync3<T1, T2> extends AsyncTask<Void, Void, Void> {
        Context context;
        ArrayList<T1> ts;
        String dialogMessage;
        ITFexecute_pd<T1, T2> itf;
        ITFResult<ArrayList<T2>> itfResult;
        ProgressDialog prgDialog;
        HashMap<T1, T2> hm = new HashMap<>();
        int x = 0;

        public MyAsync3(Context context, ArrayList<T1> ts, String dialogMessage, ITFexecute_pd<T1, T2> itf, ITFResult<ArrayList<T2>> itfResult) {
            this.context = context;
            this.ts = ts;
            this.dialogMessage = dialogMessage;
            this.itf = itf;
            this.itfResult = itfResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgDialog = new ProgressDialog(context);
            prgDialog.setMessage(dialogMessage);
            prgDialog.setIndeterminate(false);
            prgDialog.setMax(ts.size());
            prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            prgDialog.setCancelable(false);
            prgDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExecutorService es = Executors.newCachedThreadPool();
            for (T1 t1 : ts) {
                es.execute(() -> {
                    CountDownLatch latch = new CountDownLatch(1);
                    new HandlerThread("UIHandler") {
                        @Override
                        public void run() {
                            hm.put(t1, itf.run(t1));
                            latch.countDown();
                        }
                    }.start();
                    try {
                        latch.await();
                        prgDialog.setProgress(++x);
                    } catch (InterruptedException ignored) {
                    }
                });
            }
            es.shutdown();
            try {
                es.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<T2> r = new ArrayList<>();
            for (T1 t1 : ts) {
                T2 value = hm.get(t1);
                if (value != null) r.add(value);
            }
            prgDialog.dismiss();
            if (r.size() == 0) itfResult.onFailed(new ArrayIndexOutOfBoundsException());
            else itfResult.onSuccess(r);
        }
    }
}
