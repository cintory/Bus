package io.cintroy.www.bus.base

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by Cintory on 2019/10/10 17:27
 * Email：Cintory@gmail.com
 */
abstract class InjectingBaseActivity : InjectableBaseActivity(), HasAndroidInjector {

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}