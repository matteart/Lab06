package it.polito.tdp.meteo.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private List<Citta> percorso= new ArrayList<>();
	private List<Citta> tempS= new ArrayList<>();
	private int costoMin= Integer.MAX_VALUE;
    MeteoDAO dao = new MeteoDAO();
	
	public Model() {

	}
	public List <Rilevamento> temp ( int mese, Citta citta){
	 return dao.getAllRilevamentiLocalitaMese(mese, citta.getNome());
	}
	
	
	public String getUmiditaMedia(int mese, List<Citta> citta) {
		
		String result="";
		for(Citta c: citta) {
			if(c!=null) {
			List<Rilevamento> tempL = new ArrayList<>(this.calcolaData(mese, dao.getAllRilevamentiLocalitaMese(mese, c.getNome())));
			int umiditaTOT=0;
			for(Rilevamento r:tempL)
			 umiditaTOT+=r.getUmidita();
			int media = umiditaTOT/tempL.size();
			result+= c.getNome()+" Umidita: "+ media+"\n";
			}
			
		}
		
		return result;
	}
	
	public List<Rilevamento> calcolaData (int mese, List<Rilevamento> rilevamenti){
		List<Rilevamento> tempL = new ArrayList<>();
		
		 Data inizio = new Data( 2013, mese, 01);
		 
		 Data fine = new Data(2013, mese+1, 01);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 
		 
		for(Rilevamento r:rilevamenti) {
			if(r!=null ) {
				String data = df.format(r.getData());
				
				if(data.compareTo(inizio.getData())>=0 && data.compareTo(fine.getData())<0)
					tempL.add(r);
			}
				
		}
		return tempL;
	}
	
	public String trovaSequenza(int mese, List<Citta> daFare) {
		List<Citta> disponibiliTotale = new ArrayList<Citta> (daFare);
		
		for(Citta c:daFare)
			if(c!=null) {
				List <Rilevamento> tempL = new ArrayList<>(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
				c.setRilevamenti(tempL);
			}
		
		String result="";
		
		ricorsiva(daFare, tempS, mese);
		System.out.println(percorso.get(1).getNome());
		for(Citta c:percorso) {
			
		  result+=c.getNome()+"\n";
		}
		return result;
	}
	
	public void ricorsiva (List<Citta> disponibli , List <Citta> tempSoluzione , int mese) {
		
		int livello = tempSoluzione.size()+1;
		
		if(livello == this.NUMERO_GIORNI_TOTALI) {
			// System.out.println("TROVATA");
			   
			if(this.umiditaPercorso(tempSoluzione, mese) < this.costoMin) {
				
			  // System.out.println(this.umiditaPercorso(tempSoluzione, mese));
		       this.costoMin=this.umiditaPercorso(tempSoluzione, mese);	
				this.aggiornaPercorso(tempSoluzione);
		}
		}
		List<Citta> daFare = new ArrayList<>(disponibli);
		
		for(Citta c : daFare) {
			if(this.aggiuntaValida(tempSoluzione, c)) {
			
				tempSoluzione.add(c);
				this.ricorsiva(daFare, tempSoluzione, mese);
				tempSoluzione.remove(tempSoluzione.size()-1);
			}
		}
		
	}
	
	private Boolean aggiuntaValida(List<Citta> tempSoluzione, Citta prova) {
		
	
		int counter = 0;
		
		if(tempSoluzione.size()==0)
			return true;
		
		for(Citta c : tempSoluzione)
			if(c.equals(prova))
				counter++;
		
		if(counter >= this.NUMERO_GIORNI_CITTA_MAX)
			return false;
		
		if(tempSoluzione.size()==1 || tempSoluzione.size()==2) 
			return tempSoluzione.get(tempSoluzione.size()-1).equals(prova);
		
		if(tempSoluzione.get(tempSoluzione.size()-1).equals(prova))
			return true;
		
		if(tempSoluzione.get(tempSoluzione.size()-1).equals(tempSoluzione.get(tempSoluzione.size()-2)) &&  tempSoluzione.get(tempSoluzione.size()-2).equals(tempSoluzione.get(tempSoluzione.size()-3)))
			return true;
		
		return false;
	}
	
	
	public void aggiornaPercorso(List<Citta> tempSoluzione) {
		//System.out.println("sto aggiornando");
		percorso = new ArrayList<>(tempSoluzione);
	}
	
	public int umiditaPercorso(List <Citta> percorso, int mese) {
		int umidita=0;
		int giorno=1;
		for(Citta c:percorso) 
			if(c!=null) {
				
				umidita+= c.getUmiditaGiorno(mese, giorno);
				giorno++;
			}
				
			
		return umidita;
	}

}
