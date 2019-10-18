package www.cintroy.com.bus.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.jakewharton.rxrelay2.BehaviorRelay
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import dagger.android.AndroidInjection
import io.reactivex.Observable
import www.cintroy.com.bus.base.ActivityEvent.*
import www.cintroy.com.bus.util.setLightStatusBar

/**
 * Created by Cintory on 2018/12/10 10:54
 * Email：Cintory@gmail.com
 */
abstract class BaseActivity : AppCompatActivity(), LifecycleScopeProvider<ActivityEvent> {

  private val lifecycleRelay = BehaviorRelay.create<ActivityEvent>()

  protected inline fun <T, R> Observable<T>.doOnCreate(
      r: R,
      crossinline action: R.() -> Unit
  ): Observable<T> = apply {
    doOnNext {
      if (it == CREATE) {
        r.action()
      }
    }
  }

  protected inline fun <T, R> Observable<T>.doOnStart(
      r: R,
      crossinline action: R.() -> Unit
  ): Observable<T> = apply {
    doOnNext {
      if (it == START) {
        r.action()
      }
    }
  }

  protected inline fun <T, R> Observable<T>.doOnResume(
      r: R,
      crossinline action: R.() -> Unit
  ): Observable<T> = apply {
    doOnNext {
      if (it == RESUME) {
        r.action()
      }
    }
  }

  protected inline fun <T, R> Observable<T>.doOnPause(
      r: R,
      crossinline action: R.() -> Unit
  ): Observable<T> = apply {
    doOnNext {
      if (it == PAUSE) {
        r.action()
      }
    }
  }

  protected inline fun <T, R> Observable<T>.doOnStop(
      r: R,
      crossinline action: R.() -> Unit
  ): Observable<T> = apply {
    doOnNext {
      if (it == STOP) {
        r.action()
      }
    }
  }

  protected inline fun <T, R> Observable<T>.doOnDestroy(
      r: R,
      crossinline action: R.() -> Unit
  ): Observable<T> = apply {
    doOnNext {
      if (it == DESTROY) {
        r.action()
      }
    }
  }

  @SuppressLint("AutoDispose")
  protected inline fun <T> T.doOnDestroy(crossinline action: T.() -> Unit): T = apply {
    lifecycle().doOnDestroy(this) { action() }.subscribe()
  }

  @CheckResult
  override fun lifecycle(): Observable<ActivityEvent> {
    return lifecycleRelay
  }

  final override fun correspondingEvents(): CorrespondingEventsFunction<ActivityEvent> {
    return ActivityEvent.LIFECYCLE
  }

  final override fun peekLifecycle(): ActivityEvent? {
    return lifecycleRelay.value
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    findViewById<View>(android.R.id.content).setLightStatusBar()
    lifecycleRelay.accept(CREATE)
  }

  @CallSuper
  override fun onStart() {
    super.onStart()
    lifecycleRelay.accept(START)
  }

  @CallSuper
  override fun onResume() {
    super.onResume()
    lifecycleRelay.accept(RESUME)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      NavUtils.navigateUpFromSameTask(this)
    }
    return super.onOptionsItemSelected(item)
  }

  @CallSuper
  override fun onPause() {
    lifecycleRelay.accept(PAUSE)
    super.onPause()
  }

  @CallSuper
  override fun onStop() {
    lifecycleRelay.accept(STOP)
    super.onStop()
  }

  @CallSuper
  override fun onDestroy() {
    lifecycleRelay.accept(DESTROY)
    super.onDestroy()
  }

  override fun onBackPressed() {
    supportFragmentManager.fragments.filterIsInstance<BackpressHandler>().forEach {
      if (it.onBackPressed()) {
        return
      }
    }
    super.onBackPressed()
  }

}