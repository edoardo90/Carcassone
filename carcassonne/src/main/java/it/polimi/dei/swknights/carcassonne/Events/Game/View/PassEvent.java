package it.polimi.dei.swknights.carcassonne.Events.Game.View;

import it.polimi.dei.swknights.carcassonne.Events.Game.ComandoView;

public class PassEvent extends ViewEvent
{

	public PassEvent(Object source)
	{
		super(source);
	}

	@Override
	protected void setComando()
	{
		this.comando = ComandoView.pass;

	}

	private static final long	serialVersionUID	= 2085506187547788810L;

}