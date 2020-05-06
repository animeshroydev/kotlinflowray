import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
/*
Changing the flow context
 */
fun main() = runBlocking {

    fun duelOfTheFates(): kotlinx.coroutines.flow.Flow<ForceUser> = flow {
        for (forceUser in forceUsers) {
            delay(DELAY)
            log("Battling ${forceUser.name}")
            emit(forceUser)
        }
    }.transform { forceUser ->
        if (forceUser is Sith) {
            forceUser.name = "Darth ${forceUser.name}"
        }
        emit(forceUser)
    }.flowOn(Dispatchers.Default)

    duelOfTheFates().collect {
        log("Battled ${it.name}")
    }
}
