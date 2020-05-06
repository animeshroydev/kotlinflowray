import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/*
Combine multiple flows
 */

fun main() = runBlocking {

    exampleOf("zip")

    var characters = characterNames.asFlow()
    var weapons = weaponNames.asFlow()

    characters.zip(weapons) { character, weapon -> "$character: $weapon" }
        .collect { log(it) }

    exampleOf("onEach and zip with delays")

    characters = characterNames.asFlow().onEach { delay(DELAY / 2) }
    weapons = weaponNames.asFlow().onEach { delay(DELAY) }
    var start = System.currentTimeMillis()
    characters.zip(weapons) { character, weapon -> "$character: $weapon" }
        .collect { characterToWeapon ->
            log("$characterToWeapon at ${System.currentTimeMillis() - start} ms")
        }

    exampleOf("combine")

    characters = characterNames.asFlow().onEach { delay(DELAY / 2) }
    weapons = weaponNames.asFlow().onEach { delay(DELAY) }
    start = System.currentTimeMillis()
    characters.combine(weapons) { character, weapon -> "$character: $weapon" }
        .collect { characterToWeapon ->
            log("$characterToWeapon at ${System.currentTimeMillis() - start} ms")
        }

    exampleOf("flatMapConcat")

    fun suitUp(string: String): Flow<String> = flow {
        emit("$string gets dressed for battle")
        delay(DELAY)
        emit("$string dons their helmet")
    }

    characterNames.asFlow().map { suitUp(it) }
        .collect { println(it) }

    start = System.currentTimeMillis()

    characterNames.asFlow().onEach { delay(DELAY / 2) }
        .flatMapConcat { suitUp(it) }
        .collect { value ->
            log("$value at ${System.currentTimeMillis() - start} ms")
        }

    exampleOf("flatMapMerge")

    characterNames.asFlow().onEach { delay(DELAY / 2) }
        .flatMapMerge { suitUp(it) }
        .collect { value ->
            log("$value at ${System.currentTimeMillis() - start} ms")
        }

    exampleOf("flatMapLatest")

    characterNames.asFlow().onEach { delay(DELAY / 2) }
        .flatMapLatest { suitUp(it) }
        .collect { value ->
            log("$value at ${System.currentTimeMillis() - start} ms")
        }
}
