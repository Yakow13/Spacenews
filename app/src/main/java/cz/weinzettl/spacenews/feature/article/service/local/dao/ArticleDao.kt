package cz.weinzettl.spacenews.feature.article.service.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.weinzettl.spacenews.feature.article.service.local.model.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    // This method returns a PagingSource for Room to integrate with Paging 3
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC") // Order by published date
    fun getPagingSource(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: Int): ArticleEntity?

    @Query("DELETE FROM articles")
    suspend fun clearAll()
}