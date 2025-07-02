package dev.adriankuta.partymania

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}
