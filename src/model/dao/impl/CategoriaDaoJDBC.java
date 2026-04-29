package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CategoriaDao;
import model.entities.Categoria;

public class CategoriaDaoJDBC implements CategoriaDao{
	private Connection conn;
	
	public CategoriaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void insert(Categoria categoria) {
		// TODO Auto-generated method stub
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
	}
	@Override
	public void update(Categoria categoria) {
		// TODO Auto-generated method stub
	}
	@Override
	public Categoria findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categoria WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			Categoria categoria = new Categoria();
			if(rs.next()) {
				categoria.setId(rs.getInt("Id"));
				categoria.setNome(rs.getString("Nome"));
				return categoria;
			}
			return null;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}
	@Override
	public List<Categoria> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
