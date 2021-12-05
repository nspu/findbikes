package com.ns.pu.findbikenantes.ui

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()

    //Use our own error system instead of Exception would be a better solution
    class Error(val exception: Exception) : Result<Nothing>()
}