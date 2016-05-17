package com.vk.sdk.dialogs;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.os.Build;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.photo.VKUploadImage;

/**
 * Created by Ilya Eremin on 4/5/16.
 */
public class VKShareDialogBuilder {

    String                linkTitle;
    String                linkUrl;
    VKUploadImage[]       attachmentImages;
    VKPhotoArray          existingPhotos;
    CharSequence          attachmentText;
    VKShareDialogListener listener;

    /**
     * Sets images that will be uploaded with post
     *
     * @param images array of VKUploadImage objects with image data and upload parameters
     * @return Returns this dialog for chaining
     */
    public VKShareDialogBuilder setAttachmentImages(VKUploadImage[] images) {
        this.attachmentImages = images;
        return this;
    }

    /**
     * Sets this dialog post text. User can change that text
     *
     * @param textToPost Text for post
     * @return Returns this dialog for chaining
     */
    public VKShareDialogBuilder setText(CharSequence textToPost) {
        this.attachmentText = textToPost;
        return this;
    }

    /**
     * Sets dialog link with link name
     *
     * @param linkTitle A small description for your link
     * @param linkUrl   Url that link follows
     * @return Returns this dialog for chaining
     */
    public VKShareDialogBuilder setAttachmentLink(String linkTitle, String linkUrl) {
        this.linkTitle = linkTitle;
        this.linkUrl = linkUrl;
        return this;
    }

    /**
     * Sets this dialog listener
     *
     * @param listener {@link VKShareDialogListener} object
     * @return Returns this dialog for chaining
     */
    public VKShareDialogBuilder setShareDialogListener(VKShareDialogListener listener) {
        this.listener = listener;
        return this;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void show(FragmentManager manager, String tag) {
        new VKShareDialogNative(this).show(manager, tag);
    }

    public void show(android.support.v4.app.FragmentManager manager, String tag) {
        new VKShareDialog(this).show(manager, tag);
    }

    public interface VKShareDialogListener {
        void onVkShareComplete(int postId);

        void onVkShareCancel();

        void onVkShareError(VKError error);
    }
}
