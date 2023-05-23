package com.ndhzs.cquptcoursewidget.widghet.week

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * .
 *
 * @author 985892345
 * 2023/5/23 18:43
 */
object Size4x1Widget {
  
  val SideTestArray = arrayOf(
    "1", "2", "3", "4", "午", "5", "6", "7", "8", "傍", "9", "10", "11", "12",
  )
  
  @Composable
  fun Content() {
    Surface(shape = RoundedCornerShape(8.dp)) {
      Column(
        modifier = Modifier
          .width(60.dp)
          .height(200.dp)
          .background(Color.White)
          .padding(top = 4.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
          horizontalArrangement = Arrangement.Center
        ) {
          Text(text = "<")
          Text(text = "周一", modifier = Modifier.padding(horizontal = 2.dp))
          Text(text = ">")
        }
        ScrollListItem()
      }
    }
  }
  
  @Composable
  private fun ScrollListItem() {
    var height by remember {
      mutableStateOf((174 * 2.75).toInt())
    }
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 2.dp, end = 2.dp)
        .onSizeChanged {
          height = it.height
        }
        .verticalScroll(state = rememberScrollState())
    ) {
      ListItem(rowHeight = height / 4F)
    }
  }
  
  @Composable
  private fun ListItem(rowHeight: Float) {
    val height = with(LocalDensity.current) { rowHeight.toDp() * 14 }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(height)
    ) {
      Column(
        modifier = Modifier
          .fillMaxHeight()
          .wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        SideTestArray.forEach {
          SideText(text = it, modifier = Modifier.weight(1F))
        }
      }
      Column(
        modifier = Modifier
          .fillMaxSize()
      ) {
        repeat(7) {
          Box(modifier = Modifier.weight(1F)) {
            Item(title = "title$it", content = "content$it")
          }
        }
      }
    }
  }
  
  
  @Composable
  private fun SideText(text: String, modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
      Text(text = text)
    }
  }
  
  @Composable
  private fun Item(title: String, content: String) {
    Surface(
      shape = RoundedCornerShape(8.dp),
      modifier = Modifier
        .padding(1.dp),
      color = Color.Gray,
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(4.dp)
      ) {
        Text(
          text = title,
          modifier = Modifier
            .padding(top = 10.dp)
            .align(Alignment.TopCenter),
          maxLines = 2,
          textAlign = TextAlign.Center,
        )
        Text(
          text = content,
          modifier = Modifier
            .padding(bottom = 10.dp)
            .align(Alignment.BottomCenter),
          maxLines = 2,
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}

@Preview
@Composable
fun ContentPreview() {
  Size4x1Widget.Content()
}