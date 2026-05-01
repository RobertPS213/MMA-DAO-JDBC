package application;

import java.util.ArrayList;
import java.util.Arrays;
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
	public Lutador compactarLutador(Scanner sc, Integer categoriaId) {
		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
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
		Lutador lutador = new Lutador(categoriaId, nome, peso, vitorias, derrotas, empates, categoriaDao.findById(idCategoria));
		return lutador;
	}
	public void printarLutadores(List<Lutador> list){
		list.forEach(x -> System.out.println("----------" + "\nId: " + x.getId() + "\nNome: " + x.getNome() + "\nPeso: " + x.getPeso() + "\nVitórias: " + x.getVitorias() + "\nDerrotas: " + x.getDerrotas() + "\nEmpates: " + x.getEmpates() + "\nCategoria de peso: " + x.getCategoria().getNome() + "\n----------"));
	}
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
		LutadorDao lutadorDao = DaoFactory.createLutadorDao();
		try (Scanner sc = new Scanner(System.in)){
			System.out.println("--- MMA-DAO-JDBC DINÂMICO ---");
			int prosseguirPrincipal = 0;
			while(prosseguirPrincipal != 3) {
				System.out.println("");
				System.out.println("[OPÇÕES]");
				System.out.println("1 - LUTADOR");
				System.out.println("2 - CATEGORIA");
				System.out.println("3 - SAIR");
				System.out.print("SELECIONE UMA OPÇÃO: ");
				prosseguirPrincipal = sc.nextInt();
				if(prosseguirPrincipal == 1) {
					System.out.println("");
					System.out.println("[OPÇÕES]");
					System.out.println("1 - INSERIR UM NOVO LUTADOR");
					System.out.println("2 - DELETAR UM LUTADOR");
					System.out.println("3 - ATUALIZAR UM LUTADOR");
					System.out.println("4 - ENCONTRAR UM LUTADOR PELO SEU ID");
					System.out.println("5 - LISTAR TODOS OS LUTADORES");
					System.out.println("6 - LISTAR TODOS OS LUTADORES PELA SUA CATEGORIA");
					System.out.print("SELECIONE UMA OPÇÃO: ");
					int prosseguirLutador = sc.nextInt();
					List<Categoria> list = categoriaDao.findAll();
					Map<Integer, String> listMap = new HashMap<>();
					for(Categoria c: list) {
						listMap.put(c.getId(), c.getNome());
					}
					Program program = new Program();
					if(prosseguirLutador == 1) {
						sc.nextLine();
						System.out.println();
						System.out.println("--- CATEGORIAS DE PESO ---");
						listMap.forEach((chave, valor) -> System.out.println(valor + " [" + chave + "]"));
						System.out.println("-------------------------");
						System.out.println("--- DADOS DO LUTADOR ---");
						lutadorDao.insert(program.compactarLutador(sc, null));
					} else if(prosseguirLutador == 2){
						System.out.println("\n--- DELETAR UM LUTADOR ---");
						System.out.print("DIGITE O ID DO LUTADOR: ");
						int idLutador = sc.nextInt();
						lutadorDao.delete(idLutador);
					} else if(prosseguirLutador == 3) {
						System.out.println();
						System.out.println("--- CATEGORIAS DE PESO ---");
						listMap.forEach((chave, valor) -> System.out.println(valor + " [" + chave + "]"));
						System.out.println("-------------------------");
						System.out.println("\n--- ATUALIZAR UM LUTADOR ---");
						System.out.print("ID DO LUTADOR: ");
						int idLutador = sc.nextInt();
						sc.nextLine();
						lutadorDao.update(program.compactarLutador(sc, idLutador));
					} else if(prosseguirLutador == 4) {
						System.out.println();
						System.out.println("--- ENCONTRAR UM LUTADOR PELO ID ---");
						System.out.print("ID DO LUTADOR: ");
						int idLutador = sc.nextInt();
						List<Lutador> listLutadores = Arrays.asList(lutadorDao.findById(idLutador));
						if(listLutadores.isEmpty()) {
							System.out.println("Nenhum lutador encontrado com esse Id!");
						} else {
							program.printarLutadores(listLutadores);
						}
					} else if(prosseguirLutador == 5) {
						System.out.println();
						System.out.println("--- LISTAR TODOS OS LUTADORES ---");
						List<Lutador> listLutadores = lutadorDao.findAll();
						program.printarLutadores(listLutadores);
					} else {
						System.out.println();
						System.out.println("--- CATEGORIAS DE PESO ---");
						listMap.forEach((chave, valor) -> System.out.println(valor + " [" + chave + "]"));
						System.out.println("--- LISTAR TODOS OS LUTADORES PELA SUA CATEGORIA ---");
						System.out.print("CATEGORIA DE PESO: ");
						int Idcategoria = sc.nextInt();
						List<Lutador> listLutadores = lutadorDao.findByCategoria(categoriaDao.findById(Idcategoria));
						if(listLutadores.isEmpty()) {
							System.out.println("\nNenhum lutador encontrado!");
						} else {
							program.printarLutadores(lutadorDao.findByCategoria(categoriaDao.findById(Idcategoria)));
						}
					}
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
