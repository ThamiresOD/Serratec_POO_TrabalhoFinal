package br.org.serratec.exception;

public class ValorInvalidoException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "Valor inválido no campo de parentesco do dependente ou salário do funcionário";
	}
}
