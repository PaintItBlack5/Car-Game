package com;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Caricamento immagini richieste
 * @author Salvatore Capobianco
 *
 */
public class LoaderImg {
	 BufferedImage img;
	 /**
	  * Inizializzazione immagine
	  */
	 public LoaderImg() {
		 img=null;
	 }
	 /**
	  * Restituizione immagine dalla directory fornita in input
	  * @param pos Posizione fisica immagine
	  * @return L'immagine scelta
	  */
	 public BufferedImage loadImg(String pos) {
		 try {
			img=ImageIO.read(getClass().getResource(pos));
		 }catch (IOException e) {
			System.out.println("Chiuso");
			//e.printStackTrace();
		 }
		 return img;
	 }
}
