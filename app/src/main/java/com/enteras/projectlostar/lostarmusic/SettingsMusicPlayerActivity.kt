package com.enteras.projectlostar.lostarmusic

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.widget.Toast
import kotlin.random.Random

class SettingsMusicPlayerActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var playPauseButton: ImageView
    private lateinit var previousButton: ImageView
    private lateinit var nextButton: ImageView
    private lateinit var repeatButton: ImageView
    private lateinit var randomPlayButton: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var musicTitleTextView: TextView
    private lateinit var artistTextView: TextView
    private lateinit var albumImageView: ImageView
    private lateinit var timeRemainingTextView: TextView
    private lateinit var airpodsIcon: ImageView
    private var musicList: MutableList<MusicData> = mutableListOf()
    private var currentMusicIndex: Int = 0
    private var isRepeatEnabled: Boolean = false
    private var isRandomPlayEnabled: Boolean = false
    private var playedIndexes: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        initMusicList()

        mediaPlayer = MediaPlayer()
        handler = Handler(mainLooper)

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

        // 블루투스 권한 요청
        requestBluetoothPermission()

        setMusic(currentMusicIndex)

        airpodsIcon.setOnClickListener{
            Toast.makeText(this, "AirPods(3세대) 연결됨", Toast.LENGTH_SHORT).show()
        }

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
            val intent = Intent(this, MainActivity::class.java)
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

        val bluetoothReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED) {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, -1)
                    if (state == BluetoothAdapter.STATE_CONNECTED) {
                        val devices: Set<BluetoothDevice>? = BluetoothAdapter.getDefaultAdapter()?.bondedDevices
                        var isAirPodsConnected = false
                        devices?.forEach { device ->
                            if (device.name == "AirPods" || device.name.startsWith("A2564") ||
                                device.name.startsWith("A2565") || device.name.startsWith("A2566") ||
                                device.name.startsWith("A2897")
                            ) {
                                isAirPodsConnected = true
                                return@forEach
                            }
                        }
                        if (isAirPodsConnected) {
                            airpodsIcon.visibility = ImageView.VISIBLE
                        } else {
                            airpodsIcon.visibility = ImageView.GONE
                        }
                    } else if (state == BluetoothAdapter.STATE_DISCONNECTED) {
                        airpodsIcon.visibility = ImageView.GONE
                        // 블루투스 장치 연결이 끊겼을 때 처리할 내용 추가 가능
                    }
                }
            }
        }
    }


    private fun initMusicList() {
        // Initialize music data
        // Example:
        musicList.add(
            MusicData(
                "PLAY",
                "Alan Walker, K-391, Tungevaag, Mangoo",
                "3:24",
                R.drawable.music_album_icon_5,
                R.raw.music1
            )
        )
        musicList.add(
            MusicData(
                "Sad Sometimes",
                "Alan Walker, CORSAK & Huang Xiaoyun",
                "3:19",
                R.drawable.music_album_icon_6,
                R.raw.music2
            )
        )
        musicList.add(MusicData("Alone", "Alan Walker", "2:43", R.drawable.music_album_icon_7, R.raw.music3))
        musicList.add(
            MusicData(
                "We'll Meet Again",
                "TheFatRat & Laura Brehm",
                "3:15",
                R.drawable.music_album_icon_4,
                R.raw.music4
            )
        )
        musicList.add(MusicData("Shiawase (VIP)", "Dion Timmer", "3:02", R.drawable.music_album_icon_3, R.raw.music5))
        musicList.add(MusicData("KOCMOC UNLEASHED", "G2961", "1:29", R.drawable.music_album_icon_8, R.raw.music6))
        musicList.add(
            MusicData(
                "Lone (Slow Hours Remix)",
                "What So Not",
                "4:33",
                R.drawable.music_album_icon_9,
                R.raw.music7
            )
        )
        musicList.add(MusicData("Sun Mother", "Melodysheep", "3:36", R.drawable.music_album_icon_10, R.raw.music8))
        musicList.add(MusicData("R", "Plum", "2:26", R.drawable.music_album_icon_11, R.raw.music9))
        musicList.add(MusicData("Terrasphere", "Plum", "2:34", R.drawable.music_album_icon_12, R.raw.music10))
        musicList.add(MusicData("PLUM MEGAMIX", "Plum", "10:03", R.drawable.music_album_icon_13, R.raw.music11))
        musicList.add(MusicData("PLUM MEGAMIX 2", "Plum", "11:36", R.drawable.music_album_icon_14, R.raw.music12))
        // Add additional music data here if needed
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

        playedIndexes.add(newIndex)
    }

    private fun setNextMusicIndex(): Int {
        val nextIndex = currentMusicIndex + 1
        return if (nextIndex >= musicList.size) {
            if (isRepeatEnabled) {
                0
            } else {
                resetPlayedIndexes()
                nextIndex
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
                    updateSeekBar()
                }, 1000)
            } else {
                handler.removeCallbacksAndMessages(null)
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun getRandomIndex(): Int {
        if (musicList.isEmpty()) return 0

        val availableIndexes = mutableListOf<Int>()
        for (i in musicList.indices) {
            if (i !in playedIndexes) {
                availableIndexes.add(i)
            }
        }

        if (availableIndexes.isEmpty()) {
            playedIndexes.clear()
            return (0 until musicList.size).random()
        }

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
        if (playedIndexes.size <= 1) return
        playedIndexes.removeAt(playedIndexes.size - 1)
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

    private val REQUEST_BLUETOOTH_PERMISSION = 1001

    // 블루투스 권한 요청
    private fun requestBluetoothPermission() {
        // 블루투스 권한이 이미 허용되었는지 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 사용자에게 권한 요청 다이얼로그 표시
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH),
                REQUEST_BLUETOOTH_PERMISSION
            )
        } else {
            // 권한이 이미 허용된 경우 블루투스 작업 수행
            performBluetoothTask()
        }
    }

    // 권한이 허용된 경우 블루투스 작업 수행
    private fun performBluetoothTask() {
        try {
            // 블루투스 권한이 이미 허용되었는지 확인
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // 권한이 허용된 경우 Bluetooth 작업 수행
                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                // BluetoothAdapter가 null이 아니고 Bluetooth 기능을 지원하는 경우에만 실행
                bluetoothAdapter?.let {
                    // Bluetooth 기능을 사용하여 원하는 작업 수행
                    // 여기에 BluetoothAdapter를 사용하는 코드 추가
                }
            } else {
                // 권한이 허용되지 않은 경우 사용자에게 권한 요청 다이얼로그 표시
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH),
                    REQUEST_BLUETOOTH_PERMISSION
                )
            }
        } catch (securityException: SecurityException) {
            // Bluetooth 기능을 사용할 때 SecurityException이 발생한 경우 처리
            // 여기서는 사용자에게 알림 메시지를 표시합니다.
            Toast.makeText(this, "Bluetooth 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 블루투스 작업 수행
                performBluetoothTask()
            } else {
                // 권한이 거부된 경우 사용자에게 메시지 표시 등의 작업 수행
                showPermissionDeniedMessage()
            }
        }
    }

    private fun showPermissionDeniedMessage() {
        Toast.makeText(this, "블루투스 권한을 거부하셔서 오버레이를 사용하실 수 없습니다.", Toast.LENGTH_SHORT).show()
    }
}
