package com.example.task_app.service

sealed class FileStatus {

    data class Safe(val file: String) : FileStatus()
//    data object UnSafe : FileStatus()
    data object Empty : FileStatus()
    data object Omitted : FileStatus()
    data object Existing : FileStatus()
}