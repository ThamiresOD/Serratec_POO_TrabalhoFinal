package br.org.serratec.enums;

public enum EnumINSS {

	RENDAA(1212.00, 0.075, 0.), RENDAB(2427.35, 0.09, 18.18), RENDAC(3641.03, 0.12, 91.), RENDAD(7087.22, 0.14, 163.82);

	private Double salario, aliquota, parcelaDeduzir;

	private EnumINSS(Double salario, Double aliquota, Double parcelaDeduzir) {
		this.salario = salario;
		this.aliquota = aliquota;
		this.parcelaDeduzir = parcelaDeduzir;
	}

	public Double getSalario() {
		return salario;
	}

	public Double getAliquota() {
		return aliquota;
	}

	public Double getParcelaDeduzir() {
		return parcelaDeduzir;
	}
}