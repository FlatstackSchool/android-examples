package com.ilyaeremin.activeandroidexample.models;

/**
 * Created by Ilya Eremin on 27.04.2016.
 */
public class SharingLinks {
    String vk;
    String facebook;
    String twitter;

    public SharingLinks(String vk, String facebook, String twitter) {
        this.vk = vk;
        this.facebook = facebook;
        this.twitter = twitter;
    }

    public String getVk() {
        return vk;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }
}
