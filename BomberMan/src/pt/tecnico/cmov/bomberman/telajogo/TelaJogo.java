package pt.tecnico.cmov.bomberman.telajogo;

import pt.tecnico.cmov.bomberman.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TelaJogo extends SurfaceView implements SurfaceHolder.Callback{

	private static final String TAG = TelaJogo.class.getSimpleName();


	private MainThread thread;

	public Bomberman bomber;//=new Bomberman (BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 50, 50);
	public Robot robot;//= new Robot(BitmapFactory.decodeResource(getResources(), R.drawable.mariobot),100,100);

//	public Button moveleft = (Button) findViewById(R.id.botaoesq);
//	
//	public Button movedown = (Button) findViewById(R.id.botaobaixo);
//	public Button moveright = (Button) findViewById(R.id.botaodir);

	public TelaJogo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);

		bomber = new Bomberman (BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 50, 50);
		robot = new Robot (BitmapFactory.decodeResource(getResources(), R.drawable.mariobot), 100, 100);
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
//		moveup.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				droid.moveUp(droid.getX(), droid.getY());
//			}
//		});
//
//		movedown.setOnClickListener(moveDown);
//
//		moveleft.setOnClickListener(moveLeft);
//
//		moveright.setOnClickListener(moveRight);

		

	}

	public TelaJogo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);

		bomber = new Bomberman (BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 50, 50);
		robot = new Robot (BitmapFactory.decodeResource(getResources(), R.drawable.mariobot), 100, 100);
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
//		moveup.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				droid.moveUp(droid.getX(), droid.getY());
//			}
//		});
//
//		movedown.setOnClickListener(moveDown);
//
//		moveleft.setOnClickListener(moveLeft);
//
//		moveright.setOnClickListener(moveRight);
		

	}

	public TelaJogo(Context context) {
		super(context);


		// adding the callback (this) to the surface holder to intercept events

		getHolder().addCallback(this);

		bomber = new Bomberman (BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 50, 50);
		robot = new Robot (BitmapFactory.decodeResource(getResources(), R.drawable.mariobot), 100, 100);
		thread = new MainThread(getHolder(), this);
		setFocusable(true);

	}


//	View.OnClickListener moveDown = new View.OnClickListener() {
//		public void onClick(View v) {
//			// it was the 1st button
//		}
//	};
//	View.OnClickListener moveLeft = new View.OnClickListener() {
//		public void onClick(View v) {
//			// it was the 1st button
//		}
//	};
//	View.OnClickListener moveRight = new View.OnClickListener() {
//		public void onClick(View v) {
//			// it was the 1st button
//		}
//	};
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
	protected void onDraw(Canvas cv) {
		cv.drawColor(Color.parseColor("#33bb22"));
		bomber.draw(cv);
		robot.draw(cv);
//		robot.moveRobot(robot.getX(), robot.getY(), robot.getBitmap().getHeight(), robot.getBitmap().getWidth(), this.getHeight(), this.getWidth());
		//        Paint p = new Paint();
		//        p.setColor(Color.GREEN);
		//        p.setStrokeWidth(5);
		//        cv.drawLine(20, 0, 20, cv.getHeight(), p);
		//        cv.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 70, 20, null);


	}


}
