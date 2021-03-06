package it.polimi.dei.swknights.carcassonne.Client.View.Handlers;

import it.polimi.dei.swknights.carcassonne.Client.View.ModuloView;
import it.polimi.dei.swknights.carcassonne.Events.AdapterTessera;
import it.polimi.dei.swknights.carcassonne.Events.Game.Controller.UpdateRotationEvent;

/**
 * Handler for update rotation event
 * @author dave
 *
 */
public class UpdateRotationHandler extends ModuloViewHandler
{
	public UpdateRotationHandler(ModuloView view)
	{
		super(view);
	}

	/**
	 * Notify the view that a card has just been rotated
	 */
	@Override
	public void visit(UpdateRotationEvent event)
	{
		this.view.aggiornaMappa();
		AdapterTessera tesseraNuova = event.getTessera();
		this.view.visualizzaTesseraCorrente(tesseraNuova);
		this.sveglia();
	}

}
