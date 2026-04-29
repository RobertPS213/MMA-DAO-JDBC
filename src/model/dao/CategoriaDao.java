package model.dao;

import java.util.List;

import model.entities.Categoria;

public interface CategoriaDao {
	void insert(Categoria categoria);
	 void delete(Integer id);
	 void update(Categoria categoria);
	 Categoria findById(Integer id);
	 List<Categoria> findAll();
}
