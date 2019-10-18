package io.cintroy.www.bus.base

import androidx.fragment.app.Fragment

/**
 * Created by Cintory on 2019/10/10 17:09
 * Email：Cintory@gmail.com
 */
abstract class BaseFragment : Fragment() {}

interface BackpressHandler {
    fun onBackPressed(): Boolean
}
