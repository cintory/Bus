package www.cintroy.com.bus.data

import androidx.annotation.Keep
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by Cintory on 2018/12/18 15:17
 * Email：Cintory@gmail.com
 */

@Dao
interface CollectionDao {

  @Query("SELECT * FROM collection")
  fun getCollectionFlow(): Flowable<MutableList<Collection>>

  @Query("SELECT * FROM collection")
  fun getCollection(): Maybe<List<Collection>>

  @Query("SELECT * FROM collection where sid like :sid and stopID like :stopID")
  fun getCollection(sid: String, stopID: String): Maybe<Collection>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun putCollection(collection: Collection): Completable

  @Delete
  fun deleteCollection(collection: Collection): Completable
}

@Keep
@Entity(tableName = "collection", primaryKeys = ["sid", "stopID"])
data class Collection(
  val sid: String,
  val lineName: String,
  val stopID: String,
  val direction: String
)