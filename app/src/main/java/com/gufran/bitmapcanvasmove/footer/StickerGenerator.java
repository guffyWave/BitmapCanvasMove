package com.gufran.bitmapcanvasmove.footer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;

import java.io.File;

/**
 * Created by gufran on 8/10/16.
 */
public class StickerGenerator {

    public static Bitmap generateSmallSticker(Context context, Bitmap sourceBitmap, String name, String phone, String email) {
        int infoFooterWidth = (int) (0.40 * sourceBitmap.getWidth());
        int userFooterWidth = (int) (0.60 * sourceBitmap.getWidth());

        int footerHeight = (int) (0.25 * sourceBitmap.getHeight());

        ImageManipulationUtil imu = new ImageManipulationUtil();
        TextConfiguration textConfiguration = new TextConfiguration(context, "For other details,please contact me");
        textConfiguration.setSize((int) (0.15 * footerHeight));
        android.graphics.Bitmap bmpInfo = imu.generateFooterInfoBitMap(infoFooterWidth, footerHeight, textConfiguration);


        File path = Environment.getExternalStorageDirectory();
        File imageFile = new File(path, "user.png");
        Bitmap userPic = BitmapFactory.decodeFile(imageFile.getPath(), null);


        //--->Name Configuration
        NameTextConfiguration nameTextConfiguration = new NameTextConfiguration(context, name
        );
        nameTextConfiguration.setSize((int) (0.25 * footerHeight));
        nameTextConfiguration.setColor(Color.DKGRAY);
        nameTextConfiguration.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"));

        //--->Phone Configuration
        PhoneTextConfiguration phoneTextConfiguration = new PhoneTextConfiguration(context, phone);
        phoneTextConfiguration.setSize((int) (0.15 * footerHeight));
        phoneTextConfiguration.setColor(Color.DKGRAY);
        phoneTextConfiguration.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));

        //--->Email Configuration
        EmailTextConfiguration emailTextConfiguration = new EmailTextConfiguration(context, email);
        emailTextConfiguration.setSize((int) (0.15 * footerHeight));
        emailTextConfiguration.setColor(Color.DKGRAY);
        emailTextConfiguration.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));

        Bitmap bmpUser = imu.generateUserInfoBitmap(userFooterWidth, footerHeight, userPic, nameTextConfiguration, phoneTextConfiguration, emailTextConfiguration);

        return bmpUser;
    }
}
