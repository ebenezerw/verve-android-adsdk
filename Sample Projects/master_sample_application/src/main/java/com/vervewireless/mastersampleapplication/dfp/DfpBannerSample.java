package com.vervewireless.mastersampleapplication.dfp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest.Builder;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.vervewireless.advert.Category;
import com.vervewireless.advert.mediation.DFPExtras;
import com.vervewireless.advert.mediation.VerveActivityListener;
import com.vervewireless.advert.mediation.VerveAdHandler;
import com.vervewireless.advert.mediation.VerveCustomEventBanner;
import com.vervewireless.mastersampleapplication.R;

/**
 * This class shows how to insert a DFP Banner into your application.
 */
public class DfpBannerSample extends Activity implements VerveAdHandler {

	/** The view to show the ad. */
	private PublisherAdView adView;

	/** The listener to be notified about activity lifecycle. */
	private VerveActivityListener verveActivityListener;

	//update this value with your DFP Ad Unit ID
	private static final String MY_AD_UNIT_ID = "";

	//update this value with your Verve Mobile supplied keyword
	private static final String MY_PARTNER_KEYWORD = "adsdkexample";

	/** The main container. */
	private RelativeLayout mainLayout;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dfp_banner_sample);

		setTitle(R.string.dfp_banner_sample);

		adView = new PublisherAdView(this);
		adView.setAdUnitId(MY_AD_UNIT_ID);
		adView.setAdSizes(isTablet() ? AdSize.LEADERBOARD : AdSize.BANNER);
		
		// Set the AdListener to listen for standard ad events.
		adView.setAdListener(new LoggingAdListener());
		
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
		int w_min = adView.getAdSize().getWidth();
		int h_min = adView.getAdSize().getHeight();
		if (w < w_min || h < h_min) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Not enough space to show ad.");
			builder.setMessage("Needs " + w_min + "x" + h_min + " dps, but only has " + w + "x" + h + " dps.\nRetry in landscape mode.");
			builder.setPositiveButton("OK", null);
			builder.show();
			return;
		}
		
		adView.loadAd(buildRequest());
	}

	private PublisherAdRequest buildRequest() {
		Builder b = new PublisherAdRequest.Builder();
		
		DFPExtras dfpExtras = new DFPExtras();
		dfpExtras.setPartnerKeyword(MY_PARTNER_KEYWORD);
		dfpExtras.setCategory(Category.NEWS_AND_INFORMATION);

		b.addCustomEventExtrasBundle(VerveCustomEventBanner.class, dfpExtras.toBundle());
		return b.build();
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
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	protected void onPause() {
		if (verveActivityListener != null) {
			verveActivityListener.onPause();
		}
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
}
