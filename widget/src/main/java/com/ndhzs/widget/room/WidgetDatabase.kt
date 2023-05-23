package com.ndhzs.widget.room

import android.content.Context
import androidx.room.*
import com.ndhzs.widget.data.IWidgetItem
import com.ndhzs.widget.data.IWidgetRank
import kotlinx.serialization.Serializable

/**
 * .
 *
 * @author 985892345
 * @date 2022/11/16 15:01
 */
@Database(entities = [WidgetItemContentEntity::class, WidgetItemRankEntity::class], version = 1)
internal abstract class WidgetDatabase : RoomDatabase() {
  
  abstract fun getItemDao(): WidgetItemDao
  
  companion object {
    
    @Volatile
    private var INSTANCE: WidgetDatabase? = null
    
    fun get(context: Context): WidgetDatabase {
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

@Serializable
@Entity(
  tableName = "widget_item_content",
  foreignKeys = [
    ForeignKey(
      entity = WidgetItemRankEntity::class,
      parentColumns = ["rank"],
      childColumns = ["rank"],
      onDelete = ForeignKey.CASCADE
    )
  ],
  indices = [Index(value = ["rank"])]
)
internal data class WidgetItemContentEntity(
  val rank: Int,
  override val week: Int,
  override val weekNum: IWidgetItem.WeekNum,
  override val start: IWidgetItem.Start,
  override val period: Int,
  override val title: String,
  override val content: String,
) : IWidgetItem {
  @PrimaryKey(autoGenerate = true)
  var itemId: Long = 0
}

@Serializable
@Entity(tableName = "widget_item_rank")
internal data class WidgetItemRankEntity(
  @PrimaryKey
  override val rank: Int,
  override val bgColor: Int,
  override val tvColor: Int
) : IWidgetRank

@Serializable
internal data class WidgetItemRank(
  @Embedded
  val rank: WidgetItemRankEntity,
  @Relation(
    parentColumn = "rank",
    entityColumn = "rank"
  )
  val items: List<WidgetItemContentEntity>
)

@Serializable
internal data class WidgetItemContent(
  @Relation(
    parentColumn = "rank",
    entityColumn = "rank"
  )
  val rank: WidgetItemRankEntity,
  @Embedded
  val item: WidgetItemContentEntity
)

@Dao
internal abstract class WidgetItemDao {
  
  @Transaction
  open fun replaceAll(map: Map<IWidgetRank, List<IWidgetItem>>) {
    deleteAll()
    insertItems(map)
  }
  
  
  // 增
  @Transaction
  open fun insertItems(map: Map<IWidgetRank, List<IWidgetItem>>) {
    insertRanks(map.keys.map { it.toEntity() })
    val items = map.flatMap { entity ->
      val rank = entity.key.rank
      entity.value.map { it.toEntity(rank) }
    }
    insertItems(items)
  }
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  protected abstract fun insertRanks(list: List<WidgetItemRankEntity>)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  protected abstract fun insertItems(list: List<WidgetItemContentEntity>)
  
  // 删
  @Transaction
  open fun deleteAll() {
    deleteAllRanks()
    deleteAllItems()
  }
  
  @Query("DELETE FROM widget_item_rank")
  protected abstract fun deleteAllRanks()
  
  @Query("DELETE FROM widget_item_content")
  protected abstract fun deleteAllItems()
  
  
  // 查
  @Transaction
  @Query("SELECT * FROM widget_item_rank")
  abstract fun getItemRanks(): List<WidgetItemRank>
  
  @Transaction
  @Query("SELECT * FROM widget_item_content")
  abstract fun getItems(): List<WidgetItemContent>
  
  @Transaction
  @Query("SELECT * FROM WIDGET_ITEM_CONTENT WHERE week = :week")
  abstract fun findItems(week: Int): List<WidgetItemContent>
  
  @Transaction
  @Query("SELECT * FROM WIDGET_ITEM_CONTENT WHERE week = :week AND weekNum = :weekNum")
  abstract fun findItems(week: Int, weekNum: IWidgetItem.WeekNum): List<WidgetItemContent>
  
  @Transaction
  @Query("SELECT * FROM WIDGET_ITEM_CONTENT WHERE week = :week AND weekNum IN (:weekNum)")
  abstract fun findItems(week: Int, weekNum: Array<IWidgetItem.WeekNum>): List<WidgetItemContent>
}

private fun IWidgetItem.toEntity(rank: Int) = WidgetItemContentEntity(
  rank, week, weekNum, start, period, title, content
)

private fun IWidgetRank.toEntity() = WidgetItemRankEntity(rank, bgColor, tvColor)