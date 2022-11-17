package com.ndhzs.widget.room

import android.content.Context
import androidx.room.*
import com.ndhzs.widget.data.IWidgetItem

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:01
 */
@Database(entities = [WidgetItem::class], version = 1)
internal abstract class WidgetDatabase : RoomDatabase() {
  
  abstract fun getItemDao(): WidgetItemDao
  
  companion object {
    
    @Volatile
    private var INSTANCE: WidgetDatabase? = null
    
    fun getInstance(context: Context): WidgetDatabase {
      if (INSTANCE != null) return INSTANCE!!
      synchronized(this) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(
            context,
            WidgetDatabase::class.java,
            "widget_db"
          ).fallbackToDestructiveMigration().build()
        }
      }
      return INSTANCE!!
    }
  }
}

@Entity(tableName = "widget_item", indices = [Index("key")])
internal data class WidgetItem(
  val key: String,
  override val rank: Int,
  override val week: Int,
  override val weekNum: Int,
  override val beginLesson: Int,
  override val period: Int,
  override val title: String,
  override val content: String,
): IWidgetItem {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0
}

@Dao
internal abstract class WidgetItemDao {
  
  @Query("SELECT * FROM widget_item")
  abstract suspend fun query(): List<WidgetItem>
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun insert(itemList: List<WidgetItem>)
  
  @Query("DELETE FROM widget_item")
  abstract suspend fun deleteAll()
  
  @Transaction
  open suspend fun replace(newItemList: List<WidgetItem>) {
    deleteAll()
    insert(newItemList)
  }
}