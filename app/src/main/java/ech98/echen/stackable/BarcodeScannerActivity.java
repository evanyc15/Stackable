package ech98.echen.stackable;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import ech98.echen.stackable.ZBarScannerActivity;
import ech98.echen.stackable.ZBarConstants;
import net.sourceforge.zbar.Symbol;


public class BarcodeScannerActivity extends ActionBarActivity {
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_barcode_scanner);

        launchScanner();
    }
    public void launchScanner() {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }
//    public void launchQRScanner(View v) {
//        if (isCameraAvailable()) {
//            Intent intent = new Intent(this, ZBarScannerActivity.class);
//            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
//            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
//        } else {
//            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
//        }
//    }
    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
//                Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
                Intent output = new Intent();
                output.putExtra("data", data.getStringExtra(ZBarConstants.SCAN_RESULT));
                setResult(RESULT_OK, output);
                finish();
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
