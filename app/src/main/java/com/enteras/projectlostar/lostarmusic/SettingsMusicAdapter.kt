package com.enteras.projectlostar.lostarmusic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SettingsMusicAdapter(private val context: Context, private val musicList: List<MusicData>) :
    RecyclerView.Adapter<SettingsMusicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_music_data_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val musicData = musicList[position]
        holder.bind(musicData)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.songTitleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        private val durationTextView: TextView = itemView.findViewById(R.id.durationTextView)
        private val albumImageView: ImageView = itemView.findViewById(R.id.albumImageView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(musicData: MusicData) {
            titleTextView.text = musicData.title
            // 아티스트 이름이 25자를 넘으면 "..."으로 표시하고, 그렇지 않으면 원래 이름을 표시
            val artistName = if (musicData.artist.length > 25) {
                "${musicData.artist.substring(0, 25)}..."
            } else {
                musicData.artist
            }
            artistTextView.text = artistName
            durationTextView.text = musicData.duration
            albumImageView.setImageResource(musicData.albumImageResId)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val musicData = musicList[position]
                val intent = Intent(context, SettingsMusicPlayerActivity::class.java).apply {
                    putExtra("music_data", musicData)
                }
                context.startActivity(intent)
            }
        }
    }
}
