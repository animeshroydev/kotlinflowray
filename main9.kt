
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/*
Handling flow exceptions
 */

fun midichlorianTest(): Flow<Int> = flow {
    for (key in midichlorianCounts.keys) {
        log("Testing $key")
        delay(DELAY)
        emit(midichlorianCounts[key] ?: 0)
    }
}

fun midichlorianTestString(): Flow<String> =
    flow<Int> {
        for (key in midichlorianCounts.keys) {
            log("Testing $key")
            delay(DELAY)
            emit(midichlorianCounts[key] ?: 0)
        }
    }.map { testResult ->
        check(testResult <= CHOSEN_ONE_THRESHOLD) { "Test Result: $testResult" }
        "$testResult"
    }

fun main() = runBlocking {

    exampleOf("Catching exceptional condition")

    try {
        midichlorianTest().collect { testResult ->
            log("$testResult")
            check(testResult <= CHOSEN_ONE_THRESHOLD) { "Test Result: $testResult" }
        }
    } catch (e: Throwable) {
        log("Could be the chosen one! ::: $e")
    }

    exampleOf("Catching from intermediate operator")

    try {
        midichlorianTestString().collect { testResult -> log("$testResult") }
    } catch (e: Throwable) {
        log("Could be the chosen one! ::: $e")
    }

//  exampleOf("Exception transparency")
//
//  midichlorianTest()
//    .catch { e -> log("Exception caught: $e") }
//    .collect { testResult ->
//      check(testResult <= CHOSEN_ONE_THRESHOLD) { "Test Result: $testResult" }
//      log("$testResult")
//    }

    exampleOf("Catching declaratively")

    midichlorianTest()
        .onEach { testResult ->
            check(testResult <= CHOSEN_ONE_THRESHOLD) { "Test Result: $testResult" }
            log("$testResult")
        }
        .catch { e -> log("Exception caught: $e") }
        .collect()
}
