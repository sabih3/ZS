package netaq.com.zayedsons.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.network.model.responses.ResponseAttendeeInfo;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.Utils;

public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler,AttendanceView{

    private static final int CAMERA_REQUEST_CODE = 111;
    private ZXingScannerView mScannerView;
    private AttendancePresenter presenter;

    private String eventDay = "";
    private String eventTime = "";
    String registrationID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);// Programmatically initialize the scanner view
        setContentView(mScannerView);

        presenter = new AttendancePresenter(this,this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.

        takeCameraPermission();

    }

    private void takeCameraPermission() {


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){//if not permitted previosuly or revoked

            //Request permission

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);
        }else{
            mScannerView.startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    mScannerView.startCamera();
            break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {

        String resultString = result.toString();

        //resultString = "07575e5e-2ae5-422e-9cba-9f17a2ca1729_636562368000000000_636558664255529178";

        if(!resultString.isEmpty()){
             
            try {
                String[] splittedArray = resultString.split("_");

                if(splittedArray != null){
                    int length = splittedArray.length;

                    if(length == 3){
                        //extract the data &
                        //Request server , on success,
                        // show Attendance Screen with data


                        registrationID = splittedArray[0];
                        eventDay = splittedArray[1];
                        eventTime = splittedArray[2];
                        Log.d("EXPIRY TIME",eventTime);
                        requestAttendeeInfo(registrationID);


                    }else{//case when splitted array length is not required length
                        handleInvalidQR();
                    }

                }else{//case when splitted array is null
                    handleInvalidQR();
                }

            }catch (Exception exception){//case when array was not able to be split
                handleInvalidQR();
            }

        }else{//case when result string is empty
            handleInvalidQR();

        }

    }

    @Override
    public void onNetworkUnAvailable() {
        //TODO: handle No network here
    }

    @Override
    public void onError(String resolvedError) {

        UIUtils.showMessageDialog(this, resolvedError, "Dismiss",
                "", new UIUtils.DialogButtonListener() {
            @Override
            public void onPositiveButtonClicked() {

            }

            @Override
            public void onNegativeButtonClicked() {

            }
        });
    }

    @Override
    public void showProgress() {
        UIUtils.showCancelableProgressDialog(this,
                "Requesting attendee info",
                new UIUtils.ProgressDialogCancelListener() {
                    @Override
                    public void onCancelClicked() {

                    }
                });
    }

    @Override
    public void onAttendanceInfoReceived(ResponseAttendeeInfo attendanceInfo) {

        attendanceInfo.setEventDay(eventDay);
        attendanceInfo.setEventTime(eventTime);
        NavigationController.showAttendanceScreen(this,attendanceInfo);

    }

    @Override
    public void hideProgress() {
        UIUtils.hideProgressDialog();
    }

    private void requestAttendeeInfo(String registrationID){
        mScannerView.stopCameraPreview();
        presenter.requestAttendanceInfo(registrationID);

    }
    public void handleInvalidQR(){
        registrationID = "";
        eventDay = "";
        eventTime = "";

        mScannerView.resumeCameraPreview(this);

        Toast.makeText(this, "Code cannot be scanned. Make sure you are scanning " +
                        "the code from Zayed Sons App",

                Toast.LENGTH_SHORT).show();
    }
}
