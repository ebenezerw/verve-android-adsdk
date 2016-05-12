package com.vervewireless.mastersampleapplication;

import android.app.Application;

import com.vervewireless.advert.Category;
import com.vervewireless.advert.LocationPermissionDelegate;
import com.vervewireless.advert.SplashAdManager;
import com.vervewireless.advert.StoragePermissionDelegate;
import com.vervewireless.advert.VerveAdSDK;

public class SampleApplication extends Application {

    private static final String MY_AD_KEYWORD = "adsdkexample";

    @Override
    public void onCreate() {
        super.onCreate();
        initVerveAdSDK();
    }

    private void initVerveAdSDK() {
        VerveAdSDK.initialize(this, new VerveAdSDK.InitializationListener() {
            @Override
            public void onInitialized(VerveAdSDK verveAdSDK) {
                // you can modify the VerveAdSDK object after the initialization had finished
                // e.g. configure permission delegates or Splash Manager...
                SplashAdManager splashAdManager = SplashAdManager.instance();
                splashAdManager.setAdKeyword(MY_AD_KEYWORD);
                splashAdManager.setCategory(Category.HOME_PAGE);
                // Show provided Splash Image on app startup
                /*
                * Images must be named correctly
                *
                * verve_splash - phone portrait
                * verve_splash_land - phone landscape
                * verve_tablet_splash - tablet portrait
                * verve_tablet_splash_land - tablet landscape
                *
                */
                splashAdManager.setShowSplashImageOnAppStart(true);
                splashAdManager.start();

                // Only used when targeting API 23 and up
                verveAdSDK.setLocationPermissionDelegate(new LocationPermissionDelegate() {
                    @Override
                    public boolean shouldAdLibraryRequestLocationPermission() {
                        /*
                        * The library will request user permissions to use location services when the app is brought to foreground.
                        * If you want to manually request permission to use location services or you want to delay it until some
                        * later time, you can cancel library's request.
                        * */
                        return true;
                    }
                });

                // Only used when targeting API 23 and up
                verveAdSDK.setStoragePermissionDelegate(new StoragePermissionDelegate() {
                    @Override
                    public boolean shouldAdLibraryRequestStoragePermission() {
                         /*
                        * The library will request user permissions to use external storage when needed.
                        * If you want to manually request permission to use external storage or you want to delay it until some
                        * later time, you can cancel library's request.
                        * */
                        return true;
                    }
                });
            }
        });

    }
}
