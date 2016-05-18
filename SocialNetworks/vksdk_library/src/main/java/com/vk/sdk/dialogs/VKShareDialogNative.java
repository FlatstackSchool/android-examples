package com.vk.sdk.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Ilya Eremin on 4/5/16.
 */
public class VKShareDialogNative extends DialogFragment implements VKShareDialogDelegate.DialogFragmentI {

    private VKShareDialogDelegate mDelegate = new VKShareDialogDelegate(this);

    public VKShareDialogNative() {
    }

    @SuppressLint("ValidFragment")
    VKShareDialogNative(VKShareDialogBuilder builder) {
        mDelegate.setAttachmentImages(builder.attachmentImages);
        mDelegate.setText(builder.attachmentText);
        if (builder.linkTitle != null && builder.linkUrl != null) {
            mDelegate.setAttachmentLink(builder.linkTitle, builder.linkUrl);
        }
        mDelegate.setUploadedPhotos(builder.existingPhotos);
        mDelegate.setShareDialogListener(builder.listener);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return mDelegate.onCreateDialog(savedInstanceState);
    }

    @Override
    @SuppressLint("NewApi")
    public void onStart() {
        super.onStart();
        mDelegate.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mDelegate.onCancel(dialog);
    }
}
