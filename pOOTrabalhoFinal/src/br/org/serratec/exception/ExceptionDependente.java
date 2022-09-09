package br.org.serratec.exception;

public class ExceptionDependente extends ArithmeticException{
	
// dependente < 18
// CPF unico
	public static int calcular(int idade) { if (idade == 18) { throw new
	     ArithmeticException("\n\n Desculpe, idade de dependentes precisa ser menor que 18 anos. \n\n");

	     } return idade;
	}
}