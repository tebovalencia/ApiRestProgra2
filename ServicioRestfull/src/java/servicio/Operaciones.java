/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.ws.rs.OPTIONS;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author estebanvalencia
 */


@Path("Usuarios")
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
    
    
    @OPTIONS
@Path("{path : .*}")
public Response handlePreflight() {
    return Response.ok()
            .header("Access-Control-Allow-Origin", "*")  // Permitir cualquier origen
            .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")  // Métodos permitidos
            .header("Access-Control-Allow-Headers", "Content-Type, Authorization")  // Headers permitidos
            .build();
}

    @GET
@Path("/listaUsuario")
@Produces(MediaType.APPLICATION_JSON)
public Response listar() {
    List<Usuarios> usuarios = consultar();
    
    return Response.ok(usuarios)
                   .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                   .build();
}


    @POST
@Path("/agregarUsuario")
@Produces("application/json")
@Consumes("application/json")
public Response agregar(Usuarios u) {
    String sql = "INSERT INTO usuarios(idUsuario,nombre, apellido, direccion, correo, telefono, password, rol, estado) VALUES(?,?,?,?,?,?,?,?,?)";

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

        // Respuesta exitosa con encabezados CORS
        return Response.ok("{\"message\": \"Registro ingresado\"}")
                       .header("Access-Control-Allow-Origin", "*") // Permitir cualquier origen
                       .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
                       .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                       .build();

    } catch (SQLException e) {
        // Manejo del error
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("{\"message\": \"Error al registrar: " + e.getMessage() + "\"}")
                       .header("Access-Control-Allow-Origin", "*") // Permitir cualquier origen
                       .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
                       .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                       .build();
    }
}


    @PUT
@Path("/modificarUsuario")
@Produces("application/json")
@Consumes("application/json")
public Response modificar(Usuarios u) {
    String sql = "UPDATE usuarios SET nombre=?, apellido=?, direccion=?, correo=?, telefono=?, rol=?, estado=? WHERE idUsuario=?";
    
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

        int rowsUpdated = ps.executeUpdate();
        
        if (rowsUpdated > 0) {
            // Si se actualizó al menos una fila, éxito
            return Response.ok("{\"message\": \"Registro actualizado\"}")
                           .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                           .build();
        } else {
            // No se encontró el usuario con ese id
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"message\": \"Usuario no encontrado\"}")
                           .header("Access-Control-Allow-Origin", "*")
                           .build();
        }

    } catch (SQLException e) {
        // Manejo de errores
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("{\"message\": \"Error al actualizar el registro: " + e.getMessage() + "\"}")
                       .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                       .build();
    }
}


    @GET
@Path("/buscarUsuario/{correo}")
@Produces(MediaType.APPLICATION_JSON)
public Response buscar(@PathParam("correo") String correo) {
    List<Usuarios> lista = new ArrayList<>();
    String sql = "SELECT * FROM Usuarios WHERE correo=?";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, correo);
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
    } catch (SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("Error en la base de datos: " + e.getMessage())
                       .build();
    }
    
    if (lista.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Usuario no encontrado")
                       .build();
    }

    // Permitir CORS para solicitudes de cualquier origen
    return Response.ok(lista)
                   .header("Access-Control-Allow-Origin", "*") // Permite solicitudes desde cualquier origen
                   .build();
}

   @GET
@Path("/buscarUsuarioid/{usuario}")
@Produces(MediaType.APPLICATION_JSON)
public Response buscarid(@PathParam("usuario") String idUsuario) {
    List<Usuarios> lista = new ArrayList<>();
    String sql = "SELECT * FROM Usuarios WHERE idUsuario=?";
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
    } catch (SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("Error en la base de datos: " + e.getMessage())
                       .build();
    }
    
    if (lista.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("Usuario no encontrado")
                       .build();
    }

    // Permitir CORS para solicitudes de cualquier origen
    return Response.ok(lista)
                   .header("Access-Control-Allow-Origin", "*") // Permite solicitudes desde cualquier origen
                   .build();
}


    @DELETE
@Path("/EliminarUsuario/{id}")
@Produces("application/json")
public Response Eliminar(@PathParam("id") int id) {
    String sql = "DELETE FROM usuarios WHERE idUsuario=?";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        int rowsDeleted = ps.executeUpdate();
        
        if (rowsDeleted > 0) {
            // Si se eliminó al menos una fila, éxito
            return Response.ok("{\"message\": \"Registro eliminado\"}")
                           .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                           .build();
        } else {
            // No se encontró el usuario con ese id
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"message\": \"Usuario no encontrado\"}")
                           .header("Access-Control-Allow-Origin", "*")
                           .build();
        }

    } catch (SQLException e) {
        // Manejo de errores
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("{\"message\": \"Error al eliminar el registro: " + e.getMessage() + "\"}")
                       .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                       .build();
    }
}


}
