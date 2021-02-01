package com.example.namlibrary.util.api;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class APITranslate {
    String resp = null;
    String url = null;
    String langFrom;
    String langTo;
    String word;

    public APITranslate(String text) {
        this.langFrom = Language.AUTO_DETECT;
        this.langTo = Language.VIETNAMESE;
        this.word = text;

        new Async().execute();
    }

    public APITranslate(String langFrom, String langTo, String text) {
        this.langFrom = langFrom;
        this.langTo = langTo;
        this.word = text;

        new Async().execute();
    }

    class Async extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" +
                        langFrom + "&tl=" + langTo + "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                resp = response.toString();
            } catch (Exception ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            StringBuilder temp = new StringBuilder();

            if (resp == null) listener.onFailure("Network Error");
            else {
                try {
                    JSONArray main = new JSONArray(resp);
                    JSONArray total = (JSONArray) main.get(0);
                    for (int i = 0; i < total.length(); i++) {
                        JSONArray currentLine = (JSONArray) total.get(i);
                        temp.append(currentLine.get(0).toString());
                    }

                    if (temp.length() > 2) {
                        listener.onSuccess(temp.toString());
                    } else {
                        listener.onFailure("Invalid Input String");
                    }
                } catch (JSONException e) {
                    listener.onFailure(e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private TranslateResult listener;

    public void getTranslateResult(TranslateResult listener) {
        this.listener = listener;
    }

    public interface TranslateResult {
        void onSuccess(String translatedText);

        void onFailure(String errorText);
    }

    public static class Language {
        public static final String AUTO_DETECT = "auto";
        public static final String AFRIKAANS = "af";
        public static final String ALBANIAN = "sq";
        public static final String ARABIC = "ar";
        public static final String ARMENIAN = "hy";
        public static final String AZERBAIJANI = "az";
        public static final String BASQUE = "eu";
        public static final String BELARUSIAN = "be";
        public static final String BENGALI = "bn";
        public static final String BULGARIAN = "bg";
        public static final String CATALAN = "ca";
        public static final String CHINESE = "zh-CN";
        public static final String CROATIAN = "hr";
        public static final String CZECH = "cs";
        public static final String DANISH = "da";
        public static final String DUTCH = "nl";
        public static final String ENGLISH = "en";
        public static final String ESTONIAN = "et";
        public static final String FILIPINO = "tl";
        public static final String FINNISH = "fi";
        public static final String FRENCH = "fr";
        public static final String GALICIAN = "gl";
        public static final String GEORGIAN = "ka";
        public static final String GERMAN = "de";
        public static final String GREEK = "el";
        public static final String GUJARATI = "gu";
        public static final String HAITIAN_CREOLE = "ht";
        public static final String HEBREW = "iw";
        public static final String HINDI = "hi";
        public static final String HUNGARIAN = "hu";
        public static final String ICELANDIC = "is";
        public static final String INDONESIAN = "id";
        public static final String IRISH = "ga";
        public static final String ITALIAN = "it";
        public static final String JAPANESE = "ja";
        public static final String KANNADA = "kn";
        public static final String KOREAN = "ko";
        public static final String LATIN = "la";
        public static final String LATVIAN = "lv";
        public static final String LITHUANIAN = "lt";
        public static final String MACEDONIAN = "mk";
        public static final String MALAY = "ms";
        public static final String MALTESE = "mt";
        public static final String NORWEGIAN = "no";
        public static final String PERSIAN = "fa";
        public static final String POLISH = "pl";
        public static final String PORTUGUESE = "pt";
        public static final String ROMANIAN = "ro";
        public static final String RUSSIAN = "ru";
        public static final String SERBIAN = "sr";
        public static final String SLOVAK = "sk";
        public static final String SLOVENIAN = "sl";
        public static final String SPANISH = "es";
        public static final String SWAHILI = "sw";
        public static final String SWEDISH = "sv";
        public static final String TAMIL = "ta";
        public static final String TELUGU = "te";
        public static final String THAI = "th";
        public static final String TURKISH = "tr";
        public static final String UKRAINIAN = "uk";
        public static final String URDU = "ur";
        public static final String VIETNAMESE = "vi";
        public static final String WELSH = "cy";
        public static final String YIDDISH = "yi";
        public static final String CHINESE_SIMPLIFIED = "zh-CN";
        public static final String CHINESE_TRADITIONAL = "zh-TW";
    }
}
