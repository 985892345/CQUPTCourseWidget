package com.ndhzs.widget.ui.single

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * .
 *
 * @author 985892345
 * 2022/11/16 21:25
 */
internal class SingleWidgetReceiver : GlanceAppWidgetReceiver() {
  override val glanceAppWidget: GlanceAppWidget
    get() = SingleWidget()
  
  override fun onEnabled(context: Context) {
    super.onEnabled(context)
    SingleWidgetWorker.enqueue(context)
  }
  
  override fun onDisabled(context: Context) {
    super.onDisabled(context)
    SingleWidgetWorker.cancel(context)
  }
}