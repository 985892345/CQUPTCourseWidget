package com.ndhzs.cquptcoursewidget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ndhzs.widget.CourseWidget
import com.ndhzs.widget.data.IWidgetItem
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    lifecycleScope.launch {
      CourseWidget.setData(
        this@MainActivity,
        11,
        mapOf(
          "123" to listOf(
            WidgetData(
              0, 11, 4, 3, 2, "软件分析与设计", "B411/B412"
            )
          )
        )
      )
    }
  }
  
  data class WidgetData(
    override val rank: Int,
    override val week: Int,
    override val weekNum: Int,
    override val beginLesson: Int,
    override val period: Int,
    override val title: String,
    override val content: String
  ) : IWidgetItem
}