package com.example.task_app.controller

import com.example.task_app.service.UploadService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/file")

class FileController(private val fileService: UploadService) {
    @GetMapping("/{fileName}")
    fun getFileByName(@PathVariable(name = "fileName") fileName: String): ResponseEntity<ByteArray>{
        val fileBytes = fileService.download(fileName)
        val headers = HttpHeaders().apply{
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = $fileName")
            add(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
        }
        return ResponseEntity.ok()
            .headers(headers)
            .body(fileBytes)
    }
}