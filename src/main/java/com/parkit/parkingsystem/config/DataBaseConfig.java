package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/prod","root","rootroot");
    }

    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.error("Error while closing connection",e);
            }
        }
    }

    public void closePreparedStatement(PreparedStatement ps) {
        if(ps!=null){
            try {
                ps.close();
                logger.info("Closing Prepared Statement");
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement",e);
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
                logger.info("Closing Result Set");
            } catch (SQLException e) {
                logger.error("Error while closing result set",e);
            }
        }
    }

    public void executeSqlFromFile(String fileName) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // Utiliser le ClassLoader pour charger le fichier SQL depuis les ressources
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(DataBaseConfig.class.getClassLoader().getResourceAsStream(fileName))
            );

            // Lire tout le contenu du fichier SQL
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
            String sql = sqlBuilder.toString();

            // Exécuter le fichier SQL
            statement.executeUpdate(sql);
            System.out.println("Tables et données fictives créées avec succès !");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("Erreur : Impossible de charger ou exécuter le fichier SQL.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
