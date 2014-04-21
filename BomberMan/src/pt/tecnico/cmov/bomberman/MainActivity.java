package pt.tecnico.cmov.bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public Nivel nivelFacil;
	
	public static final String NOME = null;

	private static final String TEMPO = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

//	public String insereJogador(View v){
//		
//	}
	
	
	// jogador facil: tempo inicial=50; timeoutExplosao=7
	public void insereJogadorFacil(View v){
		
		Integer duracao = 50;
		Integer timeoutExplosao = 7;
		nivelFacil = new Nivel(duracao, timeoutExplosao);
		
		
		Intent intent = new Intent(this, JogoActivity.class);
		//inserir o nome do jogador
//		String nome = insereJogador(v);
		
		EditText editText = (EditText) findViewById(R.id.nomeJogador);
		String nome = editText.getText().toString();
		intent.putExtra(NOME, nome);
//		intent.putExtra(NOME, nivelFacil.getDuracaoJogo().toString());
		startActivity(intent);
		
		
		
		intent.putExtra(TEMPO, nivelFacil.getDuracaoJogo());
		startActivity(intent);
	}
	
//	public void insereJogadorMedio(View v){
//		Intent intent = new Intent(this, JogoActivity.class);
//		EditText editText = (EditText) findViewById(R.id.nomeJogador);
//		String nome = editText.getText().toString();
//		intent.putExtra(NOME, nome);
//		startActivity(intent);
//	}
//	
//	public void insereJogadorDificil(View v){
//		Intent intent = new Intent(this, JogoActivity.class);
//		EditText editText = (EditText) findViewById(R.id.nomeJogador);
//		String nome = editText.getText().toString();
//		intent.putExtra(NOME, nome);
//		startActivity(intent);
//	}

}
