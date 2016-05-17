package com.vk.sdk.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.photo.VKUploadImage;

/**
 * Created by Ilya Eremin on 4/5/16.
 */
public class VKShareDialog extends DialogFragment implements VKShareDialogDelegate.DialogFragmentI {

    private VKShareDialogDelegate mDelegate = new VKShareDialogDelegate(this);

    /** Use VKShareDialogBuilder */
    @Deprecated
    public VKShareDialog() {
    }

    @SuppressLint("ValidFragment") VKShareDialog(VKShareDialogBuilder builder) {
        mDelegate.setAttachmentImages(builder.attachmentImages);
        mDelegate.setText(builder.attachmentText);
        if (builder.linkTitle != null && builder.linkUrl != null) {
            mDelegate.setAttachmentLink(builder.linkTitle, builder.linkUrl);
        }
        mDelegate.setUploadedPhotos(builder.existingPhotos);
        mDelegate.setShareDialogListener(builder.listener);
    }

    /**
     * Sets images that will be uploaded with post
     *
     * @param images array of VKUploadImage objects with image data and upload parameters
     * @return Returns this dialog for chaining
     */
    public VKShareDialog setAttachmentImages(VKUploadImage[] images) {
        mDelegate.setAttachmentImages(images);
        return this;
    }

    /**
     * Sets this dialog post text. User can change that text
     *
     * @param textToPost Text for post
     * @return Returns this dialog for chaining
     */
    public VKShareDialog setText(CharSequence textToPost) {
        mDelegate.setText(textToPost);
        return this;
    }

    /**
     * Sets dialog link with link name
     *
     * @param linkTitle A small description for your link
     * @param linkUrl   Url that link follows
     * @return Returns this dialog for chaining
     */
    public VKShareDialog setAttachmentLink(String linkTitle, String linkUrl) {
        mDelegate.setAttachmentLink(linkTitle, linkUrl);
        return this;
    }

    /**
     * Sets array of already uploaded photos from VK, that will be attached to post
     *
     * @param photos Prepared array of {@link VKApiPhoto} objects
     * @return Returns this dialog for chaining
     */
    public VKShareDialog setUploadedPhotos(VKPhotoArray photos) {
        mDelegate.setUploadedPhotos(photos);
        return this;
    }

    /**
     * Sets this dialog listener
     *
     * @param listener {@link VKShareDialogListener} object
     * @return Returns this dialog for chaining
     */
    public VKShareDialog setShareDialogListener(VKShareDialogListener listener) {
        mDelegate.setShareDialogListener(listener);
        return this;
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

    public interface VKShareDialogListener extends VKShareDialogBuilder.VKShareDialogListener {
    }
}