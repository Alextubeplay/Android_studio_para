package com.rgncompany.book.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.activity.StandardActivity;
import com.rgncompany.book.language.AppLanguage;
import com.rgncompany.book.language.AppLanguages;

import java.util.ArrayList;
import java.util.List;

public class TranslateLanguageDialogFragment extends SettingsDialogFragment {

    public boolean from;

    public TranslateLanguageDialogFragment(StandardActivity activity, boolean from) {
        super(activity);
        this.from = from;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        List<String> list = new ArrayList<>();

        for (AppLanguage language : AppLanguages.getLanguageList()) {
            String name = "language_" + language.getId();
            if (language.getId().isEmpty()) {
                name = "language_none";
            } else {
                name = name.replace('-', '_');
                name = name.toLowerCase();
            }
            list.add(getResources().getString(activity.getResId(name, R.string.class)) + " '" + language.getId() + "'");
        }
        list.add(getResources().getString(R.string.settings_reset));

        String[] array = list.toArray(new String[0]);

        builder.setTitle(from ? R.string.settings_language_source : R.string.settings_language_translate)
                .setItems(array, (dialog, which) -> {
                    if (which < AppLanguages.getLanguageList().size()) {
                        AppLanguage language = AppLanguages.getLanguageList().get(which);
                        if (from) {
                            AppSettings.currentLanguageTranslateFrom = language;
                        } else {
                            AppSettings.currentLanguageTranslateTo = language;
                        }
                        if (AppSettings.currentLanguageTranslateTo == AppLanguages.NONE) AppSettings.currentLanguageTranslateTo = AppLanguages.EN;
                    } else {
                        if (from) {
                            AppSettings.currentLanguageTranslateFrom = AppLanguages.NONE;
                        } else {
                            AppSettings.currentLanguageTranslateTo = AppLanguages.EN;
                        }
                    }
                    activity.saveSettings();
                    setTheme();
                    openSettingsDialog();
                });
        Dialog dialog = builder.create();
        return dialog;
    }
}
