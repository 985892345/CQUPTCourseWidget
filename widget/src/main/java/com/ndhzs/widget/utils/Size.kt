package com.ndhzs.widget.utils

import androidx.core.util.SizeFCompat

/**
 * .
 *
 * @author 985892345
 * 2022/11/18 21:39
 */
interface Size {
  
  /**
   * ```
   * 小米12x的手机 (1080 x 2400，6.28寸，419ppi)
   * 4x6:
   *      Width     Height
   * 1     64         94
   * 2     157        209
   * 3     249        324
   * 4     341        440
   * 5                555
   * 6                670
   *
   * 5x6:
   * 1     54         94
   * 2     128        210
   * 3     202        325
   * 4     276        441
   * 5     350        557
   * 6                672
   *
   * 谷歌 Pixel 4 (1080 x 2220，5.7寸，444ppi)
   * 1     57         102
   * 2     130        220
   * 3     203        337
   * 4     276        455
   * 5     349
   * n     (73n-16)   (118n-16)
   *
   * 建议尺寸
   * 1     60         90
   * 2     150        200
   * 3     240        320
   * 4     340        440
   * 5     430        550
   * 6     520        670
   * ```
   */
  val size: SizeFCompat
}