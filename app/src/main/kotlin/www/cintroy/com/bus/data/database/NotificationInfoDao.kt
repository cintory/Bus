package www.cintroy.com.bus.data.database

import androidx.annotation.Keep
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by Cintory on 2019/1/16 17:54
 * Email：Cintory@gmail.com
 */
@Dao
interface NotificationInfoDao {

  @Query("SELECT * FROM notificationInfo")
  fun getNotificationInfoFlow(): Flowable<MutableList<NotificationInfo>>

  @Query("SELECT * FROM notificationInfo")
  fun getNotificationInfo(): Maybe<List<NotificationInfo>>

  @Query("SELECT * FROM notificationInfo where sid like :sid and stopID like :stopID and direction like :direction")
  fun getNotificationInfo(
    sid: String,
    stopID: String,
    direction: String
  ): Maybe<NotificationInfo>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun putNotificationInfo(notificationInfo: NotificationInfo): Completable

  @Delete
  fun deleteNotificationInfo(notificationInfo: NotificationInfo): Completable

}

@Keep
@Entity(tableName = "notificationInfo", primaryKeys = ["sid", "stopID", "direction","startTime"])
data class NotificationInfo(
  val sid: String,
  val lineName: String,
  val stopID: String,
  val direction: String,
  val startTime: String
)