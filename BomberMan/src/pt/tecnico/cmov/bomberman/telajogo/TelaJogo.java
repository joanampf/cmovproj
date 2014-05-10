package pt.tecnico.cmov.bomberman.telajogo;

import java.util.ArrayList;

import pt.tecnico.cmov.bomberman.JogoActivity;
import pt.tecnico.cmov.bomberman.MainActivity;
import pt.tecnico.cmov.bomberman.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.Toast;

public class TelaJogo extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = TelaJogo.class.getSimpleName();

	public boolean controlo = false;
	private MainThread thread;

	public Bomberman bomber;
	
	public static Context cont;
	public Activity currentActv;
	public boolean onlineMode = false; 
	public boolean running = true;
	public Wall wall;
	public Obstaculo obstaculo;
	public Bomba bomba;
	ArrayList<Robot> robots = new ArrayList<Robot>();
	Tabuleiro tabuleiro = JogoActivity.tabuleiroInit;
	Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
    private final Object lock = new Object();
    
    int counter = 0;

	int num_robots = 0;

	public TelaJogo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);

		cont = context;
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
	}

	public TelaJogo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);

		cont = context;

		thread = new MainThread(getHolder(), this);
		setFocusable(true);
	}

	public TelaJogo(Context context) {
		super(context);

		// adding the callback (this) to the surface holder to intercept events

		getHolder().addCallback(this);

		cont = context;

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
		
		if(!onlineMode){
			new Thread(new Runnable() { 
				 public void run() { 
						GameLogic();
				 } 
				 }).start(); 
		}
	}

	public void GameLogic(){
		int velocidade = JogoActivity.nivel.getRobotSpeed();
		while(running){
			try {
				if (JogoActivity.tempoActivo) {
			        synchronized (lock) {

					for (int i = 0; i < num_robots; i++)
						tabuleiro.moveRobot(robots.get(i), bit);
			        }
					Thread.sleep(1000 * velocidade);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
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
        synchronized (lock) {

        tabuleiro = JogoActivity.tabuleiroInit;
		int coluna;
		int linha;
		robots = new ArrayList<Robot>();
		num_robots = 0;
		int num_linhas = tabuleiro.getNum_linhas();
		int num_colunas = tabuleiro.getNum_colunas();


		cv.drawColor(Color.parseColor("#33bb22"));
		


		for (linha = 0; linha < num_linhas; linha++) {
			for (coluna = 0; coluna < num_colunas; coluna++) {
				getHolder().addCallback(this);
				
				final int[] posicao = new int[2];
				posicao[0] = linha;
				posicao[1] = coluna;
				
				switch (tabuleiro.getTabuleiro(linha, coluna)) {
				case 'W':
					wall = new Wall(BitmapFactory.decodeResource(
							getResources(), R.drawable.wall), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					wall.draw(cv);
					break;
				case '1':
					// case '2':
					// case '3':
					bomber = new Bomberman(BitmapFactory.decodeResource(
							getResources(), R.drawable.bomberman), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					bomber.draw(cv);
					break;
				case 'R':
					// robots - andar aleatoriamente
					Robot robot;
					robot = new Robot(BitmapFactory.decodeResource(
							getResources(), R.drawable.robot), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					robots.add(robot);
					num_robots++;
					robot.draw(cv);

					break;
				case 'O':
					obstaculo = new Obstaculo(BitmapFactory.decodeResource(
							getResources(), R.drawable.obstaculo), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					obstaculo.draw(cv);
					break;
				case 'B':
					

					bomba = new Bomba(BitmapFactory.decodeResource(
							getResources(), R.drawable.bomba), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					bomba.draw(cv);
					if(!onlineMode){
						new Thread(new Runnable() { 
							 public void run() { 
									try {
										bomba.explode(posicao);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
							 } 
							 }).start(); 
					}
					break;

				case 'E':
					bomba = new Bomba(BitmapFactory.decodeResource(
							getResources(), R.drawable.fogo), coluna
							* bit.getWidth() + bit.getWidth() / 2, linha
							* bit.getHeight() + bit.getHeight() / 2);
					bomba.draw(cv);
					/*new Thread(new Runnable() { 
						 public void run() { 
								try {
									bomba.acabaExplosao(posicao);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
						 } 
						 }).start(); 
					break;
					 */
				}
			}
		}
		controlo = true;
		
		
		// ve se o player está morto
        int []playerPos = tabuleiro.getPosicao('1');
        
        if (playerPos == null){
        	try {
				running = false;
				currentActv.runOnUiThread(new Runnable() {
				    public void run() {
				    	Toast toast = Toast.makeText(cont, "  Game Over\nFinal Score: " + JogoActivity.score, 20000);
				    	toast.setGravity(Gravity.CENTER, 0, 0);
				    	toast.show();
				    }
				});
				Thread.sleep(2000);
				Intent intent = new Intent(cont, MainActivity.class);
				cont.startActivity(intent);
				System.out.println("finishing current activity");
				currentActv.finish();
				thread.running = false;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

        }
        }
	}
	// robots[0].moveRobot();

	// Paint p = new Paint();
	// p.setColor(Color.GREEN);
	// p.setStrokeWidth(5);
	// cv.drawLine(20, 0, 20, cv.getHeight(), p);
	// cv.drawBitmap(BitmapFactory.decodeResource(getResources(),
	// R.drawable.bomberman), 70, 20, null);
	
}
