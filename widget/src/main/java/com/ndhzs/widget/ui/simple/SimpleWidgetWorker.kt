package com.ndhzs.widget.ui.simple

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.ndhzs.widget.ui.simple.model.SimpleWidgetRepository
import java.util.concurrent.TimeUnit

/**
 * .
 *
 * @author 985892345
 * 2022/11/17 16:20
 */
internal class SimpleWidgetWorker(
  private val context: Context,
  workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
  
  companion object {
    private val uniqueWorkName = SimpleWidgetWorker::class.java.simpleName
  
    fun enqueue(context: Context, force: Boolean = false) {
      val manager = WorkManager.getInstance(context)
      val requestBuilder = PeriodicWorkRequestBuilder<SimpleWidgetWorker>(
        30, TimeUnit.MINUTES
      )
      var workPolicy = ExistingPeriodicWorkPolicy.KEEP
    
      // Replace any enqueued work and expedite the request
      if (force) {
        workPolicy = ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE
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
    val glanceIds = manager.getGlanceIds(SimpleWidget::class.java)
    return try {
      // Update state to indicate loading
      setWidgetState(glanceIds, SimpleWidgetInfo.Loading)
      // Update state with new data
      setWidgetState(glanceIds, getAvailableSimpleInfo())
      Result.success()
    } catch (e: Exception) {
      setWidgetState(glanceIds, SimpleWidgetInfo.Unavailable(e.message.orEmpty()))
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
  private suspend fun setWidgetState(glanceIds: List<GlanceId>, newState: SimpleWidgetInfo) {
    glanceIds.forEach { glanceId ->
      updateAppWidgetState(
        context = context,
        definition = SimpleWidgetInfoStateDefinition,
        glanceId = glanceId,
        updateState = { newState }
      )
    }
    SimpleWidget().updateAll(context)
  }
  
  private suspend fun getAvailableSimpleInfo(): SimpleWidgetInfo.Available {
    return SimpleWidgetRepository.getAvailable(context)
  }
}