package com.jonathanluco.dawanmap.data.api;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class RetrofitClientTest {

    @Test
    public void getInstance_shouldReturnSingleton() {
        ApiService first = RetrofitClient.getInstance();
        ApiService second = RetrofitClient.getInstance();

        assertNotNull(first);
        assertSame(first, second);
    }
}
