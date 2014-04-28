package pt.tecnico.cmov.bomberman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import pt.tecnico.cmov.bomberman.telajogo.Tabuleiro;
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
	public static Tabuleiro tabuleiroInit;



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
		BufferedReader br_aux = new BufferedReader(new InputStreamReader(in));
		BufferedReader br  = new BufferedReader(new InputStreamReader(in));
		
		
		
		int num_colunas=0;
		int num_linhas=0;

		try {
			StringBuilder sb = new StringBuilder();
			String line = br_aux.readLine();
			//br.aux para contar o numero de linhas, de forma a determinar as correspondentes ao tabuleiro
			while (line != null) {
				num_linhas++;
				line = br_aux.readLine();
			}
			
			in = JogoActivity.class.getResourceAsStream("gridLayout.txt");
			br = new BufferedReader(new InputStreamReader(in));
			
			line = br.readLine();
			num_linhas--;
			
			String name = line;
			line = br.readLine();
			num_linhas--;
			Integer duracao = Integer.valueOf(line);
			line = br.readLine();
			num_linhas--;
			Integer timeoutExplosao = Integer.valueOf(line);
			line = br.readLine();
			num_linhas--;
			Integer duracaoExplosao = Integer.valueOf(line);
			line = br.readLine();
			num_linhas--;
			Integer rangeExplosao = Integer.valueOf(line);
			line = br.readLine();
			num_linhas--;
			Integer velocidadeRobot = Integer.valueOf(line);
			line = br.readLine();
			num_linhas--;
			Integer pontosRobot = Integer.valueOf(line);
			line = br.readLine();
			num_linhas--;
			Integer pontosRival = Integer.valueOf(line);
			line=br.readLine();
			
			num_colunas = line.length();
			tabuleiroInit=new Tabuleiro(num_linhas, num_colunas);

			int coluna;
			int linha = 0;

			while (line != null) {
				coluna=0;
				sb.append(line);
				sb.append(System.lineSeparator());

				//numero colunas


				for(coluna=0;coluna<line.length();coluna++){

					tabuleiroInit.tabuleiro[linha][coluna]=line.charAt(coluna);
				}
//				System.out.println("tabuleiro na linha"+linha+": "+tabuleiroInit[linha][0]);
				linha++;
				line = br.readLine();

			}
			String filepath = sb.toString();
			nivel = new Nivel(name, duracao, timeoutExplosao, duracaoExplosao, rangeExplosao, velocidadeRobot, pontosRobot, pontosRival, filepath);
			return nivel;
		} finally {
			
			br.close();
			br_aux.close();
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
//		tj.bomber.moveUp(tj.bomber.getY(),tj.bomber.getBitmap().getHeight());
		tj.bomber.moveUp();
		//		tj.robot.moveUp(tj.robot.getY(), tj.robot.getBitmap().getHeight());
	}

	public void moveDown(View v){
		tj = (TelaJogo) findViewById(R.id.telajogo);
		tj.bomber.moveDown();
		//		tj.robot.moveDown(tj.robot.getY(),tj.getHeight(), tj.robot.getBitmap().getHeight());
	}

	public void moveLeft(View v){
		tj = (TelaJogo) findViewById(R.id.telajogo);
		tj.bomber.moveLeft();
		//		tj.robot.moveLeft(tj.robot.getX(), tj.robot.getBitmap().getWidth());
	}

	public void moveRight(View v){
		tj = (TelaJogo) findViewById(R.id.telajogo);
		tj.bomber.moveRight();
		//		tj.robot.moveRight(tj.robot.getX(),tj.getWidth(), tj.robot.getBitmap().getWidth());
	}

}
