package io.cintroy.www.bus.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import io.cintroy.www.bus.R
import java.io.File
import java.io.IOException

/**
 * Created by Cintory on 2018/12/12 16:47
 * Email：Cintory@gmail.com
 */
fun Context.clearCache() = cleanDir(applicationContext.cacheDir)

private fun cleanDir(dir: File): Long {
  var bytesDeleted: Long = 0
  dir.listFiles()
      .forEach {
        val length = it.length()
        try {
          if (it.delete()) {
            bytesDeleted += length
          }
        } catch (e: IOException) {
          // Ignore these for now
        }
      }
  return bytesDeleted
}

/**
 * Attempt to launch the supplied [Intent]. Queries on-device packages before launching and
 * will display a simple message if none are available to handle it.
 */
fun Context.maybeStartActivity(intent: Intent): Boolean = maybeStartActivity(intent, false)

/**
 * Attempt to launch Android's chooser for the supplied [Intent]. Queries on-device
 * packages before launching and will display a simple message if none are available to handle
 * it.
 */
fun Context.maybeStartChooser(intent: Intent): Boolean = maybeStartActivity(intent, true)

private fun Context.maybeStartActivity(
    inputIntent: Intent,
    chooser: Boolean
): Boolean {
  var intent = inputIntent
  return if (hasHandler(intent)) {
    if (chooser) {
      intent = Intent.createChooser(intent, null)
    }
    startActivity(intent)
    true
  } else {
    Toast.makeText(
        this, R.string.no_intent_handler,
        LENGTH_LONG
    ).show()
    false
  }
}

/**
 * Queries on-device packages for a handler for the supplied [Intent].
 */
private fun Context.hasHandler(intent: Intent) =
    !packageManager.queryIntentActivities(intent, 0).isEmpty()

private val typedValue = TypedValue()

@ColorInt
@UiThread
fun Context.resolveAttributeColor(@AttrRes resId: Int): Int {
  theme.resolveAttribute(resId, typedValue, true)
  if (typedValue.type in TypedValue.TYPE_FIRST_COLOR_INT..TypedValue.TYPE_LAST_COLOR_INT) {
    return typedValue.data
  }
  val colorRes = if (typedValue.resourceId != 0) {
    typedValue.resourceId
  } else {
    typedValue.data
  }
  return ContextCompat.getColor(this, colorRes)
}

fun Context.isInNightMode(): Boolean {
  val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
  return when (currentNightMode) {
    Configuration.UI_MODE_NIGHT_NO -> false // Night mode is not active, we're in day time
    Configuration.UI_MODE_NIGHT_YES -> true // Night mode is active, we're at night!
    else -> false // We don't know what mode we're in, assume notnight
  }
}

/**
 * Determine if the navigation bar will be on the bottom of the screen, based on logic in
 * PhoneWindowManager.
 */
fun Context.isNavBarOnBottom(): Boolean {
  val res = resources
  val cfg = resources.configuration
  val dm = res.displayMetrics
  val canMove = dm.widthPixels != dm.heightPixels && cfg.smallestScreenWidthDp < 600
  return !canMove || dm.widthPixels < dm.heightPixels
}

inline fun Context.dp2px(dipValue: Float) = resources.dp2px(dipValue)

inline fun Resources.dp2px(dipValue: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, displayMetrics)

fun Context.registerReceiver(intentFilter: IntentFilter): Observable<Intent> {
  return Observable.create { emitter ->
    val receiver = object : BroadcastReceiver() {
      override fun onReceive(context: Context, intent: Intent) {
        emitter.onNext(intent)
      }
    }

    registerReceiver(receiver, intentFilter)

    emitter.setCancellable { unregisterReceiver(receiver) }
  }
}