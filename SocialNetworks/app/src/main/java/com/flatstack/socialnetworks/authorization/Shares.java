package com.flatstack.socialnetworks.authorization;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ilya Eremin on 1/6/16.
 */
public class Shares {

    public static void facebook(String text, String link, Activity activity) {
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        ShareDialog shareDialog = new ShareDialog(activity);
        ShareLinkContent content = new ShareLinkContent.Builder()
            .setContentTitle(text)
            .setImageUrl(Uri.parse("http://www.flatstack.com/logo.svg"))
            .setContentUrl(Uri.parse(link)).build();
        shareDialog.show(content);
    }

    public static void facebookLocalPhoto(Activity activity, Bitmap bitmap){
        SharePhoto photo = new SharePhoto.Builder()
            .setBitmap(bitmap)
            .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
            .addPhoto(photo)
            .build();
        ShareDialog dialog = new ShareDialog(activity);
        dialog.show(content);
    }

    public static void facebook(String link, Activity activity) {
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        ShareDialog shareDialog = new ShareDialog(activity);
        ShareLinkContent content = new ShareLinkContent.Builder()
            .setContentUrl(Uri.parse(link)).build();
        shareDialog.show(content);
    }


    public static void twitter(String link, Context context) {
        try {
            new TweetComposer.Builder(context)
                .url(new URL(link)).show();
        } catch (MalformedURLException e) {
        }
    }


    /**
     * We have problem here!
     * We can't share web links or resources (but we can create image from resource on disk!)
     *
     * @param context
     * @param uriToImage The image Uri should be a file Uri (i.e. file://absolute_path scheme) to a local file.
     */
    public static void twitterPicture(Context context, Uri uriToImage){
        new TweetComposer.Builder(context)
            .image(uriToImage)
            .show();
    }

    public static void vkImage(Context context, Bitmap bitmap){
        VKRequest request1 = VKApi.uploadWallPhotoRequest(new VKUploadImage(bitmap, VKImageParameters.jpgImage(0.9f)), 0, 0);
        request1.executeWithListener(new VKRequest.VKRequestListener() {
            @Override public void onComplete(VKResponse response) {
                super.onComplete(response);
            }

            @Override public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    public static void vk(String link, final Context context) {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.MESSAGE, link));
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override public void onComplete(VKResponse response) {
                super.onComplete(response);
                Toast.makeText(context, "successfully shared", Toast.LENGTH_SHORT).show();
            }

            @Override public void onError(VKError error) {
                super.onError(error);
                Toast.makeText(context, "an error occured while sharing", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
