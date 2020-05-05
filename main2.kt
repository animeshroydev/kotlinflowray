import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
/*
Use Flow Builders Video
 */
fun main() = runBlocking {

    exampleOf("flowOf")

    val favorites = flowOf(episodeV, episodeIX, episodeIV)

    favorites.collect { movie -> log(movie) }

    exampleOf("asFlow")

    val topAdjustedGrosses = listOf(episodeIV, episodeVII, episodeV).asFlow()

    topAdjustedGrosses.collect { movie -> log(movie) }
}