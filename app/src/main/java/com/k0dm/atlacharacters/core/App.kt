package com.k0dm.atlacharacters.core

import android.app.Application

class App : Application(), ProvideRepresentative {


    override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T {
        TODO("Not yet implemented")
    }
}

interface ProvideRepresentative {

    fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T
}