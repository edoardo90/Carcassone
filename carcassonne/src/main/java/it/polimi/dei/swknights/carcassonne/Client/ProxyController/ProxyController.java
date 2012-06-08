package it.polimi.dei.swknights.carcassonne.Client.ProxyController;

import it.polimi.dei.swknights.carcassonne.Debug;
import it.polimi.dei.swknights.carcassonne.Client.ProxyController.ProxyControllerHandlers.PassHandler;
import it.polimi.dei.swknights.carcassonne.Client.ProxyController.ProxyControllerHandlers.PlaceHandler;
import it.polimi.dei.swknights.carcassonne.Client.ProxyController.ProxyControllerHandlers.ProxyControllerHandler;
import it.polimi.dei.swknights.carcassonne.Client.ProxyController.ProxyControllerHandlers.RuotaHandler;
import it.polimi.dei.swknights.carcassonne.Client.ProxyController.ProxyControllerHandlers.TileHandler;
import it.polimi.dei.swknights.carcassonne.Events.Game.Controller.ControllerEvent;
import it.polimi.dei.swknights.carcassonne.ModuliAstratti.Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 */
public class ProxyController extends AbstractConnessioneController implements Model
{

	public ProxyController(Socket socket) throws IOException
	{

		this.connessione = new ConnessioneControllerSocket(socket, this);
		this.inizializzaHandlers();
		this.contattaServerInizia(socket);
	}

	public ProxyController() // RMI
	{

		this.connessione = new ConnessioneControllerRMI();
	}

	public void setRequestString(String requestString)
	{
		this.requestString = requestString;
	}

	@Override
	public void run()
	{
		Debug.print(" sono proxy controller, run");
		int n = 0;
		boolean nonCacciatoUser = true;
		while (nonCacciatoUser )
		{

			while (true)
			{

				try
				{
					this.connessione.riceviInput();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

		}

		this.connessione.close();
	}

	private void inviaSocket()
	{

		this.connessione.invia(this.requestString);
	}

	private void inviaRMI(ControllerEvent event)
	{
		this.connessione.invia(event);

	}

	@Override
	public void request()
	{
	}

	public void fire(ControllerEvent event)
	{
		// TODO Auto-generated method stub

	}

	// inizio fatto comunque via socket?
	private void contattaServerInizia(Socket socket)
	{
		boolean contatto = false;
		Debug.print(" sono proxy controller - contattaServer ");
		try
		{
			printer = new PrintWriter( socket.getOutputStream());
			printer.println("connect");
			printer.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
			try
			{

				PrintWriter printer = new PrintWriter(socket.getOutputStream());
				printer.println("connection request by " + socket.getLocalAddress() + ": "
						+ socket.getLocalPort());
				printer.flush();
				Scanner scann = new Scanner(socket.getInputStream());
				String s = scann.nextLine();
				if (s.length() > 0)
				{
					contatto = true;
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		*/
	}

	private void inizializzaHandlers()
	{
		this.handlers = new ArrayList<ProxyControllerHandler>();

		this.handlers.add(new PassHandler(this));
		this.handlers.add(new PlaceHandler(this));
		this.handlers.add(new RuotaHandler(this));
		this.handlers.add(new TileHandler(this));

	}

	PrintWriter printer;
	private List<ProxyControllerHandler>	handlers;
	private String							requestString;
	private ConnessioneController			connessione;

}
