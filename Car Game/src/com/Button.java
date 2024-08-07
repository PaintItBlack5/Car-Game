package com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 * Generazione dei bottoni con immagine
 * @author Salvatore Capobianco
 *
 */
public class Button extends Sprite{
	/**
	 * Impostazione posizione e immagine del pulsante
	 * @param img L'immagine del pulsante
	 * @param width La lunghezza del pulsante
	 * @param height L'altezza del pulsante
	 * @param main L'istanza a cui appartiene
	 */
	public Button(BufferedImage img, int width, int height, GiocoAuto main) {
		//Chiamata al costruttore principale
		super(img,width,height,main);
		//Imposta le coordinate X e Y al centro dello schermo
		this.setX(GiocoAuto.WIDTH/2-this.getHeight());
		this.setY(GiocoAuto.HEIGHT/2-this.getWidth());
	}
	/**
	 * Rappresentazione dei pulsanti
	 */
	@Override
	public void draw(Graphics g) {
		if(this.getImg()!=null)	{
			g.drawImage(this.getImg(),this.getX(),this.getY(),this.getWidth(),this.getHeight(),this.main);
		}
	}
	/**
	 * NON IMPLEMENTATO
	 */
	@Override
	public void update() {}

}
