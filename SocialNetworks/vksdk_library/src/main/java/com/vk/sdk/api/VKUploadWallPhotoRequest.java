package com.vk.sdk.api;

import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.api.photo.VKUploadPhotoBase;
import com.vk.sdk.util.VKJsonHelper;
import com.vk.sdk.util.VKUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Ilya Eremin on 4/5/16.
 */
public class VKUploadWallPhotoRequest extends VKUploadPhotoBase {
    private static final long serialVersionUID = 4732771149932923938L;

    public VKUploadWallPhotoRequest(File image, long userId, int groupId) {
        super();
        mUserId = userId;
        mGroupId = groupId;
        mImages = new File[]{image};
    }

    public VKUploadWallPhotoRequest(VKUploadImage image, long userId, int groupId) {
        super();
        mUserId = userId;
        mGroupId = groupId;
        mImages = new File[]{image.getTmpFile()};
    }
    public VKUploadWallPhotoRequest(VKUploadImage[] images, long userId, int groupId) {
        super();
        mUserId = userId;
        mGroupId = groupId;
        mImages = new File[images.length];
        for (int i = 0; i < images.length; i++) {
            mImages[i] = images[i].getTmpFile();
        }
    }

    @Override
    protected VKRequest getServerRequest() {
        if (mGroupId != 0)
            return VKApi.photos().getWallUploadServer(mGroupId);
        else
            return VKApi.photos().getWallUploadServer();
    }

    @Override
    protected VKRequest getSaveRequest(JSONObject response) {
        VKRequest saveRequest;
        try {
            saveRequest = VKApi.photos().saveWallPhoto(new VKParameters(VKJsonHelper.toMap(response)));
        } catch (JSONException e) {
            return null;
        }
        if (mUserId != 0)
            saveRequest.addExtraParameters(VKUtil.paramsFrom(VKApiConst.USER_ID, mUserId));
        if (mGroupId != 0)
            saveRequest.addExtraParameters(VKUtil.paramsFrom(VKApiConst.GROUP_ID, mGroupId));
        return saveRequest;
    }
}