package com;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Creazione striscia stradale in movimento
 * per dare l'illusione ottica del movimento
 * @author Salvatore Capobianco
 *
 */
public class Stripes extends Sprite{
	/**
	 * Inizializzazione della striscia
	 * @param x Posizione X sul campo
	 * @param y Posizione Y sul campo
	 * @param width Larghezza della striscia
	 * @param height Altezza della striscia
	 * @param main Istanza principale
	 */
	public Stripes(int x,int y, int width, int height,GiocoAuto main) {
		//Chiamata al costruttore principale
		super(null,width,height,main);
		this.setX(x);
		this.setY(y);
		this.setMov(5);
		this.setTime(5);
		
		this.start();
	}
	/**
	 * Thread di una striscia che cade in continuazione dall'alto
	 */
	@Override
	public void run() {
		while(this.isActive()) {
			update();
			try {
				Thread.sleep(this.getTime());
			} catch (InterruptedException e) {
				// TODO Blocco catch generato automaticamente
				e.printStackTrace();
			}
		}
	}
	/**
	 * Aggiornamento posizione della striscia
	 */
	@Override
	public void update() {
		if(this.getY()>=GiocoAuto.HEIGHT) {
			this.setY(-this.getHeight());
		}
		this.setY(this.getY()+1);
	}
	/**
	 * Rappresentazione della striscia sullo schermo alle sue coordinate 
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		
	}
}
