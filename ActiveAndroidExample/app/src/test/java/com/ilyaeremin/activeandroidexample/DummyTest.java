package com.ilyaeremin.activeandroidexample;

import com.ilyaeremin.activeandroidexample.models.Article;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;

/**
 * Created by Ilya Eremin on 08.05.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class DummyTest {

    @Test public void testExample() {
        Article article = new Article();
        article.set_id(1L);
        Assert.assertTrue(article.get_id() == 1L);
    }

}
