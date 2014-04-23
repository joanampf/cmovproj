package pt.tecnico.cmov.bomberman.telajogo;

import android.graphics.Canvas;
import android.util.Log;

public class MainThread extends Thread{

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
