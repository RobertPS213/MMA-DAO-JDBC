package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.LutadorDao;
import model.entities.Categoria;
import model.entities.Lutador;

public class LutadorDaoJDBC implements LutadorDao{
	private Connection conn;

	public LutadorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void insert(Lutador lutador) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO lutador " 
					+ "(Nome, Peso, Vitorias, Derrotas, Empates, CategoriaId) "
				    + "VALUES (?, ?, ?, ?, ?, ?)",
				    Statement.RETURN_GENERATED_KEYS);
			st.setString(1, lutador.getNome());
			st.setDouble(2, lutador.getPeso());
			st.setInt(3, lutador.getVitorias());
			st.setInt(4, lutador.getDerrotas());
			st.setInt(5, lutador.getEmpates());
			st.setInt(6, lutador.getCategoria().getId());
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				int id = 0;
				if(rs.next()) {
					id = rs.getInt(1);
					lutador.setId(id);
				}
				System.out.println("\nLutador adicionado com sucesso!\nId do lutador: " + id + "");
				DB.closeResult(rs);
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	@Override
	public void delete(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM lutador WHERE Id = ?;");
			st.setInt(1, id);
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				System.out.println("Lutador com ID " + id + " deletado com sucesso!");
			} else {
				System.out.println("Nenhum lutador encontrado com o ID " + id);
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	@Override
	public void update(Lutador lutador) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE lutador "
					+ "SET Nome = ?, Peso = ?, Vitorias = ?, Derrotas = ?, Empates = ?, CategoriaId = ? "
					+ "WHERE Id = ?");
			st.setString(1, lutador.getNome());
			st.setDouble(2, lutador.getPeso());
			st.setInt(3, lutador.getVitorias());
			st.setInt(4, lutador.getDerrotas());
			st.setInt(5, lutador.getEmpates());
			st.setInt(6, lutador.getCategoria().getId());
			st.setInt(7, lutador.getId());
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				System.out.println("Lutador com o id " + lutador.getId() + " teve seus dados alterados");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	@Override
	public Lutador findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st= conn.prepareStatement(
					"SELECT lutador.*, categoria.Nome as CatNome "
					+ "FROM lutador INNER JOIN categoria "
					+ "ON lutador.CategoriaId = categoria.Id "
					+ "WHERE lutador.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				return compactarLutador(rs, compactarCategoria(rs));
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
	public List<Lutador> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT lutador.*, categoria.Nome as CatNome "
					+ "FROM lutador INNER JOIN categoria "
					+ "ON lutador.CategoriaId = categoria.Id "
					+ "ORDER BY Nome");
			rs = st.executeQuery();
			List<Lutador> list = new ArrayList<>();
			Map<Integer, Categoria> map = new HashMap<>();
			while(rs.next()) {
				Categoria cat = map.get(rs.getInt("CategoriaId"));
				if(cat == null) {
					cat = compactarCategoria(rs);
					map.put(rs.getInt("CategoriaId"), cat);
				}
				list.add(compactarLutador(rs, cat));
			}
			return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}
	@Override
	public List<Lutador> findByCategoria(Categoria categoria) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT lutador.*, categoria.Nome as CatNome "
					+ "FROM lutador INNER JOIN categoria "
					+ "ON lutador.CategoriaId = categoria.Id "
					+ "WHERE CategoriaId = ? "
					+ "ORDER BY Nome");
			st.setInt(1, categoria.getId());
			rs = st.executeQuery();
			List<Lutador> list = new ArrayList<>();
			while(rs.next()) {
				list.add(compactarLutador(rs, categoria));
			}
			return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}
	public Categoria compactarCategoria(ResultSet rs) throws SQLException{
		Categoria categoria = new Categoria();
		categoria.setId(rs.getInt("CategoriaId"));
		categoria.setNome(rs.getString("CatNome"));
		return categoria;
	}
	public Lutador compactarLutador(ResultSet rs, Categoria categoria) throws SQLException {
		Lutador lutador = new Lutador();
		lutador.setId(rs.getInt("Id"));
		lutador.setNome(rs.getString("Nome"));
		lutador.setPeso(rs.getDouble("Peso"));
		lutador.setVitorias(rs.getInt("Vitorias"));
		lutador.setDerrotas(rs.getInt("Derrotas"));
		lutador.setEmpates(rs.getInt("Empates"));
		lutador.setCategoria(categoria);
		return lutador;
	}
}
