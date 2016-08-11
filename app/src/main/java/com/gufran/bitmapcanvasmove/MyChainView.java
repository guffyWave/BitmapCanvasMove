package com.gufran.bitmapcanvasmove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import com.gufran.bitmapcanvasmove.footer.ImageManipulationUtil;
import com.gufran.bitmapcanvasmove.footer.StickerGeneratorUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by gufran on 8/10/16.
 */
public class MyChainView extends View {
    Bitmap baseBitmap, stickerBitmap;
    Context context;
    float x = 0;
    float y = 0;

    String name = "Gufran Khurshid";
    String phone = "+91 7042935653";
    String email = "gufran.khurshid@okutech.in";

    StickerGeneratorUtil stickerGeneratorUtil;

    public MyChainView(Context context, Bitmap baseBitmap) {
        super(context);
        this.context = context;
        this.baseBitmap = baseBitmap;
        setBackgroundColor(Color.TRANSPARENT);
        stickerGeneratorUtil = new StickerGeneratorUtil();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        baseBitmap = ImageManipulationUtil.scaleDown(baseBitmap, getMeasuredWidth(), true);
        stickerBitmap = stickerGeneratorUtil.generateFullSticker(context, baseBitmap, name, phone, email);

        //position the sticker at bottom of the bitmap
        x = 0 + stickerBitmap.getWidth() / 2; // adding the centered padding
        y = baseBitmap.getHeight() + stickerBitmap.getHeight() / 2; // adding the centered padding
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(baseBitmap, 0, 0, null);
        canvas.drawBitmap(stickerBitmap, x - stickerBitmap.getWidth() / 2, y - stickerBitmap.getHeight() / 2, null);
    }


    public Bitmap saveSnapshot() {

        int printableWidth = baseBitmap.getWidth();
        int printableHeight = baseBitmap.getHeight();

        if (y >= baseBitmap.getHeight()) {
            printableHeight = baseBitmap.getHeight() + stickerBitmap.getHeight();
        }

        Bitmap bitmap = Bitmap.createBitmap(printableWidth, printableHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);

        File file = new File(Environment.getExternalStorageDirectory() + "/sign.png");

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
