package com.ndhzs.widget.ui.simple

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
internal object SimpleWidgetInfoStateDefinition : GlanceStateDefinition<SimpleWidgetInfo> {
  
  private val DATA_STORE_FILENAME = SimpleWidgetInfo::class.java.simpleName
  
  private val Context.datastore by dataStore(DATA_STORE_FILENAME, OneInfoSerializer)
  
  override suspend fun getDataStore(context: Context, fileKey: String): DataStore<SimpleWidgetInfo> {
    return context.datastore
  }
  
  override fun getLocation(context: Context, fileKey: String): File {
    return context.dataStoreFile(DATA_STORE_FILENAME)
  }
  
  object OneInfoSerializer : Serializer<SimpleWidgetInfo> {
    
    override val defaultValue: SimpleWidgetInfo
      get() = SimpleWidgetInfo.Unloaded
  
    override suspend fun readFrom(input: InputStream): SimpleWidgetInfo {
      return Json.decodeFromString(
        SimpleWidgetInfo.serializer(),
        input.readBytes().decodeToString()
      )
    }
  
    override suspend fun writeTo(t: SimpleWidgetInfo, output: OutputStream) {
      output.use {
        it.write(Json.encodeToString(SimpleWidgetInfo.serializer(), t).encodeToByteArray())
      }
    }
  }
}