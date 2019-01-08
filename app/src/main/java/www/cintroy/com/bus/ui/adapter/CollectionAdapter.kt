package www.cintroy.com.bus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import www.cintroy.com.bus.R
import www.cintroy.com.bus.data.Collection

/**
 * Created by Cintory on 2018/12/23 22:32
 * Email：Cintory@gmail.com
 */
class CollectionAdapter(private val collectionList: MutableList<Collection>) :
  RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(
        R.layout.activity_main,
        parent,
        false
      )
    )
  }

  override fun getItemCount() = collectionList.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  }
}