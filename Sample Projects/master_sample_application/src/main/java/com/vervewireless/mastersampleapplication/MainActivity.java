package com.vervewireless.mastersampleapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vervewireless.advert.VerveAdSDK;
import com.vervewireless.mastersampleapplication.dfp.DfpBannerSample;
import com.vervewireless.mastersampleapplication.dfp.DfpInterstitialSample;
import com.vervewireless.mastersampleapplication.mopub.MoPubBannerSample;
import com.vervewireless.mastersampleapplication.mopub.MoPubInterstitialSample;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getTitle() + " - " + VerveAdSDK.sdkVersion());
        init();
    }

    private void init() {
        ListView samples = (ListView) findViewById(R.id.listSamples);
        List<SampleItem> items = new ArrayList<>();
        // Verve
        items.add(new SampleItem(getString(R.string.verve)));
        items.add(new SampleItem(VerveSample.class, getString(R.string.verve_sdk_samples)));
        items.add(new SampleItem(SplashManagerSample.class, getString(R.string.splash_manager_sample)));
        // Dfp
        items.add(new SampleItem(getString(R.string.dfp)));
        items.add(new SampleItem(DfpBannerSample.class, getString(R.string.dfp_banner_sample)));
        items.add(new SampleItem(DfpInterstitialSample.class, getString(R.string.dfp_interstitial_sample)));
        // MoPub
        items.add(new SampleItem(getString(R.string.mopub)));
        items.add(new SampleItem(MoPubBannerSample.class, getString(R.string.mopub_banner_sample)));
        items.add(new SampleItem(MoPubInterstitialSample.class, getString(R.string.mopub_interstitial_sample)));
        final SamplesAdapter adapter = new SamplesAdapter(items);
        samples.setAdapter(adapter);
        samples.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class target = adapter.getItem(position).getTarget();
                if (target != null) {
                    Intent intent = new Intent(MainActivity.this, target);
                    startActivity(intent);
                }
            }
        });
    }
}
