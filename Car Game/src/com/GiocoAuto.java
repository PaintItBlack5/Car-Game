package com;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Classe principale del gioco
 * @author Salvatore Capobianco
 *
 */
public class GiocoAuto extends Canvas implements Runnable,MouseListener,MouseMotionListener,KeyListener{
	
	// VARIABILI STATICHE (DI CLASSE)
	
	/**
	 *  Dimensioni schermo
	 */
	private static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 *  Larghezza schermo
	 */
	public static final int WIDTH = SCREEN.width;
	
	/**
	 *  Altezza schermo
	 */
	public static final int HEIGHT = SCREEN.height;
	
	// Nome gioco
	private static final String gameName = "Gioco Auto";
	
	/**
	 * Immagini
	 */
	static BufferedImage carImg = null;
	static BufferedImage ostacoloImg = null;
	static BufferedImage bg = null;
	static BufferedImage gameOver = null;
	static BufferedImage playImg = null;
	static BufferedImage loadImg = null;
	static BufferedImage menuBg = null;
	static BufferedImage menuImg = null;
	
	
	// OGGETTI
		
	/**
	 *  Nome del giocatore
	 */
	public static String pName;

	/**
	 * Auto
	 */
	public static Car car;

	/**
	 * Ostacoli
	 */
	public static ArrayList<Ostacolo> ostacoli;
	
	// Strisce della carreggiata per creare l'illusione ottica del movimento
	private static ArrayList<Stripes> street;
		
	// Pulsanti di avvio, caricamento e ritorno al menu
	private static Button start;
	private static Button loading;
	private static Button goMenu;

	
	
	/**
	 *  Variabile dello stato della partita (in corso/non in corso)
	 */
	public boolean play;


	/**
	 * Inizializzazione del gioco (non della partita ma del menu)
	 */
	public GiocoAuto() {
		
		// Carica immagini
		this.play = false;
		ostacoli = new ArrayList<Ostacolo>();
		street = new ArrayList<Stripes>();
		addMouseListener(this);
		addKeyListener(this);
		
		// Caricamento immagini e creazione del menu
		loadResource();
		menu();
	}

	public static void main(String[] args) {
		GiocoAuto giocoAuto = new GiocoAuto();
		
		//Dimensioni finestra gioco
		Dimension windowDim = new Dimension(WIDTH,HEIGHT);
		
		//Creazione finestra gioco
		JFrame window = new JFrame(gameName);
		window.setPreferredSize(windowDim);
		window.setMaximumSize(windowDim);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(giocoAuto);
		window.pack();
		//Visualizzazione frame
		window.setVisible(true);
		//Creazione e inizio del thread principale
		Thread gameT = new Thread(giocoAuto);
		gameT.start();

	}
	
	/**
	 * Inizio di una nuova partita
	 */
	private void startGame() {
		
		// Crea auto e ostacoli
		car = new Car(carImg, this);
		ostacoli.add(creaOstacolo());
		
		// Inizio thread auto
		car.start();
		
		// Partita avviata
		play = true;

	}


	/**
	 * Caricamento delle immagini
	 */
	private void loadResource() {
		LoaderImg loader = new LoaderImg();
		carImg = loader.loadImg("/img/rosso.png");
		ostacoloImg = loader.loadImg("/img/ostacolo.png");
		bg = loader.loadImg("/img/strada.png");
		gameOver = loader.loadImg("/img/gameover.png");
		playImg = loader.loadImg("/img/play.png");
		loadImg = loader.loadImg("/img/load.png");
		menuImg = loader.loadImg("/img/menu.png");
		menuBg = loader.loadImg("/img/menubg.png");
		System.out.println("Done.");
	}
	
