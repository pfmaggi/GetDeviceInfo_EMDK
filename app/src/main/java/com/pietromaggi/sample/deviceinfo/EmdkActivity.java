package com.pietromaggi.sample.deviceinfo;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.VersionManager;

/**
 * Created by pietro on 10/10/15.
 */
public class EmdkActivity extends AppCompatActivity implements EMDKManager.EMDKListener {
    private TextView VersionEmkdkTextView;
    private TextView VersionOSxTextView;
    private TextView VersionMxTextView;
    private TextView VersionScannerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emdk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, getString(R.string.msg_app_info), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        VersionEmkdkTextView = (TextView)findViewById(R.id.version_emdk);

        VersionOSxTextView = (TextView)findViewById(R.id.version_osx);
//        DeviceNameTextView.setText(Build.DEVICE);

        VersionMxTextView = (TextView)findViewById(R.id.version_mxmf);
//        VersionMxTextView.setText(Build.SERIAL);

        VersionScannerTextView = (TextView)findViewById(R.id.version_scanner);
//        VersionScannerTextView.setText(Build.ID);

        EMDKManager.getEMDKManager(this, this);

    }

    @Override
    public void onOpened(EMDKManager emdkManager) {
        VersionManager version = (VersionManager)emdkManager.getInstance(EMDKManager.FEATURE_TYPE.VERSION);

        VersionEmkdkTextView.setText(version.getVersion(VersionManager.VERSION_TYPE.EMDK));
        //VersionOSxTextView.setText(version.getVersion(VersionManager.VERSION_TYPE.));
        VersionMxTextView.setText(version.getVersion(VersionManager.VERSION_TYPE.MX));
        VersionScannerTextView.setText(version.getVersion(VersionManager.VERSION_TYPE.BARCODE));
    }

    @Override
    public void onClosed() {

    }
}
