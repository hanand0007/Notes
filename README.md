Notes (Room DB Encryption using SQL Cipher)

•	How to Implement:
•	Add the SQLCipher dependency to your build.gradle file:

    dependencies {
      implementation 'net.zetetic:android-database-sqlcipher:4.5.0'
    }

•	Create or migrate your Room database to use SQLCipher:
    
    val passphrase: ByteArray = SQLiteDatabase.getBytes("your_secure_passphrase".toCharArray())
    val factory = SupportFactory(passphrase)
    val db = Room.databaseBuilder(context, YourDatabase::class.java, "encrypted_db_name")
      .openHelperFactory(factory)
      .build()

 •	This approach encrypts the entire database file, including the schema.
 •	Or You can use Keystore Integration:
 
     val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) } 
     val secretKey = (keyStore.getEntry("YourKeyAlias", null) as KeyStore.SecretKeyEntry).secretKey
     val passphrase = secretKey.encoded
     val factory = SupportFactory(passphrase)
     val db = Room.databaseBuilder(context, YourDatabase::class.java, "encrypted_db_name")
        .openHelperFactory(factory)
        .build()
