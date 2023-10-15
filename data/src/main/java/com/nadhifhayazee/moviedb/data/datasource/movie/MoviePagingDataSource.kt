package com.nadhifhayazee.moviedb.data.datasource.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nadhifhayazee.moviedb.commons.exception.NoDataException
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.data.retrofit.model.mapToUIMovie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingDataSource @Inject constructor(
    private val remoteMovieDataSource: RemoteMovieDataSource
) : PagingSource<Int, UIMovie>() {
    override fun getRefreshKey(state: PagingState<Int, UIMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UIMovie> {
        return try {
            val currentPage = params.key ?: 1
            val response = remoteMovieDataSource.getMovies(currentPage)
            val movies = response?.results?.mapToUIMovie()

            if (movies?.isNotEmpty() == true) {
                LoadResult.Page(
                    data = movies,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if ((response.total_pages
                            ?: 1) > currentPage
                    ) currentPage + 1 else null
                )
            } else {
                LoadResult.Error(NoDataException())
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}