package com.ndhzs.widget.ui.simple

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * .
 *
 * @author 985892345
 * 2022/11/16 21:25
 */
internal class SimpleWidgetReceiver : GlanceAppWidgetReceiver() {
  override val glanceAppWidget: GlanceAppWidget
    get() = SimpleWidget()
  
  override fun onEnabled(context: Context) {
    super.onEnabled(context)
    SimpleWidgetWorker.enqueue(context)
  }
  
  override fun onDisabled(context: Context) {
    super.onDisabled(context)
    SimpleWidgetWorker.cancel(context)
  }
}