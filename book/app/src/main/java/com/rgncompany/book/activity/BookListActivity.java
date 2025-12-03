package com.rgncompany.book.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import com.rgncompany.book.R;

public class BookListActivity extends StandardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_list);
        init();

        bookmarksButton.setOnClickListener(view -> openBookmarksPage());
        mainButton.setOnClickListener(view -> openMainPage());
        sidebarButton.setOnClickListener(view -> openDrawer());
        setTheme();
    }
}