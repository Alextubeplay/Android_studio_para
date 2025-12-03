package com.rgncompany.book;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.rgncompany.book.book.Book;
import com.rgncompany.book.language.AppLanguage;
import com.rgncompany.book.language.AppLanguages;
import com.rgncompany.book.theme.AppTheme;
import com.rgncompany.book.theme.AppThemes;

import java.util.ArrayList;
import java.util.List;

public class AppSettings {
    private static boolean isInit = false;
    public static final String APP_PREFERENCES = "apppref";
    public static String API_KEY;
    public static AppTheme currentTheme = AppThemes.DAY_THEME;
    public static AppLanguage currentLanguageTranslateFrom = AppLanguages.NONE;
    public static AppLanguage currentLanguageTranslateTo = AppLanguages.EN;
    public static String appLanguage = "en";
    public static int textSize = 14;
    public static Book currentBook;
    public static List<Book> books = new ArrayList<>();
    public static boolean isTranslateTask = false;

    public static void init(SharedPreferences sharedPreferences) {
        if (!isInit) {
            AppThemes.init();
            AppLanguages.init();
            openSettings(sharedPreferences);
            isInit = true;
        }
    }

    public static SharedPreferences getSharedPreferences(Activity activity) {
        return activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void openSettings(SharedPreferences sharedPreferences) {
        String themeId = sharedPreferences.getString("appTheme", "day");
        String languageTranslateFromId = sharedPreferences.getString("currentLanguageTranslateFrom", "");
        String languageTranslateToId = sharedPreferences.getString("currentLanguageTranslateTo", "en");
        API_KEY = sharedPreferences.getString("API_KEY", "");
        appLanguage = sharedPreferences.getString("appLanguage", "en");
        textSize = sharedPreferences.getInt("textSize", 14);

        AppTheme theme = AppThemes.getTheme(themeId);
        if (theme != null) currentTheme = theme;

        AppLanguage languageTranslateFrom = AppLanguages.getLanguage(languageTranslateFromId);
        if (languageTranslateFrom != null) currentLanguageTranslateFrom = languageTranslateFrom;

        AppLanguage languageTranslateTo = AppLanguages.getLanguage(languageTranslateToId);
        if (languageTranslateTo != null) currentLanguageTranslateTo = languageTranslateTo;
    }

    public static void saveSettings(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appTheme", currentTheme.getId());
        editor.putString("currentLanguageTranslateFrom", currentLanguageTranslateFrom.getId());
        editor.putString("currentLanguageTranslateTo", currentLanguageTranslateTo.getId());
        editor.putString("API_KEY", API_KEY);
        editor.putString("appLanguage", appLanguage);
        editor.putInt("textSize", textSize);
        editor.apply();
    }
}
