package it.polimi.dei.swknights.carcassonne.server.Model;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import it.polimi.dei.swknights.carcassonne.Coordinate;
import it.polimi.dei.swknights.carcassonne.Events.AdapterTessera;
import it.polimi.dei.swknights.carcassonne.Events.AdapterTesseraObject;
import it.polimi.dei.swknights.carcassonne.Events.Model;
import it.polimi.dei.swknights.carcassonne.Events.View;
import it.polimi.dei.swknights.carcassonne.Events.Game.Controller.InizioGiocoEvent;
import it.polimi.dei.swknights.carcassonne.Events.Game.Controller.UpdatePositionEvent;
import it.polimi.dei.swknights.carcassonne.Exceptions.FinitiColoriDisponibiliException;
import it.polimi.dei.swknights.carcassonne.Exceptions.PartitaFinitaException;
import it.polimi.dei.swknights.carcassonne.Exceptions.TesseraNonTrovataException;
import it.polimi.dei.swknights.carcassonne.server.Model.Giocatore.Giocatore;
import it.polimi.dei.swknights.carcassonne.server.Model.Tessere.Tessera;

public class ModuloModel implements Model
{
	/**
	 * Default constructor. Initialize the data structures
	 */

	public ModuloModel()
	{
		this.datiPartita = new DatiPartita();
		this.listeners = new ArrayList<View>();
	}

	/**
	 * Add Listener to the listerner list
	 * 
	 * @param listener
	 *            to be added, if it is not a ViewListener, it would not be
	 *            added to the list
	 * @see it.polimi.dei.swknights.carcassonne.Events.EventSource#addListener(java.util.EventListener)
	 */

	public void addListener(EventListener eventListener)
	{
		View controllerListener;
		if (eventListener instanceof View)
		{
			controllerListener = (View) eventListener;
			this.listeners.add(controllerListener);
		}
	}

	/**
	 * Remove listener to the listener list
	 * 
	 * @param listener
	 *            to be removed from the listener list
	 * @see it.polimi.dei.swknights.carcassonne.Events.EventSource#removeListener(java.util.EventListener)
	 */

	public void removeListener(EventListener eventListener)
	{
		this.listeners.remove(eventListener);
	}

	public void fire(EventObject event)
	{
		for (View listener : this.listeners)
		{
			listener.riceviModificheModel(event);
		}
	}

	public void creaGiocatori(int numeroGiocatori)
	{
		try
		{
			for (int i = 1; i < numeroGiocatori; i++)
			{
				this.datiPartita.addGiocatore();
			}
		}
		catch (FinitiColoriDisponibiliException e)
		{
			throw new IllegalArgumentException("hai chiesto troppi giocatori");
		}

	}

	public void iniziaGioco(int numeroGiocatori)
	{
		Tessera primaTessera = this.datiPartita.pescaPrimaTessera();
		AdapterTessera tessera = new AdapterTesseraObject(primaTessera);
		this.creaGiocatori(numeroGiocatori);
		this.datiPartita.getAreaDiGioco().addTessera(new Coordinate(0, 0), primaTessera);
		this.fire(new InizioGiocoEvent(this, tessera, this.getGiocatoreCorrente().getColore()));
	}

	public void posizionaTessera(Tessera tessera, Coordinate coordinate)
	{
		AreaDiGioco areaDiGioco = this.datiPartita.getAreaDiGioco();
		Giocatore giocatore = this.datiPartita.getGiocatoreCorrente();
		areaDiGioco.addTessera(coordinate, tessera);
		this.fire(new UpdatePositionEvent(tessera, coordinate, giocatore.getColore(), this));
	}

	public Tessera getTessera(Coordinate coordinate) throws TesseraNonTrovataException
	{
		return this.datiPartita.getAreaDiGioco().getTessera(coordinate);
	}

	public Giocatore getGiocatoreCorrente()
	{
		return this.datiPartita.getGiocatoreCorrente();
	}

	public Tessera getTesseraDaMazzo() throws PartitaFinitaException
	{
		return this.datiPartita.pescaTesseraDalMazzo();
	}

	public List<Giocatore> getListaGiocatori()
	{
		return this.datiPartita.getListaGiocatori();
	}

	private DatiPartita	datiPartita;

	private List<View>	listeners;

}
