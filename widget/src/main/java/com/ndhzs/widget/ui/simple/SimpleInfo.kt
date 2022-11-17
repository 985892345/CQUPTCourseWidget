package com.ndhzs.widget.ui.simple

import kotlinx.serialization.Serializable

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/17 8:36
 */
@Serializable
sealed interface SimpleInfo {
  
  @Serializable
  object Unloaded : SimpleInfo
  
  @Serializable
  data class Available(
    val time: String,
    val title: String,
    val content: String
  ) : SimpleInfo
  
  @Serializable
  data class Unavailable(val message: String) : SimpleInfo
}