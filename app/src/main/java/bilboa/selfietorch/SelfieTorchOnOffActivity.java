package bilboa.selfietorch;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Build;

public class SelfieTorchOnOffActivity extends AppCompatActivity {

    private static CameraManager CamManager;
    private static String CameraIdFrontFacing = null;
    private boolean IsTorchOn = false;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_torch_on_off);



        final Button ButtonOnOff = (Button) findViewById(R.id.c_ButtonOnOff);

        //get front facing camera
        CamManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String[] CameraIdList = null;
        try {
            CameraIdList = CamManager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        for(String CameraId : CameraIdList)
        {
            try {
                CameraCharacteristics CamCharacteristics = CamManager.getCameraCharacteristics(CameraId);
                if(CamCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT)
                {
                    CameraIdFrontFacing = CameraId;
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        if(CameraIdFrontFacing == null)
        {
            return;
        }

        ButtonOnOff.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                if(IsTorchOn)
                {
                    ButtonOnOff.setText("Off");
                    IsTorchOn = false;
                    handleActionTurnOffFlashLight(v.getContext());
                }else
                {
                    ButtonOnOff.setText("On");
                    IsTorchOn = true;
                    handleActionTurnOnFlashLight(v.getContext());
                }
            }
        });
    }

    private static void handleActionTurnOnFlashLight(Context context){

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CamManager.setTorchMode(CameraIdFrontFacing, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleActionTurnOffFlashLight(Context context){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CamManager.setTorchMode(CameraIdFrontFacing, false);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
