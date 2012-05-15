package it.polimi.dei.swknights.carcassonne.server.Model.Tessere;

import it.polimi.dei.swknights.carcassonne.PuntoCardinale;
import it.polimi.dei.swknights.carcassonne.server.Controller.Costruzione;
import it.polimi.dei.swknights.carcassonne.server.Model.Segnalino;

import java.util.HashMap;
import java.util.Map;

/**
 * That class encapsulate the four elements of a card
 * 
 * @author Edo & Dave
 * 
 */
public class Lati
{

	public Lati(Elemento nord, Elemento sud, Elemento ovest, Elemento est)
	{
		this.nord = nord;
		this.sud = sud;
		this.ovest = ovest;
		this.est = est;
		this.datiSegnalino = new DatiSegnalino();
	}

	public Lati(Elemento[] elementiTessera)
	{
		this(elementiTessera[PuntoCardinale.nord.toInt()], elementiTessera[PuntoCardinale.sud.toInt()],
				elementiTessera[PuntoCardinale.ovest.toInt()], elementiTessera[PuntoCardinale.est.toInt()]);

	}

	/**
	 * gets the element on the specified cardinal point
	 * 
	 * @param puntoCardinale
	 *            cardinal point
	 * @return the element on that cardinal point
	 */
	public Elemento getTipoElementoInDirezione(PuntoCardinale puntoCardinale)
	{
		Elemento[] elementi =
		{ this.nord, this.sud, this.ovest, this.est };
		return elementi[puntoCardinale.toInt()];
	}

	/**
	 * rotate 90° clockwise the 4 elements of the card.
	 */
	public void ruota()
	{
		Elemento nordTmp = this.nord;
		this.nord = this.ovest;
		this.ovest = this.sud;
		this.sud = this.est;
		this.est = nordTmp;
	}

	public Map<Costruzione, PuntoCardinale> getMappaCostruzioniPrimitive(Tessera tessera)
	{
		Map<Costruzione, PuntoCardinale> listaCostruzioni = new HashMap<Costruzione, PuntoCardinale>();
		Elemento[] elementi =
		{ this.nord, this.sud, this.est, this.ovest };
		PuntoCardinale[] punti =
		{ PuntoCardinale.nord, PuntoCardinale.sud, PuntoCardinale.est, PuntoCardinale.ovest };
		int indicePuntiCardinali = 0;
		for (Elemento elemento : elementi)
		{
			Costruzione costruzione = elemento.getCostruzione(tessera);
			if (costruzione != null)
				listaCostruzioni.put(costruzione, punti[indicePuntiCardinali]);
			indicePuntiCardinali++;
		}
		return listaCostruzioni;
	}
	
	public void setSegnalino(Segnalino segnalino, PuntoCardinale puntoCardinale)
	{
		this.datiSegnalino.segnalino=segnalino;
		this.datiSegnalino.puntoCardinale=puntoCardinale;
	}

	@Override
	public String toString()
	{
		Elemento[] elementi ={this.nord, this.sud, this.ovest, this.est};
		StringBuilder componiStringhe = new StringBuilder();
		for(PuntoCardinale puntoCardinale : PuntoCardinale.values())
		{
			String stringPuntoCardinale = puntoCardinale.toString();
			String stringElemento = elementi[puntoCardinale.toInt()].toString();
			componiStringhe.append(stringPuntoCardinale + '=' + stringElemento + ' ');
		}
		String stringSegnalino = printSegnalino();
		if (stringSegnalino.length() > 0)
		{
			final int caratteriPerElemento= 4;
			final int offsetPrimoElemento = caratteriPerElemento -1;
			PuntoCardinale direzione = this.datiSegnalino.puntoCardinale;
			int index = direzione.toInt() * caratteriPerElemento + offsetPrimoElemento;
			componiStringhe.insert(index, stringSegnalino);
		}
		return componiStringhe.toString();
	}

	private String printSegnalino()
	{
		Segnalino segnalino = this.datiSegnalino.segnalino;
		if (segnalino != null)
		{
			return segnalino.toString();
		} else
			return "";
	}

	private Elemento		nord;

	private Elemento		sud;

	private Elemento		ovest;

	private Elemento		est;

	private DatiSegnalino	datiSegnalino;

	private class DatiSegnalino
	{
		public Segnalino		segnalino;
		public PuntoCardinale	puntoCardinale;
	}

}