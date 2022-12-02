package hw.project.cryptoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio")
data class Asset(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "cryptoTag") var cryptoTag: String,
    @ColumnInfo(name = "amount") var amount: Double
)