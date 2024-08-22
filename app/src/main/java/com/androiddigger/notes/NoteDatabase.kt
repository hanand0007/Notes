package com.androiddigger.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    //  we can user Keystore Integration
                    //  val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
                    //  val secretKey = (keyStore.getEntry("YourKeyAlias", null) as KeyStore.SecretKeyEntry).secretKey
                    //  val passphrase = secretKey.encoded
                    val passphrase: ByteArray =
                        SQLiteDatabase.getBytes("your_secure_passphrase".toCharArray())
                    val factory = SupportFactory(passphrase)
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, NoteDatabase::class.java,
                        "NotesDB"
                    )
                        .openHelperFactory(factory)
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}