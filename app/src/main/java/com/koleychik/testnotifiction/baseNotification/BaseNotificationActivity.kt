package com.koleychik.testnotifiction.baseNotification

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.koleychik.testnotifiction.R
import com.koleychik.testnotifiction.WorkWithJson

class BaseNotificationActivity : AppCompatActivity() {

    //    val NOTIFICATION_SERVICE = "NOTIFICATION_SERVICE"
    private val channel1 = "channel1"

    private var notificationId = 0

    private val baseNotification by lazy {
        BaseNotification(this, channel1, createPendingIntent())
    }


    private val edtTitle by lazy { findViewById<EditText>(R.id.edtTitle) }
    private val edtText by lazy { findViewById<EditText>(R.id.edtText) }
    private val btn by lazy { findViewById<Button>(R.id.btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_notification)

//        val manager = baseNotification.createNotificationManager()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            manager.createNotificationChannel(baseNotification.createNotificationChannel())
//        }
        btn.setOnClickListener {

            val workWithJson = WorkWithJson<PendingIntent>()

            val data = Data.Builder()
                .putString(ForegroundWorker.getTitle, edtTitle.text.toString())
                .putString(ForegroundWorker.getText, edtText.text.toString())
                .putString(ForegroundWorker.getChannelId, edtText.text.toString())
//                .putString(
//                    ForegroundWorker.getPendingIntentJson,
//                    workWithJson.serializeToJson(createPendingIntent())
//                )
                .build()

            val workManager = WorkManager.getInstance(this)
            val workRequest = OneTimeWorkRequest.Builder(ForegroundWorker::class.java)
                .setInputData(data).build()
            workManager.enqueue(workRequest)

//            baseNotification.putNotification(
//                manager,
//                notificationId,
//                edtTitle.text.toString(),
//                edtText.text.toString()
//            )
//            notificationId++
        }
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this@BaseNotificationActivity, BaseNotificationActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, 0)
    }

}