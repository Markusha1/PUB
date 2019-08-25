package com.example.pub.utilities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.pub.R;

public class PhotoDialog extends DialogFragment {
    PhotoClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (PhotoClickListener) context;
    }

    public interface PhotoClickListener{
        void cameraOnClick();
        void galleryClick();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.photo_dialog, null);
        builder.setView(layout);
        Button mCamera = layout.findViewById(R.id.camera);
        Button mGallery = layout.findViewById(R.id.gallery);
        mGallery.setOnClickListener(c -> listener.galleryClick());
        mCamera.setOnClickListener(c -> listener.cameraOnClick());
        builder.setNegativeButton(R.string.cancel_dialog, (dialog, which) -> getDialog().cancel());
        return builder.create();
    }
}
