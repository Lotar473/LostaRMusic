package com.enteras.projectlostar.lostarmusic

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NatalieHoltArtistPlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_bar_layout_natalieholt_background)

        // 메뉴 바 추가
        val menuBarLayout = findViewById<LinearLayout>(R.id.menu_bar_layout)

        // 홈 버튼 클릭 시 MainActivity로 이동
        val homeButton = menuBarLayout.findViewById<ImageView>(R.id.homeButton)
        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 플레이리스트 버튼 클릭 시 PlaylistActivity로 이동
        val playlistButton = menuBarLayout.findViewById<ImageView>(R.id.playlistButton)
        playlistButton.setOnClickListener {
            startActivity(Intent(this, PlaylistActivity::class.java))
        }

        // 설정 버튼 클릭 시 설정 상태를 표시
        val settingsButton = menuBarLayout.findViewById<ImageView>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            // 이미지를 변경하여 선택된 상태를 표시
            settingsButton.setImageResource(R.drawable.ic_setting_clicked)

            // 다른 버튼들의 선택 상태를 초기화
            homeButton.setImageResource(R.drawable.ic_home)
            playlistButton.setImageResource(R.drawable.ic_playlist)
        }

        // 음악 리스트 표시
        displayMusicList()
    }

    private fun displayMusicList() {
        // 음악 데이터 리스트 생성
        val musicList = listOf(
            MusicData("Loki Green Theme", "Natalie Holt", "2:24", R.drawable.music_album_icon_29, R.raw.music34),
            MusicData("TVA", "Natalie Holt", "2:28", R.drawable.music_album_icon_29, R.raw.music35),
            MusicData("Purpose Is Glorious", "Natalie Holt", "3:07", R.drawable.music_album_icon_30, R.raw.music36),
            MusicData("History Is Now", "Natalie Holt", "2:32", R.drawable.music_album_icon_30, R.raw.music37)
        )

        // RecyclerView 초기화
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =NatalieHoltArtistPlaylistAdapter(this, musicList)
    }
}
