package io.cintroy.www.bus.ui.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.cintroy.www.bus.data.network.BusModule
import io.cintroy.www.bus.injection.scopes.PerActivity
import io.cintroy.www.bus.ui.activity.MainActivity.BusArrivalStatusSheetBindingModule

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