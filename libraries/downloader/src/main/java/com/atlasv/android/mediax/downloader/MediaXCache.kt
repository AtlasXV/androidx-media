package com.atlasv.android.mediax.downloader

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.okhttp.OkHttpDataSource
import okhttp3.OkHttpClient

/**
 * Created by weiping on 2024/7/8
 */
@SuppressLint("UnsafeOptInUsageError")
class MediaXCache(private val appContext: Context, val cache: SimpleCache, private val okhttpClient: OkHttpClient) {

    private fun createHttpDataSourceFactory(): DefaultDataSource.Factory {
        return DefaultDataSource.Factory(
            appContext, OkHttpDataSource.Factory(okhttpClient)
        )
    }

    val cacheDataSourceFactory by lazy {
        createCacheDataSourceFactory()
    }

    private fun createCacheDataSourceFactory(): CacheDataSource.Factory {
        return CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(createHttpDataSourceFactory())
    }

    fun createDataSource(): CacheDataSource {
        return cacheDataSourceFactory.createDataSource()
    }
}