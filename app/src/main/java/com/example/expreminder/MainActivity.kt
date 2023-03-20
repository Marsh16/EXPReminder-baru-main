package com.example.expreminder

import Database.GlobarVar
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (GlobarVar.listUser.size > 0) {
            GlobarVar.listUser.clear()
        }
        if (GlobarVar.listProduct.size > 0) {
            GlobarVar.listProduct.clear()
        }

        login1.setOnClickListener() {
            intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
        signup1.setOnClickListener() {
            intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }


    }


}