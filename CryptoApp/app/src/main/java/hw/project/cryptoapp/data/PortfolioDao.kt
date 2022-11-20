package hw.project.cryptoapp.data

import androidx.room.*

@Dao
interface PortfolioDao{
    @Query("SELECT * FROM portfolio")
    fun getAll(): List<Asset>

    @Insert
    fun insert(asset: Asset): Long

    @Query("SELECT amount FROM portfolio WHERE cryptoTag = :cryptoTag")
    fun getAmount(cryptoTag: String): Double

    @Query("UPDATE portfolio SET amount = amount + :plusAmount WHERE cryptoTag = :cryptoTag")
    fun updateAmount(cryptoTag: String, plusAmount: Double)

    @Update
    fun update(asset: Asset)

    @Delete
    fun deleteItem(asset: Asset)
}