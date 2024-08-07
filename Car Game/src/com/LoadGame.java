package com;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
/**
 * Ripristino di uno stato di gioco salvato su file
 * @author Salvatore Capobianco
 *
 */
public class LoadGame implements Serializable{
	// Posizione X salvata del giocatore
	private int carX;
	// Vite salvate del giocatore
	private int vite;
	// Nome salvato del giocatore
	private String name;
	// Coordinate X e Y degli ostacoli salvati
	private int[] ostX;
	private int[] ostY;
	// Flag per salvataggio esistente
	private boolean is_Null;
	/**
	 * Caricamento stato precedente del gioco
	 */
	public LoadGame() {
		try {
			FileInputStream fileInputStream = new FileInputStream("src/saved_games/data.dat");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			SaveGame savedGame = (SaveGame) objectInputStream.readObject();
			//Se trova il file da caricare ripristina le coordinate memorizzate
		    this.setCarX(savedGame.getCarX());
		    this.setVite(savedGame.getVite());
		    this.setName(savedGame.getName());
		    this.ostX=new int[savedGame.getOstx().length];
		    this.ostY=new int[savedGame.getOsty().length];
		    for(int i=0;i<savedGame.getOstx().length;i++) {
		    	   this.ostX[i]=savedGame.getOstx()[i];
		    	   this.ostY[i]=savedGame.getOsty()[i];
		    }
		    //Imposta il flag per far sapere che ha trovato dei dati
		    this.is_Null=false;
		    objectInputStream.close();
		}catch(Exception e) {
			//Se il file non può essere caricato imposta il flag per far sapere che è vuoto
			System.out.println("File not found.");
			this.is_Null=true;
		}
	}
	//Setters
	private void setVite(int x) {
		vite=x;
	}
	private void setCarX(int carX) {
		this.carX = carX;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	//Getters
	/**
	 * Controllo dell'esistenza di uno stato di gioco salvato
	 * @return true se il file non e' presente, false altrimenti
	 */
	public boolean isNull() {
		return this.is_Null;
	}
	/**
	 * Restituzione del numero di vite memorizzate in precedenza
	 * @return Numero di vite salvato
	 */
	public int getVite() {
		return vite;
	}
	/**
	 * Restituzione di un vettore di coordinate X degli ostacoli memorizzati
	 * @return Posizioni X degli ostacoli
	 */
	public int[] getOstx() {
		return ostX;
	}
	/**
	 * Restituzione di un vettore di coordinate Y degli ostacoli memorizzati
	 * @return Posizioni Y degli ostacoli
	 */
	public int[] getOsty() {
		return ostY;
	}
	/**
	 * Restituzione della coordinata X del giocatore memorizzata
	 * @return Posizione X del giocatore
	 */
	public int getCarX() {
		return carX;
	}
	/**
	 * Restituzione della coordinata Y del giocatore memorizzata
	 * @return Nome giocatore
	 */
	public String getName() {
		return name;
	}
}
