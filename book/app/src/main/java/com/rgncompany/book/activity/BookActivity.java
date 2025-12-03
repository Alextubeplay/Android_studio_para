package com.rgncompany.book.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.FragmentManager;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.book.Page;
import com.rgncompany.book.dialog.TranslateDialogFragment;
import com.rgncompany.book.theme.AppTheme;

public class BookActivity extends StandardActivity {

    public TextView bookText;
    public static String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        init();

        bookText = findViewById(R.id.book_text);
        if (AppSettings.currentBook != null) {
            Page page = AppSettings.currentBook.getCurrentPage();
            if (page != null) {
                text = page.getText();
            }
        }
        bookText.setText(text);

        setTheme();
    }

    @Override
    public boolean isMain() {
        return false;
    }

    @Override
    public void setTheme(AppTheme theme) {
        super.setTheme(theme);
        Context context = getApplicationContext();
        bookText.setTextColor(getColorStateList(context, theme.getTextColor()));
        bookText.setTextSize(AppSettings.textSize);
    }

    @Override
    public void translateText() {
        FragmentManager manager = getSupportFragmentManager();
        TranslateDialogFragment translateDialogFragment = new TranslateDialogFragment(this);
        translateDialogFragment.show(manager, "settings");
    }
}