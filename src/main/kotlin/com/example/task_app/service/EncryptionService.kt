package com.example.task_app.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Service
class EncryptionService {

    private val algorithm = "AES"

    @Value("\${encryption.key}")
    private lateinit var encryptionKey: String
    private val key: Key
        get() = SecretKeySpec(encryptionKey.toByteArray(), algorithm);

    fun encode (file:ByteArray): ByteArray{

        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(file)
    }

    fun decode(file: ByteArray): ByteArray{
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(file)
    }






}