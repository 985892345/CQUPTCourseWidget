package com.ndhzs.widget.utils

import java.util.*

/**
 * .
 *
 * @author 985892345
 * 2022/11/17 16:31
 */

fun getTodayWeekNumStr(): String {
  return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
    Calendar.MONDAY -> "周一"
    Calendar.TUESDAY -> "周二"
    Calendar.WEDNESDAY -> "周三"
    Calendar.THURSDAY -> "周四"
    Calendar.FRIDAY -> "周五"
    Calendar.SATURDAY -> "周六"
    Calendar.SUNDAY -> "周日"
    else -> "null"
  }
}