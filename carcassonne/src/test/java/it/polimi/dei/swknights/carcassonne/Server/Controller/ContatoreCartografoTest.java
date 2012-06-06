package it.polimi.dei.swknights.carcassonne.Server.Controller;

import it.polimi.dei.swknights.carcassonne.Server.Controller.ContatoreCartografo;
import it.polimi.dei.swknights.carcassonne.Server.Model.ModuloModel;
import it.polimi.dei.swknights.carcassonne.Server.Model.Tessere.Elemento;
import it.polimi.dei.swknights.carcassonne.Server.Model.Tessere.Lati;
import it.polimi.dei.swknights.carcassonne.Server.Model.Tessere.Link;
import it.polimi.dei.swknights.carcassonne.Server.Model.Tessere.Tessera;
import it.polimi.dei.swknights.carcassonne.Server.Model.Tessere.TesseraNormale;
import it.polimi.dei.swknights.carcassonne.Util.Coordinate;

import org.junit.Before;
import org.junit.Test;

public class ContatoreCartografoTest
{
	private  ModuloModel model;
	
	@Before
	public  void initializeTest() throws Exception
	{
		model = new ModuloModel();
	}
	
	@Test
	public void atLeastStart() throws Exception
	{
		ContatoreCartografo contatore = new ContatoreCartografo(model);
	
	}
	
	@Test 
	public void stradaPiccola() throws Exception
	{
		ContatoreCartografo contatore = new ContatoreCartografo(model);
		
		CostruzioneCoord[] stradella = this.stradella();
		
		
		
		
		for(int i=0; i<stradella.length; i++)
		{
			this.model.posizionaTessera(stradella[i].tessera, stradella[i].coord);
			System.out.println(" Tessera Numero: " + i );
			contatore.riceviCoordinateTessera(stradella[i].coord);
		}
		 
		
	}

	@Test
	public void testCostruzioniCorrette() throws Exception
	{

	}

	@Test
	public void testCostruzioniScorrette() throws Exception
	{

	}
	
     public CostruzioneCoord[]  stradella()
	{
		CostruzioneCoord[] stradaPiccola = new CostruzioneCoord[5];
		
		for(int i=0; i<5; i++)
		{
			stradaPiccola[i] = new CostruzioneCoord();
		}
		
		Tessera incrocio0 = 
				new TesseraNormale(creaLatiIncrocioAQuattro(), creaLinkIncrocioAQuattro());
		Coordinate c0 = new Coordinate(-1, 0);
		
		Tessera t1 = new TesseraNormale(creaLatiStradaEO(), creaLinkStradaEO());
		Coordinate c1 = new Coordinate(0, 0);
		
		Tessera t2 = new TesseraNormale(creaLatiStradaEO(), creaLinkStradaEO());
		Coordinate c2 = new Coordinate(1, 0);
		
		Tessera t3 = new TesseraNormale(creaLatiStradaEO(), creaLinkStradaEO());
		Coordinate c3 = new Coordinate(2, 0);
		
		Tessera incrocio4 = 
				new TesseraNormale(creaLatiIncrocioAQuattro(), creaLinkIncrocioAQuattro());
		Coordinate c4 = new Coordinate(3, 0);
		
		stradaPiccola[0].daiCoppia(incrocio0, c0);
		stradaPiccola[1].daiCoppia(t1, c1);
		stradaPiccola[2].daiCoppia(t2, c2);
		stradaPiccola[3].daiCoppia(t3, c3);
		stradaPiccola[4].daiCoppia(incrocio4, c4);
	
		return stradaPiccola;
	}

	private Lati creaLatiStradaEO()
	{
		Lati latiCreandi;
		Elemento nord = Elemento.prato;
		Elemento sud = Elemento.prato;
		Elemento ovest = Elemento.strada;
		Elemento est = Elemento.strada;
		latiCreandi = new Lati(nord, sud, ovest, est);
		return latiCreandi;
	}

	private Link creaLinkStradaEO() throws IllegalArgumentException
	{
		/* NS(0), NE(1), NW(2), WE(3), SE(4), SW(5); */
		boolean[] bl = { false, false, false, true, false, false };
		Link l = new Link(bl);
		return l;
	
	}

	private Lati creaLatiIncrocioAQuattro()
	{
		Lati latiCreandi;
		Elemento nord = Elemento.strada;
		Elemento sud = Elemento.strada;
		Elemento ovest = Elemento.strada;
		Elemento est = Elemento.strada;
		latiCreandi = new Lati(nord, sud, ovest, est);
		return latiCreandi;
	}

	private Link creaLinkIncrocioAQuattro() throws IllegalArgumentException
	{
		/* NS(0), NE(1), NW(2), WE(3), SE(4), SW(5); */
		boolean[] bl = { false, false, false, false, false, false };
		Link l = new Link(bl);
		return l;
	
	}

	static class CostruzioneCoord
	{
		
		public Tessera tessera;
		public Coordinate coord;
		
		public void daiCoppia(Tessera t1, Coordinate c1)
		{
			this.tessera = t1;
			this.coord = c1;
			
		}
		
	}
	

}
