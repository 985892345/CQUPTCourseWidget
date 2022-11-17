package com.ndhzs.widget.ui.simple

import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.ndhzs.widget.model.WidgetRepository
import com.ndhzs.widget.utils.getShowStartTimeStr
import com.ndhzs.widget.utils.getStartRow
import com.ndhzs.widget.utils.getTodayWeekNumStr
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * .
 *
 * @author 985892345
 * 2022/11/17 16:20
 */
class SimpleWidgetWorker(
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
    val glanceIds = manager.getGlanceIds(SimpleWidget::class.java)
    return try {
      // Update state with new data
      setWidgetState(glanceIds, getAvailableSimpleInfo().apply {
        Log.d("ggg", "(${Exception().stackTrace[0].run { "$fileName:$lineNumber" }}) -> " +
          "doWork: data = $this")
      })
      Result.success()
    } catch (e: Exception) {
      setWidgetState(glanceIds, SimpleInfo.Unavailable(e.message.orEmpty()))
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
  private suspend fun setWidgetState(glanceIds: List<GlanceId>, newState: SimpleInfo) {
    glanceIds.forEach { glanceId ->
      updateAppWidgetState(
        context = context,
        definition = SimpleInfoStateDefinition,
        glanceId = glanceId,
        updateState = { newState }
      )
    }
    SimpleWidget().updateAll(context)
  }
  
  private suspend fun getAvailableSimpleInfo(): SimpleInfo.Available {
    try {
      val todayItem = WidgetRepository.getTodayItem(context)
      if (todayItem != null) {
        return SimpleInfo.Available(
          "今日：${getShowStartTimeStr(getStartRow(todayItem.beginLesson))}",
          todayItem.title,
          todayItem.content
        )
      }
      // 明天有课时只在晚上才显示
      if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 19) {
        return SimpleInfo.Available(getTodayWeekNumStr(), "今天没有课了~", "")
      }
      val tomorrowItem = WidgetRepository.getTomorrowItem(context)
      if (tomorrowItem != null) {
        return SimpleInfo.Available(
          "明日：${getShowStartTimeStr(getStartRow(tomorrowItem.beginLesson))}",
          tomorrowItem.title,
          tomorrowItem.content
        )
      }
      return SimpleInfo.Available(getTodayWeekNumStr(), "今明都没有课了~", "")
    } catch (e: Exception) {
      Log.d("ggg", "(${Exception().stackTrace[0].run { "$fileName:$lineNumber" }}) -> " +
        "e = ${e.message}")
      throw e
    }
  }
}