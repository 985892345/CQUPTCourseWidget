package com.ndhzs.widget.ui.week

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.state.updateAppWidgetState

/**
 * .
 *
 * @author 985892345
 * 2022/11/18 12:17
 */

internal fun actionDayPositionWeekWidget(incOrDec: Boolean): Action {
  return actionRunCallback<DayPositionAction>(
    actionParametersOf(IncOrDecKey to incOrDec)
  )
}

internal fun actionWeekPositionWeekWidget(incOrDec: Boolean): Action {
  return actionRunCallback<WeekPositionAction>(
    actionParametersOf(IncOrDecKey to incOrDec)
  )
}

internal val DiffDayPositionKey = intPreferencesKey("DiffDayPosition")
internal val DiffWeekPositionKey = intPreferencesKey("DiffWeekPosition")

private val IncOrDecKey = ActionParameters.Key<Boolean>("key")

private class DayPositionAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    val incOrDec = parameters[IncOrDecKey] ?: return
    updateAppWidgetState(context, glanceId) {
      val old = it[DiffDayPositionKey] ?: return@updateAppWidgetState
      it[DiffDayPositionKey] = if (incOrDec) old + 1 else old - 1
    }
    WeekWidget().update(context, glanceId)
  }
}


private class WeekPositionAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    val incOrDec = parameters[IncOrDecKey] ?: return
    updateAppWidgetState(context, glanceId) {
      val old = it[DiffWeekPositionKey] ?: return@updateAppWidgetState
      it[DiffWeekPositionKey] = if (incOrDec) old + 1 else old - 1
    }
    WeekWidget().update(context, glanceId)
  }
}