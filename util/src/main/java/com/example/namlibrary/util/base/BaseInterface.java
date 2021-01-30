package com.example.namlibrary.util.base;

public class BaseInterface {
    public interface AdapterClick<T> {
        void click(T t);
    }

    public interface AdapterClick2<T> {
        void click(T t, int pos);
    }

    public interface AdapterLongClick<T> {
        void longClick(T t);
    }

    public interface Continuation {
        void runContinue();
    }

    public interface ContinuationT<T> {
        void runContinue(T t);
    }
}
