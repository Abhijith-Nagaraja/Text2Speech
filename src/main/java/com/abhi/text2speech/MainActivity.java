package com.abhi.text2speech;

import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set application layout
        setContentView(R.layout.activity_main);
        //Initialize the tts object
        tts = new TextToSpeech(this, this);
        //Refer 'Speak' button
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
        //Refer 'Text' control
        txtText = (EditText) findViewById(R.id.txtText);
        //Handle onClick event for button 'Speak'
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Method yet to be defined
                speakOut();
            }

        });
    }


    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        //TTS is successfully initialized
        if (status == TextToSpeech.SUCCESS) {
            //Setting speech language
            int result = tts.setLanguage(Locale.US);
            //If your device doesn't support language you set above
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Cook simple toast message with message
                Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
            //Enable the button - It was disabled in main.xml (Go back and Check it)
            else {
                btnSpeak.setEnabled(true);
            }
            //TTS is not initialized properly
        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG).show();
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut() {
        //Get the text typed
        String text = txtText.getText().toString();
        //If no text is typed, tts will read out 'You haven't typed text'
        //else it reads out the text you typed
        if (text.length() == 0) {
            tts.speak("You haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}
