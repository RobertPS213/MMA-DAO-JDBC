package model.dao;

import db.DB;
import model.dao.impl.CategoriaDaoJDBC;
import model.dao.impl.LutadorDaoJDBC;

public class DaoFactory {
	public static LutadorDao createLutadorDao() {
		return new LutadorDaoJDBC(DB.getConnection());
	}
	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoJDBC(DB.getConnection());
	}
}
