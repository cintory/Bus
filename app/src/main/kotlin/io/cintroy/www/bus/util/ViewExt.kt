package io.cintroy.www.bus.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

/**
 * Created by Cintory on 2018/12/12 16:45
 * Email：Cintory@gmail.com
 */
fun View.setLightStatusBar() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    if ((View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR and systemUiVisibility) != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
      var flags = systemUiVisibility
      flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      systemUiVisibility = flags
    }
  }
}

fun View.clearLightStatusBar() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    var flags = systemUiVisibility
    // TODO noop if it's already not set. My bitwise-fu is not strong enough to figure out the inverse of setting it
    flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    systemUiVisibility = flags
  }
}

fun View.setLightNavBar() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    if ((View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR and systemUiVisibility) != View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) {
      var flags = systemUiVisibility
      flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
      systemUiVisibility = flags
    }
  }
}

fun View.clearLightNavBar() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    var flags = systemUiVisibility
    // TODO noop if it's already not set. My bitwise-fu is not strong enough to figure out the inverse of setting it
    flags = flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
    systemUiVisibility = flags
  }
}

inline fun View.show(animate: Boolean = false) {
  if (animate) {
    alpha = 0F
    visibility = View.VISIBLE
    animate()
      .setDuration(300)
      .setInterpolator(FastOutSlowInInterpolator())
      .withLayer()
      .alpha(1F)
      .setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          animation.removeAllListeners()
        }
      })
  } else {
    visibility = View.VISIBLE
  }
}

inline infix fun View.showIf(condition: Boolean) {
  if (condition) {
    show()
  } else {
    hide()
  }
}

inline fun View.hide(animate: Boolean = false) {
  if (animate) {
    animate()
      .setDuration(300)
      .setInterpolator(LinearOutSlowInInterpolator())
      .withLayer()
      .alpha(0F)
      .setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          animation.removeAllListeners()
          visibility = View.GONE
          alpha = 1F
        }
      })
  } else {
    visibility = View.GONE
  }
}

inline infix fun View.hideIf(condition: Boolean) {
  if (condition) {
    hide()
  } else {
    show()
  }
}

inline fun View.toggleVisibility(animate: Boolean = false) {
  if (isVisible) {
    hide(animate)
  } else {
    show(animate)
  }
}

fun Context.asDayContext(): Context {
  return if (isInNightMode()) {
    createConfigurationContext(
      Configuration(resources.configuration)
        .apply { uiMode = Configuration.UI_MODE_NIGHT_NO })
  } else this
}