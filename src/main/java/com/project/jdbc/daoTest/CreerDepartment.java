package com.project.jdbc.daoTest;

import com.project.jdbc.config.ConfigConnection;
import com.project.jdbc.dao.Departement;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by marti on 23/01/2017.
 */
public class CreerDepartment {
    static public void main(String[] argv)
            throws ClassNotFoundException, SQLException, java.io.IOException {
        Connection _cx = null;
        DatabaseMetaData dbmd;

        // Code using the ConfigConnection class
        String username = argv[0];
        String password = argv[1];
        //String fichierProp = argv[2];


        try {
            // obtention de la connexion
            _cx = ConfigConnection.getConnection(/*fichierProp,*/username, password);

            _cx.setAutoCommit(false);

            dbmd = _cx.getMetaData(); //get MetaData to confirm connection

            System.out.println("Connection to SGBD " + dbmd.getDatabaseProductName() + " version " +
                    dbmd.getDatabaseProductVersion() + " database " +
                    dbmd.getURL() + " \nusing " + dbmd.getDriverName() + " version " +
                    dbmd.getDriverVersion() + " " + "successful.\n");

            // Insertion d'un nouveau departement
            Departement d = new Departement("DEP");
            d.save(_cx);
            _cx.commit();
            System.out.println("Département créé et persistant : " + d.toString());

            // Récuperation des Départements de la base
            Statement sql = null;
            sql = _cx.createStatement();

            String sqlText = "SELECT * FROM Departement";
            System.out.println("Executing this command: " + sqlText);
            ResultSet rset = sql.executeQuery(sqlText);

            /*ArrayList<Departement> plante avec la version Java 1.4 du CRIO UNIX
              Donc mettre en commentaire la ligne suivante
              et enlever les commentaires de la ligne d'après */
            ArrayList<Departement> liste = new ArrayList<Departement>();
            //ArrayList liste = new ArrayList() ;
            while (rset.next()) {
                liste.add(new Departement(rset));
            }
            rset.close();

            // Affichage
            System.out.println("\nDépartements : ");
            for (int i = 0; i < liste.size(); i++) {
                System.out.println(liste.get(i).toString());
            }

            d.setNom("Dép. Education Permanente");
            d.save(_cx);
            _cx.commit();

            // Mise à jour
            sqlText = d.getQuery() + " WHERE Departement_ID=" + d.getId();
            System.out.println("Executing this command: " + sqlText);
            rset = sql.executeQuery(sqlText);
            if (rset.next()) {
                Departement d2 = new Departement(rset);
                System.out.println("Département modifié : " + d2.toString() + "\n");
                if (d2.equals(d)) System.out.println("Attention les objets d et d2 représentent le même nuplet!\n");
            }
            rset.close();

            // Suppression
            d.delete(_cx);
            _cx.commit();
            System.out.println("Executing this command: " + sqlText);
            rset = sql.executeQuery(sqlText);
            if (!rset.next())
                System.out.println("Département non persistant!\n");
            rset.close();

        } catch (SQLException ex) {
            System.out.println("***Exception:\n" + ex);
            ex.printStackTrace();
            // ROLLBACK updates
            System.out.println("\nROLLBACK!\n" + ex);
            if (_cx != null) _cx.rollback();
        } finally {
            System.out.println("Deconnexion");
            try {
                if (_cx != null) _cx.close();
            } catch (SQLException ex) {
            }
        }
    }
}
