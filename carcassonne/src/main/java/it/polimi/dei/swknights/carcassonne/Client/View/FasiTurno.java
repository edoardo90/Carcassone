package it.polimi.dei.swknights.carcassonne.Client.View;

public enum FasiTurno {
	Inizio("Place card or rotate"), Media("Tile or pass"), Attesa("wait server response...");

	private FasiTurno(String messaggio)
	{
		this.messaggioUtente = messaggio;
	}

	public String toString()
	{
		return this.messaggioUtente;
	}

	// TODO: controllare
	public FasiTurno nextPhase()
	{
		switch (this)
		{
			case Inizio:
				return Media;
			case Media:
				return Attesa;
			case Attesa:
				return Inizio;

			default:
				return Attesa;
		}
	}

	private String	messaggioUtente;

}