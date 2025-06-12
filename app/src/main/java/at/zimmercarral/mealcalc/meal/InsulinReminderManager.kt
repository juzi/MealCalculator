import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import at.zimmercarral.mealcalc.R

class InsulinReminderManager(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "be_calculator_channel"
        private const val TIMER_DURATION_MILLIS = 15 * 60 * 1000L
    }

    private var insulinReminderTimer: CountDownTimer? = null

    init {
        createNotificationChannel()
    }

    fun startOrResetTimer() {
        insulinReminderTimer?.cancel()

        insulinReminderTimer = object : CountDownTimer(TIMER_DURATION_MILLIS, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Empty implementation
            }

            override fun onFinish() {
                showInsulinReminderNotification()
            }
        }.start()
    }

    fun cancelTimer() {
        insulinReminderTimer?.let {
            it.cancel()
            insulinReminderTimer = null
        }
    }

    private fun showInsulinReminderNotification() {

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Hearty365")
            .setContentTitle("Nicht vergessen!")
            .setContentText("Insulin für Mahlzeit pumpen! Danach Mahlzeit löschen.")
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .build()

        notification.flags = notification.flags or Notification.FLAG_INSISTENT
        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "My Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Insulin pumpen!"
            enableLights(true)
            lightColor = Color.RED
            vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            enableVibration(true)
            setAllowBubbles(true)
            setShowBadge(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }
}