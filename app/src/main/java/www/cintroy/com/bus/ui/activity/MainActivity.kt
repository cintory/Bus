package www.cintroy.com.bus.ui.activity

import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import www.cintroy.com.bus.R
import www.cintroy.com.bus.app.BusApplication
import www.cintroy.com.bus.base.BaseActivity
import www.cintroy.com.bus.data.BusDatabase
import www.cintroy.com.bus.ui.fragment.BusArrivalStatusSheet

/**
 * Created by Cintory on 2018/12/10 10:37
 * Email：Cintory@gmail.com
 */
class MainActivity : BaseActivity() {

  private var sid = ""

  override fun getLayoutID(): Int {
    return R.layout.activity_main
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

  override fun initView() {
    setSupportActionBar(toolBar)
  }

  override fun initData() {
    addSubscribe(
      BusDatabase.getDatabase(BusApplication.INSTANCE)
        .collectionDao()
        .getCollectionFlow()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            data ->
        }
          , { e -> e.printStackTrace() })
    )
  }
}