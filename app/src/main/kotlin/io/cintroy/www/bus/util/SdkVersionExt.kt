package io.cintroy.www.bus.util

import android.os.Build

/**
 * Created by Cintory on 2018/12/12 16:50
 * Email：Cintory@gmail.com
 */
fun isM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun isN(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
fun isO(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
fun isOMR1(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1
fun isP(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

// Not totally safe to use yet
// https://issuetracker.google.com/issues/64550633
inline fun sdk(level: Int, func: () -> Unit) {
  if (Build.VERSION.SDK_INT >= level) func.invoke()
}