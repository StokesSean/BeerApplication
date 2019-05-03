package com.example.beercraft.main

import android.app.Application
import com.example.beercraft.models.AlcoholicJSONStore
import com.example.beercraft.models.AlcoholicMemStore
import com.example.beercraft.models.AlcoholicModel
import com.example.beercraft.models.AlcoholicStore
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info



class MainApp : Application(), AnkoLogger {
    private lateinit var database: DatabaseReference
    lateinit var Alcoholics: AlcoholicStore

    override fun onCreate() {
        super.onCreate()
        Alcoholics = AlcoholicJSONStore(applicationContext)

        info("alcoholic Activity started..")
    }

}
