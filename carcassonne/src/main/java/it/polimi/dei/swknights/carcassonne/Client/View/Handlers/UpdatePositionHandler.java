package it.polimi.dei.swknights.carcassonne.Client.View.Handlers;

import it.polimi.dei.swknights.carcassonne.Coordinate;
import it.polimi.dei.swknights.carcassonne.Client.View.ModuloView;
import it.polimi.dei.swknights.carcassonne.Events.Game.Controller.UpdatePositionEvent;

public class UpdatePositionHandler extends ViewHandler
{
	public UpdatePositionHandler(ModuloView view)
	{
		this.view=view;
	}
	
	@Override
	public void visit(UpdatePositionEvent  event)
	{
		UpdatePositionEvent upe = (UpdatePositionEvent) event;
		Coordinate coord = upe.getCoordinate();
		this.view.posizionaTessera(coord);
		this.view.aggiornaMappa();
	}
	
	private ModuloView	view;
}
