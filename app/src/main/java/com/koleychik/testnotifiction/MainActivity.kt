package com.koleychik.testnotifiction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.koleychik.testnotifiction.baseNotification.BaseNotificationActivity
import com.koleychik.testnotifiction.firebaseMessanger.FirebaseActivity

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MAIN_APP_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.baseNotification).setOnClickListener {
            startActivity(Intent(applicationContext, BaseNotificationActivity::class.java))
        }

        findViewById<Button>(R.id.firebaseMessaging).setOnClickListener {
            startActivity(Intent(applicationContext, FirebaseActivity::class.java))
        }

    }
}