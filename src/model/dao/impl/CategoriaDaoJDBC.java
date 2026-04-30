package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.CategoriaDao;
import model.entities.Categoria;

public class CategoriaDaoJDBC implements CategoriaDao{
	private Connection conn;
	
	public CategoriaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void insert(Categoria categoria) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO categoria (Nome)"
					+ "VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, categoria.getNome());
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				System.out.println("Linhas afetadas: " + linhasAfetadas);
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					categoria.setId(rs.getInt(1));
				}
				DB.closeResult(rs);
			}
		} catch(SQLException e){
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	@Override
	public void delete(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM categoria WHERE Id = ?");
			st.setInt(1, id);
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				System.out.println("Categoria com o id " + id + " deletado com sucesso");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	@Override
	public void update(Categoria categoria) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE categoria SET Nome = ? WHERE Id = ?");
			st.setString(1, categoria.getNome());
			st.setInt(2, categoria.getId());
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				System.out.println("Categoria com o id " + categoria.getId() + " foi atualizado com sucesso!");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categoria");
			rs = st.executeQuery();
			List<Categoria> list = new ArrayList<>();
			while(rs.next()){
				Categoria categoria = new Categoria();
				categoria.setId(rs.getInt("Id"));
				categoria.setNome(rs.getString("Nome"));
				list.add(categoria);
			}
			return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}
}
