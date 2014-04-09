package pt.tecnico.cmov.bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JogoActivity extends Activity {
	
	public CountDownTimer clock;
	public Boolean isPaused=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogo);
		Intent intent = getIntent();
		String nome = intent.getStringExtra(MainActivity.NOME);
		TextView tx = (TextView) findViewById(R.id.playerName);
//		TextView tx = new TextView(this);
		tx.setText(nome);
		init();
	}
	
	public void init(){
		TextView tx = (TextView) findViewById(R.id.playerScore);
		tx.setText("0");
		int a=0;
		final TextView tx1 = (TextView) findViewById(R.id.timeLeft);
		
		clock = new CountDownTimer(30000, 1000) {

		     public void onTick(long millisUntilFinished) {
		         tx1.setText(""+ millisUntilFinished / 1000);
		     }

		     public void onFinish() {
		         tx1.setText("done!");
		     }
		  }.start();
		  
	}
	//comentario

	
	public void pausePlay(View v) throws InterruptedException{
		Button b = (Button)findViewById(R.id.pauseplay);
		if(!isPaused){
			clock.cancel();
			b.setText("Resume");
			isPaused=true;
			
		}else{
			// poe o contador no inicio
			// TA MAL
			// comment
			clock.start();
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
