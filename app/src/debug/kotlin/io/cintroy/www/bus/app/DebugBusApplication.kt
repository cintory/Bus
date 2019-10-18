package io.cintroy.www.bus.app

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Cintory on 2019/10/9 16:53
 * Email：Cintory@gmail.com
 */
class DebugBusApplication : BusApplication() {
  @Inject
  lateinit var flipperPlugins: Set<@JvmSuppressWildcards FlipperPlugin>

  override fun onPreInject() {
    SoLoader.init(this, false)
  }

  override fun inject() {
    DaggerApplicationComponent.builder()
      .application(this)
      .build()
      .inject(this)
  }

  override fun onCreate() {
    super.onCreate()
    GlobalScope.launch(Dispatchers.IO) {
      if (FlipperUtils.shouldEnableFlipper(this@DebugBusApplication)) {
        AndroidFlipperClient.getInstance(this@DebugBusApplication).apply {
          flipperPlugins.forEach(::addPlugin)
          start()
        }
      }
      Stetho.initializeWithDefaults(this@DebugBusApplication)
    }
  }
}