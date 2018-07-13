package org.centenaire.dao.abstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.Dao;
import org.centenaire.edition.entities.TagLike;
import org.centenaire.edition.entities.Individuals;

public abstract class AbstractSemesterDao extends Dao<TagLike> {
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract TagLike anyElement();
}
