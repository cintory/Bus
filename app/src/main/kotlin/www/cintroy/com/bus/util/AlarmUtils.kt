package www.cintroy.com.bus.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import www.cintroy.com.bus.receiver.NotificationAlarmReceiver

/**
 * Created by Cintory on 2019/2/14 15:29
 * Email：Cintory@gmail.com
 */

object AlarmUtils {

  fun setAlarm(context: Context, timeOfAlarm: Long) {
    val receiverIntent = Intent(context, NotificationAlarmReceiver::class.java)

    val pIntent = PendingIntent.getBroadcast(context, 0, receiverIntent, 0)

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//    if (System.currentTimeMillis() < timeOfAlarm) {
      alarmManager.set(AlarmManager.RTC_WAKEUP, timeOfAlarm, pIntent)
//    }
  }
}