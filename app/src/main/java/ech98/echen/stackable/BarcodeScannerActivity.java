package ech98.echen.stackable;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class BarcodeScannerActivity extends ActionBarActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
//  Handles results that are received from ZBar scanner
    @Override
    public void handleResult(Result rawResult) {
        if(rawResult.getContents() != null){
            Intent output = new Intent();
            output.putExtra("data", rawResult.getContents());
            setResult(RESULT_OK, output);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
