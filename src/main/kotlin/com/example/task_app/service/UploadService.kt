package com.example.task_app.service

import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
@Service
class UploadService(private val encryptionService: EncryptionService) {

    private val uploadDirectory = "static/tasks"

    fun upload(file: MultipartFile?): FileStatus {
        if(file == null) return FileStatus.Omitted
        if(file.isEmpty) return  FileStatus.Empty

        val directory = Paths.get(uploadDirectory);
        println(!Files.exists(directory))
        if(!Files.exists(directory)) Files.createDirectory(directory)

        val filename = StringUtils.cleanPath(file.originalFilename!!)

        val extension = filename.substringAfterLast(".", "")

        val newFileName = "${java.util.UUID.randomUUID()}.$extension"

        val path = directory.resolve(newFileName)
        val encryptedFile = encryptionService.encode(file.bytes)

        Files.write(path, encryptedFile)


        return FileStatus.Safe(newFileName)




    }


    fun download(filename: String): ByteArray{
        val file = Paths.get(uploadDirectory, filename)
        if(!Files.exists(file)) throw Exception("This file does not exist, $filename")
        val decryptedImage =  encryptionService.decode(Files.readAllBytes(file))
        return decryptedImage;
    }


}