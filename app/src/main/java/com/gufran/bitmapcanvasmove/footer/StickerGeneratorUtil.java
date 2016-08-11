package com.gufran.bitmapcanvasmove.footer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;

import java.io.File;

/**
 * Created by gufran on 8/10/16.
 */
public class StickerGeneratorUtil {
    ImageManipulationUtil imu;

    public StickerGeneratorUtil() {
        imu = new ImageManipulationUtil();
    }


    final int backgroundColor = Color.WHITE;

    private Bitmap paintFooterInfoBitMap(int w, int h, TextConfiguration textConfiguration) {
        // if (textConfiguration == null) throw new Exception("Text Configuration can't be null");

        Bitmap mainBitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mainBitMap);

        //----->>creating background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        Rect rect = new Rect(0, 0, mainBitMap.getWidth(), mainBitMap.getHeight());
        canvas.drawRect(rect, backgroundPaint);

        //----->>creating text
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        if (textConfiguration != null) {
            textPaint.setColor(textConfiguration.getColor());
            textPaint.setTextSize(textConfiguration.getSize());
            int posX = (int) (w / 2 - textPaint.measureText(textConfiguration.getText()) / 2);
            int posY = ((int) h / 2) + 5;
            canvas.drawText(textConfiguration.getText(), posX, posY, textPaint);
        }
        //draw the vertical separator line
        canvas.drawLine(w - 10, 30, w - 10, h - 30, textPaint);

        canvas.drawBitmap(mainBitMap, rect, rect, backgroundPaint);
        return mainBitMap;
    }

    private Bitmap paintUserInfoBitmap(int w, int h, Bitmap bitmapUserAvatar, NameTextConfiguration nameTextConfiguration, PhoneTextConfiguration phoneTextConfiguration, EmailTextConfiguration emailTextConfiguration) {
        Bitmap mainBitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mainBitMap);

        //----->>creating background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        Rect rect = new Rect(0, 0, mainBitMap.getWidth(), mainBitMap.getHeight());
        canvas.drawRect(rect, backgroundPaint);

        //----->>creating user image
        Paint userImagePaint = new Paint();
        userImagePaint.setAntiAlias(true);
        bitmapUserAvatar = imu.scaleDown(bitmapUserAvatar, (int) (0.70 * h), true);//scale down image to 60% of the height of canvas
        int userImagePosX = (int) (0.2 * w) - bitmapUserAvatar.getWidth() / 2;// 20% of width-half of bmp width
        int userImagePosY = (int) (0.5 * h) - bitmapUserAvatar.getHeight() / 2;// 50 % of height-half of bmp height
        canvas.drawBitmap(bitmapUserAvatar, userImagePosX, userImagePosY, userImagePaint);

        //----->> setting up name
        Paint namePaint = new Paint();
        namePaint.setAntiAlias(true);
        if (nameTextConfiguration != null) {
            namePaint.setColor(nameTextConfiguration.getColor());
            namePaint.setTextSize(nameTextConfiguration.getSize());
            if (emailTextConfiguration.getTypeface() != null)
                namePaint.setTypeface(nameTextConfiguration.getTypeface());
            // namePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            int posX = (int) (0.40 * w);// 40% of width
            int posY = (int) (0.35 * h);// 25% of height
            canvas.drawText(nameTextConfiguration.getText(), posX, posY, namePaint);
        }


        //----->> setting up phoneNumber
        Paint phoneNumberPaint = new Paint();
        phoneNumberPaint.setAntiAlias(true);
        if (phoneTextConfiguration != null) {
            phoneNumberPaint.setColor(phoneTextConfiguration.getColor());
            phoneNumberPaint.setTextSize(phoneTextConfiguration.getSize());
            if (phoneTextConfiguration.getTypeface() != null)
                phoneNumberPaint.setTypeface(phoneTextConfiguration.getTypeface());
            int phonePosX = (int) (0.40 * w);// 40% of width
            int phonePosY = (int) (0.60 * h);// 50% of height
            canvas.drawText(phoneTextConfiguration.getText(), phonePosX, phonePosY, phoneNumberPaint);
        }

        //----->> setting up email
        Paint emailPaint = new Paint();
        emailPaint.setAntiAlias(true);
        if (emailTextConfiguration != null) {
            emailPaint.setColor(emailTextConfiguration.getColor());
            emailPaint.setTextSize(emailTextConfiguration.getSize());
            if (emailTextConfiguration.getTypeface() != null)
                emailPaint.setTypeface(emailTextConfiguration.getTypeface());
            int emailPosX = (int) (0.40 * w);// 40% of width
            int emailPosY = (int) (0.80 * h);// 70% of height
            canvas.drawText(emailTextConfiguration.getText(), emailPosX, emailPosY, emailPaint);
        }

        canvas.drawBitmap(mainBitMap, rect, rect, backgroundPaint);
        return mainBitMap;
    }


    private Bitmap getUserInfoBitmap(Context context, int width, int height, String name, String phone, String email) {
        File path = Environment.getExternalStorageDirectory();
        File imageFile = new File(path, "user.png");
        Bitmap userPic = BitmapFactory.decodeFile(imageFile.getPath(), null);


        //--->Name Configuration
        NameTextConfiguration nameTextConfiguration = new NameTextConfiguration(context, name
        );
        nameTextConfiguration.setSize((int) (0.25 * height));
        nameTextConfiguration.setColor(Color.DKGRAY);
        nameTextConfiguration.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"));

        //--->Phone Configuration
        PhoneTextConfiguration phoneTextConfiguration = new PhoneTextConfiguration(context, phone);
        phoneTextConfiguration.setSize((int) (0.15 * height));
        phoneTextConfiguration.setColor(Color.DKGRAY);
        phoneTextConfiguration.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));

        //--->Email Configuration
        EmailTextConfiguration emailTextConfiguration = new EmailTextConfiguration(context, email);
        emailTextConfiguration.setSize((int) (0.15 * height));
        emailTextConfiguration.setColor(Color.DKGRAY);
        emailTextConfiguration.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));

        return paintUserInfoBitmap(width, height, userPic, nameTextConfiguration, phoneTextConfiguration, emailTextConfiguration);
    }

    private Bitmap getFooterInfoBitmap(Context context, int width, int height, String name, String phone, String email) {
        TextConfiguration textConfiguration = new TextConfiguration(context, "For other details,please contact me");
        textConfiguration.setSize((int) (0.15 * height));
        return paintFooterInfoBitMap(width, height, textConfiguration);
    }

    public Bitmap generateHalfSticker(Context context, Bitmap sourceBitmap, String name, String phone, String email) {
        int userFooterWidth = (int) (1.0 * sourceBitmap.getWidth());
        int footerHeight = (int) (0.15 * sourceBitmap.getHeight());
        return getUserInfoBitmap(context, userFooterWidth, footerHeight, name, phone, email);
    }


    public Bitmap generateFullSticker(Context context, Bitmap sourceBitmap, String name, String phone, String email, boolean shouldRemoveInfo) {
        int infoFooterWidth = (int) (0.45 * sourceBitmap.getWidth());
        int footerHeight = (int) (0.25 * sourceBitmap.getHeight());
        int userFooterWidth = (int) (0.55 * sourceBitmap.getWidth());

        Bitmap bmpUser = getUserInfoBitmap(context, userFooterWidth, footerHeight, name, phone, email);
        if (shouldRemoveInfo == false) {
            Bitmap bmpInfo = getFooterInfoBitmap(context, infoFooterWidth, footerHeight, name, phone, email);
            return imu.joinBitmap(bmpInfo, bmpUser, true);
        } else {
            return bmpUser;
        }

    }
}
