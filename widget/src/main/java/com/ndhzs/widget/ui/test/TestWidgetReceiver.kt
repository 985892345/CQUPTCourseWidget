package com.ndhzs.widget.ui.test

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * .
 *
 * @author 985892345
 * 2022/11/16 23:06
 */
class TestWidgetReceiver : GlanceAppWidgetReceiver() {
  override val glanceAppWidget: GlanceAppWidget
    get() = TestWidget()
}