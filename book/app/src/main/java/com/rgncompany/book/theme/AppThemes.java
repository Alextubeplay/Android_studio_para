package com.rgncompany.book.theme;

import com.rgncompany.book.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppThemes {
    private static Map<String, AppTheme> themeMap = new HashMap<>();
    private static List<AppTheme> themeList = new ArrayList<>();

    public static AppTheme WHITE_THEME = new AppTheme("white")
            .setBackgroundColor(R.color.white_theme_background)
            .setRectangleColor(R.color.white_theme_rectangle)
            .setButtonColor(R.color.white_theme_button)
            .setSelectionColor(R.color.white_theme_selection)
            .setSearchColor(R.color.white_theme_search)
            .setTextColor(R.color.black)
            .setIconColor(R.color.black)
            .setIcon(R.drawable.sun);
    public static AppTheme BLACK_THEME = new AppTheme("black")
            .setBackgroundColor(R.color.black_theme_background)
            .setRectangleColor(R.color.black_theme_rectangle)
            .setButtonColor(R.color.black_theme_button)
            .setSelectionColor(R.color.black_theme_selection)
            .setSearchColor(R.color.black_theme_search)
            .setTextColor(R.color.white)
            .setIconColor(R.color.white)
            .setIcon(R.drawable.moon);
    public static AppTheme DAY_THEME = new AppTheme("day")
            .setBackgroundColor(R.color.day_theme_background)
            .setRectangleColor(R.color.day_theme_rectangle)
            .setButtonColor(R.color.day_theme_button)
            .setSelectionColor(R.color.day_theme_selection)
            .setSearchColor(R.color.day_theme_search)
            .setTextColor(R.color.black)
            .setIconColor(R.color.black)
            .setIcon(R.drawable.sun);
    public static AppTheme NIGHT_THEME = new AppTheme("night")
            .setBackgroundColor(R.color.night_theme_background)
            .setRectangleColor(R.color.night_theme_rectangle)
            .setButtonColor(R.color.night_theme_button)
            .setSelectionColor(R.color.night_theme_selection)
            .setSearchColor(R.color.night_theme_search)
            .setTextColor(R.color.white)
            .setIconColor(R.color.white)
            .setIcon(R.drawable.moon);

    public static void init() {
        register(WHITE_THEME);
        register(BLACK_THEME);
        register(DAY_THEME);
        register(NIGHT_THEME);
    }

    public static void register(AppTheme theme) {
        themeMap.put(theme.getId(), theme);
        themeList.add(theme);
    }

    public static AppTheme getTheme(String id) {
        return themeMap.get(id);
    }

    public static Map<String, AppTheme> getThemeMap() {
        return themeMap;
    }

    public static List<AppTheme> getThemeList() {
        return themeList;
    }
}
