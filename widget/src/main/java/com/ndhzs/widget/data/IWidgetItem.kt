package com.ndhzs.widget.data

import com.ndhzs.widget.utils.getEndRow
import com.ndhzs.widget.utils.getEndTimeMinute
import com.ndhzs.widget.utils.getStartRow
import com.ndhzs.widget.utils.getStartTimeMinute

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:00
 */
interface IWidgetItem {
  val rank: Int // 显示的优先级
  val week: Int // 周数
  val weekNum: Int // 星期数，星期一为 0
  val beginLesson: Int // 开始节数，如：1、2 节课以 1 开始；3、4 节课以 3 开始，注意：中午是以 -1 开始，傍晚是以 -2 开始
  val period: Int // 课的长度
  val title: String // 标题
  val content: String // 内容
  
  fun getStartMinuteFromToday(nowWeek: Int, nowWeekNum: Int): Int {
    return (week - nowWeek) * 7 * 24 * 60 + (weekNum - nowWeekNum) * 24 * 60 + getStartTimeMinute(
      getStartRow(beginLesson)
    )
  }
  
  fun getEndMinuteFromToday(nowWeek: Int, nowWeekNum: Int): Int {
    return (week - nowWeek) * 7 * 24 * 60 + (weekNum - nowWeekNum) * 24 * 60 + getEndTimeMinute(
      getEndRow(beginLesson, period)
    )
  }
}