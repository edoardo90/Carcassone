package it.polimi.dei.swknights.carcassonne.Client.View.Cli;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import it.polimi.dei.swknights.carcassonne.Coordinate;
import it.polimi.dei.swknights.carcassonne.PuntoCardinale;
import it.polimi.dei.swknights.carcassonne.Client.View.DatiMappa;
import it.polimi.dei.swknights.carcassonne.Client.View.Vicinato;

class StampaMappa
{

	public StampaMappa(DatiMappa datiMappa)
	{
		this.datiMappa = datiMappa;
		int width = datiMappa.getLarghezza();
		int height = datiMappa.getAltezza();
		this.builder = new StringBuilder2D(new Dimension(width, height));
	}

	public void addTessera(Coordinate coordinate, String tessera, Vicinato vicinato)
	{
		Coordinate primoPuntoTessera = this.getPrimoPuntoTessera(coordinate);
		this.scriviTessera(primoPuntoTessera,tessera, vicinato);
		this.scriviVuoti(coordinate, vicinato);
	}
	
	@Override
	public String toString()
	{
		return this.builder.toString();
	}
	
	private Coordinate getPrimoPuntoTessera(Coordinate coordinate)
	{
		int x = coordinate.getX() - this.datiMappa.getMaxA(PuntoCardinale.ovest);
		int y = coordinate.getY() - this.datiMappa.getMaxA(PuntoCardinale.nord);
		x *= larghezzaTessera;
		y *= altezzaTessera;
		return new Coordinate(x, y);
	}

	private void scriviTessera(Coordinate primoPuntoTessera, String tessera, Vicinato vicinato)
	{
		this.scriviAngoli(primoPuntoTessera);
		this.scriviElementi(primoPuntoTessera, tessera);
		this.scriviLati(primoPuntoTessera, vicinato);
	}

	private void scriviVuoti(Coordinate coordinate, Vicinato vicinato)
	{
		for(PuntoCardinale punto : PuntoCardinale.values())
		{
			Coordinate coordinateDestinazione = coordinate.getCoordinateA(punto);
			this.scriviTesseraVuota(coordinateDestinazione);
		}

	}

	private void scriviTesseraVuota(Coordinate coordinate)
	{
		Coordinate primoPunto = this.getPrimoPuntoTessera(coordinate);
		this.scriviLati(primoPunto, new Vicinato(true));
		this.scriviCordinate(coordinate);
	}

	private void scriviCordinate(Coordinate coordinate)
	{
		final int x = larghezzaTessera/2;
		final int y = altezzaTessera/2;
		final Coordinate centroRelativo = new Coordinate(x, y);
		String coordinateString = coordinate.toString();
		Coordinate primoPunto = this.getPrimoPuntoTessera(coordinate);
		this.builder.scriviStringaCentrata(primoPunto.getCoordinateA(centroRelativo), coordinateString);
		
	}

	private void scriviLati(Coordinate primoPuntoTessera, Vicinato vicinato)
	{
		Map<PuntoCardinale, Lato> mapLati = this.getMapLati(primoPuntoTessera); 
		
		for(PuntoCardinale punto : PuntoCardinale.values())
		{
			char confine = (vicinato.haVicinoA(punto))? '.' : '#';
			Lato lato = mapLati.get(punto);
			Coordinate puntoInternoLato = lato.getPuntoMedioLato();
			if(this.controllaVuotoA(puntoInternoLato))
			{
				this.builder.fillConCarattere(lato.getStart(), lato.getEnd(), confine);
			}
		}
	}
	
	private Map<PuntoCardinale, Lato> getMapLati(Coordinate primoPuntoTessera)
	{
		Map<PuntoCardinale, Lato> mappaLati = new HashMap<PuntoCardinale, StampaMappa.Lato>();
		Coordinate incrementoX = new Coordinate(larghezzaTessera, 0);
		Coordinate incrementoY = new Coordinate(0, altezzaTessera);		
		
		Coordinate angoloNO = primoPuntoTessera;
		Coordinate angoloNE = primoPuntoTessera.getCoordinateA(incrementoX);
		Coordinate angoloSO = primoPuntoTessera.getCoordinateA(incrementoY);
		Coordinate angoloSE = primoPuntoTessera.getCoordinateA(incrementoX).getCoordinateA(incrementoY);
		
		mappaLati.put(PuntoCardinale.nord, new Lato(angoloNO, angoloNE));
		mappaLati.put(PuntoCardinale.sud, new Lato(angoloSO, angoloSE));
		mappaLati.put(PuntoCardinale.ovest, new Lato(angoloNO, angoloSO));
		mappaLati.put(PuntoCardinale.est, new Lato(angoloNE, angoloSE));
		
		return mappaLati;
	}
	
	private boolean controllaVuotoA(Coordinate coordinate)
	{
		return( this.builder.getCharAt(coordinate)==' ');
	}
	
	
	private void scriviElementi(Coordinate primoPuntoTessera, String tessera)
	{
		Map<PuntoCardinale, Lato> mapLati = this.getMapLati(primoPuntoTessera); 
		String elementi[] = tessera.split(" ");
		for(PuntoCardinale puntoCardinale : PuntoCardinale.values())
		{
			Lato lato = mapLati.get(puntoCardinale);
			Coordinate coordinataInserimento = lato.getCoordinateLabel(puntoCardinale, null);
			this.builder.scriviStringa(coordinataInserimento,
										elementi[puntoCardinale.toInt()]);
		}

	}

	private void scriviAngoli(Coordinate primoPuntoTessera)
	{
		Coordinate incrementoY = new Coordinate(0, altezzaTessera);
		Coordinate incrementoX = new Coordinate(larghezzaTessera, 0);
		this.builder.scriviCarattere(primoPuntoTessera, '+');
		this.builder.scriviCarattere(primoPuntoTessera.getCoordinateA(incrementoX).getCoordinateA(incrementoY), '+');
		this.builder.scriviCarattere(primoPuntoTessera.getCoordinateA(incrementoX), '+');
		this.builder.scriviCarattere(primoPuntoTessera.getCoordinateA(incrementoY), '+');
	}

	private DatiMappa			datiMappa;

	private StringBuilder2D		builder;

	private static final int	altezzaTessera		= 7;

	private static final int	larghezzaTessera	= 14;
	
	private class Lato 
	{
		public Lato(Coordinate start, Coordinate end)
		{
			this.start = start;
			this.end = end;
			int x = (start.getX() + end.getX()) / 2;
			int y = (start.getY() + end.getY()) / 2;
			this.media = new Coordinate(x, y);
		}
		
		public Coordinate getCoordinateLabel(PuntoCardinale puntoCardinale, String label)
		{
			PuntoCardinale puntoOpposto = puntoCardinale.opposto();
			Coordinate coordPuntoInterno = this.getPuntoMedioLato();
		
			Coordinate coordinataInserimento= coordPuntoInterno.getCoordinateA(puntoOpposto);
			
			if(puntoCardinale == PuntoCardinale.est)
			{
				int x = -label.length();
				Coordinate incremento = new Coordinate(x, 0);
				coordinataInserimento.getCoordinateA(incremento);
			}
			
			return null;
		}

		public Coordinate getPuntoMedioLato()
		{
			return this.media;
		}
		
		public Coordinate getStart()
		{
			return this.start;
		}
		
		public Coordinate getEnd()
		{
			return this.end;
		}
		
		private Coordinate media; 
		
		private Coordinate start;
		
		private Coordinate end;
	}

}