	/**
	 * Creazione di un ostacolo in una posizione casuale del bordo superiore, 
	 * evitando sovrapposizioni
	 * @return Un ostacolo valido 
	 */
	public static Ostacolo creaOstacolo(){
		boolean ok;
		Ostacolo temp;
		do {
			ok = true;
			// Crea nuovo ostacolo
			temp = new Ostacolo(ostacoloImg,null);
			// Controlla tutti gli ostacoli presenti nella finestra della partita
			for(Ostacolo o : ostacoli) {
				// Rettangolo di traiettoria di temp
				Rectangle b = new Rectangle(temp.getBorder().x,
						temp.getBorder().y, temp.getBorder().width,
						GiocoAuto.HEIGHT-temp.getBorder().y);
				// Se si trova nella traiettoria di un altro ostacolo ripete la creazione
				if(o.getBorder().intersects(b) || o.getBorder().contains(b)) {
					ok = false;
					break;
				}
			}
		}while(!ok);
		temp.start();
		return temp;
	}


	
	
	/**
	 *  Thread principale
	 */
	@Override
	public void run() {
		while(true) {
			//Se non è in corso una partita -> Menu di gioco
			while(!play) {
				menuDraw();
			}
			
			//Doppia fila di striscie stradali
			for(int i=0; i<HEIGHT/140; i++) {
				street.add(new Stripes(this.getWidth()/2-200, i*150,20,HEIGHT/10,this));
				street.add(new Stripes(this.getWidth()/2+200, i*150,20,HEIGHT/10,this));
			}
			
			
			// INIZIO PARTITA
			
			/** Thread per la creazione di ostacoli
			 * ad intervalli regolari
			 * finchè non sono esaurite le vite del giocatore 
			*/
			Thread creaOst = new Thread(this) {
				@Override
				public void run(){
						while(play){
							try {
								Thread.sleep(1800);
							}
							catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(car.getVite() >= 1) ostacoli.add(creaOstacolo());
						}
				}
			};
			
			creaOst.start();
			while(play) {
				if(car.getVite()>=1) {
					//Verifica condizioni
					check();
					//Disegna componenti
					draw();
				}
				else drawGameOver();
			}
		}
	} // fine run()
	
