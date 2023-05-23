package com.ndhzs.cquptcoursewidget.widghet.simple

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * .
 *
 * @author 985892345
 * 2023/5/23 16:54
 */
object SimpleWidget {
  
  @Composable
  fun AvailableContent(
    time: String,
    title: String,
    content: String
  ) {
    Column(
      modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight()
        .background(Color.Transparent)
        .padding(horizontal = 10.dp),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = time,
        modifier = Modifier.background(Color.Transparent),
        style = TextStyle(
          color = Color.White,
          fontSize = 14.sp
        ),
        maxLines = 1
      )
      Text(
        text = title,
        modifier = Modifier.background(Color.Transparent),
        style = TextStyle(
          color = Color.White,
          fontSize = 18.sp
        ),
        maxLines = 1
      )
      Spacer(
        Modifier
          .height(0.8.dp)
          .width(140.dp)
          .background(Color.White)
      )
      Text(
        text = content,
        modifier = Modifier.background(Color.Transparent),
        style = TextStyle(
          color = Color.White,
          fontSize = 16.sp
        ),
        maxLines = 1
      )
    }
  }
}

@Preview
@Composable
fun ContentPreview() {
  SimpleWidget.AvailableContent(time = "今日：19:00", title = "唱跳rap篮球", content = "体育场")
}