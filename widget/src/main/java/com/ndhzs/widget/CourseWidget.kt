package com.ndhzs.widget

import android.content.Context
import android.util.Log
import com.ndhzs.widget.data.IWidgetItem
import com.ndhzs.widget.room.WidgetDatabase
import com.ndhzs.widget.room.WidgetItem

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 14:56
 */
object CourseWidget {
  
  internal var nowWeek: Int? = null
    private set
  
  /**
   * 设置数据
   *
   * Map 中 key 为当前 item 组的唯一标识，可以是学号这种东西
   */
  suspend fun setData(context: Context, nowWeek: Int, data: Map<String, List<IWidgetItem>>) {
    this.nowWeek = nowWeek
    WidgetDatabase.getInstance(context)
      .getItemDao()
      .replace(
        data.map { (key, list) ->
          list.map {
            WidgetItem(
              key,
              it.rank,
              it.week,
              it.weekNum,
              it.beginLesson,
              it.period,
              it.title,
              it.content
            )
          }
        }.flatten()
      )
  }
}