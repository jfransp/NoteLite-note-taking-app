package com.example.notelite.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "note_data")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val text: String,
    val timeOfCreation: Long = System.currentTimeMillis(),
    val timeOfModification: Long = 0
): Parcelable {
    //Formats the time
    val timeOfCreationFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timeOfCreation)

    //In case a "sort by edit time" function is to be implemented in the future
    val timeOfModificationFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timeOfModification)
}