package com.dmitryvim.accounter.common

import io.javalin.http.Context

open class Route(var path: String,
                 var lambda: (Context) -> Unit)