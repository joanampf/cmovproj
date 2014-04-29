package pt.tecnico.cmov.bomberman.telajogo;

import pt.tecnico.cmov.bomberman.JogoActivity;
import pt.tecnico.cmov.bomberman.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TelaJogo extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = TelaJogo.class.getSimpleName();

	public boolean controlo = false;
	private MainThread thread;

	public Bomberman bomber;
	public Robot robot;
	public Wall wall;
	public Obstaculo obstaculo;

	public TelaJogo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);

		thread = new MainThread(getHolder(), this);
		setFocusable(true);
	}

	public TelaJogo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);

		thread = new MainThread(getHolder(), this);
		setFocusable(true);
	}

	public TelaJogo(Context context) {
		super(context);

		// adding the callback (this) to the surface holder to intercept events

		getHolder().addCallback(this);

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

	public void insereNoTabuleiro() {

	}


	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas cv) {

		Tabuleiro tabuleiro = JogoActivity.tabuleiroInit;
		int numero_robot=0; 
		int coluna;
		int linha;
		int num_linhas = tabuleiro.getNum_linhas();
		int num_colunas = tabuleiro.getNum_colunas();
		Bitmap bit = BitmapFactory.decodeResource(getResources(),
				R.drawable.wall);

		cv.drawColor(Color.parseColor("#33bb22"));

		for (linha = 0; linha < num_linhas; linha++) {
			for (coluna = 0; coluna < num_colunas; coluna++) {
				getHolder().addCallback(this);
				switch (tabuleiro.getTabuleiro(linha, coluna)) {
				case 'W':
					wall = new Wall(BitmapFactory.decodeResource(
							getResources(), R.drawable.wall), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					wall.draw(cv);
					break;
				case '1':
					//					case '2':
					//					case '3':
					bomber = new Bomberman(BitmapFactory.decodeResource(
							getResources(), R.drawable.bomberman), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					bomber.draw(cv);
					break;
				case 'R':
					//robots - andar aleatoriamente
					robot = new Robot(BitmapFactory.decodeResource(
							getResources(), R.drawable.robot), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					robot.draw(cv);
					break;
				case 'O':
					obstaculo = new Obstaculo(BitmapFactory.decodeResource(
							getResources(), R.drawable.obstaculo), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					break;
					
					//criar classe obstaculo
				}
			}
		}
		if (JogoActivity.tempoActivo)
			try {
				robot.moveRobot();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// Paint p = new Paint();
		// p.setColor(Color.GREEN);
		// p.setStrokeWidth(5);
		// cv.drawLine(20, 0, 20, cv.getHeight(), p);
		// cv.drawBitmap(BitmapFactory.decodeResource(getResources(),
		// R.drawable.bomberman), 70, 20, null);

	}

}
