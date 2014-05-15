package pt.tecnico.cmov.bomberman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.net.Socket;

import pt.tecnico.cmov.bomberman.telajogo.Tabuleiro;
import pt.tecnico.cmov.bomberman.telajogo.TelaJogo;
import shared.Request;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private TextView timeLeft;
	private TextView numberPlayers;
	private TextView playerScore;

	public Boolean isPaused=false;
	public static Nivel nivel =null;
	public static int score;
	public static char myPlayerId;

	// ta a dar erro, tentar encher matriz
	public static Tabuleiro tabuleiroInit = null;



	public ImageButton moveleft ;
	public ImageButton moveup ;
	public ImageButton movedown ;
	public ImageButton moveright;
	public Button bomb;
	public Button pause;
	public TelaJogo tj;

	private Socket client = null;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private InputStream inputStream;
	private boolean online = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogo);
		Intent intent = getIntent();
		String nome = intent.getStringExtra(MainActivity.NOME);
		online = intent.getExtras().getBoolean("online");
		TextView tx = (TextView) findViewById(R.id.playerName);
		tx.setText(nome);
		score = 0;
		pause = (Button)findViewById(R.id.pauseplay);
		moveleft = (ImageButton) findViewById(R.id.botaoesq);
		moveup = (ImageButton) findViewById(R.id.botaocima);
		movedown = (ImageButton) findViewById(R.id.botaobaixo);
		moveright = (ImageButton) findViewById(R.id.botaodir);
		bomb = (Button)findViewById(R.id.bomb);
		tj = (TelaJogo) findViewById(R.id.telajogo);
		tj.currentActv = this;
		tj.onlineMode = online;
		try {
			if(!online){
				readFile();
			}else{
				new Thread(new Runnable() { 
					public void run() { 
						ConnectToServer();
					} 
				}).start(); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(nivel == null){} // wait for nivel to be created

		init(nivel);
	}

	public void ConnectToServer(){
		if(client == null){
			try {
				client = new Socket("10.0.2.2", 4000);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// connect to the server and send the message
		try {
			System.out.println("sending new request");

			Request r = new Request('0', "NewGame" );

			outputStream = client.getOutputStream();  
			objectOutputStream = new ObjectOutputStream(outputStream);  
			objectOutputStream.writeObject(r);  

			inputStream = client.getInputStream();		                
			objectInputStream = new ObjectInputStream(inputStream); 
			myPlayerId = objectInputStream.readChar();
			tabuleiroInit = (Tabuleiro) objectInputStream.readObject();
			nivel = (Nivel) objectInputStream.readObject();
			System.out.println("My player Id is: " + myPlayerId);
			RecieveUpdates();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void RecieveUpdates() throws OptionalDataException, ClassNotFoundException, IOException{
		Tabuleiro newTab;
		while ((newTab = (Tabuleiro)objectInputStream.readObject()) != null) {	
			JogoActivity.tabuleiroInit = newTab;
		}
	}

	public Nivel readFile() throws IOException
	{
		System.out.println("reading file");
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
			float duracao = Float.parseFloat(line);
			line = br.readLine();
			num_linhas--;
			float timeoutExplosao = Float.parseFloat(line);
			line = br.readLine();
			num_linhas--;
			float duracaoExplosao = Float.parseFloat(line);
			line = br.readLine();
			num_linhas--;
			float rangeExplosao = Float.parseFloat(line);
			line = br.readLine();
			num_linhas--;
			float velocidadeRobot = Float.parseFloat(line);
			line = br.readLine();
			num_linhas--;
			float pontosRobot = Float.parseFloat(line);
			line = br.readLine();
			num_linhas--;
			float pontosRival = Float.parseFloat(line);
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
			myPlayerId = '1';
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
	public void initTime(float tempo){


		timeLeft = (TextView) findViewById(R.id.timeLeft);
		playerScore = (TextView) findViewById(R.id.playerScore);
		numberPlayers = (TextView) findViewById(R.id.numberPlayers);
		if(!online)
			numberPlayers.setText("1");
			
		clock = new CountDownTimer((long)tempo*1000, 1000) {

			public void onTick(long millisUntilFinished) {
				timeLeft.setText(""+ millisUntilFinished / 1000);
				playerScore.setText("" + score);
			}
			public void onFinish() {
				tempoActivo=false;
				movedown.setEnabled(false);
				moveright.setEnabled(false);
				moveup.setEnabled(false);
				moveleft.setEnabled(false);
				bomb.setEnabled(false);
				pause.setEnabled(false);
				timeLeft.setText("done!");
			}
		}.start();

	}

	public void pausePlay(View v) throws InterruptedException{
		System.out.println("pauseplay ******");
		String tempoRestante = timeLeft.getText().toString();

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
	//	Fun���ao de chama movimentos aleatorios

	public void moveUp(View v) throws IOException {
		if (online) {
			Request newReq = new Request(myPlayerId, "MoveUp");
			objectOutputStream.writeObject(newReq);
			objectOutputStream.reset();
		} else {
			tj = (TelaJogo) findViewById(R.id.telajogo);
			tj.bomber.moveUp();
		}
	}

	public void moveDown(View v) throws IOException {
		if (online) {
			Request newReq = new Request(myPlayerId, "MoveDown");
			objectOutputStream.writeObject(newReq);
			objectOutputStream.reset();
		} else {
			tj = (TelaJogo) findViewById(R.id.telajogo);
			tj.bomber.moveDown();
		}
	}

	public void moveLeft(View v) throws IOException {
		if (online) {
			Request newReq = new Request(myPlayerId, "MoveLeft");
			objectOutputStream.writeObject(newReq);
			objectOutputStream.reset();
		} else {

			tj = (TelaJogo) findViewById(R.id.telajogo);
			tj.bomber.moveLeft();
		}
	}

	public void moveRight(View v) throws IOException {
		if (online) {
			Request newReq = new Request(myPlayerId, "MoveRight");
			objectOutputStream.writeObject(newReq);
			objectOutputStream.reset();
		} else {

			tj = (TelaJogo) findViewById(R.id.telajogo);
			tj.bomber.moveRight();
		}
	}

	public void quit(final View v) throws InterruptedException{
		isPaused=false;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		pausePlay(v);
		// set title
		alertDialogBuilder.setTitle("Quit Game");
		
		// set dialog message
		alertDialogBuilder
		.setMessage("Are you sure you want to quit?")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Intent i = new Intent(JogoActivity.this, MainActivity.class);
				startActivity(i);
							
			}
		})
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				try {
					pausePlay(v);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public void colocaBomba(View v) throws IOException{
		if(online){
			Request newReq = new Request(myPlayerId, "Bomb");
			objectOutputStream.writeObject(newReq);
			objectOutputStream.reset();
		}else{

			int[] posicao=this.tabuleiroInit.getPosicao('1');

			if(this.tabuleiroInit.getTabuleiro(posicao[0]-1, posicao[1])=='-')
				this.tabuleiroInit.setTabuleiro(posicao[0]-1, posicao[1], '1');
			else if(this.tabuleiroInit.getTabuleiro(posicao[0]+1, posicao[1])=='-')
				this.tabuleiroInit.setTabuleiro(posicao[0]+1, posicao[1], '1');
			else if(this.tabuleiroInit.getTabuleiro(posicao[0], posicao[1]+1)=='-')
				this.tabuleiroInit.setTabuleiro(posicao[0], posicao[1]+1, '1');
			else if(this.tabuleiroInit.getTabuleiro(posicao[0], posicao[1]-1)=='-')
				this.tabuleiroInit.setTabuleiro(posicao[0], posicao[1]-1, '1');

			this.tabuleiroInit.setTabuleiro(posicao[0], posicao[1], 'B');
		}



	}

}
