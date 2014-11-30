package com.martabakmanis.torch.application;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.hardware.Camera.Parameters;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Boolean isLightOn = false;
    private Camera camera;
    private Button button;

    @Override
    protected void onStop() {
        super.onStop();
        if(camera != null){
            camera.release();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonTorch);

        Context context=this;
        PackageManager packageManager = context.getPackageManager();

        //if device support camera
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Log.e("err","Device has no torch");
            return;
        }

        camera = Camera.open();
        final Parameters p =  camera.getParameters();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(isLightOn){
                    Log.i("info","Torch is turn Off!");
                    p.setFlashMode(Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    isLightOn=false;
                } else {
                    Log.i("info","Torch is turn On!");
                    p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();
                    isLightOn=true;
                }
            }
        });
    }
}
