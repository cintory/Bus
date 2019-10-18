package www.cintroy.com.bus.app

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Cintory on 2018/12/18 15:57
 * Email：Cintory@gmail.com
 */

abstract class BusApplication : Application(), HasAndroidInjector {

  open fun onPreInject() {
  }

  abstract fun inject()

  override fun onCreate() {
    super.onCreate()
    onPreInject()
    inject()
  }

  @Inject
  internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

  override fun androidInjector(): AndroidInjector<Any> = androidInjector
}