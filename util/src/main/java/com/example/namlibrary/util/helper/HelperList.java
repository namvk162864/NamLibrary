package com.example.namlibrary.util.helper;

import java.util.ArrayList;
import java.util.Set;

public class HelperList {
    public static <T> boolean listInList(ArrayList<T> childList, ArrayList<T> parentList) {
        for (T t : childList) if (!parentList.contains(t)) return false;
        return true;
    }

    public static <T> boolean listInSet(ArrayList<T> childList, Set<T> parentSet) {
        return listInList(childList, new ArrayList<>(parentSet));
    }

    public static <T> boolean equal(ArrayList<T> list1, ArrayList<T> list2) {
        if (listInList(list1, list2)) return listInList(list2, list1);
        return false;
    }

    public static <T> boolean equal(ArrayList<T> list, Set<T> set) {
        return equal(list, new ArrayList<T>(set));
    }
}
