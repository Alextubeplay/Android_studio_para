package com.rgncompany.book.theme;

import com.rgncompany.book.R;

public class AppTheme {
    private String id;
    public int backgroundColor = R.color.white;
    public int rectangleColor = R.color.white;
    public int buttonColor = R.color.white;
    public int selectionColor = R.color.white;
    public int searchColor = R.color.white;
    public int textColor = R.color.black;
    public int iconColor = R.color.black;
    public int icon = R.drawable.sun;

    public AppTheme(String id) {
        this.id = id;
    }

    public AppTheme setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public AppTheme setRectangleColor(int rectangleColor) {
        this.rectangleColor = rectangleColor;
        return this;
    }

    public AppTheme setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
        return this;
    }

    public AppTheme setSelectionColor(int selectionColor) {
        this.selectionColor = selectionColor;
        return this;
    }

    public AppTheme setSearchColor(int searchColor) {
        this.searchColor = searchColor;
        return this;
    }

    public AppTheme setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public AppTheme setIconColor(int iconColor) {
        this.iconColor = iconColor;
        return this;
    }

    public AppTheme setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getId() {
        return id;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getRectangleColor() {
        return rectangleColor;
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public int getSelectionColor() {
        return selectionColor;
    }

    public int getSearchColor() {
        return searchColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getIconColor() {
        return iconColor;
    }

    public int getIcon() {
        return icon;
    }
}
