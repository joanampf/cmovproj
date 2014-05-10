package pt.tecnico.cmov.bomberman.telajogo;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
	
	private static final String TAG = MainThread.class.getSimpleName();

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private TelaJogo gamePanel;

	// flag to hold game state 
	public boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	public MainThread(SurfaceHolder surfaceHolder, TelaJogo gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@SuppressLint("WrongCall")
	public void run() {

		Canvas canvas;
		final String TAG = TelaJogo.class.getSimpleName();
		
		Log.d(TAG, "Starting game loop");

		while (running) {

			canvas = null;

			try {

				canvas = this.surfaceHolder.lockCanvas();

				synchronized (surfaceHolder) {

					this.gamePanel.onDraw(canvas);

				}

			} finally {

				if (canvas != null) {

					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			} // end finally

		}

	}

}
