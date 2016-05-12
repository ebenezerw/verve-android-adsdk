package com.vervewireless.mastersampleapplication;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.vervewireless.advert.Category;
import com.vervewireless.advert.SplashAdManager;

public class SplashManagerSample extends Activity {

    private static final String MY_AD_KEYWORD = "adsdkexample";

    private EditText timeoutValueView;
    private EditText durationValueView;
    private EditText intervalValueView;
    private SettingsLayout settingsLayout;
    private FrameLayout shadowLayout;
    private SplashAdManager splashAdManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_sample);

        setTitle(R.string.splash_manager_sample);

        splashAdManager = SplashAdManager.instance();
        splashAdManager.setAdKeyword(MY_AD_KEYWORD);
        splashAdManager.setCategory(Category.HOME_PAGE);

        ToggleButton trackingButton = (ToggleButton) findViewById(R.id.toggleButton);
        trackingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                splashAdManager.setLimitUserTrackingEnabled(!isChecked);
            }
        });

        ToggleButton toggleSplashButton = (ToggleButton) findViewById(R.id.toggleSplashButton);
        toggleSplashButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Splash Ad request timeout in milliseconds
                    splashAdManager.setTimeout(Integer.parseInt(timeoutValueView.getText().toString()));
                    // Splash Ad display duration
                    splashAdManager.setDuration(Integer.parseInt(durationValueView.getText().toString()));
                    // Interval between the Splash Ad calls
                    splashAdManager.setInterval(Integer.parseInt(intervalValueView.getText().toString()));
                    splashAdManager.start();
                } else {
                    // we can stop the SplashAdManager
                    splashAdManager.stop();
                }

                enableSettings(!isChecked);
            }
        });

        View timeoutView = findViewById(R.id.timeoutView);
        ((TextView)timeoutView.findViewById(R.id.labelText)).setText(R.string.timeout);
        timeoutValueView = (EditText) timeoutView.findViewById(R.id.valueEdit);
        timeoutValueView.setText(String.valueOf(splashAdManager.getTimeout()));
        //
        View durationView = findViewById(R.id.durationView);
        ((TextView)durationView.findViewById(R.id.labelText)).setText(R.string.duration);
        durationValueView = (EditText) durationView.findViewById(R.id.valueEdit);
        //
        View intervalView = findViewById(R.id.intervalView);
        ((TextView)intervalView.findViewById(R.id.labelText)).setText(R.string.interval);
        intervalValueView = (EditText) intervalView.findViewById(R.id.valueEdit);
        //
        settingsLayout = (SettingsLayout) findViewById(R.id.settingsLayout);
        shadowLayout = (FrameLayout) findViewById(R.id.shadowLayout);
        //
        enableSettings(false);
    }

    private void enableSettings(boolean enable) {
        settingsLayout.setEnabled(enable);
        shadowLayout.setForeground(new ColorDrawable(enable ? 0x00000000 : 0x60000000));
        //
        timeoutValueView.clearFocus();
        durationValueView.clearFocus();
        intervalValueView.clearFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeoutValueView.setText(String.valueOf(splashAdManager.getTimeout()));
        durationValueView.setText(String.valueOf(splashAdManager.getDuration()));
        intervalValueView.setText(String.valueOf(splashAdManager.getInterval()));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}