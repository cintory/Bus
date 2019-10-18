package www.cintroy.com.bus.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet_bus_arrival_status.*
import www.cintroy.com.bus.R
import www.cintroy.com.bus.bean.Station
import www.cintroy.com.bus.bean.response.StationResponse
import www.cintroy.com.bus.data.database.BusDatabase
import www.cintroy.com.bus.data.database.Collection
import www.cintroy.com.bus.data.network.BusService
import www.cintroy.com.bus.ui.adapter.StationAdapter
import javax.inject.Inject

/**
 * Created by Cintory on 2018/12/13 15:55
 * Email：Cintory@gmail.com
 */
class BusArrivalStatusSheet : BottomSheetDialogFragment() {

  private var idnum: String = ""
  private var sid: String = ""
  private var direction: String = "1"
  private var isCollected = false
  private var stationList = mutableListOf<Station>()
  private var busArrivalStatus = mutableListOf<List<StationResponse>>()
  @Inject
  internal lateinit var busService: BusService


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AndroidSupportInjection.inject(this)
    getGetStationStatus()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.bottom_sheet_bus_arrival_status, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    rvData.layoutManager = LinearLayoutManager(activity)
    rvData.adapter = StationAdapter(stationList, busArrivalStatus, object :
      StationAdapter.OnClickListener {
      override fun onClick(station: Station) {
//        val notificationInfo = NotificationInfo(sid, idnum, station.num, direction)
//        BusDatabase.getDatabase(BusApplication.INSTANCE)
//          .notificationInfoDao().putNotificationInfo(notificationInfo).subscribeOn(Schedulers.io())
//          .observeOn(AndroidSchedulers.mainThread())
//          .subscribe({}, {
//            it.printStackTrace()
//          })
//        val work = OneTimeWorkRequest.Builder(ArrivalNotificationWorker::class.java).build()
//        WorkManager.getInstance().enqueue(work)
        val sheet = TimePickerFragment.getInstance { view, hourOfDay, minute ->
          //          val notificationInfo = NotificationInfo(sid, idnum, station.num, direction,"0")
//          BusDatabase.getDatabase(BusApplication.INSTANCE)
//            .notificationInfoDao().putNotificationInfo(notificationInfo)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({}, {
//              it.printStackTrace()
//            })
//          AlarmUtils.setAlarm(context!!, System.currentTimeMillis() + 1000)
//          val work = OneTimeWorkRequest.Builder(ArrivalNotificationWorker::class.java).build()
//          WorkManager.getInstance().enqueue(work)
        }
        sheet.show(fragmentManager!!, sheet.tag)
      }
    })
    tvLineName.text = idnum
    tvLineName.setOnClickListener {
      val collection = Collection(sid, idnum, "", direction)
      BusDatabase.getDatabase(context!!.applicationContext).collectionDao()
        .putCollection(collection)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .autoDisposable(AndroidLifecycleScopeProvider.from(this))
        .subscribe({
          isCollected = true
          ivCollection.visibility = View.VISIBLE
        }, { e ->
          e.printStackTrace()
        })
    }
    ivSwap.setOnClickListener {
      if (progress.visibility == View.VISIBLE) return@setOnClickListener
      it.animate().rotationBy(360f).start()
      stationList.clear()
      busArrivalStatus.clear()
      rvData.adapter?.notifyDataSetChanged()
      direction = if (direction == "0") {
        "1"
      } else {
        "0"
      }
      getGetStationStatus()
    }
  }

  private fun getGetStationStatus() {
    progress?.visibility = View.VISIBLE
    ivCollection?.visibility = View.GONE
    busService.getSid(idnum)
      .flatMap { t ->
        sid = t.sid
        busService.getStation(t.sid, direction)
      }
      .flattenAsObservable {
        stationList.clear()
        stationList.addAll(it)
        it
      }
      .concatMapSingle { station -> busService.getBusStop(direction, station.num, sid) }
      .toList()
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSuccess { data ->
        progress.visibility = View.GONE
        busArrivalStatus.clear()
        busArrivalStatus.addAll(data)
        rvData.adapter?.notifyDataSetChanged()
      }
      .flatMapMaybe {
        BusDatabase.getDatabase(context!!.applicationContext)
          .collectionDao()
          .getCollection(sid, "")
          .subscribeOn(Schedulers.io())
      }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
      .autoDisposable(AndroidLifecycleScopeProvider.from(this))
      .subscribe({ data ->
        isCollected = true
        ivCollection.visibility = View.VISIBLE
      }, { error ->
        progress.visibility = View.GONE
        ivCollection.visibility = View.VISIBLE
        error.printStackTrace()
        Snackbar.make(view!!.rootView, "请求发生错误", Snackbar.LENGTH_LONG).show()
      })

  }

  companion object {

    val TAG = "BusArrivalStatusSheet"

    fun getInstance(idnum: String): BusArrivalStatusSheet {
      val sheet = BusArrivalStatusSheet()
      sheet.idnum = idnum
      return sheet
    }
  }
}