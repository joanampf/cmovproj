package pt.tecnico.cmov.bomberman.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import pt.tecnico.cmov.bomberman.JogoActivity;
import pt.tecnico.cmov.bomberman.Nivel;
import pt.tecnico.cmov.bomberman.telajogo.Tabuleiro;
import shared.Request;

public class Server {

	private static ServerSocket serverSocket;
	private static ObjectInputStream[] objectInputStream = new ObjectInputStream[4];
	private static InputStream[] inputStream = new InputStream[4];;
	private static OutputStream[] outputStream = new OutputStream[4];
	private static ObjectOutputStream[] objectOutputStream = new ObjectOutputStream[4];
	private static Util util = new Util();
	private static Tabuleiro tab = null;
	private static Nivel nivel;
	private static char nextPlayerId = '1';
	private static int numberOfPlayers = 0;
	private static Socket clientSocket = null;
	private static boolean mustResendTab = false;


	public static void main(String[] args) {

		try {
			readFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		new Thread(new Runnable() {
			public void run() {
				try {

					getNewClients();

				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		while (numberOfPlayers != 2) {
			System.out.print("");
		} // wait for a player to join
		new Thread(new Runnable() {
			public void run() {
				try {
					SendBoard();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();


		new Thread(new Runnable() {
			public void run() {
				try {
					MoveRobots();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	static void MoveRobots() throws InterruptedException{
		while (true) {
			int coluna;
			int linha;
			int num_linhas = tab.getNum_linhas();
			int num_colunas = tab.getNum_colunas();
			ArrayList<int[]> robotPositions = new ArrayList<int[]>();

			for (linha = 0; linha < num_linhas; linha++) {
				for (coluna = 0; coluna < num_colunas; coluna++) {

					final int[] posicao = new int[2];
					posicao[0] = linha;
					posicao[1] = coluna;

					switch (tab.getTabuleiro(linha, coluna)) {

					case 'R':
						robotPositions.add(posicao);
					}
				}
			}

			for (int[] pos : robotPositions) {
				int[] newpos = util.moveRandom(pos[0], pos[1], tab);
				tab.setTabuleiro(pos[0], pos[1], '-');
				tab.setTabuleiro(newpos[0], newpos[1], 'R');
			}
			mustResendTab = true;
			Thread.sleep((int)(nivel.getRobotSpeed() * 1000) );
		}

	}
	static void SendBoard() throws IOException, InterruptedException {
		Thread.sleep(2000);
		while (true) {
			Thread.sleep(200);
			if(mustResendTab){
				mustResendTab = false;
				for(int i = 0; i < numberOfPlayers; i++){
					objectOutputStream[i].writeUnshared(tab);
					objectOutputStream[i].reset();
				}
			}
		}
	}

	static void getNewClients() throws ClassNotFoundException, IOException {
		try {
			serverSocket = new ServerSocket(4000);
		} catch (IOException e) {
			System.out.println("Could not listen on port: 4000");
		}

		System.out.println("Server started. Listening to the port 4000");
		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				inputStream[numberOfPlayers] = clientSocket.getInputStream();

				objectInputStream[numberOfPlayers] = new ObjectInputStream(inputStream[numberOfPlayers]);

				Request rq = (Request) objectInputStream[numberOfPlayers].readObject();

				System.out.println("request recieved: " + rq.message);

				if (rq.message.equals("NewGame")) { // envia tabuleiro e nivel
					outputStream[numberOfPlayers] = clientSocket.getOutputStream();
					objectOutputStream[numberOfPlayers] = new ObjectOutputStream(outputStream[numberOfPlayers]);
					objectOutputStream[numberOfPlayers].writeChar(nextPlayerId++);
					objectOutputStream[numberOfPlayers].writeUnshared(tab);
					objectOutputStream[numberOfPlayers].writeUnshared(nivel);

					new Thread(new Runnable() {
						public void run() {
							try {
								RecieveRequests();
							} catch (ClassNotFoundException | IOException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			} catch (IOException ex) {
				System.out.println("Problem in recieving request");
				System.out.println(ex.getMessage());
			}
		}
	}


	static void RecieveRequests() throws ClassNotFoundException, IOException{
		Request newRequest;
		int thisPlayerIndex = numberOfPlayers++;
		while ((newRequest = (Request)objectInputStream[thisPlayerIndex].readObject()) != null) {

			if(newRequest.message.equals("MoveUp")){
				util.MoveUp(newRequest.playerId, tab);

			}

			if(newRequest.message.equals("MoveDown")){
				util.MoveDown(newRequest.playerId, tab);
			}

			if(newRequest.message.equals("MoveLeft")){
				util.MoveLeft(newRequest.playerId, tab);
			}

			if(newRequest.message.equals("MoveRight")){
				util.MoveRight(newRequest.playerId, tab);
			}			
			if(newRequest.message.equals("Bomb")){
				synchronized(util.lock){
					util.currentlyActivePlayer = newRequest.playerId;
				}
				new Thread(new Runnable() {
					public void run() {
						try {
							util.PlaceBomb(tab, nivel);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		}

		mustResendTab = true;
	}


	public static void readFile() throws IOException {
		System.out.println("reading file");
		InputStream in = JogoActivity.class
				.getResourceAsStream("gridLayout.txt");
		BufferedReader br_aux = new BufferedReader(new InputStreamReader(in));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		int num_colunas = 0;
		int num_linhas = 0;

		try {
			StringBuilder sb = new StringBuilder();
			String line = br_aux.readLine();
			// br.aux para contar o numero de linhas, de forma a determinar as
			// correspondentes ao tabuleiro
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
			tab = new Tabuleiro(num_linhas, num_colunas);

			int coluna;
			int linha = 0;

			while (line != null) {
				coluna = 0;
				sb.append(line);
				sb.append(System.lineSeparator());

				// numero colunas

				for (coluna = 0; coluna < line.length(); coluna++) {

					tab.tabuleiro[linha][coluna] = line.charAt(coluna);
				}
				// System.out.println("tabuleiro na linha"+linha+": "+tabuleiroInit[linha][0]);
				linha++;
				line = br.readLine();

			}
			String filepath = sb.toString();
			nivel = new Nivel(name, duracao, timeoutExplosao, duracaoExplosao,
					rangeExplosao, velocidadeRobot, pontosRobot, pontosRival,
					filepath);
		} finally {

			br.close();
			br_aux.close();
		}
	}

}