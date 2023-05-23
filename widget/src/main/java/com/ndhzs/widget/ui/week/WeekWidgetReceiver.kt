package com.ndhzs.widget.ui.week

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * .
 *
 * @author 985892345
 * 2022/11/16 21:25
 */
internal class WeekWidgetReceiver : GlanceAppWidgetReceiver() {
  
  override val glanceAppWidget: GlanceAppWidget
    get() = WeekWidget()
  
  override fun onEnabled(context: Context) {
    super.onEnabled(context)
    WeekWidgetWorker.enqueue(context)
  }
  
  override fun onDisabled(context: Context) {
    super.onDisabled(context)
    WeekWidgetWorker.cancel(context)
  }
}