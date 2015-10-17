package com.pietromaggi.sample.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends AppCompatActivity {
    private final static String EMDK_PACKAGE_NAME = "com.symbol.emdk.emdkservice";
    private final static int IDX_MENU_EMDK = 1;
    private final static int IDX_MENU_INSTALL_EMDK = 3;

    private TextView DeviceNameTextView;
    private TextView DeviceBrandTextView;
    private TextView ESNTextView;
    private TextView VersionAndroidTextView;
    private TextView BuildNumberTextView;
    private TextView IsGoogleTextView;
    private TextView IsEMDKTextView;
    private boolean mIsEmdkAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.msg_app_info), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DeviceNameTextView = (TextView) findViewById(R.id.device_type);
        DeviceNameTextView.setText(Build.DEVICE);

        ESNTextView = (TextView) findViewById(R.id.device_esn);
        ESNTextView.setText(Build.SERIAL);

        VersionAndroidTextView = (TextView) findViewById(R.id.version_android);
        VersionAndroidTextView.setText(Build.VERSION.RELEASE);

        BuildNumberTextView = (TextView) findViewById(R.id.build_number);
        BuildNumberTextView.setText(Build.ID);

        DeviceBrandTextView = (TextView) findViewById(R.id.device_brand);
        DeviceBrandTextView.setText(Build.BRAND);

        IsGoogleTextView = (TextView) findViewById(R.id.is_gms);
        IsEMDKTextView = (TextView) findViewById(R.id.is_emdk);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPlayServices()) {
            IsGoogleTextView.setText(getString(R.string.msg_available));
        } else {
            IsGoogleTextView.setText(getString(R.string.msg_not_available));
        }

        mIsEmdkAvailable = false;
        if (isPackageInstalled(this, EMDK_PACKAGE_NAME)) {
            String emdkVversion = getPackageVersion(this, EMDK_PACKAGE_NAME);
            IsEMDKTextView.setText(getString(R.string.msg_available) + ": v" + emdkVversion);
            mIsEmdkAvailable = true;
        } else {
            IsEMDKTextView.setText(getString(R.string.msg_not_available));

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mIsEmdkAvailable) {
            menu.getItem(IDX_MENU_EMDK).setEnabled(true);
            menu.getItem(IDX_MENU_INSTALL_EMDK).setEnabled(false);
        } else {
            menu.getItem(IDX_MENU_EMDK).setEnabled(false);
            menu.getItem(IDX_MENU_INSTALL_EMDK).setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sdcard) {
            return true;
        } else if (id == R.id.action_emdk) {
            Intent i = new Intent(MainActivity.this, EmdkActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.action_battery) {
            return true;
        } else if (id == R.id.action_install_emdk) {
            // Copy apk form asset folder to secondary storage and install it
//            string apkPath = Path.Combine (Android.OS.Environment.ExternalStorageDirectory.ToString (), "tmp_app.apk");
//            using (BinaryReader br = new BinaryReader(Assets.Open("Test/Cnd.apk")))
//            {
//                using (BinaryWriter bw = new BinaryWriter(new FileStream(apkPath, FileMode.Create)))
//                {
//                    byte[] buffer = new byte[2048];
//                    int len = 0;
//                    while ((len = br.Read(buffer, 0, buffer.Length)) > 0)
//                    {
//                        bw.Write (buffer, 0, len);
//                    }
//                }
//            }
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        boolean result = true;
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SERVICE_MISSING == status) {
            result = false;
        }
        return result;
    }

    private boolean isPackageInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private String getPackageVersion(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "N/A";
        }
    }
}