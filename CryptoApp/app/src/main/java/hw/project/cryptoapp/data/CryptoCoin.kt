package hw.project.cryptoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocoin")
data class CryptoCoin(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "tag") var tag: String,
    @ColumnInfo(name = "apiID") var apiID: Int,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean,
)

