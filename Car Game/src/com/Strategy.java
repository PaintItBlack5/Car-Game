package com;

import java.awt.Rectangle;
import java.util.ConcurrentModificationException;
import java.util.Random;
/**
 * Interfaccia che schematizza le strategie di movimento del giocatore;
 * la strategia varia in base alle sue vite rimanenti
 * @author Salvatore Capobianco
 *
 */
public interface Strategy {
	/**
	 * Restituzione del movimento che dovrà eseguire il giocatore
	 * @param car Il giocatore alle coordinate attuali
	 * @return La direzione di movimento in base alla strategia scelta
	 */
	public default int strategy(Car car) {
		//Conta se sono stati controllati tutti gli ostacoli presenti sul campo
		int count=0;
		try {
			for(Ostacolo o : GiocoAuto.ostacoli) {
				//Se si trova sulla sua traiettoria
				Rectangle b = new Rectangle(o.getBorder().x,o.getBorder().y, 
						o.getBorder().width, car.main.getHeight()-o.getBorder().y);
				if(car.getBorder().intersects(b)||car.getBorder().contains(b)) {
					//Se non si muove
					if(car.getMov()==0) {
						//STRATEGIA 1 (Se si trova nel suo intorno)
						if(strategia1(car,o) && (car.getVite()>0 && car.getVite()<=3)) {
							Random rand=new Random();
							int rnd;
							do {
								rnd=rand.nextInt(2+3)-2;
							}while(rnd!=2 && rnd!=-2);
							return rnd;	
						}else
						//STRATEGIA 2 & 3 (destra e sinistra)
						if(car.getVite()==3 || car.getVite()==2) {
							switch(strategia2_3(car,o)) {
								case 1:	
										if(car.getVite()==3 || car.getVite()==2)
											return 2;
										break;
								case 2:
										if(car.getVite()==3)
											return -2;
										break;
								case 0:
										return 0;
								default:
										break;
							}
						}
					}
					//Se si trova ai bordi cambia movimento
					else{
						if(car.getX() >= GiocoAuto.WIDTH-250 || car.getX() <= 150)
							return(car.getMov()*-1);
					}
				}
				else {
					count++;
				}
			}
		}catch(ConcurrentModificationException e) {return(car.getMov()); }
		
		//Se controllando tutti gli ostacoli, nessuno si trova sulla traiettoria
		if(count >= GiocoAuto.ostacoli.size()) {return 0;} //Si ferma
		
		//Se non è soddisfatta nessuna condizione, restituisce il movimento attuale
		return(car.getMov());
		
	}
	/**
	 * Controllo che un ostacolo si trovi nell'intorno del giocatore
	 * @param car Il giocatore
	 * @param o L'ostacolo
	 * @return true se si trova nel suo intorno false altrimenti
	 */
	public static boolean strategia1(Car car, Ostacolo o) {
		if(o.getY() >= GiocoAuto.HEIGHT-car.getHeight()*4) 
			return true;
		return false;
		
	}
	/**
	 * Controllo della traiettoria di un ostacolo,
	 * il giocatore si sposta a destra se ha la maggioranza degli 
	 * ostacoli alla sua sinistra,
	 * altrimenti si sposta a sinistra.
	 * @param car Il giocatore
	 * @param o L'ostacolo
	 * @return 1 se la maggioranza degli ostacoli si trova a sinistra, 
	 * 2 se la maggioranza e' a destra, 0 se non si trova nella traiettoria
	 */
	public static int strategia2_3(Car car, Ostacolo o) {
		if(o.getY() >= GiocoAuto.HEIGHT/4-o.getHeight()) {
			int sx=0,dx=0;
			for(Ostacolo ost : GiocoAuto.ostacoli) {
				if(ost.getX() < car.getX()) 
					sx++;
				else 
					dx++;
			}
			if(sx >= dx) {return 1;}
			else {return 2;}
		}
		return 0;
	}
	
}
