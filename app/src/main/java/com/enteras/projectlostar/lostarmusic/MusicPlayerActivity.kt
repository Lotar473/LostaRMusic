package com.enteras.projectlostar.lostarmusic

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.widget.ListView

class MusicPlayerActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var playPauseButton: ImageView
    private lateinit var previousButton: ImageView
    private lateinit var nextButton: ImageView
    private lateinit var lyricsButton: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var musicTitleTextView: TextView
    private lateinit var artistTextView: TextView
    private lateinit var albumImageView: ImageView
    private lateinit var musicListView: ListView // 음악 목록을 나타내는 ListView 추가
    private var musicList: MutableList<MusicData> = mutableListOf()
    private var currentMusicIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        // 음악 데이터 초기화
        initMusicList()

        // 미디어 플레이어 초기화
        mediaPlayer = MediaPlayer()
        handler = Handler()

        // UI 요소 초기화
        playPauseButton = findViewById(R.id.playPauseButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        lyricsButton = findViewById(R.id.lyricsButton)
        seekBar = findViewById(R.id.seekBar)
        musicTitleTextView = findViewById(R.id.titleTextView)
        artistTextView = findViewById(R.id.artistTextView)
        albumImageView = findViewById(R.id.albumImageView)

        // 초기 노래 설정
        setMusic(currentMusicIndex)

        // 플레이 / 일시 정지 버튼 클릭 리스너
        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                pauseMusic()
            } else {
                playMusic()
            }
        }

        // 이전 버튼 클릭 리스너
        previousButton.setOnClickListener {
            if (currentMusicIndex > 0) {
                currentMusicIndex--
                setMusic(currentMusicIndex)
            }
        }

        // 다음 버튼 클릭 리스너
        nextButton.setOnClickListener {
            if (currentMusicIndex < musicList.size - 1) {
                currentMusicIndex++
                setMusic(currentMusicIndex)
            }
        }

        // 가사 버튼 클릭 리스너
        lyricsButton.setOnClickListener {
            // 가사 표시 기능 구현
        }

        // SeekBar 변경 리스너 설정
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // ListView 항목 클릭 리스너 설정
        musicListView.setOnItemClickListener { parent, view, position, id ->
            currentMusicIndex = position
            setMusic(currentMusicIndex)
        }

        // MediaPlayer 상태 변경 감지
        mediaPlayer.setOnCompletionListener {
            stopMusic()
        }
    }

    private fun initMusicList() {
        // 음악 데이터를 초기화합니다.
        // 예시:
        musicList.add(MusicData("PLAY", "Alan Walker, K-391, Tungevaag, Mangoo", "3:24", R.drawable.music_album_icon_1, R.raw.music1))
        musicList.add(MusicData("Sad Sometimes", "Alan Walker, CORSAK & Huang Xiaoyun", "3:19", R.drawable.music_album_icon_1, R.raw.music2))
        musicList.add(MusicData("Alone", "Alan Walker", "2:43", R.drawable.music_album_icon_1, R.raw.music3))
        musicList.add(MusicData("White Ferrari", "Frank Ocean", "4:08", R.drawable.music_album_icon_2, R.raw.music4))
        musicList.add(MusicData("Shiawase (VIP)", "Dion Timmer", "4:08", R.drawable.music_album_icon_3, R.raw.music5))
        // 추가 음악 데이터를 여기에 추가할 수 있습니다.
    }

    private fun setMusic(index: Int) {
        val musicData = musicList[index]
        val uri = Uri.parse("android.resource://${packageName}/${musicData.rawResId}")
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this, uri)
        mediaPlayer.prepare()
        musicTitleTextView.text = musicData.title
        artistTextView.text = musicData.artist
        seekBar.max = mediaPlayer.duration
        albumImageView.setImageResource(musicData.albumImageResId)
        playMusic() // 음악을 설정할 때 바로 재생하도록 호출
    }

    private fun playMusic() {
        mediaPlayer.start()
        playPauseButton.setImageResource(R.drawable.ic_pause)
        updateSeekBar()
    }

    private fun pauseMusic() {
        mediaPlayer.pause()
        playPauseButton.setImageResource(R.drawable.ic_play)
    }

    private fun stopMusic() {
        mediaPlayer.stop()
        mediaPlayer.prepare()
        playPauseButton.setImageResource(R.drawable.ic_play)
    }

    private fun updateSeekBar() {
        val delayMillis: Long = 1000

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (mediaPlayer.isPlaying) {
                    val currentPosition = mediaPlayer.currentPosition
                    seekBar.progress = currentPosition
                    handler.postDelayed(this, delayMillis)
                }
            }
        }, delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}
