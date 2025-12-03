package com.rgncompany.book.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.activity.StandardActivity;

import java.util.ArrayList;
import java.util.List;

public class LanguageDialogFragment extends SettingsDialogFragment {

    public LanguageDialogFragment(StandardActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        List<String> list = new ArrayList<>();

        list.add(getResources().getString(R.string.settings_language_en) + " 'en'");
        list.add(getResources().getString(R.string.settings_language_ru) + " 'ru'");

        String[] array = list.toArray(new String[0]);

        builder.setTitle(R.string.settings_language)
                .setItems(array, (dialog, which) -> {
                    switch (which) {
                        case 0: {
                            AppSettings.appLanguage = "en";
                            break;
                        }
                        case 1: {
                            AppSettings.appLanguage = "ru";
                            break;
                        }
                    }
                    activity.saveSettings();
                    setTheme();
                    activity.openMainPage();
                });
        Dialog dialog = builder.create();
        return dialog;
    }
}
