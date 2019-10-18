package io.cintroy.www.bus.data.network

import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.cintroy.www.bus.bean.Station
import io.cintroy.www.bus.extension.delegatingCallFactory
import io.cintroy.www.bus.injection.ServiceModule
import java.io.IOException
import javax.inject.Inject


/**
 * Created by Cintory on 2018/12/5 16:13
 * Email：Cintory@gmail.com
 */
internal class BusService @Inject constructor(private val busApi: BusApi) {

  fun getSid(idnum: String) = busApi.getSid(idnum)


  fun getStation(sid: String, direction: String): Single<MutableList<Station>> {
    return Single.create<MutableList<Station>> { emitter ->
      try {
        val doc =
            Jsoup.connect(
                BusApi.HOST + "/public/bus/mes/sid/" + sid + (if (direction == "1") {
                  "?stoptype=1"
                } else {
                  ""
                })
            ).get()
        val stationElements = doc.select("div.station")
        var stationList = mutableListOf<Station>()
        for (stationElement in stationElements) {
          val num = stationElement.getElementsByClass("num").text().replace(".", "")
          val stationName = stationElement.getElementsByClass("name").text()
          stationList.add(Station(num, stationName))
        }
        emitter.onSuccess(stationList)
      } catch (e: IOException) {
        emitter.onError(e)
      }
    }
  }

  fun getBusStop(stopType: String?, stopId: String, sid: String) =
      busApi.getBusStop(stopType, stopId, sid).onErrorReturn { mutableListOf() }
}

@ServiceModule
@Module
abstract class BusModule {

  @Module
  companion object {
    @Provides
    @JvmStatic
    internal fun provideBusServices(client: Lazy<OkHttpClient>, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): BusApi {
      return Retrofit.Builder().baseUrl(BusApi.HOST)
          .delegatingCallFactory(client)
          .addCallAdapterFactory(rxJava2CallAdapterFactory)
          .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
          .build()
          .create(BusApi::class.java)
    }
  }


}