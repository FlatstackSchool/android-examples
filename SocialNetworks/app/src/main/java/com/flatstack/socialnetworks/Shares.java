package com.flatstack.socialnetworks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.flatstack.socialnetworks.utils.ImageDownloader;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class Shares {

    public static void facebook(@NonNull String link,
                                @NonNull String title,
                                @Nullable String uriToImage,
                                @NonNull Activity activity) {
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        ShareDialog shareDialog = new ShareDialog(activity);
        ShareLinkContent.Builder builder = new ShareLinkContent.Builder()
            .setContentTitle(title)
            .setContentUrl(Uri.parse(link));
        if (!TextUtils.isEmpty(uriToImage)) {
            builder.setImageUrl(Uri.parse(uriToImage));
        }
        shareDialog.show(builder.build());
    }

    public static void facebook(String link, Activity activity) {
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        ShareDialog shareDialog = new ShareDialog(activity);
        ShareLinkContent content = new ShareLinkContent.Builder()
            .setContentUrl(Uri.parse(link)).build();
        shareDialog.show(content);
    }

    /**
     * We have problem here!
     * We can't share web links or resources - only absolute path to local images (and you can't use resources or assets images)!
     *
     * @param uriToImage The image Uri should be a file Uri (i.e. file://absolute_path scheme) to a local file.
     */
    public static void twitter(@NonNull String link,
                               @NonNull String title,
                               @Nullable Uri uriToImage,
                               @NonNull Context context) {
        try {
            TweetComposer.Builder intentBuilder = new TweetComposer.Builder(context)
                .url(new URL(link))
                .text(title);
            if (uriToImage != null) {
                intentBuilder.image(uriToImage); // link to image into gallary, you cant use external link to http and similar
            }
            context.startActivity(intentBuilder.createIntent());
        } catch (MalformedURLException e) {
            Toast.makeText(context, "Bad link: " + link, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void vk(@NonNull final String link,
                          @NonNull final String title,
                          @Nullable final String urlToImage,
                          final Activity context) {
        VKAccessToken token = VKAccessToken.currentToken();
        if (token == null) {
            Navigator.vkAuthAndShare(link, title, urlToImage, context);
            Toast.makeText(context, "You need to login via VK first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(urlToImage)) {
            // because I did not import RxJava
            final Handler mainHandler = new Handler(context.getMainLooper());
            Toast.makeText(context, "Wait matherfaca", Toast.LENGTH_SHORT).show();
            AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
                @Override public void run() {
                    final Bitmap snoopyBitmap = ImageDownloader.download(urlToImage, context);
                    mainHandler.post(new Runnable() {
                        @Override public void run() {
                            _vk(link, title, snoopyBitmap, context);
                        }
                    });
                }
            });
        } else {
            _vk(link, title, null, context);
        }
    }

    private static void _vk(@NonNull String link,
                            @NonNull String title,
                            @Nullable Bitmap bitmap,
                            final Activity context) {
        VKShareDialogBuilder vkShareDialogBuilder = new VKShareDialogBuilder()
            .setAttachmentLink(title, link)
            .setText(title);
        if (bitmap != null) {
            vkShareDialogBuilder
                .setAttachmentImages(new VKUploadImage[]{
                    new VKUploadImage(bitmap, VKImageParameters.jpgImage(.9f /* compression ratio */))
                });
        }
        vkShareDialogBuilder
            .setShareDialogListener(new VKShareDialogBuilder.VKShareDialogListener() {
                @Override public void onVkShareComplete(int postId) {
                    Toast.makeText(context, "Shared in vk with post id: " + postId, Toast.LENGTH_SHORT).show();
                }

                @Override public void onVkShareCancel() {
                }

                @Override public void onVkShareError(VKError error) {
                    Toast.makeText(context, "share error" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            })
            .show(((FragmentActivity) context).getSupportFragmentManager(), "VK_SHARE")
        ;
    }
}
