package com.ndhzs.widget.ui.week

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * .
 *
 * @author 985892345
 * 2022/11/17 16:20
 */
internal class WeekWidgetWorker(
  private val context: Context,
  workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
  
  companion object {
    private val uniqueWorkName = WeekWidgetWorker::class.java.simpleName
  
    fun enqueue(context: Context, force: Boolean = false) {
      val manager = WorkManager.getInstance(context)
      val requestBuilder = PeriodicWorkRequestBuilder<WeekWidgetWorker>(
        30, TimeUnit.MINUTES
      )
      var workPolicy = ExistingPeriodicWorkPolicy.KEEP
    
      // Replace any enqueued work and expedite the request
      if (force) {
        workPolicy = ExistingPeriodicWorkPolicy.REPLACE
      }
    
      manager.enqueueUniquePeriodicWork(
        uniqueWorkName,
        workPolicy,
        requestBuilder.build()
      )
    }
  
    fun cancel(context: Context) {
      WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
    }
  }
  
  override suspend fun doWork(): Result {
    val manager = GlanceAppWidgetManager(context)
    val glanceIds = manager.getGlanceIds(WeekWidget::class.java)
    return try {
      // Update state to indicate loading
      setWidgetState(glanceIds, WeekWidgetInfo.Loading)
      Result.success()
    } catch (e: Exception) {
      setWidgetState(glanceIds, WeekWidgetInfo.Unavailable(e.message.orEmpty()))
      if (runAttemptCount < 10) {
        // Exponential backoff strategy will avoid the request to repeat
        // too fast in case of failures.
        Result.retry()
      } else {
        Result.failure()
      }
    }
  }
  
  /**
   * Update the state of all widgets and then force update UI
   */
  private suspend fun setWidgetState(glanceIds: List<GlanceId>, newState: WeekWidgetInfo) {
    glanceIds.forEach { glanceId ->
      updateAppWidgetState(
        context = context,
        definition = WeekWidgetInfoStateDefinition,
        glanceId = glanceId,
        updateState = { newState }
      )
    }
    WeekWidget().updateAll(context)
  }
}