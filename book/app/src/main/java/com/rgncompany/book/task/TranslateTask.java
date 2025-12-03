package com.rgncompany.book.task;

import android.os.AsyncTask;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.AppTranslator;
import com.rgncompany.book.activity.BookActivity;
import com.rgncompany.book.book.Page;

public class TranslateTask extends AsyncTask<Void, Integer, Void> {

    public BookActivity activity;
    public Page page;
    public String text;

    public TranslateTask(BookActivity activity, Page page) {
        this.activity = activity;
        this.page = page;
    }

    @Override
    protected Void doInBackground(Void... unused) {
        text = AppTranslator.translate(BookActivity.text, AppSettings.currentLanguageTranslateFrom.getId(), AppSettings.currentLanguageTranslateTo.getId());
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... items) {

    }

    @Override
    protected void onPostExecute(Void unused) {
        String name = AppSettings.currentLanguageTranslateTo.getId();
        name = name.replace('-', '_');
        name = name.toLowerCase();
        page.addTranslate(name, text);
        AppSettings.isTranslateTask = false;
        activity.bookText.setText(text);
    }
}