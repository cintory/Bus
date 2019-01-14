package www.cintroy.com.bus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import www.cintroy.com.bus.R
import www.cintroy.com.bus.data.Collection

/**
 * Created by Cintory on 2018/12/23 22:32
 * Email：Cintory@gmail.com
 */
class CollectionAdapter(val collectionList: MutableList<Collection>, private val onClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_collection,
            parent,
            false
        )
    )
  }

  override fun getItemCount() = collectionList.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.itemView.setOnClickListener {
      onClickListener.onClick(collectionList[position])
    }
    holder.tvBusName.text = collectionList[position].lineName
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvBusName: TextView = itemView.findViewById(R.id.tvBusName)
  }

  interface OnItemClickListener {
    fun onClick(collection: Collection)
  }
}