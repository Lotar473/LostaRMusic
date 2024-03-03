package com.enteras.projectlostar.lostarmusic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private val context: Context, private val musicList: List<MusicData>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false)
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
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        private val durationTextView: TextView = itemView.findViewById(R.id.durationTextView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(musicData: MusicData) {
            titleTextView.text = musicData.title
            artistTextView.text = musicData.artist
            durationTextView.text = musicData.duration
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val musicData = musicList[position]
                val intent = Intent(context, MusicPlayerActivity::class.java).apply {
                    putExtra("music_data", musicData)
                }
                context.startActivity(intent)
            }
        }
    }
}
