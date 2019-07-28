package com.example.pub.Utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.example.pub.R;

public class GpsSettingsDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(getResources().getString(R.string.gps_settings));
        builder.setPositiveButton("Включить", (dialog, which) -> {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 143);
            getDialog().dismiss();
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> getDialog().dismiss());
        return builder.create();
    }
}
