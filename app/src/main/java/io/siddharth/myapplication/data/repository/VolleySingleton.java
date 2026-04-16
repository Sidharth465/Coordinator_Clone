package io.siddharth.myapplication.data.repository;

import android.graphics.Bitmap;
import androidx.collection.LruCache;

import io.siddharth.myapplication.util.MyApplication;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static volatile VolleySingleton mInstance;
    private final RequestQueue mRequestQueue;
    private final ImageLoader mImageLoader;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppInstance());

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            private final int cacheSize = maxMemory / 8;

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() / 1024;
                }
            };

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance() {
        if (mInstance == null) {
            synchronized (VolleySingleton.class) {
                if (mInstance == null) {
                    mInstance = new VolleySingleton();
                }
            }
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}