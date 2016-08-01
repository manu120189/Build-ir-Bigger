package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.Pair;
import android.test.ApplicationTestCase;

import com.builditbigger.myapplication.backend.myApi.MyApi;
import com.builditbigger.myapplication.backend.myApi.model.MyBean;

import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public static final String TEST_JOKE = "TEST JOKE!";
    private static boolean called;
    private MyApi myApiService = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().getPath());        called = false;
        myApiService = mock(MyApi.class);
        MyApi.GetJoke getJoke = mock(MyApi.GetJoke.class);
        MyBean myBean = new MyBean();
        myBean.setData(TEST_JOKE);
        when(myApiService.getJoke()).thenReturn(getJoke);
        when(getJoke.execute()).thenReturn(myBean);
    }
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testTellJoke() throws InterruptedException {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

        // Execute the async task on the UI thread! THIS IS KEY!
        new EndpointsAsyncTask().setMyApiService(myApiService)
                .execute(new Pair<Context, String>(null, ""));

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 10 seconds passes and it times out.
	     */
        signal.await(10, TimeUnit.SECONDS);

        //we check if the static variable result was set after the async task call
        assertTrue(EndpointsAsyncTask.result.equals(TEST_JOKE));
    }

}