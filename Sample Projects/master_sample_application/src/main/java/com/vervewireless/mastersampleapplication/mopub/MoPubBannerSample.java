package com.vervewireless.mastersampleapplication.mopub;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.mopub.mobileads.MoPubView;
import com.vervewireless.advert.Category;
import com.vervewireless.advert.mediation.MoPubExtras;
import com.vervewireless.advert.mediation.VerveActivityListener;
import com.vervewireless.advert.mediation.VerveAdHandler;
import com.vervewireless.advert.mediation.VerveExtras;
import com.vervewireless.mastersampleapplication.R;

import java.util.HashMap;
import java.util.Map;

/**
 * This class shows how to insert a MoPub Banner into your application.
 */
public class MoPubBannerSample extends Activity implements VerveAdHandler {

	//update these values with your MoPub Ad Unit ID
	private static final String MY_AD_UNIT_ID_PHONE = "";	// Ad Unit ID for 320x50 ads
	private static final String MY_AD_UNIT_ID_TABLET = "";	// Ad Unit ID for 728x90 ads
	
	/** The view to show the ad. */
	private MoPubView adView;
	
	/** The main container. */
	private RelativeLayout mainLayout;
	
	/** The listener to be notified about activity lifecycle. */
	private VerveActivityListener verveActivityListener;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mopub_banner_sample);

		setTitle(R.string.mopub_banner_sample);
		
		adView = new MoPubView(this);
		adView.setAdUnitId(isTablet() ? MY_AD_UNIT_ID_TABLET : MY_AD_UNIT_ID_PHONE);
		
		// Lookup your RelativeLayout assuming it's been given the attribute android:id="@+id/mainLayout"
		mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		
		// Add the adView to it.
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mainLayout.addView(adView, params);
	}

	private boolean isTablet() {
		return (getResources().getConfiguration().screenLayout
				& android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK)
				>= android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	/** Called when the refresh button is clicked. */
	public void refreshAd(View unusedView) {
		
		// Not enough space to show ad ???
		int w = (int) (mainLayout.getMeasuredWidth() / getResources().getDisplayMetrics().density);
		int h = (int) (mainLayout.getMeasuredHeight() / getResources().getDisplayMetrics().density);
		int w_min = isTablet() ? 728 : 320;
		int h_min = isTablet() ? 90 : 50;
		if (w < w_min || h < h_min) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Not enough space to show ad.");
			builder.setMessage("Needs " + w_min + "x" + h_min + " dps, but only has " + w + "x" + h + " dps.\nRetry in landscape mode.");
			builder.setPositiveButton("OK", null);
			builder.show();
			return;
		}
		
		MoPubExtras mopubExtras = new MoPubExtras();
		mopubExtras.setCategory(Category.NEWS_AND_INFORMATION);

		Map<String, Object> localExtras = new HashMap<String, Object>();
		localExtras.put(VerveExtras.EXTRAS_LABEL, mopubExtras);
		
		adView.setLocalExtras(localExtras);
		adView.loadAd();
	}

	@Override
	public void setVerveActivityListener(VerveActivityListener listener) {
		this.verveActivityListener = listener;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (verveActivityListener != null) {
			verveActivityListener.onResume();
		}
	}

	@Override
	protected void onPause() {
		if (verveActivityListener != null) {
			verveActivityListener.onPause();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}
}
