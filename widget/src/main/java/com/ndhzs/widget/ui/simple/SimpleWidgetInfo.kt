package com.ndhzs.widget.ui.simple

import kotlinx.serialization.Serializable

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/17 8:36
 */
@Serializable
internal sealed interface SimpleWidgetInfo {
  
  @Serializable
  object Unloaded : SimpleWidgetInfo
  
  @Serializable
  object Loading : SimpleWidgetInfo
  
  @Serializable
  data class Available(
    val time: String,
    val title: String,
    val content: String
  ) : SimpleWidgetInfo
  
  @Serializable
  data class Unavailable(val message: String) : SimpleWidgetInfo
}