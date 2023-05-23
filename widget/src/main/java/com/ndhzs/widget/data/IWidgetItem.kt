package com.ndhzs.widget.data

import com.ndhzs.widget.utils.*
import java.util.Calendar

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:00
 */
interface IWidgetItem {
  val week: Int // 周数
  val weekNum: WeekNum // 星期数
  val start: Start // 开始的位置
  val period: Int // 课的长度
  val title: String // 标题
  val content: String // 内容
  
  enum class Start {
    Lesson1,
    Lesson2,
    Lesson3,
    Lesson4,
    Noon,
    Lesson5,
    Lesson6,
    Lesson7,
    Lesson8,
    Dusk,
    Lesson9,
    Lesson10,
    Lesson11,
    Lesson12;
    
    val row: Int
      get() = ordinal
    val beginLesson: Int
      get() = getBeginLesson(row)
    val startTimeMinute: Int
      get() = getBeginLesson(row)
    val showStartTimeStr: String
      get() = getShowStartTimeStr(row)
    val showEndTimeStr: String
      get() = getShowEndTimeStr(row)
  }
  
  enum class WeekNum {
    Mon,
    Tue,
    Wed,
    Thu,
    Fri,
    Sat,
    Sun;
    
    operator fun plus(num: Int): WeekNum {
      val values = values()
      return values[(ordinal + num) % values.size]
    }
    
    companion object {
      fun getNowWeekNum(): WeekNum {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        /*
        * 转换星期数，为了跟 hashDay 相匹配
        * (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 的逻辑如下：
        * 星期天：1 -> 6
        * 星期一：2 -> 0
        * 星期二：3 -> 1
        * 星期三：4 -> 2
        * 星期四：5 -> 3
        * 星期五：6 -> 4
        * 星期六：7 -> 5
        *
        * 左边一栏是 Calendar.get(Calendar.DAY_OF_WEEK) 得到的数字，
        * 右边一栏是该数字距离周一的天数差
        * */
        return WeekNum.values()[(dayOfWeek + 5) % 7]
      }
    }
  }
}

val IWidgetItem.startRow: Int
  get() = start.ordinal
val IWidgetItem.endRow: Int
    get() = getEndRow(beginLesson, period)
val IWidgetItem.beginLesson: Int
  get() = getBeginLesson(startRow)
val IWidgetItem.startTimeMinute: Int
  get() = getStartTimeMinute(startRow)
val IWidgetItem.endTimeMinute: Int
  get() = getEndTimeMinute(endRow)
val IWidgetItem.showTimeStr: String
  get() = getShowTimeStr(beginLesson, period)
val IWidgetItem.showStartTimeStr: String
  get() = getShowStartTimeStr(startRow)
val IWidgetItem.showEndTimeStr: String
  get() = getShowEndTimeStr(endRow)