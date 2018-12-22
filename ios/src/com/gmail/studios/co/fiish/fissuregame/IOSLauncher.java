package com.gmail.studios.co.fiish.fissuregame;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.iad.ADBannerContentSizeIdentifier;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.pods.google.mobileads.GADAdSize;
import org.robovm.pods.google.mobileads.GADBannerView;
import org.robovm.pods.google.mobileads.GADInterstitial;
import org.robovm.pods.google.mobileads.GADInterstitialDelegateAdapter;
import org.robovm.pods.google.mobileads.GADMobileAds;
import org.robovm.pods.google.mobileads.GADRequest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.gmail.studios.co.fiish.fissuregame.FissureGame;

import java.util.Arrays;

public class IOSLauncher extends IOSApplication.Delegate implements IOSLink {
    private Preferences mIOSData;

    //private GADBannerView mBannerView;
    private GADInterstitial mInterstitial;
    private GADRequest mRequest;

    private static final String AD_APP_ID = "ca-app-pub-7523508007174708~4763020481";
    //private static final String AD_UNIT_ID_BANNER_TOP = "ca-app-pub-3940256099942544/6300978111";   //Test ID: ca-app-pub-3940256099942544/6300978111
    private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-7523508007174708/3942244474"; // Test ID: ca-app-pub-3940256099942544/1033173712

    //private static final String IPHONE_7P_DEVICE_ID = "95568467-62F5-4831-A178-1543E91569CB";

    @Override
    protected IOSApplication createApplication() {
        GADMobileAds.disableSDKCrashReporting();

        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = true;
        config.useAccelerometer = false;
        config.useCompass = false;

        GADMobileAds.configure(AD_APP_ID);

        mRequest = new GADRequest();
        //mRequest.setTestDevices(Arrays.asList(IPHONE_7P_DEVICE_ID));
        createAndLoadInterstitial();

        /*
        mBannerView = new GADBannerView(GADAdSize.SmartBannerLandscape());
        mBannerView.setAdUnitID(AD_UNIT_ID_BANNER_TOP);
        mBannerView.loadRequest(new GADRequest());
        mBannerView.setHidden(true);
        */

        return new IOSApplication(new FissureGame(this), config);
    }

    @Override
    public float getHighScore() {
        if (mIOSData == null) {
            mIOSData = Gdx.app.getPreferences("DataIOS");
        }
        return mIOSData.getFloat("iOShighScore", 0.00f);
    }

    @Override
    public void setHighScore(float highScore) {
        if (mIOSData == null) {
            mIOSData = Gdx.app.getPreferences("DataIOS");
        }
        mIOSData.putFloat("iOShighScore", highScore);
        mIOSData.flush();
    }

    @Override
    public void showBanner(boolean show) {
        /*
        if (show) {
            if (!mBannerView.getSuperview().equals(UIApplication.getSharedApplication().getKeyWindow().getRootViewController().getView())) { //throws NPL
                UIApplication.getSharedApplication().getKeyWindow().getRootViewController().getView().addSubview(mBannerView);
            }
            //mBannerView.setHidden(false);
        } else {
            mBannerView.setHidden(true);
            mBannerView.loadRequest(new GADRequest());
        }
        */
    }

    @Override
    public void showInterstitial() {
        mInterstitial.present(UIApplication.getSharedApplication().getKeyWindow().getRootViewController());
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }


    private void createAndLoadInterstitial() {
        mInterstitial = new GADInterstitial(AD_UNIT_ID_INTERSTITIAL);
        mInterstitial.setDelegate(new GADInterstitialDelegateAdapter() {
            @Override
            public void didDismissScreen(GADInterstitial ad) {
                createAndLoadInterstitial();
            }
        });
        mInterstitial.loadRequest(mRequest);
    }
}