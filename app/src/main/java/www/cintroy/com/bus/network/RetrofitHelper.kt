package www.cintroy.com.bus.network

import com.google.gson.GsonBuilder
import io.reactivex.Single
import org.jsoup.Jsoup
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import www.cintroy.com.bus.bean.Station
import java.io.IOException


/**
 * Created by Cintory on 2018/12/5 16:13
 * Email：Cintory@gmail.com
 */
object RetrofitHelper {
  private val busApi: BusApi = Retrofit.Builder()
    .baseUrl(BusApi.HOST).client(HttpModule.provideClient())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
    .build().create(BusApi::class.java)


  fun getSid(idnum: String) = busApi.getSid(idnum)


  fun getStation(sid: String): Single<MutableList<Station>> {
    return Single.create<MutableList<Station>> { emitter ->
      try {
        val doc = Jsoup.connect(BusApi.HOST + "/public/bus/mes/sid/" + sid).get()
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

  fun getBusStop(stopType: String, stopId: String, sid: String) =
    busApi.getBusStop(stopType, stopId, sid)


}