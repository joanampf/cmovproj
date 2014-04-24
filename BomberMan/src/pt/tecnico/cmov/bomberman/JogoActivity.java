package pt.tecnico.cmov.bomberman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import pt.tecnico.cmov.bomberman.telajogo.TelaJogo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi")
public class JogoActivity extends Activity {

	public static boolean tempoActivo=true;
	public CountDownTimer clock;
	private TextView tx1;
	public Boolean isPaused=false;
	public Nivel nivel;

	// ta a dar erro, tentar encher matriz
	public char[][] tabuleiroInit=new char[30][30];
	
	
	
	public ImageButton moveleft ;
	public ImageButton moveup ;
	public ImageButton movedown ;
	public ImageButton moveright;
	public Button bomb;
	public Button pause;
	public TelaJogo tj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogo);
		Intent intent = getIntent();
		String nome = intent.getStringExtra(MainActivity.NOME);
		TextView tx = (TextView) findViewById(R.id.playerName);
		tx.setText(nome);

		pause = (Button)findViewById(R.id.pauseplay);
		moveleft = (ImageButton) findViewById(R.id.botaoesq);
		moveup = (ImageButton) findViewById(R.id.botaocima);
		movedown = (ImageButton) findViewById(R.id.botaobaixo);
		moveright = (ImageButton) findViewById(R.id.botaodir);
		bomb = (Button)findViewById(R.id.bomb);

		try {
			readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

			int coluna;
			int linha = 0;
			
			// line.lenght = no. colunas
			// ver lenght br = no. linhas
			while (line != null) {
				coluna=0;
				sb.append(line);
				sb.append(System.lineSeparator());
				
				
				for(coluna=0;coluna<line.length();coluna++){
					
					tabuleiroInit[linha][coluna]=line.charAt(coluna);
				}
				
				linha++;
				
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
				tempoActivo=false;
				movedown.setEnabled(false);
				moveright.setEnabled(false);
				moveup.setEnabled(false);
				moveleft.setEnabled(false);
				bomb.setEnabled(false);
				pause.setEnabled(false);
				tx1.setText("done!");
			}
		}.start();

	}

	public void pausePlay(View v) throws InterruptedException{
		System.out.println("pauseplay ******");
		String tempoRestante = tx1.getText().toString();

		if(!isPaused){
			clock.cancel();
			isPaused=true;
			pause.setText("Resume");
			tempoActivo=false;
			movedown.setEnabled(false);
			moveright.setEnabled(false);
			moveup.setEnabled(false);
			moveleft.setEnabled(false);
			bomb.setEnabled(false);
		}
		else{
			// poe o contador no inicio
			initTime(Integer.parseInt(tempoRestante));
			pause.setText("Pause");
			tempoActivo=true;
			movedown.setEnabled(true);
			moveright.setEnabled(true);
			moveup.setEnabled(true);
			moveleft.setEnabled(true);
			bomb.setEnabled(true);
			isPaused=false;

		}

	}

	//	Robot vai mexer aleatoriamente
	//	Fun�ao de chama movimentos aleatorios

	public void moveUp(View v){
		tj = (TelaJogo) findViewById(R.id.telajogo);
		System.out.println("Altura da imagem: "+tj.bomber.getBitmap().getHeight());
		tj.bomber.moveUp(tj.bomber.getY(),tj.bomber.getBitmap().getHeight());
		//		tj.robot.moveUp(tj.robot.getY(), tj.robot.getBitmap().getHeight());
	}

	public void moveDown(View v){
		tj = (TelaJogo) findViewById(R.id.telajogo);
		tj.bomber.moveDown(tj.bomber.getY(),tj.getHeight(),tj.bomber.getBitmap().getHeight());
		//		tj.robot.moveDown(tj.robot.getY(),tj.getHeight(), tj.robot.getBitmap().getHeight());
	}

	public void moveLeft(View v){
		tj = (TelaJogo) findViewById(R.id.telajogo);
		tj.bomber.moveLeft(tj.bomber.getX(),tj.bomber.getBitmap().getWidth());
		//		tj.robot.moveLeft(tj.robot.getX(), tj.robot.getBitmap().getWidth());
	}

	public void moveRight(View v){
		tj = (TelaJogo) findViewById(R.id.telajogo);
		tj.bomber.moveRight(tj.bomber.getX(),tj.getWidth(),tj.bomber.getBitmap().getWidth());
		//		tj.robot.moveRight(tj.robot.getX(),tj.getWidth(), tj.robot.getBitmap().getWidth());
	}

}
