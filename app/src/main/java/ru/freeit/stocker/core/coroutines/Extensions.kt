package ru.freeit.stocker.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> default(noinline block: suspend CoroutineScope.() -> T) : T {
    return withContext(Dispatchers.Default, block)
}