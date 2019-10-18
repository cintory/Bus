package www.cintroy.com.bus.app

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import www.cintroy.com.bus.injection.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Cintory on 2019/10/10 14:48
 * Email：Cintory@gmail.com
 */
@Module
abstract class ApplicationModule {

  @Binds
  @ApplicationContext
  @Singleton
  abstract fun provideApplicationContext(application: Application): Context
}