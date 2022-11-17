package com.ndhzs.widget.ui.simple

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
class RefreshAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters
  ) {
    // Force the worker to refresh
    SimpleWidgetWorker.enqueue(context = context, force = true)
  }
}