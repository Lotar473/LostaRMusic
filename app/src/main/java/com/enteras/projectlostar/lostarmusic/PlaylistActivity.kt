package com.enteras.projectlostar.lostarmusic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.enteras.projectlostar.lostarmusic.databinding.ActivityPlaylistBinding
import com.enteras.projectlostar.lostarmusic.databinding.MenuBarLayoutBlackBinding

class PlaylistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var menuBarLayoutBinding: MenuBarLayoutBlackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(LayoutInflater.from(this))
        val bindingView = binding.root

        setContentView(binding.root)

        val albumIcon1 = binding.albumIcon1
        albumIcon1.setOnClickListener {
            startActivity(Intent(this, TheFatRatArtistPlaylistActivity::class.java))
        }

        val albumIcon2 = binding.albumIcon2
        albumIcon2.setOnClickListener {
            startActivity(Intent(this, HinkikArtistPlaylistActivity::class.java))
        }

        val albumIcon3 = binding.albumIcon3
        albumIcon3.setOnClickListener {
            startActivity(Intent(this, MelodysheepArtistPlaylistActivity::class.java))
        }

        val albumIcon4 = binding.albumIcon4
        albumIcon4.setOnClickListener {
            startActivity(Intent(this, HansZimmerArtistPlaylistActivity::class.java))
        }

        val albumIcon5 = binding.albumIcon5
        albumIcon5.setOnClickListener {
            startActivity(Intent(this, NatalieHoltArtistPlaylistActivity::class.java))
        }

        val albumIcon6 = binding.albumIcon6
        albumIcon6.setOnClickListener {
            startActivity(Intent(this, YOASOBIArtistPlaylistActivity::class.java))
        }

        val albumIcon7 = binding.albumIcon7
        albumIcon7.setOnClickListener {
            startActivity(Intent(this, AHisaArtistPlaylistActivity::class.java))
        }

        val albumIcon8 = binding.albumIcon8
        albumIcon8.setOnClickListener {
            startActivity(Intent(this, AlanWalkerArtistPlaylistActivity::class.java))
        }

        val albumIcon9 = binding.albumIcon9
        albumIcon9.setOnClickListener{
            startActivity(Intent(this, GarethCokerArtistPlaylistActivity::class.java))
        }

        val albumIcon10 = binding.albumIcon10
        albumIcon10.setOnClickListener{}

        val albumIcon11 = binding.albumIcon10
        albumIcon11.setOnClickListener{}

        val albumIcon12 = binding.albumIcon10
        albumIcon12.setOnClickListener{}

        menuBarLayoutBinding = MenuBarLayoutBlackBinding.inflate(layoutInflater)
        val menuBarBinding = menuBarLayoutBinding
        val menuBarLayoutView = menuBarLayoutBinding.root

        bindingView.addView(menuBarLayoutView)

        menuBarBinding.homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 플레이리스트 버튼 클릭 시 PlaylistActivity로 이동
        menuBarBinding.playlistButton.setOnClickListener {
            menuBarBinding.playlistButton.setImageResource(R.drawable.ic_playlist_clicked)
        }

        // 설정 버튼 클릭 시 설정 상태를 표시
        menuBarBinding.settingsButton.setOnClickListener {
            // 이미지를 변경하여 선택된 상태를 표시
            menuBarBinding.settingsButton.setImageResource(R.drawable.ic_setting_clicked)
            startActivity(Intent(this, SettingsActivity::class.java))

            // 다른 버튼들의 선택 상태를 초기화
            menuBarBinding.homeButton.setImageResource(R.drawable.ic_home)
            menuBarBinding.playlistButton.setImageResource(R.drawable.ic_playlist)
        }
    }
}
