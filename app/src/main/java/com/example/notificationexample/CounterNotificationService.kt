package com.example.notificationexample

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat


class CounterNotificationService(private val context: Context) {

    // Getting notification manager  from system service in context
    private val  notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification(counter : Int){


        // Create Pending Intent to launch app when you click on notification
        val  activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        //********************************************************************

        // Create Pending Intent for Action in notification bar (button for increasing counter)
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, CounterNotificationReciever::class.java),
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        //********************************************************************

        // Creating notification itself
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.star)
            .setContentTitle("Increment counter")
            .setContentText("The count is $counter")
            .setContentIntent(activityPendingIntent)
            // Action for incrementing  counter (if you want counter)
            .addAction(R.drawable.star, "Increment",incrementIntent)
            .build()

        notificationManager.notify(
            1, notification
        )
    }

    companion object{
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}