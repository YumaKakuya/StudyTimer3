package com.example.studytimer

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.concurrent.atomic.AtomicInteger

// 必要な場合、以下の行のように完全修飾名で使用できます。
// import android.app.NotificationChannel

class NotificationHelper(private val context: Context) {
    private val channelId = "timer_channel_id"
    private val channelName = "Timer Notifications"
    private val notificationIdCounter = AtomicInteger(0)

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 完全修飾名を使用して明示的に android.app.NotificationChannel を指定
            val channel = android.app.NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 1000, 500)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(message: String) {
        val notificationId = notificationIdCounter.incrementAndGet()
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // 既存のリソースを使用
            .setContentTitle("タイマー通知")
            .setContentText(message)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, builder.build())
    }
}