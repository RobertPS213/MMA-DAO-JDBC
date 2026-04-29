package model.dao;

import java.util.List;

import model.entities.Categoria;
import model.entities.Lutador;

public interface LutadorDao {
	 void insert(Lutador lutador);
	 void delete(Integer id);
	 void update(Lutador lutador);
	 Lutador findById(Integer id);
	 List<Lutador> findAll();
	 List<Lutador> findByCategoria(Categoria categoria);
}
