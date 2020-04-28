package it.polito.tdp.meteo.model;

public class Data {

	private String data;
	String annoS, meseS, giornoS;

	public Data(int anno,int mese, int giorno) {
		String annoS=Integer.toString(anno);
		if(mese<10)
			 meseS ="0"+ Integer.toString(mese);
		else
		 meseS= Integer.toString(mese);
		if(giorno<10)
			giornoS= "0"+Integer.toString(giorno);
		else
		 giornoS = Integer.toString(giorno);
		this.data=annoS+"-"+meseS+"-"+giornoS;
	}
	
	public String getData() {
		return data;
	}
}
