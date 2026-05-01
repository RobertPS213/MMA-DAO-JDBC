package application;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import db.DbException;
import db.DbIntegrityException;
import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.dao.LutadorDao;
import model.entities.Categoria;
import model.entities.Lutador;

public class Program {
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
		LutadorDao lutadorDao = DaoFactory.createLutadorDao();
		try (Scanner sc = new Scanner(System.in)){
			System.out.println("--- MMA-DAO-JDBC DINÂMICO ---");
			int prosseguir = 0;
			while(prosseguir != 3) {
				System.out.println("");
				System.out.println("[OPÇÕES]");
				System.out.println("1 - LUTADOR");
				System.out.println("2 - CATEGORIA");
				System.out.println("3 - SAIR");
				System.out.print("SELECIONE UMA OPÇÃO: ");
				prosseguir = sc.nextInt();
				if(prosseguir == 1) {
					System.out.println("");
					System.out.println("[OPÇÕES]");
					System.out.println("1 - INSERIR UM NOVO LUTADOR");
					System.out.println("2 - DELETAR UM LUTADOR");
					System.out.println("3 - ATUALIZAR UM LUTADOR");
					System.out.println("4 - ENCONTRAR UM LUTADOR PELO SEU ID");
					System.out.println("5 - LISTAR TODOS OS LUTADORES");
					System.out.println("6 - LISTAR TODOS OS LUTADORES PELA SUA CATEGORIA");
					System.out.print("SELECIONE UMA OPÇÃO: ");
					prosseguir = sc.nextInt();
					if(prosseguir == 1) {
						sc.nextLine();
						System.out.println();
						System.out.println("--- CATEGORIAS DE PESO ---");
						List<Categoria> list = categoriaDao.findAll();
						
						Map<Integer, String> listMap = new HashMap<>();
						for(Categoria c: list) {
							listMap.put(c.getId(), c.getNome());
						}
						listMap.forEach((chave, valor) -> System.out.println(valor + " [" + chave + "]"));
						System.out.println("-------------------------");
						System.out.println("--- DADOS DO LUTADOR ---");
						System.out.print("NOME: ");
						String nome = sc.nextLine();
						System.out.print("PESO: ");
						Double peso = sc.nextDouble();
						System.out.print("VITÓRIAS: ");
						int vitorias = sc.nextInt();
						System.out.print("DERROTAS: ");
						int derrotas = sc.nextInt();
						System.out.print("EMPATES: ");
						int empates = sc.nextInt();
						System.out.print("CATEGORIA DE PESO: ");
						int idCategoria = sc.nextInt();
						Categoria catEncontrada = categoriaDao.findById(idCategoria);
						if(catEncontrada != null) {
							Lutador lutador = new Lutador(null, nome, peso, vitorias, derrotas, empates, categoriaDao.findById(idCategoria));
							lutadorDao.insert(lutador);
						}
					}
				} else{
					
				}
			}
		} catch (DbIntegrityException e) {
		    System.out.println();
		    System.out.println("--- ERRO DE INTEGRIDADE ---");
		    System.out.println("Mensagem: " + e.getMessage());
		} catch (DbException e) {
		    System.out.println();
		    System.out.println("--- ERRO DE BANCO DE DADOS ---");
		    System.out.println("Mensagem: " + e.getMessage());
		} catch (RuntimeException e) {
		    System.out.println();
		    System.out.println("--- ERRO INESPERADO ---");
		    System.out.println("Mensagem: " + e.getMessage());
		}
	}
}
