package com.chroman.textureviewdemo;

/**
 * Created by chroman on 22/08/13.
 */
import android.os.Bundle;
import android.app.Activity;
import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.TextureView;
import android.graphics.SurfaceTexture;
import android.view.View;
import android.widget.FrameLayout;
import java.io.IOException;
import android.view.Gravity;

public class CameraActivity extends Activity implements TextureView.SurfaceTextureListener, View.OnTouchListener {

    private Camera camera;
    private TextureView textureView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_texture_view);
        textureView = (TextureView) findViewById(R.id.media);
        textureView.setSurfaceTextureListener(this);
        textureView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(v.getWidth(),  v.getHeight());
            params.setMargins((int)event.getRawX() - v.getWidth() / 2, (int)(event.getRawY() - v.getHeight() * 0.7), (int)event.getRawX() - v.getWidth() / 2, (int)(event.getRawY() - v.getHeight() * 0.7));
            v.setLayoutParams(params);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        camera = Camera.open();
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        textureView.setLayoutParams(new FrameLayout.LayoutParams(previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            camera.setPreviewTexture(surface);
            camera.startPreview();
            textureView.setScaleX(0.5f);
            textureView.setScaleY(0.5f);
            textureView.setRotation(90.0f);
        } catch (IOException e) {}
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {}

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        camera.stopPreview();
        camera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface){}
}
