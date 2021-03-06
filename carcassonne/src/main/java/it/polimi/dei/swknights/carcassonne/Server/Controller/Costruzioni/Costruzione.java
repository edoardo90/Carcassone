package it.polimi.dei.swknights.carcassonne.Server.Controller.Costruzioni;

import it.polimi.dei.swknights.carcassonne.Server.Model.Giocatore.Segnalino;
import it.polimi.dei.swknights.carcassonne.Server.Model.Tessere.Tessera;
import it.polimi.dei.swknights.carcassonne.Util.Punteggi;
import it.polimi.dei.swknights.carcassonne.Util.PunteggiSegnalini;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that implements the abstract idea of a human construction is used as
 * the base of the algorithm use to calculate victory points generated by
 * controlling constructions
 * 
 */

public abstract class Costruzione
{
	/**
	 * Default constructor Simply initialize a new construction whit at least
	 * one element
	 * 
	 * @param tessera
	 *            the first element of the construction
	 */

	public Costruzione(Tessera tessera)
	{
		this.contatoreSegnalini = new PunteggiSegnalini();
		this.elementi = new HashSet<Tessera>();
		this.setSegnalini = new HashSet<Segnalino>();
		this.elementi.add(tessera);
	}

	/**
	 * Method that calculates the victory points generated from structure.
	 * 
	 * @return the point generated from this construction by the player
	 */

	public Punteggi getPunteggi(boolean costruzioneCompletata)
	{
		List<Color> controllori = this.controllataDa();
		Punteggi mappaPunteggi = new Punteggi();
		for (Color colore : controllori)
		{
			mappaPunteggi.addPunteggi(colore, this.getPuntiCostruzione(costruzioneCompletata));
		}
		return mappaPunteggi;
	}

	/**
	 * Method used to join two constructions
	 * 
	 * @param costruzione
	 *            The construction to be joined with
	 */

	public void joinCostruzioni(Costruzione costruzione)
	{
		this.elementi.addAll(costruzione.elementi);
		this.contatoreSegnalini.addPunteggi(costruzione.contatoreSegnalini);
		this.setSegnalini.addAll(costruzione.setSegnalini);
	}

	/**
	 * Method used to add a marker to this construction
	 * 
	 * @param segnalino
	 *            The marker to be added to this construction
	 */

	public void addSegnalino(Segnalino segnalino)
	{
		this.contatoreSegnalini.addPunteggi(segnalino.getColore(), 1);
		this.setSegnalini.add(segnalino);
	}

	/**
	 * Method used to retrive the players that owns this construction
	 * 
	 * @return the list of the colors of the player controlling this
	 *         construction
	 */

	public List<Color> controllataDa()
	{
		return this.contatoreSegnalini.getVincitoriAttuale();
	}

	/**
	 * Observer method
	 * 
	 * @return the number of the tiles of the construction
	 */

	public int contaElementi()
	{
		return this.elementi.size();
	}

	/**
	 * Getetr method
	 * 
	 * @return the list of the tiles of the construction
	 */

	public Set<Tessera> getTessere()
	{
		return this.elementi;
	}
	
	/**
	 * Method that return whereas the specified marker should be removed from a tile
	 * @param segnalino marker found in tiles
	 * @return true ifthe marker belongs to this constructions
	 */

	public boolean daTogliere(Segnalino segnalino)
	{
		return this.setSegnalini.contains(segnalino);
	}
	
	/**
	 * String serialization of this
	 */

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder("tessere componenti: ");
		for (Tessera t : this.elementi)
		{
			builder.append(t.toString()).append(" ");
		}
		builder.append(" segnalini: ");
		for (Segnalino segnalino : this.setSegnalini)
		{
			builder.append(segnalino.toString());
		}
		return builder.toString();
	}

	protected abstract int getPuntiCostruzione(boolean costruzioneCompletata);

	protected int getSize()
	{
		return this.elementi.size();
	}

	private Set<Tessera>	elementi;

	private Set<Segnalino>	setSegnalini;

	private Punteggi		contatoreSegnalini;
}
