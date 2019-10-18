package www.cintroy.com.bus.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Cintory on 2018/12/18 15:32
 * Email：Cintory@gmail.com
 */

@Database(
    entities = [Collection::class, NotificationInfo::class], version = 1
)
abstract class BusDatabase : RoomDatabase() {

  abstract fun collectionDao(): CollectionDao

  abstract fun notificationInfoDao(): NotificationInfoDao

  companion object {

    private var INSTANCE: BusDatabase? = null

    fun getDatabase(context: Context): BusDatabase {
      return INSTANCE ?: Room.databaseBuilder(
          context.applicationContext,
          BusDatabase::class.java,
          "bus.db"
      ).fallbackToDestructiveMigration()
          .fallbackToDestructiveMigrationOnDowngrade()
          .build()
          .also { INSTANCE = it }
    }
  }
}

internal class Converters {

}