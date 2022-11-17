package hw.project.cryptoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio")
data class Portfolio(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "cryptoID") var cryptoID: Int,
    @ColumnInfo(name = "amount") var amount: Double
)