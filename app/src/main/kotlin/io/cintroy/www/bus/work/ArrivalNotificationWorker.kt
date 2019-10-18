package io.cintroy.www.bus.work

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