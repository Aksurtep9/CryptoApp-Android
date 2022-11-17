package hw.project.cryptoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CryptoCoin::class], version = 1)
abstract class CryptoCoinDatabase : RoomDatabase() {
    abstract fun cryptoCoinDao(): CryptoCoinDao

    companion object {
        fun getDatabase(applicationContext: Context): CryptoCoinDatabase {
            return Room.databaseBuilder(
                applicationContext,
                CryptoCoinDatabase::class.java,
                "cryptocoin"
            ).build();
        }
    }
}