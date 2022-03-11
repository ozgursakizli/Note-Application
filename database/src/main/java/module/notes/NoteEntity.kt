package module.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import module.DatabaseConstants

@Entity(tableName = DatabaseConstants.NOTES_TABLE)
data class NoteEntity(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "imageUrl") var imageUrl: String?,
    @ColumnInfo(name = "createdDate") val createdDate: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "id: $id, title: $title, description: $description, imageUrl: $imageUrl, createdDate: $createdDate"
    }
}
