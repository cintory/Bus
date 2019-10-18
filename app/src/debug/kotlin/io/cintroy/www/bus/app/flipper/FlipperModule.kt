package io.cintroy.www.bus.app.flipper

import android.content.Context
import com.facebook.flipper.core.FlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import okhttp3.Interceptor
import io.cintroy.www.bus.injection.SharedPreferencesName
import io.cintroy.www.bus.injection.qualifiers.ApplicationContext
import io.cintroy.www.bus.injection.qualifiers.NetworkInterceptor
import javax.inject.Singleton

/**
 * Created by Cintory on 2019/10/9 17:19
 * Email：Cintory@gmail.com
 */
@Module
abstract class FlipperModule {

  @Multibinds
  abstract fun provideFlipperPlugins(): Set<FlipperPlugin>

  @Binds
  @IntoSet
  @Singleton
  abstract fun provideNetWorkFlipperPluginIntoSet(plugin: NetworkFlipperPlugin): FlipperPlugin

  @Module
  companion object {

    @IntoSet
    @JvmStatic
    @Provides
    @Singleton
    fun provideSharedPreferencesPlugin(@ApplicationContext context: Context, @SharedPreferencesName preferencesName: String
    ): FlipperPlugin {
      return SharedPreferencesFlipperPlugin(context, preferencesName)
    }

    @IntoSet
    @JvmStatic
    @Provides
    @Singleton
    fun provideViewInspectorPlugin(@ApplicationContext context: Context): FlipperPlugin {
      return InspectorFlipperPlugin(context, DescriptorMapping.withDefaults())
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpInspectorPlugin(): NetworkFlipperPlugin {
      return NetworkFlipperPlugin()
    }

    @IntoSet
    @JvmStatic
    @NetworkInterceptor
    @Provides
    @Singleton
    fun provideOkHttpInspectorPluginInterceptor(plugin: NetworkFlipperPlugin): Interceptor {
      return FlipperOkhttpInterceptor(plugin)
    }
  }
}