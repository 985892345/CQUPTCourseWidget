package com.ndhzs.widget.ui.week

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/17 8:43
 */
internal object WeekWidgetInfoStateDefinition : GlanceStateDefinition<WeekWidgetInfo> {
  
  private val DATA_STORE_FILENAME = WeekWidgetInfo::class.java.simpleName
  
  private val Context.datastore by dataStore(DATA_STORE_FILENAME, OneInfoSerializer)
  
  override suspend fun getDataStore(context: Context, fileKey: String): DataStore<WeekWidgetInfo> {
    return context.datastore
  }
  
  override fun getLocation(context: Context, fileKey: String): File {
    return context.dataStoreFile(DATA_STORE_FILENAME)
  }
  
  object OneInfoSerializer : Serializer<WeekWidgetInfo> {
    
    override val defaultValue: WeekWidgetInfo
      get() = WeekWidgetInfo.Unloaded
  
    override suspend fun readFrom(input: InputStream): WeekWidgetInfo {
      return Json.decodeFromString(
        WeekWidgetInfo.serializer(),
        input.readBytes().decodeToString()
      )
    }
  
    override suspend fun writeTo(t: WeekWidgetInfo, output: OutputStream) {
      output.use {
        it.write(
          Json.encodeToString(WeekWidgetInfo.serializer(), t).encodeToByteArray()
        )
      }
    }
  }
}