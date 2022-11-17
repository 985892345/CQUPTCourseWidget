package com.ndhzs.widget.ui.simple

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.ndhzs.widget.utils.getTodayWeekNumStr

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:35
 */
internal class SimpleWidget : GlanceAppWidget() {
  
  override val stateDefinition: GlanceStateDefinition<*>
    get() = SimpleInfoStateDefinition
  
  @OptIn(ExperimentalUnitApi::class)
  @Composable
  override fun Content() {
    val data = when (val simpleInfo = currentState<SimpleInfo>()) {
      SimpleInfo.Unloaded -> {
        Triple(getTodayWeekNumStr(), "点击刷新", "")
      }
      is SimpleInfo.Available -> {
        Triple(simpleInfo.time, simpleInfo.title, simpleInfo.content)
      }
      is SimpleInfo.Unavailable -> {
        Triple(getTodayWeekNumStr(), "出现错误", "点击刷新试试 >_<")
      }
    }
    Column(
      modifier = GlanceModifier
        .wrapContentWidth()
        .fillMaxHeight()
        .background(Color.Transparent)
        .cornerRadius(10.dp)
        .clickable(actionRunCallback<RefreshAction>()),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = data.first,
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = TextUnit(14F, TextUnitType.Sp)
        )
      )
      Text(
        text = data.second,
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = TextUnit(18F, TextUnitType.Sp)
        )
      )
      Spacer(
        GlanceModifier
          .height(1.dp)
          .fillMaxWidth()
          .background(Color(0x7EEEEEEE))
      )
      Text(
        text = data.third,
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = TextUnit(16F, TextUnitType.Sp)
        )
      )
    }
  }
}
