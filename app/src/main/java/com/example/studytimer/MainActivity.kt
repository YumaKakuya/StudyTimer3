package com.example.studytimer

import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // タイマー用の変数
    private var timerDurationMillis: Long = 0L    // 加算されたタイマー総時間（ミリ秒）
    private var remainingTimeMillis: Long = 0L    // 残り時間
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    // alarm サウンド用の MediaPlayer を保持する変数
    private var alarmMediaPlayer: MediaPlayer? = null

    // サウンドとバイブレーション選択用チェックボックス
    private lateinit var chkSound: CheckBox
    private lateinit var chkVibration: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvDigitalClock = findViewById<TextView>(R.id.digitalClockText)
        val btnTimer1min = findViewById<Button>(R.id.btnTimer1min)
        val btnTimer5min = findViewById<Button>(R.id.btnTimer5min)
        val btnTimer10min = findViewById<Button>(R.id.btnTimer10min)
        val btnTimer25min = findViewById<Button>(R.id.btnTimer25min)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnReset = findViewById<Button>(R.id.btnReset)

        chkSound = findViewById(R.id.chkSound)
        chkVibration = findViewById(R.id.chkVibration)

        // 各時間加算ボタンの処理（タイマーが動作中でなければ総時間を加算）
        btnTimer1min.setOnClickListener {
            if (!isTimerRunning) addTimerDuration(1 * 60 * 1000L, tvDigitalClock)
        }
        btnTimer5min.setOnClickListener {
            if (!isTimerRunning) addTimerDuration(5 * 60 * 1000L, tvDigitalClock)
        }
        btnTimer10min.setOnClickListener {
            if (!isTimerRunning) addTimerDuration(10 * 60 * 1000L, tvDigitalClock)
        }
        btnTimer25min.setOnClickListener {
            if (!isTimerRunning) addTimerDuration(25 * 60 * 1000L, tvDigitalClock)
        }

        // タイマー制御ボタンの処理
        btnStart.setOnClickListener {
            if (!isTimerRunning && timerDurationMillis > 0) {
                startTimer(tvDigitalClock)
            }
        }
        btnStop.setOnClickListener {
            if (isTimerRunning) {
                stopTimer()
                // 再生中の alarm サウンドがあれば停止
                alarmMediaPlayer?.let {
                    if (it.isPlaying) {
                        it.stop()
                    }
                    it.release()
                    alarmMediaPlayer = null
                }
            }
        }
        btnReset.setOnClickListener {
            resetTimer(tvDigitalClock)
        }
    }

    // タイマーの残り時間に加算して表示更新
    private fun addTimerDuration(millis: Long, tv: TextView) {
        timerDurationMillis += millis
        remainingTimeMillis = timerDurationMillis
        tv.text = formatTime(timerDurationMillis)
    }

    // CountDownTimer を利用してタイマー開始
    private fun startTimer(tv: TextView) {
        countDownTimer = object : CountDownTimer(remainingTimeMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeMillis = millisUntilFinished
                tv.text = formatTime(millisUntilFinished)
            }
            override fun onFinish() {
                isTimerRunning = false
                tv.text = "00:00"
                // サウンド再生（チェックが入っている場合）
                if (chkSound.isChecked) {
                    alarmMediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.alarm_sound)?.apply {
                        isLooping = false
                        setOnCompletionListener {
                            it.release()
                            alarmMediaPlayer = null
                        }
                        start()
                    }
                }
                // バイブレーション（チェックが入っている場合）
                if (chkVibration.isChecked) {
                    val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator?
                    vibrator?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            it.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            it.vibrate(500)
                        }
                    }
                }
            }
        }.start()
        isTimerRunning = true
    }

    // タイマー停止
    private fun stopTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    // タイマーリセット（総時間を 0 として表示更新）
    private fun resetTimer(tv: TextView) {
        stopTimer()
        timerDurationMillis = 0L
        remainingTimeMillis = 0L
        tv.text = formatTime(0L)
    }

    // ミリ秒を "MM:SS" 形式に変換
    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}