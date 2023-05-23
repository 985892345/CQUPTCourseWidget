package com.ndhzs.widget.ui.week.size4x1

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.layout.*
import androidx.glance.text.Text
import com.ndhzs.widget.ui.week.WeekData
import com.ndhzs.widget.ui.week.actionDayPositionWeekWidget

/**
 * .
 *
 * @author 985892345
 * 2022/11/18 11:48
 */
@Composable
internal fun Size4x1Content(
  nowWeek: Int,
  nowWeekNum: Int,
  data: Map<Int, WeekData>,
  showWeek: Int,
  showWeekNum: Int,
  position: Int
) {
  Column(
    modifier = GlanceModifier
      .fillMaxSize()
  ) {
    Row(
      modifier = GlanceModifier
        .fillMaxWidth()
        .height(20.dp)
    ) {
      Button(
        text = "<",
        onClick = actionDayPositionWeekWidget(false)
      )
      Button(
        text = ">",
        onClick = actionDayPositionWeekWidget(true)
      )
    }
    Row(
      modifier = GlanceModifier
        .fillMaxSize()
    ) {
      Column(
        modifier = GlanceModifier
          .fillMaxHeight()
          .width(20.dp)
      ) {
        Text(text = "1", modifier = GlanceModifier.defaultWeight())
        Text(text = "2", modifier = GlanceModifier.defaultWeight())
        Text(text = "3", modifier = GlanceModifier.defaultWeight())
        Text(text = "4", modifier = GlanceModifier.defaultWeight())
        Text(text = "5", modifier = GlanceModifier.defaultWeight())
        Text(text = "6", modifier = GlanceModifier.defaultWeight())
        Text(text = "7", modifier = GlanceModifier.defaultWeight())
        Text(text = "8", modifier = GlanceModifier.defaultWeight())
        Text(text = "9", modifier = GlanceModifier.defaultWeight())
        Text(text = "10", modifier = GlanceModifier.defaultWeight())
        Text(text = "11", modifier = GlanceModifier.defaultWeight())
        Text(text = "12", modifier = GlanceModifier.defaultWeight())
      }
      Column(
        modifier = GlanceModifier
          .fillMaxSize()
      ) {
      
      }
    }
  }
}