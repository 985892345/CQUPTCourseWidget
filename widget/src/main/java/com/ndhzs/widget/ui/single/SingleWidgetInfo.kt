package com.ndhzs.widget.ui.single

import kotlinx.serialization.Serializable

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/17 8:36
 */
@Serializable
internal sealed interface SingleWidgetInfo {
  
  @Serializable
  object Unloaded : SingleWidgetInfo
  
  @Serializable
  object Loading : SingleWidgetInfo
  
  @Serializable
  data class Available(
    val time: String,
    val title: String,
    val content: String
  ) : SingleWidgetInfo
  
  @Serializable
  data class Unavailable(val message: String) : SingleWidgetInfo
}