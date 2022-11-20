package hw.project.cryptoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asset::class], version = 1)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao

    companion object {
        fun getDatabase(applicationContext: Context): PortfolioDatabase {
            return Room.databaseBuilder(
                applicationContext,
                PortfolioDatabase::class.java,
                "portfolio"
            )
                .fallbackToDestructiveMigration()
                .build();
        }
    }
}