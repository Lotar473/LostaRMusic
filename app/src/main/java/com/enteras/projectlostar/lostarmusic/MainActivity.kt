package com.enteras.projectlostar.lostarmusic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.enteras.projectlostar.lostarmusic.databinding.ItemMusicBinding
import com.enteras.projectlostar.lostarmusic.databinding.MenuBarLayoutBinding

class MainActivity : AppCompatActivity() {

    // 레이아웃에 대한 바인딩 객체
    private lateinit var itemMusicBinding: ItemMusicBinding
    private lateinit var menuBarLayoutBinding: MenuBarLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ItemMusic 레이아웃을 인플레이트하여 바인딩 객체 초기화
        itemMusicBinding = ItemMusicBinding.inflate(LayoutInflater.from(this))
        val itemMusicView = itemMusicBinding.root

        // MenuBarLayout 레이아웃을 인플레이트하여 바인딩 객체 초기화
        menuBarLayoutBinding = MenuBarLayoutBinding.inflate(LayoutInflater.from(this))
        val menuBarLayoutView = menuBarLayoutBinding.root

        // MainActivity의 컨텐츠로 ItemMusic을 설정
        setContentView(itemMusicView)

        // 홈 버튼 클릭 시
        val homeButton = menuBarLayoutBinding.homeButton
        homeButton.setOnClickListener {
            // 현재 액티비티를 다시 시작하여 홈으로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 플레이리스트 버튼 클릭 시
        val playlistButton = menuBarLayoutBinding.playlistButton
        playlistButton.setOnClickListener {
            // PlaylistActivity로 이동
            startActivity(Intent(this, PlaylistActivity::class.java))
        }

        // 설정 버튼 클릭 시
        val settingsButton = menuBarLayoutBinding.settingsButton
        settingsButton.setOnClickListener {
            // SettingsActivity로 이동
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // album_icon_main 클릭 시 MainMusicPlayerActivity로 이동
        val albumIconMain = itemMusicBinding.albumIconMain
        albumIconMain.setOnClickListener {
            startActivity(Intent(this, MainMusicPlayerActivity::class.java))
        }

        val mainAlbumIcon1 = itemMusicBinding.albumIcon1
        mainAlbumIcon1.setOnClickListener {
            startActivity(Intent(this, MainAlbumIcon1MusicPlayerActivity::class.java))
        }

        val mainAlbumIcon2 = itemMusicBinding.albumIcon2
        mainAlbumIcon2.setOnClickListener {
            startActivity(Intent(this, MainAlbumIcon2MusicPlayerActivity::class.java))
        }

        val mainAlbumIcon3 = itemMusicBinding.albumIcon3
        mainAlbumIcon3.setOnClickListener {
            startActivity(Intent(this, MainAlbumIcon3MusicPlayerActivity::class.java))
        }

        val mainAlbumIcon4 = itemMusicBinding.albumIcon4
        mainAlbumIcon4.setOnClickListener {
            startActivity(Intent(this, HinkikArtistPlaylistActivity::class.java))
        }

        val mainAlbumIcon5 = itemMusicBinding.albumIcon5
        mainAlbumIcon5.setOnClickListener {
            startActivity(Intent(this, TheFatRatArtistPlaylistActivity::class.java))
        }

        val mainAlbumIcon6 = itemMusicBinding.albumIcon6
        mainAlbumIcon6.setOnClickListener {
            startActivity(Intent(this, AlanWalkerArtistPlaylistActivity::class.java))
        }

        // MenuBarLayout을 ItemMusic의 자식으로 추가
        itemMusicView.addView(menuBarLayoutView)
    }
}
