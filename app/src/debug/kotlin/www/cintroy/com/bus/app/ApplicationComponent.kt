package www.cintroy.com.bus.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import www.cintroy.com.bus.app.flipper.FlipperModule
import www.cintroy.com.bus.data.DataModule
import www.cintroy.com.bus.ui.activity.ActivityModule
import javax.inject.Singleton

/**
 * Created by Cintory on 2019/10/9 18:04
 * Email：Cintory@gmail.com
 */

@Singleton
@Component(
  modules = [FlipperModule::class, DataModule::class, ApplicationModule::class, ActivityModule::class, AndroidSupportInjectionModule::class]
)
interface ApplicationComponent {

  fun inject(application: DebugBusApplication)

  @Component.Builder
  interface Builder {
    fun build(): ApplicationComponent

    @BindsInstance
    fun application(application: Application): Builder
  }
}