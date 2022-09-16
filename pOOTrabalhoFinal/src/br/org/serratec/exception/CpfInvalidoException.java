package br.org.serratec.exception;

public class CpfInvalidoException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "Cpf inválido, esse campo precisa ter 11 caracteres";
	}

}
