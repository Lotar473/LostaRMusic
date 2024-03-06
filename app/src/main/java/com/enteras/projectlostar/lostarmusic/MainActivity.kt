package com.enteras.projectlostar.lostarmusic

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_main)

        val loadingImageView = findViewById<ImageView>(R.id.loadingImageView)

        // 3초 후에 로딩 화면을 변경합니다.
        handler.postDelayed({
            // 로딩 이미지뷰를 페이드 아웃합니다.
            fadeOutView(loadingImageView)

            // 배경 이미지를 교차 디졸브 효과로 변경합니다.
            crossfadeBackground(findViewById<RelativeLayout>(R.id.rootLayout))

            // 음악 리스트를 표시합니다.
            displayMusicList()

        }, 3000)
    }

    private fun fadeOutView(view: View) {
        val animation = AlphaAnimation(1.0f, 0.0f)
        animation.duration = 1000
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                // 애니메이션이 완료되면 이미지뷰를 숨깁니다.
                view.visibility = View.GONE
            }
        })
        view.startAnimation(animation)
    }

    private fun crossfadeBackground(view: RelativeLayout) {
        val crossfadeAnimation = TransitionDrawable(arrayOf(
            requireNotNull(getDrawable(R.drawable.background_main_1)),
            requireNotNull(getDrawable(R.drawable.background_main_1_blur))
        ))
        view.background = crossfadeAnimation
        crossfadeAnimation.startTransition(1000)
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
            MusicData("i love you jxnso", "so cute", "4:08", R.drawable.music_album_icon_1, R.raw.music4)
            // 다른 음악들 추가
        )

        // RecyclerView 초기화
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MusicAdapter(this, musicList)

        // 버튼들에 클릭 리스너 설정
        findViewById<ImageView>(R.id.homeButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.playlistButton).setOnClickListener {
            startActivity(Intent(this, PlaylistActivity::class.java))
        }

        findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
