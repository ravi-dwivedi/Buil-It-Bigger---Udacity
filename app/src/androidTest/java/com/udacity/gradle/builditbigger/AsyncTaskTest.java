package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by ravi on 31/10/16.
 */

public class AsyncTaskTest extends ApplicationTestCase<Application> implements OnJokeReceivedListener {
    private CountDownLatch signal;
    private String joke;
    private static final String LOG_TAG = "NonEmptyStringTest";
    public AsyncTaskTest() {
        super(Application.class);
    }

    public void testJoke() {
        try {
            signal = new CountDownLatch(1);
            new EndpointsAsyncTask().execute(this);
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull("joke is null", joke);
            assertFalse("joke is empty", joke.isEmpty());
            Log.d(LOG_TAG, "Retrieved a non-empty string successfully: " + joke);
        } catch (Exception ex) {
            fail();
        }
    }

    @Override
    public void onReceived(String joke) {
        this.joke = joke;
        signal.countDown();
    }
}