package pt.tecnico.cmov.bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import pt.tecnico.cmov.bomberman.MainActivity;
import pt.tecnico.cmov.bomberman.Nivel;

public class JogoActivity extends Activity {


	public CountDownTimer clock;
	private TextView tx1;
	public Boolean isPaused=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogo);
		Intent intent = getIntent();

		String nome = intent.getStringExtra(MainActivity.NOME);
		TextView tx = (TextView) findViewById(R.id.playerName);
		tx.setText(nome);

		//		String tempo = intent.getStringExtra(MainActivity.NOME);
		//		TextView txTempo = (TextView) findViewById(R.id.timeLeft);
		//		txTempo.setText(tempo);
		Integer duracao = 50;
//		Integer timeoutExplosao = 7;
//		Nivel nivelFacil = new Nivel(duracao, timeoutExplosao);
		
//		String tempo = intent.getStringExtra(MainActivity.TEMPO);
//		tx = (TextView) findViewById(R.id.timeLeft);
//		tx.setText(tempo);
//		Integer duracao = Integer.parseInt(tempo);
		init(duracao);
	}



	public void init(Integer tempo){
		TextView tx = (TextView) findViewById(R.id.playerScore);
		tx.setText("0");

		//Integer tempo = 30;
		initTime(tempo);

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
