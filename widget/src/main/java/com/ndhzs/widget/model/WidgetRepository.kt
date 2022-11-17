package com.ndhzs.widget.model

import android.content.Context
import com.ndhzs.widget.CourseWidget
import com.ndhzs.widget.room.WidgetDatabase
import com.ndhzs.widget.room.WidgetItem
import com.ndhzs.widget.utils.getNowTimeMinute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:47
 */
internal object WidgetRepository {
  
  private fun getNowWeekNum(): Int {
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
    return (dayOfWeek + 5) % 7
  }
  
  suspend fun getTodayItem(context: Context): WidgetItem? {
    val nowWeek = CourseWidget.nowWeek ?: return null
    val nowWeekNum = getNowWeekNum()
    val itemDao = WidgetDatabase.getInstance(context).getItemDao()
    val itemList = itemDao.query()
    if (itemList.isEmpty()) return null
    val nowTime = getNowTimeMinute()
    // 第一步：先筛选出所有正在进行的 item
    val list1 = itemList.filter {
      nowTime in it.getStartMinuteFromToday(nowWeek, nowWeekNum) until
        it.getEndMinuteFromToday(nowWeek, nowWeekNum)
    }
    if (list1.isEmpty()) {
      // 如果 list1 为空，则说明没有正在进行的 item，就返回比 nowTime 大并且 StartTime 最小的 item
      return itemList.asSequence()
        .filter {
          it.getStartMinuteFromToday(nowWeek, nowWeekNum) > nowTime &&
            it.getEndMinuteFromToday(nowWeek, nowWeekNum) < 24 * 60
        }.minByOrNull {
          it.getStartMinuteFromToday(nowWeek, nowWeekNum)
        }
    }
    // 第二步：排序 list1，找到第一个 item
    return list1.sortedWith { o1, o2 ->
      if (o1 === o2) return@sortedWith 0
      // rank 越小越在前面
      val a1 = o1.rank - o2.rank
      if (a1 != 0) return@sortedWith a1
      // 长度越大越在前面
      val a2 = o2.period - o1.period
      if (a2 != 0) return@sortedWith a2
      // 不同的对象不应该返回 0，只能用地址比较
      System.identityHashCode(o1) - System.identityHashCode(o2)
    }.first()
  }
  
  suspend fun getTomorrowItem(context: Context): WidgetItem? {
    val nowWeek = CourseWidget.nowWeek ?: return null
    val nowWeekNum = getNowWeekNum()
    val itemDao = WidgetDatabase.getInstance(context).getItemDao()
    val itemList = itemDao.query()
    if (itemList.isEmpty()) return null
    return itemList.asSequence()
      .filter {
        it.getStartMinuteFromToday(nowWeek, nowWeekNum) > 24 * 60 &&
          it.getEndMinuteFromToday(nowWeek, nowWeekNum) < 48 * 60
      }.minByOrNull {
        it.getStartMinuteFromToday(nowWeek, nowWeekNum)
      }
  }
}