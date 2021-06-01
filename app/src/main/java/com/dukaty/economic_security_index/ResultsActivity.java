package com.dukaty.economic_security_index;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Random;

public class ResultsActivity  extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        TextView ies = (TextView) findViewById(R.id.ies);

        TextView social = (TextView) findViewById(R.id.social);
        TextView resource = (TextView) findViewById(R.id.resource);
        TextView industrial = (TextView) findViewById(R.id.industrial);
        TextView finance = (TextView) findViewById(R.id.finance);

        Random rand = new Random();
        float min = 0;
        float max = 2;
        float diff = max - min + 1;
        Random random = new Random();
            ies.setText(String.valueOf((random.nextFloat() * 2 <= 2) ? random.nextFloat() * 2 : random.nextFloat()));
            social.setText(String.valueOf((random.nextFloat() * 2 <= 2) ? random.nextFloat() * 2 : random.nextFloat()));
            resource.setText(String.valueOf((random.nextFloat() * 2 <= 2) ? random.nextFloat() * 2 : random.nextFloat()));
            industrial.setText(String.valueOf((random.nextFloat() * 2 <= 2) ? random.nextFloat() * 2 : random.nextFloat()));
            finance.setText(String.valueOf((random.nextFloat() * 2 <= 2) ? random.nextFloat() * 2 : random.nextFloat()));
        }
}