package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private Connection conn;
	private final String pathToDb = "database/scores.db";

	public Database() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");

		conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
	}

	/**
	 * @return Le dernier niveau réussi par "name", ou 1 s'il n'en a réussi
	 *         aucun.
	 */
	public int lastLevel(String name) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("SELECT level FROM scores WHERE name=? ORDER BY level ASC;");
		ps.setString(1, name);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getInt("level");
		}

		return 1;
	}

	/**
	 * @return Le score de name au niveau level.
	 */
	public int getScore(String name, int level) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("SELECT nbCoups FROM scores WHERE name=? AND level=?;");
		ps.setString(1, name);
		ps.setInt(2, level);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getInt("nbCoups");
		}

		return Integer.MAX_VALUE;
	}

	/**
	 * Mets à jour le score de name au niveau s'il est meilleur que le
	 * précédent, l'insére si name n'a aucun score.
	 */
	public void insertScore(String name, int level, int score)
			throws SQLException {
		int prevScore = getScore(name, level);

		if (prevScore == Integer.MAX_VALUE) {
			// pas encore de score -> insert into
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO scores (level, nbCoups, name) VALUES (?, ?, ?);");

			ps.setInt(1, level);
			ps.setInt(2, score);
			ps.setString(3, name);

			ps.executeUpdate();
		}

		else if (score < prevScore) {
			// meilleur score -> update
			PreparedStatement ps = conn
					.prepareStatement("UPDATE scores SET nbCoups=? WHERE name=? AND level=?;");

			ps.setInt(1, score);
			ps.setString(2, name);
			ps.setInt(3, level);

			ps.executeUpdate();
		}
	}

	/**
	 * @return Le rang de name au niveau level.
	 */
	public int getRank(String name, int level) throws SQLException {
		int score = getScore(name, level);
		PreparedStatement ps = conn
				.prepareStatement("SELECT COUNT(nbCoups) AS rank FROM "
						+ "scores WHERE level=? AND nbCoups < ?;");

		ps.setInt(1, level);
		ps.setInt(2, score);

		ResultSet rs = ps.executeQuery();

		rs.next();

		return rs.getInt("rank") + 1;
	}

	/**
	 * @return Un ResultSet contenant tous les joueurs ayant fini le niveau trié
	 *         par score (croissant).
	 */
	public ResultSet getClassement(int level) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("SELECT name, nbCoups FROM scores "
						+ " WHERE level=? ORDER BY nbCoups ASC");
		ps.setInt(1, level);
		return ps.executeQuery();
	}

	/**
	 * Ferme la BD.
	 */
	public void close() throws SQLException {
		conn.close();
	}

	/* tests */
	/*
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		Database db = new Database();
		Random r = new Random();

		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 10; ++j) {
				switch (r.nextInt() % 4) {
				case 0:
					db.insertScore("max", i, r.nextInt(32));
					break;
				case 1:
					db.insertScore("alexis", i, r.nextInt(32));
					break;
				case 2:
					db.insertScore("grég", i, r.nextInt(32));
					break;
				case 3:
					db.insertScore("guillaume", i, r.nextInt(32));
					break;
				}
			}
		}

		for (int i = 0; i < 4; ++i) {
			ResultSet rs = db.getClassement(i);

			System.out.println("Level #" + i);

			while (rs.next()) {
				System.out.println(db.getRank(rs.getString("name"), i) + ". "
						+ rs.getInt("nbCoups") + " -> " + rs.getString("name"));
			}

			System.out.println();
		}

		db.close();
	}*/
}
