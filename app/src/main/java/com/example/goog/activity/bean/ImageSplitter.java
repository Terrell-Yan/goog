package com.example.goog.activity.bean;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeanM on 2017/4/13.
 */

public class ImageSplitter {
    /**
     * 将图片切成 , piece *piece
     *
     * @param bitmap
     * @param piece
     * @return
     */
    public static List<ImagePiece> split(Bitmap bitmap, int piece)
    {
        List<ImagePiece> pieces = new ArrayList<ImagePiece>(piece * piece);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//            Log.e("TAG", "bitmap Width = " + width + " , height = " + height);
        int pieceWidth = Math.min(width, height) / piece;
        for (int i = 0; i < piece; i++)
        {
            for (int j = 0; j < piece; j++)
            {
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.index = j + i * piece;
                int xValue = j * pieceWidth;
                int yValue = i * pieceWidth;

                imagePiece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceWidth);
                pieces.add(imagePiece);
            }
        }
        return pieces;
    }
}
