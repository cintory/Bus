package www.cintroy.com.bus.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet_bus_arrival_status.*
import www.cintroy.com.bus.R
import www.cintroy.com.bus.app.BusApplication
import www.cintroy.com.bus.bean.Station
import www.cintroy.com.bus.bean.response.SidResponse
import www.cintroy.com.bus.bean.response.StationResponse
import www.cintroy.com.bus.data.BusDatabase
import www.cintroy.com.bus.data.Collection
import www.cintroy.com.bus.network.RetrofitHelper
import www.cintroy.com.bus.ui.adapter.StationAdapter

/**
 * Created by Cintory on 2018/12/13 15:55
 * Email：Cintory@gmail.com
 */
class BusArrivalStatusSheet : BottomSheetDialogFragment() {

  private val disposable = CompositeDisposable()
  private var idnum: String = ""
  private var sid: String = ""
  private var stopType: String = "0"
  private var isCollected = false
  private var stationList = mutableListOf<Station>()
  private var busArrivelStatus = mutableListOf<List<StationResponse>>()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    disposable.add(
      RetrofitHelper.getSid(idnum)
        .flatMap { t ->
          sid = t.sid
          RetrofitHelper.getStation(t.sid)
        }
        .flattenAsObservable {
          stationList.clear()
          stationList.addAll(it)
          it
        }
        .flatMapSingle { station -> RetrofitHelper.getBusStop(stopType, station.num, sid) }
        .toList()
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSuccess { data ->
          progress.visibility = View.GONE
          busArrivelStatus.clear()
          busArrivelStatus.addAll(data)
          rvData.adapter?.notifyDataSetChanged()
        }
        .flatMapMaybe {
          BusDatabase.getDatabase(BusApplication.INSTANCE)
            .collectionDao()
            .getCollection(sid, "")
            .subscribeOn(Schedulers.io())
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ data ->
          isCollected = true
          ivCollection.visibility = View.VISIBLE
        }, { error ->
          error.printStackTrace()
        })
    )

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
    rvData.adapter = StationAdapter(stationList, busArrivelStatus)
    tvLineName.text = idnum
    tvLineName.setOnClickListener {
      val collection = Collection(sid, idnum, "")
      disposable.add(
        BusDatabase.getDatabase(BusApplication.INSTANCE).collectionDao()
          .putCollection(collection)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
            isCollected = true
            ivCollection.visibility = View.VISIBLE
          }, { e ->
            e.printStackTrace()
          })
      )
    }
  }

  override fun onDestroy() {
    disposable.clear()
    super.onDestroy()
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