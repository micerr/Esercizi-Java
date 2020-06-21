package abbigliamento;

public class Capo {
	private String nome;
	private Modello modello;
	private Materiale materiale;
	private Colore colore;
	
	public Capo(Modello modello, Materiale materiale, Colore colore) {
		this.modello=modello;
		this.materiale=materiale;
		this.colore=colore;
	}
	public Capo(String nome,Modello modello, Materiale materiale, Colore colore) {
		this.nome=nome;
		this.modello=modello;
		this.materiale=materiale;
		this.colore=colore;
	}

	public double prezzo() {
		return modello.getCosto()+modello.getQuantita()*materiale.getCosto();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(modello);
		builder.append(" ");
		builder.append(colore);
		builder.append(" di ");
		builder.append(materiale);
		return builder.toString();
	}

	/**
	 * @return the modello
	 */
	public Modello getModello() {
		return modello;
	}

	/**
	 * @return the materiale
	 */
	public Materiale getMateriale() {
		return materiale;
	}

	/**
	 * @return the colore
	 */
	public Colore getColore() {
		return colore;
	}
	
	public String getNome() {
		return nome;
	}

}