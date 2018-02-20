package com.stanislavveliky.criminalintent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by stan_ on 2/12/2018.
 */

public class CrimePictureFragment extends DialogFragment {
    private static final String ARG_FILE_NAME = "file_name";
    private static final String TAG = "CrimePictureFragment";
    private ImageView mImageView;

    public static CrimePictureFragment newInstance(String photoFileName)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILE_NAME, photoFileName);

        CrimePictureFragment fragment = new CrimePictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        String fileName = getArguments().getString(ARG_FILE_NAME);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.zoomed_picture, null);
        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //this is not DRY code but I am lazy and bad at programming
        if(externalFilesDir == null)
        {
           dismiss();
        }
        File pictureFile = new File(externalFilesDir, fileName);
        mImageView = view.findViewById(R.id.image_view_large);
        if(mImageView == null || !pictureFile.exists()) {

            mImageView.setImageDrawable(null);
        }
        else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(pictureFile.getPath(), getActivity());
            mImageView.setImageBitmap(bitmap);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        return alertDialogBuilder
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .create();
    }


}
