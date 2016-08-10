package com.gufran.bitmapcanvasmove;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    Bitmap baseBitmap;

    MyChainView myChainView;
    RelativeLayout mainRelativeLayout;

    final int RQS_IMAGE1 = 1;

    String name = "Gufran Khurshid";
    String phone = "+91 7042935653";
    String email = "gufran.khurshid@okutech.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
    }

    public void onBrowse(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RQS_IMAGE1);
    }

    public void onPrint(View view) {
        myChainView.saveSnapshot();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE1:
                    try {
                        baseBitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        myChainView = new MyChainView(getApplicationContext(), baseBitmap);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.addRule(RelativeLayout.BELOW, R.id.browseButton);

                        mainRelativeLayout.addView(myChainView, layoutParams);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}
