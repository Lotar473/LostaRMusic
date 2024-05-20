package com.enteras.projectlostar.lostarmusic

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GarethCokerArtistPlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_bar_layout_garethcoker_background)

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
            MusicData("Toys on a Tear (Shrunk)", "Gareth Coker", "2:30", R.drawable.music_album_icon_33, R.raw.music40),
            MusicData("Dance of the Blocks (Shrunk)", "Gareth Coker", "2:15", R.drawable.music_album_icon_33, R.raw.music41),
            MusicData("Master Builder (Shrunk)", "Gareth Coker", "2:35", R.drawable.music_album_icon_33, R.raw.music42),
            MusicData("Double Time (Tumble)", "Gareth Coker", "1:14", R.drawable.music_album_icon_33, R.raw.music43),
            MusicData("Nimbly Does It (Tumble)", "Gareth Coker", "1:10", R.drawable.music_album_icon_33, R.raw.music44),
            MusicData("Chop Chop (Tumble)", "Gareth Coker", "1:06", R.drawable.music_album_icon_33, R.raw.music45),
            MusicData("Agile Accelerando (Tumble)", "Gareth Coker", "1:11", R.drawable.music_album_icon_33, R.raw.music46),
            MusicData("Lickety Split (Tumble)", "Gareth Coker", "1:12", R.drawable.music_album_icon_33, R.raw.music47),
            MusicData("Time is of the Essence (Tumble)", "Gareth Coker", "1:04", R.drawable.music_album_icon_33, R.raw.music48)
        )

        // RecyclerView 초기화
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =GarethCokerArtistPlaylistAdapter(this, musicList)
    }
}
