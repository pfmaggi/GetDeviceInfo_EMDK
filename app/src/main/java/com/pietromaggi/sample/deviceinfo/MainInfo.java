package com.pietromaggi.sample.deviceinfo;

import android.os.Build;

/**
 * Created by pietro on 17/10/15.
 */
public class MainInfo {
    final String deviceName;
    final String deviceBrand;
    final String deviceESN;
    final String androidRelease;
    final String androidBuild;
    final String deviceIsGMS;
    final String deviceHasEMDK;

    public MainInfo() {
        deviceName = Build.DEVICE;
        deviceBrand = Build.BRAND;
        deviceESN = Build.SERIAL;
        androidRelease = Build.VERSION.RELEASE;
        androidBuild = Build.ID;
        deviceIsGMS = "";
        deviceHasEMDK = "";
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getDeviceBrand() {
        return this.deviceBrand;
    }

    public String getDeviceESN() {
        return this.deviceESN;
    }

    public String getAndroidRelease() {
        return this.androidRelease;
    }

    public String getAndroidBuild() {
        return this.androidBuild;
    }
}
