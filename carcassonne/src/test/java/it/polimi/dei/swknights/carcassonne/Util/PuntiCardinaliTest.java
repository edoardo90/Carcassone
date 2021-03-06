package it.polimi.dei.swknights.carcassonne.Util;

import static org.junit.Assert.assertTrue;
import it.polimi.dei.swknights.carcassonne.Util.PuntoCardinale;

import java.util.LinkedList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class PuntiCardinaliTest
{
	private static List<PuntoCardinale>	Punti			= new LinkedList<PuntoCardinale>();
	private static List<PuntoCardinale>	PuntiOppostiGot	= new LinkedList<PuntoCardinale>();
	private static List<PuntoCardinale>	PuntiOppostiExp	= new LinkedList<PuntoCardinale>();

	@BeforeClass
	public static void initializeTest() throws Exception
	{
		Punti.add(PuntoCardinale.nord);
		Punti.add(PuntoCardinale.sud);
		Punti.add(PuntoCardinale.ovest);
		Punti.add(PuntoCardinale.est);

		PuntiOppostiExp.add(PuntoCardinale.sud);
		PuntiOppostiExp.add(PuntoCardinale.nord);
		PuntiOppostiExp.add(PuntoCardinale.est);
		PuntiOppostiExp.add(PuntoCardinale.ovest);
	}

	@Test
	public void testPuntiOpposti() throws Exception
	{
		for (PuntoCardinale p : Punti)
		{
			PuntiOppostiGot.add(p.opposto());
		}
		for (int i = 0; i < PuntiOppostiGot.size(); i++)
		{
			PuntoCardinale pGot = PuntiOppostiGot.get(i);
			PuntoCardinale pExp = PuntiOppostiExp.get(i);
			assertTrue("opposto fail!", pGot.toInt() == pExp.toInt());
		}

	}

	@Test
	public void testStringheScorrette() throws Exception
	{

		assertTrue(true);

	}

}
