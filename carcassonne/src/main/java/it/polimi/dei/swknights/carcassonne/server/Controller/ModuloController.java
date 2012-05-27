package it.polimi.dei.swknights.carcassonne.server.Controller;

import it.polimi.dei.swknights.carcassonne.Coordinate;
import it.polimi.dei.swknights.carcassonne.Events.Controller;
import it.polimi.dei.swknights.carcassonne.Events.Game.Controller.FinePartitaEvent;
import it.polimi.dei.swknights.carcassonne.Events.Game.Controller.UpdateTurnoEvent;
import it.polimi.dei.swknights.carcassonne.Events.Game.View.ViewEvent;
import it.polimi.dei.swknights.carcassonne.Exceptions.PartitaFinitaException;
import it.polimi.dei.swknights.carcassonne.server.Controller.Handlers.ControllerHandler;
import it.polimi.dei.swknights.carcassonne.server.Controller.Handlers.PassHandler;
import it.polimi.dei.swknights.carcassonne.server.Controller.Handlers.PlaceHandler;
import it.polimi.dei.swknights.carcassonne.server.Controller.Handlers.RuotaHandler;
import it.polimi.dei.swknights.carcassonne.server.Controller.Handlers.TileHandler;
import it.polimi.dei.swknights.carcassonne.server.Model.ModuloModel;
import it.polimi.dei.swknights.carcassonne.server.Model.Giocatore.Giocatore;
import it.polimi.dei.swknights.carcassonne.server.Model.Tessere.Tessera;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that implements theController of the MVC pattern Contains all the
 * methods to manage event handling and the high level game logic.
 * 
 * @author dave
 * 
 */

public class ModuloController implements Controller
{
	/**
	 * Default Constructor. Initialize data structures
	 * 
	 */
	public ModuloController(ModuloModel model)
	{
		this.model = model;
		this.contaPunti = new ContatoreCartografo(this.model);
		this.visitorHandlers = this.attivaHandler();
	}

	public void run()
	{
		this.cominciaGioco();
		try
		{
			while (true)
			{
				this.primaMossaTurno();
				this.attendiPosizionamentoTessera();
			}

		}
		/*
		 * catch (PartitaFinitaException e) { this.model.fire(new
		 * FinePartitaEvent(this, this.getMappaPunteggi()));
		 * 
		 * }
		 */
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Method that should be called when firing an event to the Controller
	 * 
	 * @see it.polimi.dei.swknights.carcassonne.Events.Controller#riceviInput()
	 */
	public void riceviInput(ViewEvent event)
	{
		for (ControllerHandler visitorHandler : this.visitorHandlers)
		{
			event.accept(visitorHandler);
		}
	}

	public void cominciaGioco()
	{
		System.out.println("COMINCIA GIOCO");
		this.primaMossaPartita();
	}

	/*
	 * public Tessera getTesseraCorrente() { return this.tesseraCorrente; }
	 */

	public ContatoreCartografo getContapunti()
	{
		return this.contaPunti;
	}

	public boolean ruotaOk()
	{
		//TODO controllare se puoi ruotare
		return true;
	}

	private void primaMossaPartita()
	{
		this.model.iniziaGioco(2);
	}

	private void primaMossaTurno()
	{
		System.out.println("INIZIO TURNO");
		try
		{
			this.model.cominciaTurno();
		}
		catch (PartitaFinitaException e)
		{
			//TODO: incompleto
			this.model.fire(new FinePartitaEvent(this.model, this.getMappaPunteggi()));
		}
	}

	private List<ControllerHandler> attivaHandler()
	{
		List<ControllerHandler> handlerList = new ArrayList<ControllerHandler>();
		handlerList.add(new RuotaHandler(this, this.model));
		handlerList.add(new PlaceHandler(this, this.model));
		handlerList.add(new TileHandler(this, this.model));
		handlerList.add(new PassHandler());
		return handlerList;
	}

	synchronized private void attendiPosizionamentoTessera() throws InterruptedException
	{
		while (!this.tesseraPosizionata)
		{
			System.out.println("WAIT");
			wait();
		}
	}

	private Map<Color, Integer> getMappaPunteggi()
	{
		Map<Color, Integer> mapPunteggi = new HashMap<Color, Integer>();
		List<Giocatore> giocatori = this.model.getListaGiocatori();
		for (Giocatore giocatore : giocatori)
		{
			mapPunteggi.put(giocatore.getColore(), giocatore.getPunti());
		}
		return mapPunteggi;
	}

	private boolean					tesseraPosizionata;

	private List<ControllerHandler>	visitorHandlers;

	private ContatoreCartografo		contaPunti;

	private final ModuloModel		model;

	private static final Coordinate	COORD_PRIMA_TESSERA	= new Coordinate(0, 0);

}