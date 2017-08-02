package com.example.pzar.linguisticstoolbox

import android.Manifest
import android.view.ViewGroup
import android.widget.LinearLayout
import android.support.v4.app.ActivityCompat
import android.os.Bundle
import android.media.MediaRecorder
import android.media.MediaPlayer
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.Manifest.permission
import android.Manifest.permission.RECORD_AUDIO
import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import java.io.IOException
import android.view.MenuItem;
import android.widget.FrameLayout
import android.widget.TextView


/**
 * Created by pzar on 15.07.2017.
 */
class LinguisticsToolbox : AppCompatActivity() {
    private val LOG_TAG = "AudioRecordTest1"
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private var mFileName: String? = null

    private var mRecordButton: RecordButton? = null
    private var mRecorder: MediaRecorder? = null

    private var mPlayButton: PlayButton? = null
    private var mPlayer: MediaPlayer? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private val permissions = arrayOf<String>(Manifest.permission.RECORD_AUDIO)

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!permissionToRecordAccepted) finish()

    }

    private fun onRecord(start: Boolean) {
        if (start) {
            startRecording()
        } else {
            stopRecording()
        }
    }

    private fun onPlay(start: Boolean) {
        if (start) {
            startPlaying()
        } else {
            stopPlaying()
        }
    }

    private fun startPlaying() {
        mPlayer = MediaPlayer()
        try {
            mPlayer!!.setDataSource(mFileName)
            mPlayer!!.prepare()
            mPlayer!!.start()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "prepare() failed")
        }

    }

    private fun stopPlaying() {
        mPlayer!!.release()
        mPlayer = null
    }

    private fun startRecording() {
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mRecorder!!.setOutputFile(mFileName)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mRecorder!!.prepare()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "prepare() failed")
        }

        mRecorder!!.start()
    }

    private fun stopRecording() {
        mRecorder!!.stop()
        mRecorder!!.reset()
        mRecorder!!.release()
        mRecorder = null
    }

    internal inner class RecordButton(ctx: Context) : Button(ctx) {
        var mStartRecording = true

        var clicker: OnClickListener = View.OnClickListener {
            onRecord(mStartRecording)
            if (mStartRecording) {
                setText("Stop recording")
            } else {
                setText("Start recording")
            }
            mStartRecording = !mStartRecording
        }

        init {
            setText("Start recording")
            setOnClickListener(clicker)
        }
    }

    internal inner class PlayButton(ctx: Context) : Button(ctx) {
        var mStartPlaying = true

        var clicker: OnClickListener = View.OnClickListener {
            onPlay(mStartPlaying)
            if (mStartPlaying) {
                setText("Stop playing")
            } else {
                setText("Start playing")
            }
            mStartPlaying = !mStartPlaying
        }

        init {
            setText("Start playing")
            setOnClickListener(clicker)
        }
    }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        setContentView(R.layout.activity_linguistics_toolbox)
        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath()
        mFileName += "/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        val ll = LinearLayout(this)
        mRecordButton = RecordButton(this)
        ll.addView(mRecordButton,
                LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0f))
        mPlayButton = PlayButton(this)
        ll.addView(mPlayButton,
                LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0f))

        val content = findViewById(R.id.content) as FrameLayout
        content.addView(ll)

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onStop() {
        super.onStop()
        if (mRecorder != null) {
            mRecorder!!.release()
            mRecorder = null
        }

        if (mPlayer != null) {
            mPlayer!!.release()
            mPlayer = null
        }
    }
}