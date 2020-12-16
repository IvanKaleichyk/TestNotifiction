package com.koleychik.testnotifiction.firebaseMessanger

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.koleychik.testnotifiction.R

class FirebaseActivity : AppCompatActivity() {

    private val editTitle by lazy { findViewById<EditText>(R.id.edtTitle) }
    private val editText by lazy { findViewById<EditText>(R.id.edtText) }
    private val editToken by lazy { findViewById<EditText>(R.id.edtToken) }
    private val btn by lazy { findViewById<Button>(R.id.btn) }

    private val topic = "/topics/myTopic2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            editToken.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(topic)

        val notificationUtils = NotificationUtils(topic)

        btn.setOnClickListener {
            notificationUtils.sendNotification(editTitle.text.toString(), editText.text.toString())
        }

    }
}