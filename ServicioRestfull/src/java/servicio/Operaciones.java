/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author estebanvalencia
 */
@Path("generic")
public class Operaciones {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Context
    private UriInfo context;

    public List<Usuarios> consultar() {
        List<Usuarios> lista = new ArrayList<>();

        String sql = "select * from usuarios";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setDireccion(rs.getString("direccion"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                u.setEstado(rs.getInt("estado"));
                u.setRol(rs.getInt("rol"));
                lista.add(u);

            }
            return lista;
        } catch (Exception e) {

            return lista;
        }
    }

    @GET
    @Path("/listaUsuario")
    public List<Usuarios> listar() {
        return (consultar());
    }

    @POST
    @Path("/agregarUsuario")
    @Produces("application/json")
    @Consumes("application/json")
    public String agregar(Usuarios u) {
        String sql = "insert into usuarios(idUsuario,nombre, apellido, direccion, correo, telefono, password, rol, estado) values(?,?,?,?,?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, u.getIdUsuario());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getDireccion());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getPassword());
            ps.setInt(8, u.getRol());
            ps.setInt(9, u.getEstado());
            ps.executeUpdate();

            return "Registor ingresado";

        } catch (Exception e) {
            return "Error al registrar";
        }
    }

    @PUT
    @Path("/modificarUsuario")

    public String modificar(Usuarios u) {
        String sql = "update usuarios set nombre=? , apellido=? , direccion=? , correo=? , telefono=? , rol=? , estado=? where idUsuario=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getDireccion());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTelefono());
            ps.setInt(6, u.getRol());
            ps.setInt(7, u.getEstado());
            ps.setInt(8, u.getIdUsuario());
            ps.executeUpdate();

            return "Registro actualizado";
        } catch (Exception e) {
            return "Error";
        }

    }

    @GET
    @Path("/buscarUsuario/{correo}")
    public List<Usuarios> buscar(@PathParam("correo") String idUsuario) {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "Select * from Usuarios where correo=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setDireccion(rs.getString("direccion"));
                u.setEstado(rs.getInt("estado"));
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setRol(rs.getInt("rol"));
                u.setTelefono(rs.getString("telefono"));
                u.setPassword(rs.getString("password"));
                lista.add(u);
            }

        } catch (Exception e) {
        }
        return lista;
    }

    @DELETE
    @Path("/EliminarUsuario/{id}")
    public String Eliminar(@PathParam("id") int id) {
        String sql = "delete from usuarios where idUsuario=?";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return "Registro eliminado";

        } catch (Exception e) {
            return"Error";
        }
    }

}
