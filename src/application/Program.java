package application;

import model.dao.DaoFactory;
import model.dao.LutadorDao;
import model.entities.Categoria;
import model.entities.Lutador;

public class Program {
	public static void main(String[] args) {
		LutadorDao ld = DaoFactory.createLutadorDao();
		Categoria categoria = new Categoria(1, "Peso pesado");
		Lutador lutador = new Lutador(null, "Mike Tyson", 98.0, 50, 6, 0, categoria);
		ld.insert(lutador);
	}
}
