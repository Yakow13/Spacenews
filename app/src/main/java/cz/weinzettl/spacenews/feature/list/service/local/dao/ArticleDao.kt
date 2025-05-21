package cz.weinzettl.spacenews.feature.list.service.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.weinzettl.spacenews.feature.list.service.local.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    // Example: Order by publishedAt descending, then by ID
    // Adjust the ordering to your needs (e.g., by ID if publishedAt is not always reliable or for stable append)
    @Query("SELECT * FROM articles ORDER BY id DESC") // Or publishedAt DESC
    fun getAllArticlesSorted(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM articles")
    suspend fun clearAll()
}