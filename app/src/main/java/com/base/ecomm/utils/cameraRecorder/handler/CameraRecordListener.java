package com.base.ecomm.utils.cameraRecorder.handler;

/**
 * Created by sudamasayuki on 2018/03/13.
 */

public interface CameraRecordListener {

    void onGetFlashSupport(boolean flashSupport);

    void onRecordComplete();

    void onRecordStart();

    void onError(Exception exception);

    void onCameraThreadFinish();

}
