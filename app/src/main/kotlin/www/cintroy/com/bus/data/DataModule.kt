package www.cintroy.com.bus.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Looper
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import www.cintroy.com.bus.data.database.BusDatabase
import www.cintroy.com.bus.data.database.CollectionDao
import www.cintroy.com.bus.data.database.NotificationInfoDao
import www.cintroy.com.bus.injection.SharedPreferencesName
import www.cintroy.com.bus.injection.qualifiers.ApplicationContext
import www.cintroy.com.bus.injection.qualifiers.NetworkInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Cintory on 2019/10/9 18:44
 * Email：Cintory@gmail.com
 */
@Module
abstract class DataModule {

  @NetworkInterceptor
  @Multibinds
  internal abstract fun provideNetworkInterceptors(): Set<Interceptor>

  @Multibinds
  internal abstract fun provideInterceptors(): Set<Interceptor>

  @Module
  companion object {
    private const val HTTP_RESPONSE_CACHE = (10 * 1024 * 1024).toLong()
    private const val HTTP_TIMEOUT_S = 30

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideCache(@ApplicationContext context: Context): Cache {
      check(Looper.myLooper() != Looper.getMainLooper()) { "Cache initialized on main thread." }
      return Cache(context.cacheDir, HTTP_RESPONSE_CACHE)
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, interceptors: Set<@JvmSuppressWildcards Interceptor>, @NetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        throw IllegalStateException("HTTP client initialized on main thread.")
      }

      val builder = OkHttpClient.Builder().connectTimeout(HTTP_TIMEOUT_S.toLong(), TimeUnit.SECONDS)
          .readTimeout(HTTP_TIMEOUT_S.toLong(), TimeUnit.SECONDS)
          .writeTimeout(HTTP_TIMEOUT_S.toLong(), TimeUnit.SECONDS)
          .cache(cache)

      builder.networkInterceptors()
          .addAll(networkInterceptors)
      builder.interceptors()
          .addAll(interceptors)

      return builder.build()
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
      return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @JvmStatic
    @Singleton
    @SharedPreferencesName
    fun provideSharedPreferencesName(): String {
      return "bus"
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context, @SharedPreferencesName name: String): SharedPreferences {
      return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideBusDatabase(@ApplicationContext context: Context): BusDatabase {
      return BusDatabase.getDatabase(context)
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideCollectionDao(busDatabase: BusDatabase): CollectionDao {
      return busDatabase.collectionDao()
    }

    @Provides
    @JvmStatic
    @Singleton
    internal fun provideNotificationInfoDao(busDatabase: BusDatabase): NotificationInfoDao {
      return busDatabase.notificationInfoDao()
    }

//    @Provides
//    @JvmStatic
//    @Singleton
//    internal fun provideCatchUpDatabase(@ApplicationContext context: Context): CatchUpDatabase {
//      return CatchUpDatabase.getDatabase(context)
//    }
  }
}