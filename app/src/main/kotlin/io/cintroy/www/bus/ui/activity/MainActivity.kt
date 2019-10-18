package io.cintroy.www.bus.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import io.cintroy.www.bus.R
import io.cintroy.www.bus.base.InjectingBaseActivity
import io.cintroy.www.bus.data.database.BusDatabase
import io.cintroy.www.bus.data.database.Collection
import io.cintroy.www.bus.injection.scopes.PerFragment
import io.cintroy.www.bus.ui.adapter.CollectionAdapter
import io.cintroy.www.bus.ui.fragment.BusArrivalStatusSheet

/**
 * Created by Cintory on 2018/12/10 10:37
 * Email：Cintory@gmail.com
 */
class MainActivity : InjectingBaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolBar)
    rvData.layoutManager = LinearLayoutManager(baseContext)
    rvData.adapter =
      CollectionAdapter(mutableListOf(), object : CollectionAdapter.OnItemClickListener {
        override fun onClick(collection: Collection) {
          val sheet = BusArrivalStatusSheet.getInstance(collection.lineName)
          sheet.show(supportFragmentManager, sheet.tag)
        }
      })

    BusDatabase.getDatabase(applicationContext)
      .collectionDao()
      .getCollectionFlow()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .autoDisposable(AndroidLifecycleScopeProvider.from(this))
      .subscribe({ data ->
        val collectionList = (rvData.adapter as CollectionAdapter).collectionList
        collectionList.clear()
        collectionList.addAll(data)
      }, { e -> e.printStackTrace() })
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.mian_title, menu)
    val menuItem = menu?.findItem(R.id.menu_search)
    val searchView = menuItem?.actionView as SearchView
    searchView.findViewById<View>(R.id.search_plate)
      ?.setBackgroundResource(R.color.mtrl_btn_transparent_bg_color)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

      override fun onQueryTextChange(newText: String?): Boolean {
        return false
      }

      override fun onQueryTextSubmit(query: String?): Boolean {
        query ?: return false
        val sheet = BusArrivalStatusSheet.getInstance(query)
        sheet.show(supportFragmentManager, sheet.tag)
        return true
      }
    })
    return true
  }

  @Module
  abstract class BusArrivalStatusSheetBindingModule {

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun busArrivalStatusSheet(): BusArrivalStatusSheet
  }

}