package com.chroman.textureviewdemo;

/**
 * Created by chroman on 22/08/13.
 */
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.Surface;
import android.graphics.SurfaceTexture;
import android.view.View;
import android.widget.FrameLayout;
import android.view.Gravity;

public class VideoActivity extends Activity implements TextureView.SurfaceTextureListener, View.OnTouchListener {

    private TextureView textureView;

    private static final String VIDEO_URL = "http://www.808.dk/pics/video/gizmo.mp4";
    private MediaPlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = new MediaPlayer();
        try {
            player.setDataSource(this, Uri.parse(VIDEO_URL));
            player.prepare();
            player.setLooping(true);
        } catch (Exception e){}

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
        textureView.setLayoutParams(new FrameLayout.LayoutParams(480, 320, Gravity.CENTER));
        player.setSurface(new Surface(surface));
        player.start();
        textureView.setScaleX(0.5f);
        textureView.setScaleY(0.5f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {}

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        player.stop();
        player.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface){}
}
