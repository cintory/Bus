package www.cintroy.com.bus.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import www.cintroy.com.bus.util.setLightStatusBar

/**
 * Created by Cintory on 2018/12/10 10:54
 * Email：Cintory@gmail.com
 */
abstract class BaseActivity : AppCompatActivity() {

  val disposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(getLayoutID())
    findViewById<View>(android.R.id.content).setLightStatusBar()
    initView()
    initData()
  }

  fun addSubscribe(subscription: Disposable) {
    disposable.add(subscription)
  }

  fun unsubscribe() {
    disposable.clear()
  }

  override fun onDestroy() {
    unsubscribe()
    super.onDestroy()
  }

  abstract fun getLayoutID(): Int

  abstract fun initView()

  abstract fun initData()

}