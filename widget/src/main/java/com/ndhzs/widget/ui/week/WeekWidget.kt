package com.ndhzs.widget.ui.week

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:35
 */
internal class WeekWidget : GlanceAppWidget() {
  
  companion object {
    private val Size4x1 = DpSize(60.dp, 200.dp)
    private val Size4x3 = DpSize(180.dp, 200.dp)
    private val Size4x7 = DpSize(340.dp, 200.dp)
    private val Size12x1 = DpSize(60.dp, 550.dp)
    private val Size12x3 = DpSize(180.dp, 550.dp)
    private val Size12x7 = DpSize(340.dp, 550.dp)
  }
  
  override val stateDefinition: GlanceStateDefinition<*>
    get() = WeekWidgetInfoStateDefinition
  
  override suspend fun provideGlance(context: Context, id: GlanceId) {
    provideContent {
      Content()
    }
  }
  
  override val sizeMode: SizeMode
    get() = SizeMode.Responsive(
      setOf(
        Size4x1, Size4x3,
        Size4x7, Size12x1,
        Size12x3, Size12x7,
      )
    )
  
  @Composable
  private fun Content() {
    val info = currentState<WeekWidgetInfo>()
    val size = LocalSize.current
    val diffDayPosition = currentState(key = DiffDayPositionKey) ?: 0
    val diffWeekPosition = currentState(key = DiffWeekPositionKey) ?: 0
    when (info) {
      WeekWidgetInfo.Unloaded -> {
        UnloadedContent()
      }
      WeekWidgetInfo.Loading -> {
        Box(contentAlignment = Alignment.Center) {
          CircularProgressIndicator()
        }
      }
      is WeekWidgetInfo.Available -> {
        val showWeek = getShowWeek(info.nowWeek, diffWeekPosition, info.minWeek, info.maxWeek)
        val showWeekNum = getShowWeekNum(info.nowWeekNum, diffDayPosition)
        when (size) {
//          Size1x5 -> Size1x5Content()
//          Size1x6 -> Size1x6Content()
//          Size2x5 -> Size2x5Content()
//          Size2x6 -> Size2x6Content()
//          Size3x5 -> Size3x5Content()
//          Size3x6 -> Size3x6Content()
//          Size4x5 -> Size4x5Content()
//          Size4x6 -> Size4x6Content()
        }
      }
      is WeekWidgetInfo.Unavailable -> {
        UnloadedContent()
      }
    }
  }
  
  @Composable
  private fun UnloadedContent() {
    Box(
      modifier = GlanceModifier
        .fillMaxSize()
        .background(Color.White),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "点击刷新",
        modifier = GlanceModifier
          .wrapContentSize()
          .clickable(actionRunCallback<WeekWidgetRefreshAction>()),
        style = TextStyle(
          color = ColorProvider(Color.Black),
          fontSize = 50.sp,
          textAlign = TextAlign.Center,
        )
      )
    }
  }
  
  @Composable
  private fun UnavailableContent() {
    Column(
      modifier = GlanceModifier
        .wrapContentWidth()
        .fillMaxHeight()
        .background(Color.White)
        .clickable(actionRunCallback<WeekWidgetRefreshAction>()),
      verticalAlignment = Alignment.CenterVertically,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "出现错误",
        modifier = GlanceModifier.wrapContentSize(),
        style = TextStyle(
          color = ColorProvider(Color.Black),
          fontSize = 30.sp
        )
      )
      Text(
        text = "点击刷新试试 >_<",
        modifier = GlanceModifier.wrapContentSize(),
        style = TextStyle(
          color = ColorProvider(Color.Black),
          fontSize = 24.sp
        )
      )
    }
  }
  
  private fun getShowWeek(nowWeek: Int, diffWeek: Int, minWeek: Int, maxWeek: Int): Int {
    val week = nowWeek + diffWeek
    return if (week < minWeek) minWeek else if (week > maxWeek) maxWeek else week
  }
  
  private fun getShowWeekNum(nowWeekNum: Int, diffWeekNum: Int): Int {
    val week = nowWeekNum + diffWeekNum
    return if (week < 0) 0 else if (week > 6) 6 else week
  }
}
