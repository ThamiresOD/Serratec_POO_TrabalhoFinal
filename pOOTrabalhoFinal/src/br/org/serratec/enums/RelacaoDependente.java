package br.org.serratec.enums;

public enum RelacaoDependente {

	FILHO("FILHO"), SOBRINHO("SOBRINHO"), OUTROS("OUTROS");

	private String relacao;

	private RelacaoDependente(String relacao) {
		this.relacao = relacao;
	}

	public String getRelacao() {
		return relacao;
	}

}
