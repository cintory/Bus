package www.cintroy.com.bus.bean.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Cintory on 2018/12/10 18:00
 * Email：Cintory@gmail.com
 */
data class StationResponse(
  @SerializedName("@attributes")
  val attributes: Attributes,
  val distance: String,
  val stopdis: String,
  val terminal: String,
  val time: String
)

data class Attributes(
  val cod: String
)