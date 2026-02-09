package com.ndhzs.cquptcoursewidget

import android.graphics.Color
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.ndhzs.cquptcoursewidget.theme.ComposeTestTheme
import com.ndhzs.cquptcoursewidget.widghet.week.ContentPreview
import com.ndhzs.widget.CourseWidget
import com.ndhzs.widget.data.IWidgetItem
import com.ndhzs.widget.data.IWidgetRank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposeTestTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          ContentPreview()
        }
      }
    }
    
    
    lifecycleScope.launch(Dispatchers.IO) {
      CourseWidget.setData(
        this@MainActivity,
        14,
        mapOf(
          WidgetRank(0, Color.YELLOW, Color.BLACK) to listOf(
            WidgetData(
              14,
              IWidgetItem.WeekNum.Tue,
              IWidgetItem.Start.Lesson9,
              2,
              "软件分析与设计",
              "B411/B412"
            )
          )
        )
      )
    }
  }
  
  data class WidgetRank(
    override val rank: Int,
    override val bgColor: Int,
    override val tvColor: Int
  ) : IWidgetRank
  
  data class WidgetData(
    override val week: Int,
    override val weekNum: IWidgetItem.WeekNum,
    override val start: IWidgetItem.Start,
    override val period: Int,
    override val title: String,
    override val content: String
  ) : IWidgetItem
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  ComposeTestTheme {
    Greeting("Android")
  }
}