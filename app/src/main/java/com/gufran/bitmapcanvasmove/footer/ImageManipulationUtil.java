package com.gufran.bitmapcanvasmove.footer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * Created by gufran on 8/2/16.
 */
public class ImageManipulationUtil {


    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }


    public static Bitmap tintBitmap(Bitmap bitmap, int color) {
        Paint paint = new Paint(color);
        ColorFilter filter = new LightingColorFilter(color, 1);
        paint.setColorFilter(filter);
        //paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        Bitmap bitmapResult = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapResult);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmapResult;
    }

    public Bitmap joinBitmap(Bitmap footerInfo, Bitmap footerUser, boolean isJoinHorizontally) {
        Bitmap joinedBitmap = null;

        int w;
        int h;
        if (isJoinHorizontally == true) {
            w = footerInfo.getWidth() + footerUser.getWidth();
            if (footerInfo.getHeight() >= footerUser.getHeight()) {
                h = footerInfo.getHeight();
            } else {
                h = footerUser.getHeight();
            }
        } else {
            h = footerInfo.getHeight() + footerUser.getHeight();
            if (footerInfo.getWidth() >= footerUser.getWidth()) {
                w = footerInfo.getWidth();
            } else {
                w = footerUser.getWidth();
            }
        }

        Bitmap.Config config = footerUser.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }

        joinedBitmap = Bitmap.createBitmap(w, h, config);
        Canvas newCanvas = new Canvas(joinedBitmap);

        newCanvas.drawBitmap(footerInfo, 0, 0, null);
        if (isJoinHorizontally == true) {
            newCanvas.drawBitmap(footerUser, footerInfo.getWidth(), 0, null);
        } else {
            newCanvas.drawBitmap(footerUser, 0, footerInfo.getHeight(), null);
        }
        return joinedBitmap;
    }
}
