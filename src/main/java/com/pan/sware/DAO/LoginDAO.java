package com.pan.sware.DAO;

import com.pan.sware.Queries.Usuarios;
import com.pan.sware.db.ConnectionManager;
import com.pan.sware.TO.UsuarioTO;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author estebanfcv
 */
public class LoginDAO {

    public List<UsuarioTO> consultarUsuarios() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UsuarioTO> listaUsuarios = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            ps = con.prepareCall(Usuarios.CONSULTAR_USUARIOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioTO u = new UsuarioTO();
                u.setId(rs.getInt("ID"));
                u.setNombre(rs.getString("USERNAME"));
                u.setPassword(rs.getString("PASSWORD"));
                listaUsuarios.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listaUsuarios;
    }

    public boolean guardarImagen(String ruta, String nombre, String extension) {
        FileInputStream fis = null;
        Connection con = null;
        File file = null;
        PreparedStatement ps = null;
        int i = 1;
        boolean bandera = false;
        try {
            file = new File(ruta);
            fis = new FileInputStream(file);
            con = ConnectionManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(Usuarios.INSERTAR_IMAGENES);
            ps.setBinaryStream(1, fis, (int) file.length());
            ps.setString(2, nombre);
            ps.setString(3, extension);
            ps.executeUpdate();
            con.commit();
            bandera = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bandera;
    }

    public InputStream consultarImagenes() {
        List<Image> listaImagenes = new ArrayList<>();
        Connection con = null;
        InputStream is = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(Usuarios.CONSULTAR_IMAGENES);
            rs = ps.executeQuery();
            while (rs.next()) {
                is = rs.getBlob("IMAGEN").getBinaryStream();
// Bucle de lectura del blob y escritura en el fichero, de 1024
// en 1024 bytes.
//                System.out.println(crearArchivo(is, rs.getString("NOMBRE"),rs.getString("EXTENSION")));   
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return is;
    }

    public boolean crearArchivo(InputStream is, String nombre, String extension) {
        boolean bandera;
        try {
//            File f = new File("/home/estebanfcv/Documentos/"+nombre+extension);
//            /home/estebanfcv/Documentos/emma.png
            File f = new File("/imagenes/zelda.png");
            try (OutputStream out = new FileOutputStream(f)) {
                byte buf[] = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            is.close();

            bandera = true;
            String ext = FilenameUtils.getExtension(f.getPath());
            System.out.println("File is created...................................");
        } catch (IOException e) {
            e.printStackTrace();
            bandera = false;
        }
        return bandera;
    }
}
