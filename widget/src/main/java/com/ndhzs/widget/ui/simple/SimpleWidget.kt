package com.ndhzs.widget.ui.simple

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:35
 */
internal class SimpleWidget : GlanceAppWidget() {
  
  override val stateDefinition: GlanceStateDefinition<*>
    get() = SimpleWidgetInfoStateDefinition
  
  override suspend fun provideGlance(context: Context, id: GlanceId) {
    SimpleWidgetWorker.enqueue(context, true)
    provideContent {
      Content()
    }
  }
  
  @Composable
  private fun Content() {
    when (val simpleInfo = currentState<SimpleWidgetInfo>()) {
      SimpleWidgetInfo.Unloaded -> {
        UnloadedContent()
      }
      
      SimpleWidgetInfo.Loading -> {
        Box(contentAlignment = Alignment.Center) {
          CircularProgressIndicator()
        }
      }
      
      is SimpleWidgetInfo.Available -> {
        AvailableContent(
          time = simpleInfo.time,
          title = simpleInfo.title,
          content = simpleInfo.content
        )
      }
      
      is SimpleWidgetInfo.Unavailable -> {
        UnavailableContent()
      }
    }
  }
  
  @Composable
  private fun UnloadedContent() {
    Text(
      text = "点击刷新课表",
      modifier = GlanceModifier
        .background(Color.Transparent)
        .clickable(actionRunCallback<SimpleWidgetRefreshAction>()),
      style = TextStyle(
        color = ColorProvider(Color.White),
        fontSize = 26.sp
      )
    )
  }
  
  @Composable
  fun AvailableContent(
    time: String,
    title: String,
    content: String
  ) {
    Column(
      modifier = GlanceModifier
        .wrapContentWidth()
        .wrapContentHeight()
        .background(Color.Transparent)
        .padding(horizontal = 10.dp)
        .clickable(actionRunCallback<SimpleWidgetRefreshAction>()),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = time,
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = 14.sp
        ),
        maxLines = 1
      )
      Text(
        text = title,
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = 18.sp
        ),
        maxLines = 1
      )
      Spacer(
        GlanceModifier
          .height(0.8.dp)
          .width(140.dp)
          .background(Color.White)
      )
      Text(
        text = content,
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = 16.sp
        ),
        maxLines = 1
      )
    }
  }
  
  @Composable
  private fun UnavailableContent() {
    Column(
      modifier = GlanceModifier
        .wrapContentWidth()
        .fillMaxHeight()
        .background(Color.Transparent)
        .clickable(actionRunCallback<SimpleWidgetRefreshAction>()),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "出现错误",
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = 20.sp
        )
      )
      Text(
        text = "点击刷新试试 >_<",
        modifier = GlanceModifier.background(Color.Transparent),
        style = TextStyle(
          color = ColorProvider(Color.White),
          fontSize = 18.sp
        )
      )
    }
  }
}
