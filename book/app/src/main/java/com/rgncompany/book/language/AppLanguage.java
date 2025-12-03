package com.rgncompany.book.language;

import com.rgncompany.book.R;

public class AppLanguage {
    private String id;
    public int name = R.string.app_name;

    public AppLanguage(String id) {
        this.id = id;
    }

    public AppLanguage setNam(int name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public int getName() {
        return name;
    }
}
