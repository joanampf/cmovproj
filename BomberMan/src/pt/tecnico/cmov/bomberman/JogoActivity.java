package pt.tecnico.cmov.bomberman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class JogoActivity extends Activity {

	public CountDownTimer clock;
	private TextView tx1;
	public Boolean isPaused=false;
	public Nivel nivel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogo);
		Intent intent = getIntent();
		String nome = intent.getStringExtra(MainActivity.NOME);
		TextView tx = (TextView) findViewById(R.id.playerName);
		tx.setText(nome);

		// ****************************
		// parametros lidos de ficheiro
		// falta grid layout
		//		String name = "Facil";
		//		Integer duracao = 50;
		//		Integer timeoutExplosao = 7;
		//		Integer duracaoExplosao = 2;
		//		Integer rangeExplosao = 2;
		//		Integer velocidadeRobot = 1;
		//		Integer pontosRobot = 2;
		//		Integer pontosRival = 2;
		//		String filepath = "caca";



		try {
			readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// parametros lidos de ficheiro
		// ****************************


		init(nivel);
	}

	public Nivel readFile() throws IOException
	{
		InputStream in = JogoActivity.class.getResourceAsStream("gridLayout.txt");

		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			StringBuilder sb = new StringBuilder();

			String line = br.readLine();

			String name = line;
			line = br.readLine();
			Integer duracao = Integer.valueOf(line);
			line = br.readLine();
			Integer timeoutExplosao = Integer.valueOf(line);
			line = br.readLine();
			Integer duracaoExplosao = Integer.valueOf(line);
			line = br.readLine();
			Integer rangeExplosao = Integer.valueOf(line);
			line = br.readLine();
			Integer velocidadeRobot = Integer.valueOf(line);
			line = br.readLine();
			Integer pontosRobot = Integer.valueOf(line);
			line = br.readLine();
			Integer pontosRival = Integer.valueOf(line);
			line = br.readLine();


			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				System.out.println(line);
				//Inserir linha no tabuleiro
				//desenhar tabuleiro de jogo inicial


				line = br.readLine();

			}
			String filepath = sb.toString();
			//			System.out.println("grid: "+filepath);
			nivel = new Nivel(name, duracao, timeoutExplosao, duracaoExplosao, rangeExplosao, velocidadeRobot, pontosRobot, pontosRival, filepath);
			return nivel;
		} finally {
			br.close();
		}
	}


	public void init(Nivel n){
		TextView tx = (TextView) findViewById(R.id.playerScore);
		tx.setText("0");


		initTime(n.getGameDuration());

	}

	// inicializar temporizador
	public void initTime(Integer tempo){


		tx1 = (TextView) findViewById(R.id.timeLeft);
		clock = new CountDownTimer((long)tempo*1000, 1000) {

			public void onTick(long millisUntilFinished) {
				tx1.setText(""+ millisUntilFinished / 1000);

			}

			public void onFinish() {
				tx1.setText("done!");
			}
		}.start();

	}

	public void pausePlay(View v) throws InterruptedException{
		Button b = (Button)findViewById(R.id.pauseplay);
		String tempoRestante = tx1.getText().toString();
		if(!isPaused){
			clock.cancel();
			//			comentario
			b.setText("Resume");
			isPaused=true;

		}else{
			// poe o contador no inicio
			initTime(Integer.parseInt(tempoRestante));
			b.setText("Pause");
			isPaused=false;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogo, menu);
		return true;
	}

}
