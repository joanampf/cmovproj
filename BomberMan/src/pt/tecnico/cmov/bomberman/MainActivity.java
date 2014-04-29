package pt.tecnico.cmov.bomberman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final String NOME = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		
	}

	public void insereNomeJogador(View v){
		Intent intent = new Intent(this, JogoActivity.class);
		EditText editText = (EditText) findViewById(R.id.nomeJogador);
		String nome = editText.getText().toString();
		intent.putExtra(NOME, nome);
		startActivity(intent);
	}
	

}
