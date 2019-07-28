package com.example.pub.Utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;

import com.example.pub.R;

public class FullSizePhotoDialog extends DialogFragment {
    private String fileName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileName = (String) getArguments().get("photo");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.full_size_dialog);
        ImageView Photo = getDialog().findViewById(R.id.photo);
        Photo.setImageURI(Uri.parse(fileName));
        return dialog;
    }
}
