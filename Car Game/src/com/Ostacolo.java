package com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * Creazione di un ostacolo alla posizione casuale X;
 * contiene tutte le informazioni sulla sua posizione, composizione e stato
 * @author Salvatore Capobianco
 *
 */
public class Ostacolo extends Sprite{
	private Random rand=new Random();
	/**
	 * Costruttore di ostacolo, genera un ostacolo con posizione casuale e movimento casuale
	 * @param img L'immagine dell' ostacolo
	 * @param main Istanza principale
	 */
	public Ostacolo(BufferedImage img, GiocoAuto main) {
		//Costruttore dell'ostacolo, chiamata al costruttore principale
		super(img,75,125,main);
		//Movimento casuale di 1 o 2
		this.setMov(this.rand.nextInt(2)+1);
		this.setTime(15);
		//Posizione casuale della prima riga
		this.setX(rand.nextInt(GiocoAuto.WIDTH-300-this.getWidth())+150);//1000-150)+150);
		//Posizione Y fuori dallo schermo per dare l'effetto che entri nello schermo
		this.setY(-(this.getHeight()));
	}
	/**
	 * Thread di avvio per ostacolo
	 */
	@Override
	public void run() {
		while(this.isActive()) {
			update();
			try {
				Thread.sleep(this.getTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Aggiornamento della posizione dell'ostacolo nel tempo
	 */
	@Override
	public void update(){
			this.setY(this.getY()+this.getMov());
	}
	/**
	 * Rappresentazione dell'ostacolo nelle sue coordinate attuali
	 */
	@Override
	public void draw(Graphics g) {
		if(this.getImg()!=null)	{
			g.drawImage(this.getImg(),this.getX(),this.getY(),this.getWidth(),this.getHeight(),this.main);
		}
	}
}