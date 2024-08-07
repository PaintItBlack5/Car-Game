package com;
/**
 * 
 * Controllo collisioni tra auto e ostacoli
 * @author Salvatore Capobianco
 */
public class CrashManager {
	/**
	 * Controlla l'intersezione di due rettangoli 
	 * formati dal giocatore e l'ostacolo
	 * @param car il giocatore con le sue coordinate
	 * @param o l'ostacolo con le sue coordinate
	 * @return true se c'e' intersezione, false altrimenti
	 */
	public static boolean checkCrash(Sprite car, Sprite o) {
		//Confronta se i rettangoli si sovrappongono
		return car.getBorder().intersects(o.getBorder());

	}
}
