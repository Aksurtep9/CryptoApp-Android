package hw.project.cryptoapp.data

import androidx.room.*

@Dao
interface CryptoCoinDao{
    @Query("SELECT * FROM CryptoCoin")
    fun getAll(): List<CryptoCoin>

    @Insert
    fun insert(cryptoCoin: CryptoCoin): Long

    @Query("UPDATE cryptocoin SET price=:price WHERE tag = :tag AND apiID = :apiID")
    fun updatePrice_With_tag_ApiID(price: Double, tag: String, apiID:Int);

    @Query("SELECT price FROM cryptocoin WHERE apiID = :apiID")
    fun getPrice(apiID: Int): Double

    @Query("SELECT * FROM cryptocoin WHERE tag = :tag")
    fun getCryptoCoin(tag: String): CryptoCoin

    @Update
    fun update(cryptoCoin: CryptoCoin)

    @Delete
    fun deleteItem(cryptoCoin: CryptoCoin)
}
