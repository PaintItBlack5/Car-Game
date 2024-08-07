package com;

import java.io.Serializable;

import javax.swing.JOptionPane;
/**
 * Creazione di un file di salvataggio per memorizzare lo stato attuale della partita
 * @author Salvatore Capobianco
 *
 */
public class SaveGame implements Serializable {
	//Posizione X del giocatore
	private int carX;
	//Vite del giocatore
	private int vite;
	//Posizione X e Y degli ostacoli
	private int[] ostX;
	private int[] ostY;
	//Nome del giocatore
	private String name;
	/**
	 * Memorizzazione  dello stato attuale del gioco
	 */
	public SaveGame() {
		this.setCarX(GiocoAuto.car.getX());
		this.setVite(GiocoAuto.car.getVite());
		this.setName(GiocoAuto.pName);
		this.ostX=new int[GiocoAuto.ostacoli.size()];
		this.ostY=new int[GiocoAuto.ostacoli.size()];
		for(int i=0;i<ostX.length;i++) {
			this.ostX[i]=GiocoAuto.ostacoli.get(i).getX();
			this.ostY[i]=GiocoAuto.ostacoli.get(i).getY();
			//System.out.println(this.ostX[i]+" "+this.ostY[i]);
		}
		
	}
	//Setters
	private void setVite(int x) {
		vite=x;
	}
	private void setCarX(int x) {
		carX=x;
	}
	private void setName(String name) {
		this.name = name;
	}
	//Getters
	/**
	 * Posizione X degli ostacoli
	 * @return un vettore con le coordinate X degli ostacoli salvati
	 */
	public int[] getOstx() {
		return ostX;
	}
	/**
	 * Posizione Y degli ostacoli
	 * @return un vettore con le coordinate Y degli ostacoli salvati
	 */
	public int[] getOsty() {
		return ostY;
	}
	/**
	 * Posizione X del giocatore
	 * @return La posizione X del giocatore salvato
	 */
	public int getCarX() {
		return carX;
	}
	/**
	 * Numero di vite del giocatore
	 * @return Il numero di vite del giocatore salvato
	 */
	public int getVite() {
		return vite;
	}
	/**
	 * Nome del giocatore attuale
	 * @return Il nome del giocatore salvato
	 */
	public String getName() {
		return name;
	}

}
