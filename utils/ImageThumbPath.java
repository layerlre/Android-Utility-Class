package utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by layer on 4/23/2015.
 */
public class ImageThumbPath {


    public static String getImageThumbPath(Context context, Uri path) {

        try {
            Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(
                    context.getContentResolver(), ContentUris.parseId(path),
                    MediaStore.Images.Thumbnails.MINI_KIND,
                    null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));

            } else {
                Log.e("ImageThumbPath", "-----cursor MediaStore.Images.Thumbnails.queryMiniThumbnail is null");
                //setImageThumbRealUri(picturePathList.get(imgPosition), imgPosition);


            }
        } catch (Exception e) {
            Log.e("ImageThumbPath", e.getMessage());
            //setImageThumbRealUri(picturePathList.get(imgPosition), imgPosition);

        }

        return null;
    }

//    private void setImageThumbRealUri(Uri path, int imgPosition) {
//        Log.i("debug", "---- setImageThumbRealUri");
//        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapUtils.decodeSampledBitmapFromResource(path.getPath(), thumbSize), thumbSize[0], thumbSize[1]);
//        thumbList.get(imgPosition).setImageBitmap(ThumbImage);
//        thumbList.get(imgPosition).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View com.layer.android.supervision.view) {
//                thumbPopupMenu(com.layer.android.supervision.view);
//            }
//        });
//    }
//
//    private void setImageThumbRealUri(String path, int imgPosition) {
//        Log.i("debug", "---- setImageThumbRealUri");
//        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapUtils.decodeSampledBitmapFromResource(path, thumbSize), thumbSize[0], thumbSize[1]);
//        thumbList.get(imgPosition).setImageBitmap(ThumbImage);
//        thumbList.get(imgPosition).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View com.layer.android.supervision.view) {
//                thumbPopupMenu(com.layer.android.supervision.view);
//            }
//        });
//    }
//
//
//    private void setVideoThumb() {
//        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoPath,
//                MediaStore.Images.Thumbnails.MINI_KIND);
//        thumbList.get(2).setImageBitmap(thumb);
//        thumbList.get(2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View com.layer.android.supervision.view) {
//                thumbPopupMenu(com.layer.android.supervision.view);
//            }
//        });
//    }


    }
