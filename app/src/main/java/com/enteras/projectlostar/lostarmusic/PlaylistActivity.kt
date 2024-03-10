package com.enteras.projectlostar.lostarmusic

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.enteras.projectlostar.lostarmusic.databinding.ActivityPlaylistBinding
import com.enteras.projectlostar.lostarmusic.databinding.MenuBarLayoutBinding

class PlaylistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var menuBarBinding: MenuBarLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 메뉴 바 추가
        menuBarBinding = MenuBarLayoutBinding.inflate(layoutInflater)
        binding.root.addView(menuBarBinding.root)

        // 홈 버튼 클릭 시 MainActivity로 이동
        menuBarBinding.homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 플레이리스트 버튼 클릭 시 PlaylistActivity로 이동
        menuBarBinding.playlistButton.setOnClickListener {
            // 이미 PlaylistActivity를 표시하고 있으므로 아무런 동작을 하지 않거나 다시 자신을 표시할 수 있습니다.
        }

        // 설정 버튼 클릭 시 설정 상태를 표시
        menuBarBinding.settingsButton.setOnClickListener {
            // 이미지를 변경하여 선택된 상태를 표시
            menuBarBinding.settingsButton.setImageResource(R.drawable.ic_setting_clicked)

            // 다른 버튼들의 선택 상태를 초기화
            menuBarBinding.homeButton.setImageResource(R.drawable.ic_home)
            menuBarBinding.playlistButton.setImageResource(R.drawable.ic_playlist)
        }
    }
}
