package com.ndhzs.widget.ui.single

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
internal object SingleWidgetInfoStateDefinition : GlanceStateDefinition<SingleWidgetInfo> {
  
  private val DATA_STORE_FILENAME = SingleWidgetInfo::class.java.simpleName
  
  private val Context.datastore by dataStore(DATA_STORE_FILENAME, OneInfoSerializer)
  
  override suspend fun getDataStore(context: Context, fileKey: String): DataStore<SingleWidgetInfo> {
    return context.datastore
  }
  
  override fun getLocation(context: Context, fileKey: String): File {
    return context.dataStoreFile(DATA_STORE_FILENAME)
  }
  
  object OneInfoSerializer : Serializer<SingleWidgetInfo> {
    
    override val defaultValue: SingleWidgetInfo
      get() = SingleWidgetInfo.Unloaded
  
    override suspend fun readFrom(input: InputStream): SingleWidgetInfo {
      return Json.decodeFromString(
        SingleWidgetInfo.serializer(),
        input.readBytes().decodeToString()
      )
    }
  
    override suspend fun writeTo(t: SingleWidgetInfo, output: OutputStream) {
      output.use {
        it.write(Json.encodeToString(SingleWidgetInfo.serializer(), t).encodeToByteArray())
      }
    }
  }
}