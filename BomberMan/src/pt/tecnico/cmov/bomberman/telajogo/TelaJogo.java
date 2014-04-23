package pt.tecnico.cmov.bomberman.telajogo;

import pt.tecnico.cmov.bomberman.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TelaJogo extends SurfaceView implements SurfaceHolder.Callback{

	private static final String TAG = TelaJogo.class.getSimpleName();


	private MainThread thread;

	private Bomberman droid;


	public TelaJogo(Context context) {
		super(context);


		// adding the callback (this) to the surface holder to intercept events

		getHolder().addCallback(this);

		droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 50, 50);

		thread = new MainThread(getHolder(), this);

		setFocusable(true);

	}

	@Override

	public void surfaceChanged(SurfaceHolder holder, int format, int width,

			int height) {
	}
	@Override

	public void surfaceCreated(SurfaceHolder holder) {

		// we can safely start the game loop

		thread.setRunning(true);

		thread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		Log.d(TAG, "Surface is being destroyed");

		// tell the thread to shut down and wait for it to finish

		// this is a clean shutdown

		boolean retry = true;

		while (retry) {

			try {

				thread.join();

				retry = false;

			} catch (InterruptedException e) {

				// try again shutting down the thread

			}

		}

		Log.d(TAG, "Thread was shut down cleanly");

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			// delegating event handling to the droid

			droid.handleActionDown((int)event.getX(), (int)event.getY());

			// check if in the lower part of the screen we exit

			if (event.getY() > getHeight() - 50) {

				thread.setRunning(false);

				((Activity)getContext()).finish();

			} else {

				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());

			}

		} if (event.getAction() == MotionEvent.ACTION_MOVE) {

			// the gestures
			if (droid.isTouched()) {

				// the droid was picked up and is being dragged

				droid.setX((int)event.getX());

				droid.setY((int)event.getY());

			}

		} if (event.getAction() == MotionEvent.ACTION_UP) {

			// touch was released

			if (droid.isTouched()) {

				droid.setTouched(false);

			}

		}

		return true;

	}


	@Override
	protected void onDraw(Canvas cv) {
		cv.drawColor(Color.WHITE);
		//        Paint p = new Paint();
		//        p.setColor(Color.GREEN);
		//        p.setStrokeWidth(5);
		//        cv.drawLine(20, 0, 20, cv.getHeight(), p);
		//        cv.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 70, 20, null);


	}


}
