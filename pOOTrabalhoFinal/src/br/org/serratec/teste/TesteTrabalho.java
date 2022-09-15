package br.org.serratec.teste;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Scanner;

import br.org.serratec.enums.RelacaoDependente;
import br.org.serratec.exception.IdadeInvalidaException;
import br.org.serratec.exception.SalarioInvalidoException;
import br.org.serratec.model.Dependente;
import br.org.serratec.model.Funcionario;

public class TesteTrabalho {
	public static void main(String[] args) {
		final int idadeMaxima = 18;

		// Arquivo pre-definido
		String localArquivo = "C:/Users/ASUS/Serratec/pOOTrabalhoFinal/pOOFuncionarios.csv";
		String novoArquivo = "C:/Users/ASUS/Serratec/pOOTrabalhoFinal/NovoPOOFuncionarios.csv";

		LinkedHashSet<Funcionario> funcionarios = new LinkedHashSet<>();
		LinkedHashSet<Dependente> dependentes = new LinkedHashSet<>();

		// Arquivo com input do usuario
		/*
		 * Scanner userInput = new Scanner(System.in);
		 * System.out.println("Digite o local do arquivo de entrada: "); String
		 * localArquivo = userInput.next();
		 * System.out.println("Digite o local do arquivo de saida: "); String
		 * novoArquivo = userInput.next(); userInput.close();
		 */

		// Lendo o arquivo csv
		try {
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			File arquivo = new File(localArquivo);
			Scanner sc = new Scanner(arquivo);

			while (sc.hasNextLine()) {
				String linha = sc.nextLine();
				String[] var = linha.split(";");
				if (!linha.isEmpty()) {
					String nome = var[0];
					String cpf = var[1];
					LocalDate data = LocalDate.parse(var[2], formatoData);

					// Checa se o 4º campo do CSV inclui Filho, Sobrinho ou Outros, caso seja
					// verdadeiro, salva os dados da linha na lista de Dependentes
					if (var[3].toUpperCase().equals(RelacaoDependente.FILHO.getRelacao())
							|| var[3].toUpperCase().equals(RelacaoDependente.SOBRINHO.getRelacao())
							|| var[3].toUpperCase().equals(RelacaoDependente.OUTROS.getRelacao())) {
						LocalDate dataAtual = LocalDate.now();
						Period idade = Period.between(data, dataAtual);
						if (idade.getYears() < idadeMaxima) { // Utilizado variavel para facilitar a manutencao do
																// codigo
							String parentesco = var[3].toUpperCase();
							dependentes.add(new Dependente(nome, cpf, data, parentesco));
						} else {
							throw new IdadeInvalidaException();
						}

						// Checa se o 4º campo do CSV inclui é um valor Double maior que 0, caso seja
						// verdadeiro, salva os dados da linha na lista de Funcionarios
					} else if (Double.parseDouble(var[3]) > 0) {
						double salario = Double.parseDouble(var[3]);
						dependentes = new LinkedHashSet<>();
						funcionarios.add(new Funcionario(nome, cpf, data, salario, dependentes));
					} else {
						throw new SalarioInvalidoException();
					}
				}
			}
			sc.close();

			System.out.println("---------------Novo arquivo CSV:----------------\n");

			// Criando novo arquivo
			PrintWriter gravacaoArquivo = new PrintWriter(novoArquivo);

			for (Funcionario funcionario : funcionarios) {
				funcionario.calcularSalarioLiquido();
				String linha = funcionario.criarTextoCsv();
				gravacaoArquivo.printf("%s", linha);
				System.out.printf(funcionario.criarTextoCsv());
				for (Dependente dependente : funcionario.getDependentes()) {
					linha = dependente.criarTextoCsv();
					gravacaoArquivo.printf("%s", linha);
					System.out.printf(dependente.criarTextoCsv());
				}
				System.out.printf("\n");
				gravacaoArquivo.printf("\n");

			}
			gravacaoArquivo.close();

			System.out.println("--------Dados do novo arquivo:-----------------\n");

			for (Funcionario funcionario : funcionarios) {
				System.out.println("Funcionario:\n" + funcionario);
				if (funcionario.getDependentes().size() > 0) {
					System.out.println("Dependente(s): ");
				}
				for (Dependente dependente : funcionario.getDependentes()) {
					System.out.print(dependente + "\n");
				}
				System.out.println("*************************");
			}
			
		} catch (IdadeInvalidaException e) {
			System.out.println(e.getMessage());
		} catch (SalarioInvalidoException e) { // Avisa salario negativo ou nulo
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Valor invalido em coluna do csv");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado, verifique o diretório inserido");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro, mostre esse texto ao desenvolvedor para mais informações");
			e.printStackTrace();
		}
	}

}