package hw.project.cryptoapp.data

import androidx.room.*

@Dao
interface CryptoCoinDao{
    @Query("SELECT * FROM CryptoCoin")
    fun getAll(): List<CryptoCoin>

    @Insert
    fun insert(cryptoCoin: CryptoCoin): Long

    @Update
    fun update(cryptoCoin: CryptoCoin)

    @Delete
    fun deleteItem(cryptoCoin: CryptoCoin)
}
