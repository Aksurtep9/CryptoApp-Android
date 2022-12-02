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

    @Query("SELECT (SELECT COUNT(*) FROM portfolio WHERE cryptoTag =:tag) == 0")
    fun isNotInPortfolio(tag: String): Boolean

    @Query("UPDATE portfolio SET amount = 0 WHERE cryptoTag = :tag ")
    fun setAmountToZero(tag: String)

    @Query("UPDATE portfolio SET amount = 0")
    fun setAmountsToZero()

    @Update
    fun update(asset: Asset)

    @Delete
    fun deleteItem(asset: Asset)
}