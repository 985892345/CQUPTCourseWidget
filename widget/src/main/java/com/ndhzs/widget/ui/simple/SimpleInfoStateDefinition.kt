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
object SimpleInfoStateDefinition : GlanceStateDefinition<SimpleInfo> {
  
  private const val DATA_STORE_FILENAME = "oneInfo"
  
  private val Context.datastore by dataStore(DATA_STORE_FILENAME, OneInfoSerializer)
  
  override suspend fun getDataStore(context: Context, fileKey: String): DataStore<SimpleInfo> {
    return context.datastore
  }
  
  override fun getLocation(context: Context, fileKey: String): File {
    return context.dataStoreFile(DATA_STORE_FILENAME)
  }
  
  object OneInfoSerializer : Serializer<SimpleInfo> {
    
    override val defaultValue: SimpleInfo
      get() = SimpleInfo.Unloaded
  
    override suspend fun readFrom(input: InputStream): SimpleInfo {
      return Json.decodeFromString(
        SimpleInfo.serializer(),
        input.readBytes().decodeToString()
      )
    }
  
    override suspend fun writeTo(t: SimpleInfo, output: OutputStream) {
      output.use {
        it.write(
          Json.encodeToString(SimpleInfo.serializer(), t).encodeToByteArray()
        )
      }
    }
  }
}