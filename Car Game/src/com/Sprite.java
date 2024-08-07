package com;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
/**
 * Superclasse astratta di tutti gli oggetti mobili che hanno immagine e coordinate
 * @author Salvatore Capobianco
 */
public abstract class Sprite extends Thread{
	//Classe madre che viene ereditata dagli oggetti di gioco
	//X,Y posizione sullo schermo
	private int x,y;
	//Dimensioni oggetto
	private int width;
	private int height;
	private boolean active;
	//Movimento
	private int mov;
	//Tempo del thread
	private int time;
	//Immagine
	private BufferedImage img;
	//Istanza
	public GiocoAuto main;
	/**
	 * Impostazioni di base 
	 * @param img L'immagine del componente
	 * @param width La larghezza
	 * @param height L'altezza
	 * @param main Istanza
	 */
	public Sprite(BufferedImage img, int width, int height, GiocoAuto main) {
		this.img=img;
		this.width=width;
		this.height=height;
		this.main=main;
		this.setActive(true);
		this.mov=0;
		this.time=0;
	}
	//Getter
	/**
	 * Restituzione stato (attivo/non attivo) dello Sprite
	 * @return true se e' attivo false altrimenti
	 */
	public final boolean isActive() {
		return this.active;
	}
	/**
	 * Restituzione della posizione X dello Sprite 
	 * @return La coordinata X attuale
	 */
	public final int getX() {
		return this.x;
	}
	/**
	 * Restituzione della posizione X dello Sprite
	 * @return La coordinata Y attuale
	 */
	public final int getY() {
		return this.y;
	}
	/**
	 * Restituzione della larghezza dello Sprite
	 * @return La larghezza attuale
	 */
	public final int getWidth() {
		return this.width;
	}
	/**
	 * Restituzione dell'altezza dello Sprite 
	 * @return L'altezza attuale
	 */
	public final int getHeight() {
		return this.height;
	}
	/**
	 * Restituzione del numero di posizioni di movimento dello Sprite
	 * @return numero di movimenti
	 */
	public final int getMov() {
		return this.mov;
	}
	/**
	 * Tempo del thread
	 * @return tempo di movimento
	 */
	public final int getTime() {
		return this.time;
	}
	/**
	 * Creazione rettangolo sui bordi dello Sprite, per la gestione delle collisioni
	 * @return rettangolo corrispondente all'oggetto
	 */
	public final Rectangle getBorder() {
		//Ritorna un rettangolo alla posizione dell'oggetto
		return new Rectangle(this.getX()+10,this.getY(),this.getWidth()-15,this.getHeight()-15);
	}
	/**
	 * Restituzione dell'immagine dello Sprite
	 * @return l'immagine 
	 */
	public final BufferedImage getImg() {
		return this.img;
	}
	//Setter
	/**
	 * Impostazione dell'immagine dello Sprite
	 * @param img L'immagine da assegnare
	 */
	public final void setImg(BufferedImage img) {
		this.img=img;
	}
	/**
	 * Impostazione dello stato dello Sprite
	 * @param val true per attivo false per disattivo
	 */
	public final void setActive(boolean val) {
		this.active=val;		
	}
	/**
	 * Impostazione della coordinata X dello Sprite 
	 * @param val il punto X sul campo
	 */
	public final void setX(int val) {
		this.x=val;
	}
	/**
	 * Impostazione della coordinata Y dello Sprite
	 * @param val il punto Y sul campo
	 */
	public final void setY(int val) {
		this.y=val;
	}
	/**
	 * Impostazione della larghezza dello Sprite
	 * @param val Larghezza
	 */
	public final void setWidth(int val) {
		this.width=val;
	}
	/**
	 * Impostazione dell'altezza dello Sprite
	 * @param val Altezza
	 */
	public final void setHeight(int val) {
		this.height=val;
	}
	/**
	 * Impostazione del numero di posizioni di movimento dello Sprite
	 * @param val numero movimenti
	 */
	public final void setMov(int val) {
		this.mov=val;
	}
	/**
	 * Impostazione del tempo del thread
	 * @param val tempo in millis
	 */
	public final void setTime(int val) {
		this.time=val;
	}
	
	/**
	 * Metodo astratto per l'aggiornamento nel tempo,
	 *  implementazione delegata alle sottoclassi
	 */
	public abstract void update();
	/**
	 * Metodo astratto per la rappresentazione del componente,
	 * implementazione delegata alle sottoclassi
	 * @param g Graphics
	 */
	public abstract void draw(Graphics g);

}
