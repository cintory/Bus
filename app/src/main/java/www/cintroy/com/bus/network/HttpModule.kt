package www.cintroy.com.bus.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import www.cintroy.com.bus.BuildConfig
import java.util.concurrent.TimeUnit

/**
 * Created by Cintory on 2018/12/10 14:53
 * Email：Cintory@gmail.com
 */
class HttpModule {
  companion object {
    fun provideClient(): OkHttpClient {
      val builder = OkHttpClient.Builder();
      if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        builder.addInterceptor(loggingInterceptor)
      }

      //  Timeout
      builder.connectTimeout(10, TimeUnit.SECONDS)
      builder.readTimeout(20, TimeUnit.SECONDS)
      builder.writeTimeout(20, TimeUnit.SECONDS)

      //  Retry
      builder.retryOnConnectionFailure(true)
      return builder.build()
    }



  }
}