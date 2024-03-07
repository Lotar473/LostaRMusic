package com.enteras.projectlostar.lostarmusic

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_bar_layout)

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
            MusicData("PLAY", "Alan Walker, K-391, Tungevaag, Mangoo", "2:49", R.drawable.music_album_icon_5, R.raw.music1),
            MusicData("Sad Sometimes", "Alan Walker, CORSAK & Huang Xiaoyun", "3:19", R.drawable.music_album_icon_6, R.raw.music2),
            MusicData("Alone", "Alan Walker", "2:43", R.drawable.music_album_icon_7, R.raw.music2),
            MusicData("We'll Meet Again", "TheFatRat & Laura Brehm", "3:15", R.drawable.music_album_icon_4, R.raw.music4),
            MusicData("Shiawase (VIP)", "Dion Timmer", "3:02", R.drawable.music_album_icon_3, R.raw.music5),
            MusicData("KOCMOC UNLEASHED", "G2961", "1:29", R.drawable.music_album_icon_8, R.raw.music6),
            MusicData("Lone (Slow Hours Remix)", "What So Not", "4:33", R.drawable.music_album_icon_9, R.raw.music7),
            MusicData("Sun Mother", "Melodysheep", "3:36", R.drawable.music_album_icon_10, R.raw.music8),
            MusicData("i love you jinseo!!!", "so cute", "4:08", R.drawable.music_album_icon_1, R.raw.music4)
            // 다른 음악들 추가
        )

        // RecyclerView 초기화
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingsMusicAdapter(this, musicList)
    }
}
