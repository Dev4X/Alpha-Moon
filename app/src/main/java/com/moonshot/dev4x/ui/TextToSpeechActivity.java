package com.moonshot.dev4x.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;

import com.moonshot.dev4x.R;

import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adrian on 03/11/15.
 */
public class TextToSpeechActivity extends Activity implements TextToSpeech.OnInitListener {

    @Bind(R.id.text_to_speech_activity_input_et) EditText inputET;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_to_speech_activity);
        ButterKnife.bind(this);

        tts = new TextToSpeech(this, this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Set<Locale> languages = tts.getAvailableLanguages();
            for (Locale locale : languages) {
                Log.d("LANG", locale.getDisplayLanguage(Locale.ENGLISH));
            }
            int result = tts.setLanguage(Locale.GERMAN);
        }
    }

    @OnClick(R.id.text_to_speech_activity_speak_bt)
    void speak() {
        speech();
    }

    private void speech() {
        tts.speak(inputET.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

}
