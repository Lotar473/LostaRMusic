package com.enteras.projectlostar.lostarmusic

import android.os.Parcel
import android.os.Parcelable

data class MusicData(
    val title: String,
    val artist: String,
    val duration: String,
    val albumImageResId: Int,
    val rawResId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(artist)
        parcel.writeString(duration)
        parcel.writeInt(albumImageResId)
        parcel.writeInt(rawResId)
    }

    companion object CREATOR : Parcelable.Creator<MusicData> {
        override fun createFromParcel(parcel: Parcel): MusicData {
            return MusicData(parcel)
        }

        override fun newArray(size: Int): Array<MusicData?> {
            return arrayOfNulls(size)
        }
    }
}
