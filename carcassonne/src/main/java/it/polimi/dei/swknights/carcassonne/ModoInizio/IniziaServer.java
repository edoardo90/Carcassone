package it.polimi.dei.swknights.carcassonne.ModoInizio;

import java.net.Socket;

import it.polimi.dei.swknights.carcassonne.Client.CarcassonneSocket;
import it.polimi.dei.swknights.carcassonne.Client.ProxyController.ProxyController;
import it.polimi.dei.swknights.carcassonne.Events.Controller;
import it.polimi.dei.swknights.carcassonne.server.CarcassonneServer;
import it.polimi.dei.swknights.carcassonne.server.Controller.ModuloController;
import it.polimi.dei.swknights.carcassonne.server.Model.ModuloModel;
import it.polimi.dei.swknights.carcassonne.server.ProxyView.ProxyView;

public class IniziaServer extends Inizio
{

	
	@Override
	public void inizia()
	{
		ModuloModel model = new ModuloModel();
		Controller controller = new ModuloController(model);
		
		System.out.println("Server");
		new CarcassonneServer(); //TODO: così fa abbastanza schifo...
		socket = CarcassonneSocket.dammiSocket();			
		view = new ProxyView(socket);
		model.addListener(view);
		
		superStarDestroyer.execute(controller);
		superStarDestroyer.execute(view);
		
	}
	
	
	Socket socket = null;
	ProxyController controller = null;
	ProxyView view = null; 

}