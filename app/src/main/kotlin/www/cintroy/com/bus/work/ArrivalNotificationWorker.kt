package www.cintroy.com.bus.work

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import dagger.android.AndroidInjection
import io.reactivex.Single
import www.cintroy.com.bus.R
import www.cintroy.com.bus.data.database.BusDatabase
import www.cintroy.com.bus.data.database.NotificationInfo
import www.cintroy.com.bus.data.network.BusService
import javax.inject.Inject

/**
 * Created by Cintory on 2019/1/16 16:35
 * Email：Cintory@gmail.com
 */
//class ArrivalNotificationWorker(
//  context: Context,
//  private val workerParameters: WorkerParameters
//) : RxWorker(context, workerParameters) {
//
//  @Inject
//  internal lateinit var busService: BusService
//
//  lateinit var notificationInfo: NotificationInfo
//  var time = 1
//
//
//  override fun createWork(): Single<Result> {
//    AndroidInjection.inject(this)
//    return BusDatabase.getDatabase(applicationContext).notificationInfoDao()
//      .getNotificationInfo()
//      .flattenAsFlowable { it }
//      .singleOrError()
//      .flatMap { info ->
//        notificationInfo = info
//        BusService.getBusStop(
//          "0",
//          notificationInfo.stopID,
//          notificationInfo.sid
//        )
//      }.map { data ->
//        var builder = NotificationCompat.Builder(applicationContext)
//          .setSmallIcon(R.drawable.navigation_empty_icon)
//          .setContentTitle("到站信息")
//          .setContentText(time.toString() + "" + data[0].terminal + "还有" + data[0].time)
//        Log.e("time", time.toString())
//        val no = NotificationManagerCompat.from(applicationContext)
//        no.notify(notificationId, builder.build())
//        return@map Result.success()
//      }.onErrorReturn { error ->
//        error.printStackTrace()
//        Result.failure()
//      }
//  }
//
//
//  companion object {
//    const val notificationId = 0X0001
//  }
//}