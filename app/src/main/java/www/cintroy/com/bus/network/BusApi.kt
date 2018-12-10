package www.cintroy.com.bus.network

import io.reactivex.Single
import retrofit2.http.*
import www.cintroy.com.bus.bean.response.SidResponse
import www.cintroy.com.bus.bean.response.StationResponse

/**
 * Created by Cintory on 2018/12/4 16:47
 * Email：Cintory@gmail.com
 */
interface BusApi {

  @POST("/public/bus/get")
  fun getSid(@Query("idnum") idnum: String): Single<SidResponse>

  @GET("/public/bus/mes/sid/{sid}")
  fun getStopId(@Path("sid") sid: String)

  @Headers("Referer: http://shanghaicity.openservice.kankanews.com/public/bus/mes/sid/")
  @POST("/public/bus/Getstop")
  fun getBusStop(@Query("stoptype") stopType: String, @Query("stopid") stopId: String, @Query("sid") sid: String): Single<StationResponse>

  companion object {
    const val HOST = "https://shanghaicity.openservice.kankanews.com"
  }

}