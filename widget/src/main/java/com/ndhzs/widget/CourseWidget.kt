package com.ndhzs.widget

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.ndhzs.widget.data.IWidgetItem
import com.ndhzs.widget.data.IWidgetRank
import com.ndhzs.widget.room.WidgetDatabase
import com.ndhzs.widget.ui.single.SingleWidgetWorker

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 14:56
 */
object CourseWidget {
  
  internal fun getNowWeek(context: Context): Int {
    return context.getSharedPreferences("CourseWidget", Context.MODE_PRIVATE).getInt("nowWeek", 0)
  }
  
  /**
   * 设置数据
   *
   * Map 中 key 为当前 item 组的显示优先级，越小越先显示上面
   */
  @WorkerThread
  fun setData(context: Context, nowWeek: Int, data: Map<IWidgetRank, List<IWidgetItem>>) {
    context.getSharedPreferences("CourseWidget", Context.MODE_PRIVATE).edit {
      putInt("nowWeek", nowWeek)
    }
    WidgetDatabase.get(context)
      .getItemDao()
      .replaceAll(data)
    SingleWidgetWorker.enqueue(context, true)
  }
  
  /*
  * 因为 Glance 是残缺版 Compose，很多无法实现，所以不再继续使用 glance 实现小组件
  *
  *
  *
  * */
}