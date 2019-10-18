package www.cintroy.com.bus.base

import android.os.Bundle
import dagger.android.AndroidInjection

/**
 * Created by Cintory on 2019/10/10 17:11
 * Email：Cintory@gmail.com
 */
abstract class InjectableBaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}