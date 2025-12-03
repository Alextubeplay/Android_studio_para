package com.rgncompany.book.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.activity.StandardActivity;

public class FontSizeDialogFragment extends SettingsDialogFragment {

    public FontSizeDialogFragment(StandardActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        EditText input = new EditText(activity);
        input.setText(String.valueOf(AppSettings.textSize));

        builder.setTitle(R.string.settings_font_size)
                .setView(input)
                .setPositiveButton(R.string.settings_ok, (dialog, whichButton) -> {
                    String value = String.valueOf(input.getText());
                    AppSettings.textSize = Math.abs(Integer.parseInt(value));
                    if (AppSettings.textSize <= 0) AppSettings.textSize = 1;
                    activity.saveSettings();
                    setTheme();
                    openSettingsDialog();
                })
                .setNeutralButton(R.string.settings_reset, (dialog, whichButton) -> {
                    AppSettings.textSize = 14;
                    activity.saveSettings();
                    setTheme();
                    openSettingsDialog();
                });

        Dialog dialog = builder.create();
        return dialog;
    }
}
