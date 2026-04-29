package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.LutadorDao;
import model.entities.Lutador;

public class LutadorDaoJDBC implements LutadorDao{
	private Connection conn;

	public LutadorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void insert(Lutador lutador) {
		// TODO Auto-generated method stub
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
	}
	@Override
	public void update(Lutador lutador) {
		// TODO Auto-generated method stub	
	}
	@Override
	public Lutador findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Lutador> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
