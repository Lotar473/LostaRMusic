package com.enteras.projectlostar.lostarmusic

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Looper
import android.widget.Toast

class HansZimmerArtistPlaylistMusicPlayerActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var playPauseButton: ImageView
    private lateinit var previousButton: ImageView
    private lateinit var nextButton: ImageView
    private lateinit var lyricsButton: ImageView
    private lateinit var repeatButton: ImageView
    private lateinit var randomPlayButton: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var musicTitleTextView: TextView
    private lateinit var artistTextView: TextView
    private lateinit var albumImageView: ImageView
    private lateinit var timeRemainingTextView: TextView
    private var musicList: MutableList<MusicData> = mutableListOf()
    private var currentMusicIndex: Int = 0
    private var isRepeatEnabled: Boolean = false
    private var isRandomPlayEnabled: Boolean = false
    private var playedIndexes: MutableList<Int> = mutableListOf()
    private lateinit var airpodsIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player_3)

        initMusicList()

        mediaPlayer = MediaPlayer()
        handler = Handler(Looper.getMainLooper())

        playPauseButton = findViewById(R.id.playPauseButton)
        seekBar = findViewById(R.id.seekBar)
        timeRemainingTextView = findViewById(R.id.timeRemainingTextView)
        repeatButton = findViewById(R.id.repeatButton)
        randomPlayButton = findViewById(R.id.randomPlayButton)
        musicTitleTextView = findViewById(R.id.titleTextView)
        artistTextView = findViewById(R.id.artistTextView)
        albumImageView = findViewById(R.id.albumImageView)
        timeRemainingTextView = findViewById(R.id.timeRemainingTextView)
        airpodsIcon = findViewById(R.id.airpods3Icon)

        setMusic(currentMusicIndex)

        airpodsIcon.setOnClickListener{
            Toast.makeText(this, "AirPods(3세대) 연결됨", Toast.LENGTH_SHORT).show()
        }

        seekBar.progressDrawable.setColorFilter(
            resources.getColor(R.color.white),
            PorterDuff.Mode.SRC_IN
        )

        seekBar.thumb.setColorFilter(
            resources.getColor(R.color.white),
            PorterDuff.Mode.SRC_IN
        )

        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                pauseMusic()
            } else {
                playMusic()
            }
        }

        previousButton = findViewById(R.id.previousButton)
        previousButton.setOnClickListener {
            if (isRandomPlayEnabled) {
                playPreviousRandomMusic()
            } else {
                playPreviousMusic()
            }
        }

        nextButton = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            if (currentMusicIndex < musicList.size - 1) {
                currentMusicIndex++
                setMusic(currentMusicIndex)
            } else {
                playNextMusic()
            }
        }

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, HansZimmerArtistPlaylistActivity::class.java)
            startActivity(intent)
            finish()
        }

        mediaPlayer.setOnCompletionListener {
            stopMusic()
            if (isRepeatEnabled) {
                setMusic(currentMusicIndex)
            } else if (isRandomPlayEnabled) {
                playNextRandomMusic()
            } else {
                playNextMusic()
            }
        }

        repeatButton.setOnClickListener {
            isRepeatEnabled = !isRepeatEnabled
            if (isRepeatEnabled) {
                repeatButton.setImageResource(R.drawable.ic_repeat_enabled)
            } else {
                repeatButton.setImageResource(R.drawable.ic_repeat)
            }
        }

        randomPlayButton.setOnClickListener {
            isRandomPlayEnabled = !isRandomPlayEnabled
            if (isRandomPlayEnabled) {
                randomPlayButton.setImageResource(R.drawable.ic_random_enabled)
            } else {
                randomPlayButton.setImageResource(R.drawable.ic_random)
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initMusicList() {
        musicList.add(MusicData("First Step", "Hans Zimmer", "1:47", R.drawable.music_album_icon_21, R.raw.music21))
        musicList.add(MusicData("Mountains", "Hans Zimmer", "3:39", R.drawable.music_album_icon_21, R.raw.music22))
        musicList.add(MusicData("No Time for Caution", "Hans Zimmer", "4:06", R.drawable.music_album_icon_21, R.raw.music23))
        musicList.add(MusicData("Detach", "Hans Zimmer", "6:42", R.drawable.music_album_icon_21, R.raw.music24))
        musicList.add(MusicData("S.T.A.Y.", "Hans Zimmer", "6:23", R.drawable.music_album_icon_21, R.raw.music25))
        musicList.add(MusicData("Time", "Hans Zimmer", "4:35", R.drawable.music_album_icon_23, R.raw.music28))
    }

    private fun setMusic(index: Int) {
        val newIndex = if (isRandomPlayEnabled) getRandomIndex() else index
        val musicData = musicList[newIndex]
        val uri = Uri.parse("android.resource://${packageName}/${musicData.rawResId}")
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this, uri)
        mediaPlayer.prepare()
        musicTitleTextView.text = musicData.title
        artistTextView.text = musicData.artist
        seekBar.max = mediaPlayer.duration
        albumImageView.setImageResource(musicData.albumImageResId)
        playMusic()

        // 현재 선택한 노래의 인덱스를 playedIndexes에 추가
        playedIndexes.add(newIndex)
    }

    private fun setNextMusicIndex(): Int {
        val nextIndex = currentMusicIndex + 1
        return if (nextIndex >= musicList.size) {
            if (isRepeatEnabled) {
                0 // 반복 모드인 경우 첫 번째 노래를 선택
            } else {
                // 모든 노래가 재생된 경우, playedIndexes를 초기화하고 새로운 재생 목록을 생성
                resetPlayedIndexes()
                nextIndex // 랜덤 플레이 모드인 경우 다음 노래를 무작위로 선택
            }
        } else {
            nextIndex
        }
    }

    private fun setPreviousMusicIndex(): Int {
        var previousIndex = currentMusicIndex - 1
        return if (previousIndex < 0) {
            musicList.size - 1
        } else {
            previousIndex
        }
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
        try {
            if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
                val duration = mediaPlayer.duration
                val currentPosition = mediaPlayer.currentPosition
                val remainingTime = duration - currentPosition

                val minutes = remainingTime / 1000 / 60
                val seconds = (remainingTime / 1000) % 60

                val timeRemainingText = String.format("%02d:%02d", minutes, seconds)
                timeRemainingTextView.text = timeRemainingText

                seekBar.progress = currentPosition

                handler.postDelayed({
                    updateSeekBar() // 재귀적으로 호출하지 않고 해당 함수를 호출, 오류 방지
                }, 1000)
            } else {
                // mediaPlayer가 초기화되지 않았거나 재생 중이 아닐 때, handler의 모든 콜백 및 메시지 제거
                handler.removeCallbacksAndMessages(null)
            }
        } catch (e: IllegalStateException) {
            // MediaPlayer가 예상치 못한 상태에 있을 때의 예외 처리
            e.printStackTrace()
        }
    }

    private fun getRandomIndex(): Int {
        if (musicList.isEmpty()) return 0 // 음악 리스트가 비어 있을 때, 첫 번째 곡의 인덱스를 반환

        // playedIndexes에 포함되지 않은 곡들의 인덱스를 저장할 리스트
        val availableIndexes = mutableListOf<Int>()

        // playedIndexes에 포함되지 않은 곡들의 인덱스를 찾아 availableIndexes에 추가
        for (i in musicList.indices) {
            if (i !in playedIndexes) {
                availableIndexes.add(i)
            }
        }

        // 만약 availableIndexes가 비어 있다면, 모든 곡이 재생되었으므로 playedIndexes를 초기화하고 새로운 재생 목록을 생성
        if (availableIndexes.isEmpty()) {
            playedIndexes.clear()
            return (0 until musicList.size).random()
        }

        // availableIndexes에서 랜덤으로 인덱스 선택
        val randomIndex = availableIndexes.random()
        return randomIndex
    }

    private fun resetPlayedIndexes() {
        playedIndexes.clear()
    }

    private fun playNextMusic() {
        currentMusicIndex = setNextMusicIndex()
        setMusic(currentMusicIndex)
    }

    private fun playPreviousMusic() {
        currentMusicIndex = setPreviousMusicIndex()
        setMusic(currentMusicIndex)
    }

    private fun playPreviousRandomMusic() {
        if (playedIndexes.size <= 1) return // No previous music to play
        playedIndexes.removeAt(playedIndexes.size - 1) // Remove the last played music index
        val previousIndex = playedIndexes[playedIndexes.size - 1]
        setMusic(previousIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun playNextRandomMusic() {
        val nextIndex = getRandomIndex()
        setMusic(nextIndex)
    }
}