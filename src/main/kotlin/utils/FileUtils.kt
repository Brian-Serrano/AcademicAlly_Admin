package utils

import api.User
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object FileUtils {

    fun read(fileName: String): User {
        val file = File(fileName)
        if (file.exists()) {
            return readData(fileName)
        }
        else {
            file.createNewFile()
            return readData(fileName)
        }
    }

    private fun readData(fileName: String): User {
        return FileInputStream(fileName).bufferedReader().use {
            try {
                val line = it.readLine().split("|")
                User(line[0], line[1].toInt(), line[2], line[3], line[4])
            } catch (e: Exception) {
                User()
            }
        }
    }

    fun write(fileName: String, pref: User) {
        FileOutputStream(fileName).bufferedWriter().use {
            it.write("${pref.token}|${pref.id}|${pref.name}|${pref.email}|${pref.password}")
        }
    }

    fun clearData(fileName: String) {
        FileOutputStream(fileName).bufferedWriter().use { it.write("") }
    }
}