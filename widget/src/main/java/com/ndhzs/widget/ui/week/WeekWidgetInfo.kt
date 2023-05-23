package com.ndhzs.widget.ui.week

import com.ndhzs.widget.room.WidgetItemContent
import kotlinx.serialization.Serializable

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/17 8:36
 */
@Serializable
internal sealed interface WeekWidgetInfo {
  
  @Serializable
  object Unloaded : WeekWidgetInfo
  
  @Serializable
  object Loading : WeekWidgetInfo
  
  @Serializable
  data class Available(
    val nowWeek: Int,
    val nowWeekNum: Int,
    val data: Map<Int, WeekData>,
    val minWeek: Int,
    val maxWeek: Int,
  ) : WeekWidgetInfo
  
  @Serializable
  data class Unavailable(val message: String) : WeekWidgetInfo
}

@Serializable
internal data class WeekData(
  val month: String,
  val mon: DayData,
  val tue: DayData,
  val wed: DayData,
  val thu: DayData,
  val fri: DayData,
  val sat: DayData,
  val sun: DayData
)

@Serializable
internal data class DayData(
  val weekNumStr: String,
  val itemList: List<WidgetItemContent>
)