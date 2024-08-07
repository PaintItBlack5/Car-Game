package com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 *  
 * Creazione auto che viaggia sul percorso;
 * contiene tutte le informazioni sulla sua posizione, composizione e stato
 * @author Salvatore Capobianco
 */
public class Car extends Sprite implements Strategy {
	private int vite;
	/**
	 * 
	 * @param img Immagine caricata del giocatore
	 * @param main Istanza attuale
	 */
	public Car(BufferedImage img, GiocoAuto main) {
		//Chiamata al costruttore della superclasse Sprite
		super(img,75,125,main);
		this.setMov(0);
		this.setTime(12);
		this.vite=3;
		setPosition();
	}
	/**
	 * Posizionamento giocatore a centro percorso
	 */
	public void setPosition() {
		this.setX(75+GiocoAuto.WIDTH/2-this.getWidth());
		this.setY(GiocoAuto.HEIGHT-this.getHeight()*2+50);
	}
	/**
	 * Thread per la gestione del movimento dell'auto
	 */
	@Override
	public void run() {
		//Thread auto
		while(this.getVite()>=1) {
			 update();
			try {
				Thread.sleep(this.getTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Aggiornamento posizione attuale del giocatore secondo le strategie implementate
	 */
	@Override
	public void update(){
		//Movimento in base alla strategia scelta
		this.setMov(this.strategy(this));
		this.setX(this.getX()+this.getMov());		
	}
	/**
	 * Rappresentazione componente giocatore con colore basato sulle vite
	 */
	@Override
	public void draw(Graphics g) {
		//Disegna auto in base alle vite
		LoaderImg loader=new LoaderImg();
		String img;
		switch(this.vite) {
		case 0:
				img="/img/nero.png";
				break;
		case 1:
				img="/img/viola.png";
				break;
		case 2:
				img="/img/arancione.png";
				break;
		case 3:
				img="/img/rosso.png";
				break;
		default:
				img=null;
		}
		if(img!=null) {
			this.setImg(loader.loadImg(img));
			g.drawImage(this.getImg(),this.getX(),this.getY(),this.getWidth(),this.getHeight(),this.main);
		}
	}
	
	//Accessori - Getters
	/**
	 * Restituzione numero delle vite attuali
	 * @return numero di vite rimaste 
	 */
	public int getVite() {
		return this.vite;
	}
	//Modificatori - Setters
	/**
	 * Impostazione del numero di vite
	 * @param num numero di vite da impostare 
	 */
	public void setVite(int num) {
		this.vite=num;
	}
}
