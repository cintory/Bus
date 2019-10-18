package io.cintroy.www.bus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.cintroy.www.bus.R
import io.cintroy.www.bus.bean.Station
import io.cintroy.www.bus.bean.response.StationResponse

/**
 * Created by Cintory on 2018/12/13 17:22
 * Email：Cintory@gmail.com
 */
class StationAdapter(
    var stationList: MutableList<Station>,
    var busArriveStatus: MutableList<List<StationResponse>>,
    var onClickListener: OnClickListener
) :
    RecyclerView.Adapter<StationAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_station,
            parent,
            false
        )
    )
  }

  override fun getItemCount() = stationList.size


  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (position == 0) holder.vUpLine.visibility = View.INVISIBLE
    else holder.vUpLine.visibility = View.VISIBLE

    if (position == itemCount - 1) holder.vDownLine.visibility = View.INVISIBLE
    else holder.vDownLine.visibility = View.VISIBLE

    val station = stationList[position]
    holder.tvStation.text = station.stationNmae
    holder.tvStation.setOnClickListener {
      onClickListener.onClick(station)
    }
    val busArriveStatus = if (position < busArriveStatus.size) busArriveStatus[position] else null
    if (!busArriveStatus.isNullOrEmpty()) {
      if (busArriveStatus[0].stopdis == "1") {
        holder.ivBus.visibility = View.VISIBLE
        holder.tvCarLicense.visibility = View.VISIBLE
        holder.tvCarLicense.text = busArriveStatus[0].terminal
      } else {
        holder.ivBus.visibility = View.INVISIBLE
        holder.tvCarLicense.visibility = View.INVISIBLE
        holder.tvCarLicense.text = busArriveStatus[0].terminal
      }
      val time = busArriveStatus[0].time.toInt() / 60
      holder.tvArriveTime.text = if (time > 0) {
        time.toString() + "分钟 到站"
      } else {
        "少于一分钟，即将到站"
      }
    } else {
      holder.ivBus.visibility = View.INVISIBLE
      holder.tvCarLicense.visibility = View.INVISIBLE
      holder.tvCarLicense.text = ""
      holder.tvArriveTime.text = ""
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvStation: TextView = itemView.findViewById(R.id.tvStationName)
    val tvCarLicense: TextView = itemView.findViewById(R.id.tvCarLicense)
    val tvArriveTime: TextView = itemView.findViewById(R.id.tvArriveTime)
    val ivBus: ImageView = itemView.findViewById(R.id.ivBus)
    val vUpLine: View = itemView.findViewById(R.id.vUpLine)
    val vDownLine: View = itemView.findViewById(R.id.vDownLine)
  }

  interface OnClickListener {
    fun onClick(station: Station)
  }
}



