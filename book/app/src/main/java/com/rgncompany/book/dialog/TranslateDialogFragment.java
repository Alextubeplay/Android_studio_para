package com.rgncompany.book.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.activity.BookActivity;
import com.rgncompany.book.book.Page;
import com.rgncompany.book.task.TranslateTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TranslateDialogFragment extends DialogFragment {

    public BookActivity activity;

    public TranslateDialogFragment(BookActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        List<String> list = new ArrayList<>();

        if (AppSettings.currentBook != null) {
            Page page = AppSettings.currentBook.getCurrentPage();
            if (page != null) {
                list.add(getResources().getString(R.string.settings_language_original));
                for (String lang : page.langFiles.keySet()) {
                    String name = "language_" + lang;
                    list.add(getResources().getString(activity.getResId(name, R.string.class)) + " '" + lang + "'");
                }
                list.add(getResources().getString(R.string.menu_translate));
            }
        }

        String[] array = list.toArray(new String[0]);

        builder.setTitle(R.string.menu_translate)
                .setItems(array, (dialog, which) -> {
                    if (AppSettings.currentBook != null) {
                        Page page = AppSettings.currentBook.getCurrentPage();
                        if (page != null) {
                            Set<String> set = page.langFiles.keySet();
                            List<String> langs = new ArrayList<>();
                            langs.addAll(set);
                            if (which < 1 + page.langFiles.size()) {
                                if (which == 0) {
                                    activity.bookText.setText(page.getText());
                                } else {
                                    activity.bookText.setText(page.getText(page.langFiles.get(langs.get(which - 1))));
                                }
                            } else {
                                String name = AppSettings.currentLanguageTranslateTo.getId();
                                name = name.replace('-', '_');
                                name = name.toLowerCase();
                                if (!page.langFiles.containsKey(name)) {
                                    if (!AppSettings.isTranslateTask) {
                                        AppSettings.isTranslateTask = true;
                                        new TranslateTask(activity, page).execute();
                                    }
                                }
                            }
                        }
                    }
                });
        Dialog dialog = builder.create();
        return dialog;
    }
}
