package utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by layer on 26/1/2558.
 */
public class BitmapUtils {

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String path,int[] size) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, size[0], size[1]);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap resizeBitmap(String filePath, int REQUIRED_SIZE) {
        Bitmap bitmap;
        // bmp=BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

        try {

            if (filePath != null) {
                // bitmap = decodeFile(filePath);
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, o);


                // Find the correct scale value. It should be the power of 2.
                float width_tmp = o.outWidth, height_tmp = o.outHeight;
                int width = 0, height = 0;
                if (width_tmp > REQUIRED_SIZE || height_tmp > REQUIRED_SIZE) {
                    if (width_tmp > height_tmp) {
                        float scale = width_tmp / REQUIRED_SIZE;
                        width = REQUIRED_SIZE;
                        height = Math.round(height_tmp / scale);
                        Log.d("scale", "[" + String.valueOf(scale) + "]");
                        Log.d("height", height_tmp + " / " + scale + "= ["
                                + String.valueOf(height) + "]");
                        Log.d("width", "[" + String.valueOf(width) + "]");
                        Log.d("type", "width_tmp > height_tmp");
                    } else {
                        float scale = height_tmp / REQUIRED_SIZE;
                        width = Math.round(width_tmp / scale);
                        height = REQUIRED_SIZE;
                        Log.d("height", "[" + String.valueOf(height) + "]");
                        Log.d("width", width_tmp + " / " + scale + "= ["
                                + String.valueOf(width) + "]");
                        Log.d("type", "width_tmp < height_tmp");
                    }
                } else {
                    width = o.outWidth;
                    height = o.outHeight;
                }

                Bitmap bm = BitmapFactory.decodeFile(filePath);
                bitmap = Bitmap.createScaledBitmap(bm, width, height, false);
                bm = null;
                // bm.recycle();
            } else {
                bitmap = null;

            }
            return bitmap;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
            bitmap = null;
            return bitmap;
        }
    }

    public static void resizeImage(String originPath, String savePath,int size) {
//        int[] bmSize = {1800,1200};
        Bitmap resizeBitmap = BitmapUtils.resizeBitmap(originPath, size);
        File file = new File(savePath);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void prescaledbitmap(String filename, Context context,int maxSize) {
        try { // Facebook image size
            final int IMAGE_MAX_SIZE = maxSize;

            File file = null;
            FileInputStream fis;

            BitmapFactory.Options opts;
            int resizeScale;
            Bitmap bmp = null;

            file = new File(filename);

            // This bit determines only the width/height of the bitmap without loading the contents
            opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;

            fis = new FileInputStream(file);

            BitmapFactory.decodeStream(fis, null, opts);
            try {
                fis.close();

                // Find the correct scale value. It should be a power of 2
                resizeScale = 1;

                if (opts.outHeight > IMAGE_MAX_SIZE || opts.outWidth > IMAGE_MAX_SIZE) {
                    resizeScale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(opts.outHeight, opts.outWidth)) / Math.log(0.5)));
                }

                // Load pre-scaled bitmap
                opts = new BitmapFactory.Options();
                opts.inSampleSize = resizeScale;
                fis = new FileInputStream(file);

                //need rotation?
                float rotation = rotationForImage(context, Uri.fromFile(file));

                if (rotation != 0) {
                    //rotate
                    Matrix matrix = new Matrix();
                    matrix.preRotate(rotation);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis, null, opts);
                    bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                } else {
                    //use the original
                    bmp = BitmapFactory.decodeStream(fis, null, opts);
                }


                File saveFile = new File(filename);
                FileOutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(saveFile);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //return bmp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //return null;
        }
    }

    public static float rotationForImage(Context context, Uri uri) {
        try {
            if (uri.getScheme().equals("content")) {
                String[] projection = { MediaStore.Images.ImageColumns.ORIENTATION };
                Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToFirst()) {
                    return c.getInt(0);
                }
            } else if (uri.getScheme().equals("file")) {
                ExifInterface exif = new ExifInterface(uri.getPath());
                int rotation = (int) exifOrientationToDegrees(exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL));
                return rotation;
            }
            return 0;

        } catch (IOException e) {
//	        Log.e("", "Error checking exif", e);
            return 0;
        }
    }

    private static float exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
}
