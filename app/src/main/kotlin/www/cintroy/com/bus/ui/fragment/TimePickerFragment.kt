package www.cintroy.com.bus.ui.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * Created by Cintory on 2019/2/13 17:16
 * Email：Cintory@gmail.com
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

  internal var callBack: ((view: TimePicker, hourOfDay: Int, minute: Int) -> Unit)? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    // Use the current time as the default values for the picker
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    // Create a new instance of TimePickerDialog and return it
    return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
  }

  override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
    // Do something with the time chosen by the user
    callBack?.invoke(view, hourOfDay, minute)
  }


  companion object {

    val TAG = "TimePickerFragment"

    fun getInstance(callback: (view: TimePicker, hourOfDay: Int, minute: Int) -> Unit): TimePickerFragment {
      val fragment = TimePickerFragment()
      fragment.callBack = callback
      return fragment
    }
  }

}