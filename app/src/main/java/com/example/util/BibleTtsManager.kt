package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class BibleTtsManager(context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    var onVerseCompletedListener: ((Int) -> Unit)? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _currentPlayingIndex = MutableStateFlow<Int?>(null)
    val currentPlayingIndex: StateFlow<Int?> = _currentPlayingIndex.asStateFlow()

    private val _currentVoiceLanguage = MutableStateFlow("Setswana")
    val currentVoiceLanguage: StateFlow<String> = _currentVoiceLanguage.asStateFlow()

    private val _speechRate = MutableStateFlow(1.0f)
    val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

    private var lastSpokenText: String? = null
    private var lastIsSetswana: Boolean = true

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isPlaying.value = true
                        _isPaused.value = false
                    }

                    override fun onDone(utteranceId: String?) {
                        val completedIdx = _currentPlayingIndex.value
                        _isPlaying.value = false
                        _isPaused.value = false
                        if (completedIdx != null) {
                            onVerseCompletedListener?.invoke(completedIdx)
                        }
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        _isPlaying.value = false
                        _isPaused.value = false
                        _currentPlayingIndex.value = null
                    }
                })
            } else {
                Log.e("BibleTtsManager", "TTS initialization failed with code: $status")
            }
        }
    }

    fun setSpeechRate(rate: Float) {
        _speechRate.value = rate.coerceIn(0.5f, 2.0f)
        tts?.setSpeechRate(_speechRate.value)
    }

    fun setVoiceLanguage(language: String) {
        _currentVoiceLanguage.value = language
    }

    fun speak(text: String, isSetswana: Boolean, index: Int? = null, rate: Float = _speechRate.value) {
        if (!isInitialized || tts == null) return

        tts?.stop()
        _currentPlayingIndex.value = index
        _speechRate.value = rate
        lastSpokenText = text
        lastIsSetswana = isSetswana

        val locale = if (isSetswana) {
            _currentVoiceLanguage.value = "Setswana"
            Locale("tn", "BW")
        } else {
            _currentVoiceLanguage.value = "English"
            Locale.ENGLISH
        }

        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.language = Locale.ENGLISH
        }

        tts?.setSpeechRate(rate)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utterance_${index ?: System.currentTimeMillis()}")
        _isPlaying.value = true
        _isPaused.value = false
    }

    fun pause() {
        if (_isPlaying.value) {
            tts?.stop()
            _isPlaying.value = false
            _isPaused.value = true
        }
    }

    fun resume() {
        if (_isPaused.value && lastSpokenText != null) {
            speak(lastSpokenText!!, lastIsSetswana, _currentPlayingIndex.value, _speechRate.value)
        }
    }

    fun stop() {
        tts?.stop()
        _isPlaying.value = false
        _isPaused.value = false
        _currentPlayingIndex.value = null
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
