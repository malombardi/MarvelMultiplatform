package com.mlombardi.marvelcharacters

import android.app.Application
import com.mlombardi.marvelcharacters.di.initKoin
import org.koin.android.ext.koin.androidContext

class MarvelCharacters: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MarvelCharacters)
        }
    }
}
