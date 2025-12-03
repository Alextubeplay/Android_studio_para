package com.rgncompany.book.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.activity.StandardActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingsDialogFragment extends DialogFragment {

    public StandardActivity activity;

    public SettingsDialogFragment(StandardActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        List<String> list = new ArrayList<>();

        list.add(getResources().getString(R.string.settings_font_size) + ": " + AppSettings.textSize);
        list.add(getResources().getString(R.string.settings_language_app) + ": " +
                getResources().getString(R.string.settings_language) +
                " '" + AppSettings.appLanguage + "'");
        list.add(getResources().getString(R.string.settings_language_source) + ": " +
                AppSettings.currentLanguageTranslateFrom.getId() +
                " '" + AppSettings.currentLanguageTranslateFrom.getId() + "'");
        list.add(getResources().getString(R.string.settings_language_translate) + ": " +
                AppSettings.currentLanguageTranslateTo.getId() +
                " '" + AppSettings.currentLanguageTranslateTo.getId() + "'");
        list.add(getResources().getString(R.string.settings_api_key));

        String[] array = list.toArray(new String[0]);

        builder.setTitle(R.string.settings)
                .setItems(array, (dialog, which) -> {
                    FragmentManager manager = activity.getSupportFragmentManager();
                    switch (which) {
                        case 0: {
                            FontSizeDialogFragment settingsDialogFragment = new FontSizeDialogFragment(activity);
                            settingsDialogFragment.show(manager, "settingsFont");
                            break;
                        }
                        case 1: {
                            LanguageDialogFragment settingsDialogFragment = new LanguageDialogFragment(activity);
                            settingsDialogFragment.show(manager, "settingsLanguage");
                            break;
                        }
                        case 2: {
                            TranslateLanguageDialogFragment settingsDialogFragment = new TranslateLanguageDialogFragment(activity, true);
                            settingsDialogFragment.show(manager, "settingsLanguageFrom");
                            break;
                        }
                        case 3: {
                            TranslateLanguageDialogFragment settingsDialogFragment = new TranslateLanguageDialogFragment(activity, false);
                            settingsDialogFragment.show(manager, "settingsLanguageTo");
                            break;
                        }
                        case 4: {
                            ApiKeyDialogFragment settingsDialogFragment = new ApiKeyDialogFragment(activity);
                            settingsDialogFragment.show(manager, "settingsApiKey");
                            break;
                        }
                    }
                });
        Dialog dialog = builder.create();
        return dialog;
    }

    public void openSettingsDialog() {
        FragmentManager manager = activity.getSupportFragmentManager();
        SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment(activity);
        settingsDialogFragment.show(manager, "settings");
    }

    public void setTheme() {
        activity.setTheme();
    }

    public boolean isMain()  {
        return true;
    }
}
