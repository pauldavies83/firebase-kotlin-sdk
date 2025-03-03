/*
 * Copyright (c) 2020 GitLive Ltd.  Use of this source code is governed by the Apache 2.0 license.
 */

package dev.gitlive.firebase.auth

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual val emulatorHost: String = "localhost"

actual val context: Any = Unit

actual val currentPlatform: Platform = Platform.JS

actual fun runTest(skip: Boolean, test: suspend () -> Unit) = GlobalScope
    .promise {
        if (skip) {
            console.log("Skip the test.")
            return@promise
        }

        try {
            test()
        } catch (e: dynamic) {
            (e as? Throwable)?.log()
            throw e
        }
    }.asDynamic()

internal fun Throwable.log() {
    console.error(this)
    cause?.let {
        console.error("Caused by:")
        it.log()
    }
}
