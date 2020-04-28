package it.polito.tdp.meteo.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class TestModel {

	public static void main(String[] args) {
		
		List <Citta> tempL = new ArrayList<Citta>();
		Model m = new Model();
		Citta c = new Citta("Genova");
		Citta c2= new Citta("Torino");
		Citta c3 = new Citta ("Milano");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		tempL.add(c);
		tempL.add(c2);
		tempL.add(c3);
		
		//System.out.println("CIAO");
	    System.out.println(m.trovaSequenza(10, tempL));
	    
	    
		
		//System.out.println(m.getUmiditaMedia(12, tempL));
		
		//System.out.println(m.trovaSequenza(5));
		

	}

}
