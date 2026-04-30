package application;

import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.dao.LutadorDao;
import model.entities.Categoria;
import model.entities.Lutador;

public class Program {
	public static void main(String[] args) {
		
		System.out.println("--- MÉTODO: INSERT ---");
		
		LutadorDao ld = DaoFactory.createLutadorDao();
		CategoriaDao cd = DaoFactory.createCategoriaDao();
		/*Categoria categoria = new Categoria(1, "Peso pesado");
		Lutador lutador = new Lutador(null, "Mike Tyson", 98.0, 50, 6, 0, categoria);
		ld.insert(lutador);*/
		
		System.out.println("--- MÉTODO: INSERT USANDO O FINDBYID DA CATEGORIA ---");
		
		/*Lutador lutador = new Lutador(null, "Lennox Lewis", 116.0, 41, 2, 1, cd.findById(1));
		ld.insert(lutador);*/
		
		System.out.println("--- MÉTODO: DELETE(LUTADOR) ---");
		
		/*ld.delete(4);*/
		
		System.out.println("--- MÉTODO: UPDATE(LUTADOR) ---");
		
		/*Categoria categoria = new Categoria(2, "Peso leve");*/
		/*Lutador lutador = new Lutador(5, "Gervonta Davis", 62.5, 30, 0, 1, cd.findById(2));
		ld.update(lutador);*/
		
		System.out.println("--- MÉTODO> FINDBYID(LUTADOR) ---");
		
		/*System.out.println(ld.findById(5));*/
		
		System.out.println("--- MÉTODO: FINDALL(LUTADOR) ---");
		
		/*System.out.println(ld.findAll());*/
	}
}
