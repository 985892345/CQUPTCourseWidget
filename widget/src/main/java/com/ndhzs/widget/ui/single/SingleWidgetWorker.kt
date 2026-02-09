package com.ndhzs.widget.ui.single

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.ndhzs.widget.ui.single.model.SingleWidgetRepository
import java.util.concurrent.TimeUnit

/**
 * .
 *
 * @author 985892345
 * 2022/11/17 16:20
 */
internal class SingleWidgetWorker(
  private val context: Context,
  workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
  
  companion object {
    private val uniqueWorkName = SingleWidgetWorker::class.java.simpleName
  
    fun enqueue(context: Context, force: Boolean = false) {
      val manager = WorkManager.getInstance(context)
      val requestBuilder = PeriodicWorkRequestBuilder<SingleWidgetWorker>(
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
    val glanceIds = manager.getGlanceIds(SingleWidget::class.java)
    return try {
      // Update state to indicate loading
      setWidgetState(glanceIds, SingleWidgetInfo.Loading)
      // Update state with new data
      setWidgetState(glanceIds, getAvailableSingleInfo())
      Result.success()
    } catch (e: Exception) {
      setWidgetState(glanceIds, SingleWidgetInfo.Unavailable(e.message.orEmpty()))
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
  private suspend fun setWidgetState(glanceIds: List<GlanceId>, newState: SingleWidgetInfo) {
    glanceIds.forEach { glanceId ->
      updateAppWidgetState(
        context = context,
        definition = SingleWidgetInfoStateDefinition,
        glanceId = glanceId,
        updateState = { newState }
      )
    }
    SingleWidget().updateAll(context)
  }
  
  private fun getAvailableSingleInfo(): SingleWidgetInfo.Available {
    return SingleWidgetRepository.getAvailable(context)
  }
}