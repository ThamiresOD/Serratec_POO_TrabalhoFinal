package br.org.serratec.exception;

public class SalarioInvalidoException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "Valor inválido no campo de salário do funcionário";
	}
}
