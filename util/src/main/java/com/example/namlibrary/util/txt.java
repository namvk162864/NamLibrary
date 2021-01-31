package com.example.namlibrary.util;

import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class txt {
    private static final String modeClearAllFile = "w";

    public static void l2t(ArrayList<String> list, String path_txt) {
        l2t(list, path_txt, modeClearAllFile);
    }

    private static void l2t(ArrayList<String> list, String path_txt, String mode) {
        try {
            File file = new File(path_txt);
            if (!file.exists()) file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            if (mode.equals(modeClearAllFile)) {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.print("");
                printWriter.close();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                myOutWriter.append(String.join("\n", list));
            }
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (IOException ignored) {
        }
    }

    public static ArrayList<String> t2l(String path_txt) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(path_txt);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) list.add(line);
        } catch (IOException ignored) {
        }
        return list;
    }
}
