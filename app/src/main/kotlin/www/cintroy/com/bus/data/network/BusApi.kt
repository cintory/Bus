package www.cintroy.com.bus.data.network

import io.reactivex.Single
import retrofit2.http.*
import www.cintroy.com.bus.bean.response.SidResponse
import www.cintroy.com.bus.bean.response.StationResponse

/**
 * Created by Cintory on 2018/12/4 16:47
 * Email：Cintory@gmail.com
 */
interface BusApi {

  @FormUrlEncoded
  @POST("/public/bus/get")
  fun getSid(@Field("idnum") idnum: String): Single<SidResponse>

  @GET("/public/bus/mes/sid/{sid}?stoptype={stoptype}")
  fun getStopId(@Path("sid") sid: String,@Path("stoptype")stopType :String)

  @FormUrlEncoded
  @Headers("Referer: http://shanghaicity.openservice.kankanews.com/public/bus/mes/sid/")
  @POST("/public/bus/Getstop")
  fun getBusStop(@Field("stoptype") stopType: String?, @Field("stopid") stopId: String, @Field("sid") sid: String): Single<List<StationResponse>>

  companion object {
    const val HOST = "https://shanghaicity.openservice.kankanews.com"
  }

}