	/**
	 * Creazione della schermata menu e dei relativi pulsanti
	 */
	private void menu(){
		/**
		 * Pulsante PLAY	
		 */
		start=new Button(playImg,100,50,this);
		/**
		 * Pulsante CARICA
		 */
		loading=new Button(loadImg,100,50,this);
		loading.setY(start.getY()+start.getHeight()+30);
			
	}
	
	
	/**
	 * Rappresentazione a schermo dei componenti del menu di gioco
	 */
	private void menuDraw() {
		BufferStrategy buffer = this.getBufferStrategy();
		if(buffer==null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g=buffer.getDrawGraphics();
		
		//Background
		g.drawImage(menuBg, 0, 0, WIDTH, HEIGHT, this);
		
		//Pulsante PLAY
		start.draw(g);
		
		//Pulsante LOAD
		loading.draw(g);
		
		g.dispose();
		buffer.show();
		buffer=null;
	}
	
	private void drawGameOver() {
		BufferStrategy buffer=this.getBufferStrategy();
		if(buffer==null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g=buffer.getDrawGraphics();
		g.setFont(new Font("TimesRoman",Font.BOLD,15));
		g.drawImage(gameOver,WIDTH/2-250,HEIGHT/2-500,500,340,this);
		g.drawString("Vite:GAMEOVER", 25, 100);
		goMenu=new Button(menuImg,100,50,this);
		goMenu.setY(450);
		goMenu.draw(g);
		buffer.show();
		buffer=null;
	}
	
	
	/**
	 * Rappresentazione a schermo dei componenti di gioco
	 */
	private void draw() {
		BufferStrategy buffer=this.getBufferStrategy();
		if(buffer==null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g=null;
		do {
			try {
				g=buffer.getDrawGraphics();
				g.setFont(new Font("TimesRoman",Font.BOLD,15));
				
				//Scenario di gioco
				g.setColor(Color.GREEN.darker());
				g.fillRect(0, 0, WIDTH, HEIGHT);
				g.setColor(Color.GRAY);
				g.fillRect(150, 0, WIDTH-300, HEIGHT);
				g.setColor(Color.BLACK);
				g.drawRect(150, 0, WIDTH-300, HEIGHT);
				for(int i=0; i<street.size(); i++)
					street.get(i).draw(g);
				
				//Auto
				car.draw(g);
				
				//Ostacoli
				for(int i=0;i<ostacoli.size();i++)
				{
					ostacoli.get(i).draw(g);
					/*
					  Inserendo questa riga nel codice
					  viene visualizzata la traiettoria degli ostacoli
					  g.drawRect(ostacoli.get(i).getBorder().x,ostacoli.get(i).getBorder().y,ostacoli.get(i).getBorder().width,this.getHeight()-ostacoli.get(i).getBorder().y);
					*/
					
				}
				/*
				 Inserendo queste righe nel codice verranno visualizzate
				 le coordinate X & Y dell'auto e il numero di ostacoli attivi
				 g.drawString("ostSIZE: "+ostacoli.size(), 25, 240);
				 g.drawString("carX: "+car.getX(), 25, 210);
				 g.drawString("carY: "+car.getY(), 25, 225);
				 */
				
				// Nome giocatore
				g.drawString("Nome",25,30);
				g.drawString(pName,25,50);
				
				// Vite
				g.drawString("Vite:",25, 100);
				g.drawString("PREMI S", 25, HEIGHT-150);
				g.drawString("PER SALVARE", 25, HEIGHT-130);
				for(int i=0;i<car.getVite();i++) g.drawImage(car.getImg(),20+i*30,110,30,50,this);
				
			}finally {
				g.dispose();
				}
			buffer.show();
		}while(buffer.contentsLost());
		buffer=null;		
	}
	
	/**
	 * Controllo collisioni o fuoriuscita dalla finestra degli ostacoli
	 */
	private void check() {
		// Controllo collisioni
		for(Ostacolo o : ostacoli) {
			// Collisione avvenuta
			if(CrashManager.checkCrash(car,o)) {
				// Pausa di qualche secondo
				try {
					Thread.sleep(1250);
				} catch (InterruptedException e) {
					// TODO Blocco catch generato automaticamente
					e.printStackTrace();
				}
				// Elimina gli ostacoli presenti
				ostacoli.clear();
				// Decrementa le vite di 1 se possibile
				if(car.getVite()>=1) {
					car.setVite(car.getVite()-1);
				}
				// Altrimenti se esaurite, termina la partita
				else {
					car.setVite(0);
					break;
				}
				
				// Riposiziona l'auto
				car.setPosition();
				break;
			}
		}
		
		// Controllo fuoriuscita dallo schermo degli ostacoli e relativa rimozione
		for(int i=0;i<ostacoli.size();i++) {
			if(ostacoli.get(i).getY()>GiocoAuto.HEIGHT) {
				ostacoli.remove(i);
				break;
			}
		}		
	}
	
	/**
	 * Controlla se viene premuto un tasto, se viene premuto S salva lo stato del gioco
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		//if(KeyEvent.VK_SPACE==arg0.getKeyCode()) {car.setVite(0);}
		
		//Se viene premuto S salva lo stato del gioco
		if(KeyEvent.VK_S==arg0.getKeyCode()) {save();}
		
		/*
		Possibilità  di muovere l'auto con le freccette
		
		if(KeyEvent.VK_LEFT==arg0.getKeyCode()) {
			if(!(car.getX()<150))
				car.setX(car.getX()-5);
		}
		if(KeyEvent.VK_RIGHT==arg0.getKeyCode()) {
			if(!(car.getX()>WIDTH-250))
				car.setX(car.getX()+5);
		}
		*/
	}
	/**
	 * NON USATO
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {}
	/**
	 * NON USATO
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	/**
	 * Controllo dei click sui bottoni
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		String name;
		// Se la partita non è iniziata si può cliccare solo PLAY o LOAD
		if(!play) {			
			// Click su PLAY
			if(x >= start.getX() && x <= start.getX()+start.getWidth()) {
				if(y >= start.getY() && y <= start.getY()+start.getHeight()) {
					// Nome del giocatore
					name=JOptionPane.showInputDialog(null, "INSERISCI NOME GIOCATORE (max 10)", "PLAY",JOptionPane.INFORMATION_MESSAGE);
					if(name!=null) {
						if(!name.isBlank()) {
							if(name.length()>10)
								name=name.substring(0, 10);
							pName=name;
						}
						else pName = "PLAYER 1";
						ostacoli.clear();
						startGame();
					}		
				}
			}
			
			//Click su LOADING
			if(x >= loading.getX() && x <= loading.getX() + loading.getWidth()) {
				if(y >= loading.getY() && y <= loading.getY() + loading.getHeight()) {
					int dialogResult = JOptionPane.showConfirmDialog(this, 
							"Ripristinare partita precedente?",
							"Load", JOptionPane.YES_NO_OPTION);
					//Ricarica dei dati salvati
					if(dialogResult == JOptionPane.YES_OPTION){
							LoadGame load=new LoadGame();
							if(load.isNull()) JOptionPane.showMessageDialog(this, "Nessun dato salvato", "NO DATA", JOptionPane.ERROR_MESSAGE);
							else{
								GiocoAuto.car=new Car(carImg,this);
								GiocoAuto.car.setX(load.getCarX());
								GiocoAuto.car.setVite(load.getVite());
								this.pName=load.getName();
								for(int i=0;i<load.getOstx().length;i++) {
									GiocoAuto.ostacoli.add(creaOstacolo());
									GiocoAuto.ostacoli.get(i).setX(load.getOstx()[i]);
									GiocoAuto.ostacoli.get(i).setY(load.getOsty()[i]);
								}
							play=true;
							GiocoAuto.car.start();
							}
					}
					else if(dialogResult==JOptionPane.CLOSED_OPTION || 
							dialogResult==JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(this, "Non caricato", "ABORT", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}
		}
		
		// Esaurite le vite, si può solo tornare al menu attraverso il relativo pulsante
		else if(play && car.getVite()<=0){
			ostacoli.clear();
			street.clear();
			//Click su MENU
			if(x >= goMenu.getX() && x <= goMenu.getX()+goMenu.getWidth()) {
				if(y >= goMenu.getY() && y <= goMenu.getY()+goMenu.getHeight()) {
					play=false;
				}
			}
		 }		
	}
	/**
	 * NON USATO
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	/**
	 * NON USATO
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}
	/**
	 * NON USATO
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}
	/**
	 * NON USATO
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
	/**
	 * NON USATO
	 */
	@Override
	public void mouseDragged(MouseEvent e) {}
	/**
	 * NON USATO
	 */
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	/**
	 * Salvataggio dello stato della partita e ritorno al menu
	 */
	public void save() {
		// Permetti il salvataggio solo se la partita è in corso e le vite non sono esaurite
		if(play && car.getVite()>=1) {
			try { 				
				//Crea o sostituisce il file di salvataggio "data.dat"
				FileOutputStream fileStream = new FileOutputStream("src/saved_games/data.dat");   
		        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		        //Salva lo stato attuale
		        objectStream.writeObject((SaveGame) new SaveGame());
		        car.setVite(0);
		        objectStream.close();   
		        fileStream.close();   
		        JOptionPane.showMessageDialog(this, 
		            "Salvataggio RIUSCITO.", 
		            "Gioco Auto",   
		            JOptionPane.INFORMATION_MESSAGE);   
		    }
			catch (Exception e) {   
		        JOptionPane.showMessageDialog(this, 
		            "Salvataggio NON eseguito.",   
		            "Gioco Auto", 
		            JOptionPane.ERROR_MESSAGE);   
		    }
			// Ritorno al menu
			finally {
				ostacoli.clear();
				street.clear();
				play=false;
			}   
		}
	}
}
