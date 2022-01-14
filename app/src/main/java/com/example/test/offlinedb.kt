package com.example.test

import android.app.Application
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class offlinedb: Application() {

    override fun onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}