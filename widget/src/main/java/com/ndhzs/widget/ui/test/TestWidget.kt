package com.ndhzs.widget.ui.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.layout.size
import androidx.glance.text.Text

/**
 * .
 *
 * @author 985892345
 * 2022/11/16 23:06
 */
class TestWidget : GlanceAppWidget() {
  
  @Composable
  override fun Content() {
    Text(
      text = "Test",
      modifier = GlanceModifier.size(20.dp)
    )
  }
}