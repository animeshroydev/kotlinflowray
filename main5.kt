
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    fun turnToDarkSide(): Flow<ForceUser> = forceUsers.asFlow()
            .transform { forceUser ->
                if (forceUser is Jedi) {
                    emit(Sith(forceUser.name))
                }
            }

    exampleOf("Imperative completion")

    try {
        turnToDarkSide().collect { sith ->
            log("${sith.name}, your journey to the Dark Side is now complete.")
        }
    } finally {
        log("Everything is proceeding as I have foreseen.")
    }

    exampleOf("Declarative completion")

    turnToDarkSide()
            .onCompletion { log("Everything is proceeding as I have foreseen.") }
            .collect { sith ->
                log("${sith.name}, your journey to the Dark Side is now complete.")
            }
}