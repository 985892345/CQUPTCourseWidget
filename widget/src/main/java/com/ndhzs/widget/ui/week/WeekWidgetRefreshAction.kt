package com.ndhzs.widget.ui.week

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

/**
 * .
 *
 * @author 985892345
 * 2022/11/17 16:44
 */
internal class WeekWidgetRefreshAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters
  ) {
    // Force the worker to refresh
    WeekWidgetWorker.enqueue(context = context, force = true)
  }
}