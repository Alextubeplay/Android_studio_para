package com.rgncompany.book.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import com.rgncompany.book.R;

public class BookmarksActivity extends StandardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookmarks);
        init();

        mainButton.setOnClickListener(view -> openMainPage());
        bookListButton.setOnClickListener(view -> openBookListPage());
        sidebarButton.setOnClickListener(view -> openDrawer());
        setTheme();
    }
}