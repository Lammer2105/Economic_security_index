package com.dukaty.economic_security_index;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView about_text = (TextView) findViewById(R.id.about_text);
        about_text.setMovementMethod(new ScrollingMovementMethod());
    }
}
