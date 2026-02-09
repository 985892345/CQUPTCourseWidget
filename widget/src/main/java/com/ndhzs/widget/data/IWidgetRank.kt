package com.ndhzs.widget.data

/**
 * .
 *
 * @author 985892345
 * 2023/5/23 12:30
 */
interface IWidgetRank {
  val rank: Int // 优先级，越小越显示在上面
  val bgColor: Int // 背景颜色
  val tvColor: Int // 文本颜色
}