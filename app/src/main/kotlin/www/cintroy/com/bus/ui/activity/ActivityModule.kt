package www.cintroy.com.bus.ui.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import www.cintroy.com.bus.data.network.BusModule
import www.cintroy.com.bus.injection.scopes.PerActivity
import www.cintroy.com.bus.ui.activity.MainActivity.BusArrivalStatusSheetBindingModule

/**
 * Created by Cintory on 2019/10/12 10:37
 * Email：Cintory@gmail.com
 */
@Module
abstract class ActivityModule {

  @PerActivity
  @ContributesAndroidInjector(modules = [BusModule::class, BusArrivalStatusSheetBindingModule::class])
  internal abstract fun mainActivity(): MainActivity

}