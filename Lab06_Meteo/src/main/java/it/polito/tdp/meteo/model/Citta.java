package it.polito.tdp.meteo.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Citta {
	
	
	private String nome;
	private List<Rilevamento> rilevamenti;
	private int counter = 0;
	
	
	
	public Citta(String nome) {
		this.nome = nome;
	}
	
	public Citta(String nome, List<Rilevamento> rilevamenti) {
		this.nome = nome;
		this.rilevamenti = rilevamenti;
	}

	public String getNome() {
		return nome;
	}
   public int getUmiditaGiorno(int mese, int giorno) {
	   Data data = new Data(2013, mese, giorno);
	   
	   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	   for(Rilevamento r: rilevamenti) 
		   if(r!=null) {
			   String dataS = df.format(r.getData());
			   if(dataS.contentEquals(data.getData()))
				   return r.getUmidita();
		   }
	   return -1;
   }
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rilevamento> getRilevamenti() {
		return rilevamenti;
	}

	public void setRilevamenti(List<Rilevamento> rilevamenti) {
		this.rilevamenti = rilevamenti;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void increaseCounter() {
		this.counter += 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Citta other = (Citta) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
	

}
