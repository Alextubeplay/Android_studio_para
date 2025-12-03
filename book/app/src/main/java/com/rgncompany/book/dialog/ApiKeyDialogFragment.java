package com.rgncompany.book.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.activity.StandardActivity;

public class ApiKeyDialogFragment extends SettingsDialogFragment {

    public ApiKeyDialogFragment(StandardActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        EditText input = new EditText(activity);
        input.setText(String.valueOf(AppSettings.API_KEY));

        builder.setTitle(R.string.settings_api_key)
                .setView(input)
                .setPositiveButton(R.string.settings_ok, (dialog, whichButton) -> {
                    AppSettings.API_KEY = String.valueOf(input.getText());
                    activity.saveSettings();
                    setTheme();
                    openSettingsDialog();
                });

        Dialog dialog = builder.create();
        return dialog;
    }
}
