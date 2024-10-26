/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import servicio.Conexion;

/**
 *
 * @author edin1
 */
@Path("Cita")
public class CitaControlador {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Context
    private UriInfo context;

    public List<CitaModelo> consultar() {
        List<CitaModelo> lista = new ArrayList<>();

        String sql = "SELECT cita.* , usuarios.nombre , usuarios.apellido FROM cita JOIN usuarios ON cita.idUsuario = usuarios.idUsuario;";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CitaModelo obj = new CitaModelo();
                obj.setIdCita(rs.getInt("idCita"));
                obj.setDependiente(rs.getString("dependiente"));
                obj.setIdUsuario(rs.getInt("idUsuario"));
                obj.setFechahora(rs.getString("fechahora"));
                obj.setEstado(rs.getInt("estado"));
                obj.setNombreUsuario(rs.getString("nombre"));
                obj.setApellidoUsuario(rs.getString("apellido"));
                obj.setComentarios(rs.getString("comentarios"));
                lista.add(obj);
            }
            return lista;
        } catch (SQLException e) {

            return lista;
        }
    }

    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<CitaModelo> Cita = consultar();

        return Response.ok(Cita)
                .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                .build();
    }

    @POST
    @Path("/agregar")
    @Produces("application/json")
    @Consumes("application/json")
    public Response agregar(CitaModelo obj) {
        String sql = "INSERT INTO cita(dependiente, idUsuario,fechahora, comentarios) VALUES(?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, obj.getDependiente());
            ps.setInt(2, obj.getIdUsuario());
            ps.setString(3, obj.getFechahora());
            ps.setString(4, obj.getComentarios());
            ps.executeUpdate();

            // Respuesta exitosa con encabezados CORS
            return Response.ok("{\"message\": \"Registro ingresado\"}")
                    .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                    .build();

        } catch (SQLException e) {
            // Manejo del error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al registrar: " + e.getMessage() + "\"}")
                    .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                    .build();
        }
    }

    @PUT
    @Path("/modificar")
    @Produces("application/json")
    @Consumes("application/json")
    public Response modificar(CitaModelo obj) {
        String sql = "UPDATE cita SET estado=?, comentarios=? WHERE idCita=?";
        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, obj.getEstado());
            ps.setString(2, obj.getComentarios());
            ps.setInt(3, obj.getIdCita());

            int rowsUpdated = ps.executeUpdate();

            ps.close();
            con.close();

            if (rowsUpdated > 0) {
                // Si se actualizó al menos una fila, éxito
                return Response.ok("{\"message\": \"Registro actualizado " + obj.getComentarios() + "\"}")
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
    @Path("/buscar/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@PathParam("idUsuario") String idUsuario) {
        List<CitaModelo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cita WHERE idUsuario=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                CitaModelo obj = new CitaModelo();
                obj.setDependiente(rs.getString("dependiente"));
                obj.setIdUsuario(rs.getInt("idUsuario"));
                obj.setFechahora(rs.getString("fechahora"));
                obj.setComentarios(rs.getString("comentarios"));
                obj.setEstado(rs.getInt("estado"));
                obj.setIdCita(rs.getInt("idCita"));
                lista.add(obj);
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error en la base de datos: " + e.getMessage())
                    .build();
        }

        if (lista.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cita no encontrado")
                    .build();
        }

        // Permitir CORS para solicitudes de cualquier origen
        return Response.ok(lista)
                .header("Access-Control-Allow-Origin", "*") // Permite solicitudes desde cualquier origen
                .build();
    }

    @DELETE
    @Path("/eliminar/{id}")
    @Produces("application/json")
    public Response Eliminar(@PathParam("id") int id) {
        String sql = "DELETE FROM Cita WHERE idCita=?";

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
                        .entity("{\"message\": \"Cita no encontrado\"}")
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
