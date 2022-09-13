package br.org.serratec.teste;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Scanner;

import br.org.serratec.enums.RelacaoDependente;
import br.org.serratec.exception.DependenteException;
import br.org.serratec.exception.ValorInvalidoException;
import br.org.serratec.model.Dependente;
import br.org.serratec.model.Funcionario;

public class TesteTrabalhoSemString {
	public static void main(String[] args) {

		//Arquivo pre-definido
		String localArquivo = "C:/Users/ASUS/Serratec/pOOTrabalhoFinal/pOOFuncionarios.csv";
		String novoArquivo = "C:/Users/ASUS/Serratec/pOOTrabalhoFinal/NovoPOOFuncionarios.csv";
		
		LinkedHashSet<Funcionario> funcionarios = new LinkedHashSet<>();
		LinkedHashSet<Dependente> dependentes = new LinkedHashSet<>();
		
		//Arquivo com input do usuario
		/*Scanner userInput = new Scanner(System.in);
		System.out.println("Digite o local do arquivo de entrada: ");
		String localArquivo = userInput.next();
		System.out.println("Digite o local do arquivo de saida: ");
		String novoArquivo = userInput.next();
		userInput.close();*/

		try {
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy,MM,dd");

			File arquivo = new File(localArquivo);
			Scanner sc = new Scanner(arquivo);

			while (sc.hasNextLine()) {
				String linha = sc.nextLine();
				String[] var = linha.split(";");
				if (!linha.isEmpty()) {
					String nome = var[0];
					String cpf = var[1];
					LocalDate data = LocalDate.parse(var[2], formatoData);
					if (var[3].toUpperCase().equals(RelacaoDependente.FILHO.getRelacao())
							|| var[3].toUpperCase().equals(RelacaoDependente.SOBRINHO.getRelacao())
							|| var[3].toUpperCase().equals(RelacaoDependente.OUTROS.getRelacao())) {
						LocalDate dataAtual = LocalDate.now();
						Period idade = Period.between(data, dataAtual);
						if (idade.getYears() < 18) {
							String parentesco = var[3].toUpperCase();
							dependentes.add(new Dependente(nome, cpf, data, parentesco));
						} else {
							throw new DependenteException();
						}
					} else if (Double.parseDouble(var[3]) >= 0 || Double.parseDouble(var[3]) < 0) {
						double salario = Double.parseDouble(var[3]);
						dependentes = new LinkedHashSet<>();
						funcionarios.add(new Funcionario(nome, cpf, data, salario, dependentes));
					} else {
						throw new ValorInvalidoException();
					}
				}
			}
			sc.close();
		} catch (DependenteException e) {
			System.out.println(e.getMessage());
		} catch (ValorInvalidoException g) {
			System.out.println(g.getMessage());
		} catch (Exception f) {
			f.printStackTrace();
		}

		System.out.println("\n---------------Pro CSV:----------------\n");

		for (Funcionario funcionario : funcionarios) {
			funcionario.calcularSalarioLiquido();
			funcionario.textoCSV();
			System.out.println("");

		}
		System.out.println("\n--------Pro usuario:-----------------\n");

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

		try {

			PrintWriter gravacaoArquivo = new PrintWriter(novoArquivo);
			for (Funcionario funcionario : funcionarios) {
				String linha = funcionario.getNome() + ";" + funcionario.getCpf() + ";"
						+ funcionario.getDataNascimento() + ";" + String.format("%.2f", funcionario.getSalarioBruto())
						+ ";" + String.format("%.2f", funcionario.getDescontoINSS()) + ";"
						+ String.format("%.2f", funcionario.getDescontoIR()) + ";"
						+ String.format("%.2f", funcionario.getSalarioLiquido()) + "\n";
				gravacaoArquivo.printf("%s", linha);
				for (Dependente dependente : funcionario.getDependentes()) {
					linha = dependente.getNome() + ";" + dependente.getCpf() + ";" + dependente.getDataNascimento()
							+ ";" + dependente.getParentesco() + "\n";
					gravacaoArquivo.printf("%s", linha);
				}
				gravacaoArquivo.printf("\n");

			}
			gravacaoArquivo.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}