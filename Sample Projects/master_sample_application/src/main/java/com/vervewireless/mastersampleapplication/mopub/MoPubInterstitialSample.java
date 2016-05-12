package com.vervewireless.mastersampleapplication.mopub;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
import com.vervewireless.advert.Category;
import com.vervewireless.advert.mediation.MoPubExtras;
import com.vervewireless.advert.mediation.VerveExtras;
import com.vervewireless.mastersampleapplication.R;

import java.util.HashMap;
import java.util.Map;

/**
 * This class shows how to request and display a MoPub Interstitials in your application.
 */
public class MoPubInterstitialSample extends Activity {

	/** The log tag. */
	private static final String LOG_TAG = "MoPubInterstitialSample";

	//update this value with your MoPub Ad Unit ID
	private static final String MY_AD_UNIT_ID = "";

	/** Button text values for the interstitial show button. */
	private static final String TEXT_NOT_READY = "Interstitial Not Ready";
	private static final String TEXT_LOADING = "Loading Interstitial...";
	private static final String TEXT_SHOW = "Show Interstitial";
	private static final String TEXT_FAILED_TO_LOAD = "Ad Failed to Load";

	/** The interstitial ad. */
	private MoPubInterstitial interstitialAd;

	/** The "Show Interstitial" button. */
	private Button showButton;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mopub_interstitial_sample);

		setTitle(R.string.mopub_interstitial_sample);

		// Create an ad.
		interstitialAd = new MoPubInterstitial(this, MY_AD_UNIT_ID);
		
		// Set the AdListener.
		interstitialAd.setInterstitialAdListener(new SampleListener());
		
		showButton = (Button) findViewById(R.id.showButton);
		showButton.setText(TEXT_NOT_READY);
		showButton.setEnabled(false);
	}

	/** Called when the "Load Interstitial" button is clicked. */
	public void loadInterstitial(View unusedview) {
		// Disable the show button until the new ad is loaded.
		showButton.setText(TEXT_LOADING);
		showButton.setEnabled(false);
		
		MoPubExtras mopubExtras = new MoPubExtras();
		mopubExtras.setCategory(Category.NEWS_AND_INFORMATION);

		Map<String, Object> localExtras = new HashMap<String, Object>();
		localExtras.put(VerveExtras.EXTRAS_LABEL, mopubExtras);
		
		interstitialAd.setLocalExtras(localExtras);
		interstitialAd.load();
	}

	/** Called when the "Show Interstitial" button is clicked. */
	public void showInterstitial(View unusedview) {
		// Show the interstitial if it's loaded.
		if (interstitialAd.isReady()) {
			interstitialAd.show();
		} else {
			Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
		}
		
		// Disable the show button until another interstitial is loaded.
		showButton.setText(TEXT_NOT_READY);
		showButton.setEnabled(false);
	}

	@Override
	protected void onDestroy() {
		interstitialAd.destroy();
		super.onDestroy();
	}

	/**
	 * An ad listener that logs and toasts ad events, and enables/disables the "Show Interstitial"
	 * button when appropriate.
	 */
	private class SampleListener implements InterstitialAdListener {
		/** Called when an ad is loaded. */
		@Override
		public void onInterstitialLoaded(MoPubInterstitial interstitial) {
			Log.d(MoPubInterstitialSample.LOG_TAG, "onInterstitialLoaded");
			Toast.makeText(MoPubInterstitialSample.this, "onInterstitialLoaded", Toast.LENGTH_SHORT).show();
			
			// Change the button text and enable the show button.
			showButton.setText(TEXT_SHOW);
			showButton.setEnabled(true);
		}

		/** Called when an ad failed to load. */
		@Override
		public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
			String message = String.format("onInterstitialFailed - %s", errorCode.toString());
			Log.d(MoPubInterstitialSample.LOG_TAG, message);
			Toast.makeText(MoPubInterstitialSample.this, message, Toast.LENGTH_SHORT).show();
			
			// Change the button text and disable the show button.
			showButton.setText(TEXT_FAILED_TO_LOAD);
			showButton.setEnabled(false);
		}

		/**
		 * Called when an Activity is created in front of the app (an interstitial is shown).
		 */
		@Override
		public void onInterstitialShown(MoPubInterstitial interstitial) {
			Log.d(MoPubInterstitialSample.LOG_TAG, "onInterstitialShown");
			Toast.makeText(MoPubInterstitialSample.this, "onInterstitialShown", Toast.LENGTH_SHORT).show();
		}

		/**
		 * Called when an ad is clicked and going to start a new Activity that will leave the
		 * application (e.g. breaking out to the Browser or Maps application).
		 */
		@Override
		public void onInterstitialClicked(MoPubInterstitial interstitial) {
			Log.d(MoPubInterstitialSample.LOG_TAG, "onInterstitialClicked");
			Toast.makeText(MoPubInterstitialSample.this, "onInterstitialClicked", Toast.LENGTH_SHORT).show();
		}

		/** Called when an ad is closed and about to return to the application. */
		@Override
		public void onInterstitialDismissed(MoPubInterstitial interstitial) {
			Log.d(MoPubInterstitialSample.LOG_TAG, "onInterstitialDismissed");
			Toast.makeText(MoPubInterstitialSample.this, "onInterstitialDismissed", Toast.LENGTH_SHORT).show();
		}
	}
}
