package com.vervewireless.mastersampleapplication;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.vervewireless.advert.Ad;
import com.vervewireless.advert.AdClickedListener;
import com.vervewireless.advert.AdError;
import com.vervewireless.advert.AdListener;
import com.vervewireless.advert.AdRequest;
import com.vervewireless.advert.AdResponse;
import com.vervewireless.advert.AdView;
import com.vervewireless.advert.Category;
import com.vervewireless.advert.InterstitialAd;
import com.vervewireless.advert.InterstitialAdListener;
import com.vervewireless.advert.SplashAd;
import com.vervewireless.advert.SplashAdListener;

public class VerveSample extends Activity {

    private static final String MY_AD_KEYWORD = "adsdkexample";
    private static final String TAG = VerveSample.class.getSimpleName();

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private SplashAd splashAd;

    private ToggleButton trackingButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verve_sample);

        setTitle(R.string.verve_sdk_samples);

        trackingButton = (ToggleButton) findViewById(R.id.toggleButton);

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdKeyword(MY_AD_KEYWORD);
        mAdView.setAdClickedListener(new AdClickedListener() {

            @Override
            public boolean onAdClicked(Ad ad, Uri uri) {
                Log.d(TAG, "AdView link clicked - uri: " + uri.toString());

				/*
                 * If you want to handle clicks on ad
				 * and override the default behavior,
				 * return true, otherwise return false.
				 */
                return false;
            }
        });
        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded(AdResponse response) {
                Log.d(TAG, "AdView loaded");
            }

            @Override
            public void onAdError(AdError e) {
                Log.e(TAG, "AdView error:" + e);
            }

            @Override
            public void onNoAdReturned(AdResponse response) {
                Log.d(TAG, "AdView no ad");
            }

            @Override
            public void onAdPageFinished() {
                Log.d(TAG, "AdView page finished loading");
            }
        });

        mInterstitialAd = new InterstitialAd(VerveSample.this);
        mInterstitialAd.setAdKeyword(MY_AD_KEYWORD);
        mInterstitialAd.setInterstitialAdListener(new InterstitialAdListener() {

            @Override
            public void onAdReady() {
                Log.d(TAG, "InterstitialAd downloaded and ready for display");
                mInterstitialAd.display();
            }

            @Override
            public void onAdFailed(AdError adError) {
                Log.d(TAG, "InterstitialAd failed");
            }

            @Override
            public void onNoAdReturned() {
                Log.d(TAG, "InterstitialAd No Fill");
            }
        });

        Button buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNewAd();
            }
        });

        Button buttonInterstitialAd = (Button) findViewById(R.id.buttonInterstitial);
        buttonInterstitialAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNewInterstitialAd();
            }
        });


        splashAd = new SplashAd(this);
        splashAd.setAdKeyword(MY_AD_KEYWORD);

        splashAd.setSplashAdListener(new SplashAdListener() {
            @Override
            public void onAdReady() {
                Log.d(TAG, "Splash Ad successfully loaded");
            }

            @Override
            public void onAdFailed() {
                Log.d(TAG, "Splash Ad failed");
            }
        });

        Button buttonStartSplashAd = (Button) findViewById(R.id.buttonStartSplash);
        buttonStartSplashAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSplashlAd();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.onResume();
        requestNewAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private AdRequest createAdRequest() {
        // Create a new AdRequest.
        AdRequest adRequest = new AdRequest();

		/*
         * Add a category to the ad request (optional).
		 * Default to Category.NEWS
		 */
        adRequest.setCategory(Category.HOME_PAGE);

		/*
         * You can decide not to have tracking on your users (optional).
		 */
        adRequest.setLimitUserTrackingEnabled(!trackingButton.isChecked());

        return adRequest;
    }

    private void requestNewAd() {
		/*
         * Make the ad request.
		 * Location data is automatically added to the request if
		 * uses-permissions for location set with either:
		 * ACCESS_COARSE_LOCATION
		 * ACCESS_FINE_LOCATION
		 */
        mAdView.requestAd(createAdRequest());
    }

    private void requestNewInterstitialAd() {
		/*
         * Make the ad request.
		 * Location data is automatically added to the request if
		 * uses-permissions for location set with either:
		 * ACCESS_COARSE_LOCATION
		 * ACCESS_FINE_LOCATION
		 */
        mInterstitialAd.requestAd(createAdRequest());
    }

    private void requestSplashlAd() {
		/*
         * Make the ad request.
		 * Location data is automatically added to the request if
		 * uses-permissions for location set with either:
		 * ACCESS_COARSE_LOCATION
		 * ACCESS_FINE_LOCATION
		 */
        // set request timeout in milliseconds
        splashAd.setTimeout(1500);

        // set ad display duration in milliseconds
        splashAd.setDuration(4000);
        /*
        * show splash image when the splash ad is requested
        * Images must be named correctly
        *
        * verve_splash - phone portrait
        * verve_splash_land - phone landscape
        * verve_tablet_splash - tablet portrait
        * verve_tablet_splash_land - tablet landscape
        *
        */
        splashAd.setShowSplashImage(true);

        splashAd.requestAd(createAdRequest());
    }
}