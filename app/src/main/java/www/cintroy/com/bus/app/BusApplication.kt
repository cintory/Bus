package www.cintroy.com.bus.app

import android.app.Application
import kotlin.properties.Delegates

/**
 * Created by Cintory on 2018/12/18 15:57
 * Email：Cintory@gmail.com
 */

class BusApplication : Application() {

  init {
    INSTANCE = this
  }

  companion object {

    var INSTANCE: BusApplication by Delegates.notNull()

  }
}