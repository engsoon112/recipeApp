package com.base.ecomm.utils.cameraRecorder;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLException;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;

import com.base.ecomm.BaseActivity;
import com.base.ecomm.MyApplication;
import com.base.ecomm.utils.cameraRecorder.handler.CameraRecordListener;
import com.base.ecomm.utils.cameraRecorder.handler.CameraRecorder;
import com.base.ecomm.utils.cameraRecorder.handler.CameraRecorderBuilder;
import com.base.ecomm.utils.cameraRecorder.handler.LensFacing;
import com.base.ecomm.utils.cameraRecorder.widget.Filters;
import com.base.ecomm.utils.cameraRecorder.widget.SampleGLView;
import com.jalanjalan.makan.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;


// https://github.com/smartmobilefactory/android-recording
abstract public class BaseCameraActivity extends BaseActivity {

    public FrameLayout cameraView;

    private SampleGLView sampleGLView;
    private AlertDialog filterDialog;
    private boolean toggleClick = false;

    protected CameraRecorder cameraRecorder;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected boolean isFlip = false;
    protected int cameraWidth = 480;
    protected int cameraHeight = 720;
    protected int videoWidth = 480;
    protected int videoHeight = 720;
    protected boolean isVideoRecord = false;
    protected String imagePath;
    protected String videoPath;


    protected void initCamera(FrameLayout _cameraView) {

        cameraView = _cameraView;
    }


    public void droidVideoRecord() {
        if (isVideoRecord) {
            cameraRecorder.stop();
        } else {
            videoPath = getVideoFilePath();
            cameraRecorder.start(videoPath);
        }
        isVideoRecord = !isVideoRecord;
    }

    public void droidFlash() {
        if (cameraRecorder != null && cameraRecorder.isFlashSupport()) {
            cameraRecorder.switchFlashMode();
            cameraRecorder.changeAutoFocus();
        }
    }

    public void droidSwitchCamera() {
        releaseCamera();
        if (lensFacing == LensFacing.BACK) {
            lensFacing = LensFacing.FRONT;
            isFlip = true;
        } else {
            lensFacing = LensFacing.BACK;
            isFlip = false;
        }
        toggleClick = true;
    }

    public void droidImageFilter(View v) {
        if (filterDialog == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Choose a filter");
            builder.setOnDismissListener(dialog -> {
                filterDialog = null;
            });

            final Filters[] filters = Filters.values();
            CharSequence[] charList = new CharSequence[filters.length];
            for (int i = 0, n = filters.length; i < n; i++) {
                charList[i] = filters[i].name();
            }
            builder.setItems(charList, (dialog, item) -> {
                changeFilter(filters[item]);
            });
            filterDialog = builder.show();
        } else {
            filterDialog.dismiss();
        }
    }

    public void droidImageCapture() {
        captureBitmap(bitmap -> {
            new Handler().post(() -> {
                imagePath = getImageFilePath();
                saveImage(bitmap, imagePath);
                exportPngToGallery(getApplicationContext(), imagePath);
            });
        });

    }

    public void droidCaptureAnimation(View v) {

        v.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {

            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blinking));
            v.setVisibility(View.GONE);

        }, 50);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }


    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (cameraRecorder != null) {
            cameraRecorder.stop();
            cameraRecorder.release();
            cameraRecorder = null;
        }

        if (sampleGLView != null) {
            if (cameraView != null) {
                cameraView.removeView(sampleGLView);
            }
            sampleGLView = null;
        }
    }

    private void setUpCameraView() {
        runOnUiThread(() -> {

            if (cameraView != null) {
                cameraView.removeAllViews();

                sampleGLView = null;
                sampleGLView = new SampleGLView(getApplicationContext());
                sampleGLView.setTouchListener((event, width, height) -> {
                    if (cameraRecorder != null) {
                        cameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
                    }
                });
                cameraView.addView(sampleGLView);
            }
        });
    }


    private void setUpCamera() {
        setUpCameraView();

        cameraRecorder = new CameraRecorderBuilder(this, sampleGLView)
                //.recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(boolean flashSupport) {
                    }

                    @Override
                    public void onRecordComplete() {
                        exportMp4ToGallery(getApplicationContext(), videoPath);
                    }

                    @Override
                    public void onRecordStart() {
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("CameraRecorder", exception.toString());
                    }

                    @Override
                    public void onCameraThreadFinish() {
                        if (toggleClick) {
                            runOnUiThread(() -> {
                                setUpCamera();
                            });
                        }
                        toggleClick = false;
                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .flipHorizontal(isFlip)
                .build();


    }

    private void changeFilter(Filters filters) {
        cameraRecorder.setFilter(Filters.getFilterInstance(filters, getApplicationContext()));
    }


    private interface BitmapReadyCallbacks {
        void onBitmapReady(Bitmap bitmap);
    }

    private void captureBitmap(final BitmapReadyCallbacks bitmapReadyCallbacks) {
        sampleGLView.queueEvent(() -> {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();
            Bitmap snapshotBitmap = createBitmapFromGLSurface(sampleGLView.getMeasuredWidth(), sampleGLView.getMeasuredHeight(), gl);

            runOnUiThread(() -> {
                bitmapReadyCallbacks.onBitmapReady(snapshotBitmap);
            });
        });
    }

    private Bitmap createBitmapFromGLSurface(int w, int h, GL10 gl) {

        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2, texturePixel, blue, red, pixel;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    texturePixel = bitmapBuffer[offset1 + j];
                    blue = (texturePixel >> 16) & 0xff;
                    red = (texturePixel << 16) & 0x00ff0000;
                    pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e("CreateBitmap", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }

    public void saveImage(Bitmap bitmap, String filePath) {
        try {

            File file = new File(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outStream);
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void exportMp4ToGallery(Context context, String filePath) {
        final ContentValues values = new ContentValues(2);
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, filePath);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
        } else {
            context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
        }

    }

    public static File getAndroidMoviesFolder() {
        // return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File saveFolder = new File(MyApplication.contextApp.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + File.separator + MyApplication.contextApp.getString(R.string.app_name));
        if (!saveFolder.exists()) saveFolder.mkdir();
        return saveFolder;
    }

    public static String getVideoFilePath() {
        return getAndroidMoviesFolder().getAbsolutePath() + "/"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
    }


    private static void exportPngToGallery(Context context, String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static File getAndroidImageFolder() {
        // return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File saveFolder = new File(MyApplication.contextApp.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + File.separator + MyApplication.contextApp.getString(R.string.app_name));
        if (!saveFolder.exists()) saveFolder.mkdir();
        return saveFolder;
    }

    public static String getImageFilePath() {
        return getAndroidImageFolder().getAbsolutePath() + "/"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpeg";
    }


}
