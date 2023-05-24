package com.ndhzs.widget.ui.single.model

import android.content.Context
import com.ndhzs.widget.CourseWidget
import com.ndhzs.widget.data.IWidgetItem
import com.ndhzs.widget.data.endTimeMinute
import com.ndhzs.widget.data.showEndTimeStr
import com.ndhzs.widget.data.showStartTimeStr
import com.ndhzs.widget.data.startTimeMinute
import com.ndhzs.widget.room.WidgetDatabase
import com.ndhzs.widget.room.WidgetItemContent
import com.ndhzs.widget.room.WidgetItemDao
import com.ndhzs.widget.ui.single.SingleWidgetInfo.*
import com.ndhzs.widget.utils.getNowTimeMinute
import com.ndhzs.widget.utils.getTodayWeekNumStr
import java.util.Calendar

/**
 * .
 *
 * @author 985892345
 * 2023/5/23 14:52
 */
internal object SingleWidgetRepository {
  
  fun getAvailable(context: Context): Available {
    val itemDao = WidgetDatabase.get(context).getItemDao()
    val nowWeek = CourseWidget.getNowWeek(context)
    val nowWeekNum = IWidgetItem.WeekNum.getNowWeekNum()
    return getTodayItemAvailable(itemDao, nowWeek, nowWeekNum)
      ?: if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 19) null else {
        Available(
          getTodayWeekNumStr(),
          "今天没有课了~",
          ""
        )
      }
      ?: getTomorrowItemAvailable(itemDao, nowWeek, nowWeekNum)
      ?: Available(getTodayWeekNumStr(), "今明都没课了~", "")
  }
  
  private fun getTodayItemAvailable(
    itemDao: WidgetItemDao,
    nowWeek: Int,
    nowWeekNum: IWidgetItem.WeekNum
  ): Available? {
    val nowTimeMinute = getNowTimeMinute()
    val todayItems = itemDao.findItems(nowWeek, nowWeekNum)
    return getTodayNowItem(nowTimeMinute, todayItems)?.let {
      Available(
        "下课：${it.item.showEndTimeStr}",
        it.item.title,
        it.item.content
      )
    } ?: getTodayNextItem(nowTimeMinute, todayItems)?.let {
      Available(
        "今日：${it.item.showStartTimeStr}",
        it.item.title,
        it.item.content
      )
    }
  }
  
  private fun getTomorrowItemAvailable(
    itemDao: WidgetItemDao,
    nowWeek: Int,
    nowWeekNum: IWidgetItem.WeekNum
  ): Available? {
    val tomorrowWeekNum = nowWeekNum + 1
    val tomorrowWeek = if (tomorrowWeekNum < nowWeekNum) nowWeek + 1 else nowWeek
    val tomorrowItems = itemDao.findItems(tomorrowWeek, tomorrowWeekNum)
    return getTomorrowItem(tomorrowItems)?.let {
      Available(
        "明日：${it.item.showStartTimeStr}",
        it.item.title,
        it.item.content
      )
    }
  }
  
  private fun getTodayNowItem(
    nowTimeMinute: Int,
    todayItems: List<WidgetItemContent>,
  ): WidgetItemContent? {
    return todayItems.filter {
      nowTimeMinute in it.item.startTimeMinute until it.item.endTimeMinute
    }.minWithOrNull { o1, o2 ->
      if (o1 === o2) return@minWithOrNull 0
      // rank 越小越在前面
      var diff = o1.rank.rank - o2.rank.rank
      if (diff != 0) return@minWithOrNull diff
      // 长度越大越在前面
      diff = o2.item.period - o1.item.period
      if (diff != 0) return@minWithOrNull diff
      // 不同的对象不应该返回 0，先用 hashcode 比较
      diff = o1.hashCode() - o2.hashCode()
      if (diff != 0) return@minWithOrNull diff
      // 如果连 hashcode 也一样，只能用地址比较了
      System.identityHashCode(o1) - System.identityHashCode(o2)
    }
  }
  
  private fun getTodayNextItem(
    nowTimeMinute: Int,
    todayItems: List<WidgetItemContent>,
  ): WidgetItemContent? {
    return todayItems.filter {
      it.item.startTimeMinute > nowTimeMinute
    }.minByOrNull {
      it.item.startTimeMinute
    }
  }
  
  private fun getTomorrowItem(
    tomorrowItems: List<WidgetItemContent>
  ): WidgetItemContent? {
    return tomorrowItems.minByOrNull {
      it.item.startTimeMinute
    }
  }
}