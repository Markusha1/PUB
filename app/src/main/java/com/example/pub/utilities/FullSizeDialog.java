package com.example.pub.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.pub.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullSizeDialog extends DialogFragment {
    private String Uri;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.fullsize_dialog, null);
        builder.setView(layout);
        PhotoView mPhoto = layout.findViewById(R.id.image);
        mPhoto.setImageURI(android.net.Uri.parse(Uri));
        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri = getArguments().getString("image");
    }
}
