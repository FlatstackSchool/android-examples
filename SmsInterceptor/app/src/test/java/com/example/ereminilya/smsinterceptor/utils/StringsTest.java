package com.example.ereminilya.smsinterceptor.utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by ereminilya on 24/11/16.
 */
public class StringsTest {

    @Test
    public void testEquals() throws Exception {
        Assert.assertFalse(Strings.equals(null, null));
        Assert.assertFalse(Strings.equals(null, "random string"));
        Assert.assertFalse(Strings.equals("kek", null));
        Assert.assertTrue(Strings.equals("kek", "kek"));
        Assert.assertFalse(Strings.equals("kek", "pek"));
    }
}