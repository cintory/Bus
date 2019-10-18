package www.cintroy.com.bus.app.data

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger

import www.cintroy.com.bus.injection.qualifiers.NetworkInterceptor

import javax.inject.Singleton

/**
 * Created by Cintory on 2019/10/14 15:32
 * Email：Cintory@gmail.com
 */

private inline fun httpLoggingInterceptor(level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.NONE, crossinline logger: (String) -> Unit): HttpLoggingInterceptor {
  return HttpLoggingInterceptor(object : Logger {
    override fun log(message: String) {
      logger(message)
    }
  }).also { it.level = level }
}

@Module
object VariantDataModule {

  @Provides
  @NetworkInterceptor
  @IntoSet
  @JvmStatic
  @Singleton
  internal fun provideLoggingInterceptor(): Interceptor = httpLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC) { message ->
    Log.v("OkHttp", message)
  }

}