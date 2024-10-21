/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Medicamento;

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
import servicio.Usuarios;

/**
 *
 * @author edin1
 */
@Path("Medicamento")
public class MedicamentoControlador {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Context
    private UriInfo context;

    public List<MedicamentoModelo> consultar() {
        List<MedicamentoModelo> lista = new ArrayList<>();

        String sql = "select * from Medicamento";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MedicamentoModelo me = new MedicamentoModelo();
                me.setIdMedicamento(rs.getInt("idMedicamento"));
                me.setDescipcion(rs.getString("descipcion"));
                me.setUnidadMedida(rs.getString("unidadMedida"));
                lista.add(me);

            }
            return lista;
        } catch (Exception e) {

            return lista;
        }
    }

    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<MedicamentoModelo> medicamento = consultar();

        return Response.ok(medicamento)
                .header("Access-Control-Allow-Origin", "*") // Permitir CORS
                .build();
    }

    @POST
    @Path("/agregar")
    @Produces("application/json")
    @Consumes("application/json")
    public Response agregar(MedicamentoModelo me) {
        String sql = "INSERT INTO Medicamento(descipcion, unidadMedida) VALUES(?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, me.getDescipcion());
            ps.setString(2, me.getUnidadMedida());
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
    public Response modificar(MedicamentoModelo me) {
        String sql = "UPDATE Medicamento SET descipcion=?, unidadMedida=? WHERE idMedicamento=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, me.getDescipcion());
            ps.setString(2, me.getUnidadMedida());
            ps.setInt(3, me.getIdMedicamento());

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
    @Path("/buscar/{descipcion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@PathParam("descipcion") String descipcion) {
        List<MedicamentoModelo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Medicamento WHERE descipcion=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, descipcion);
            rs = ps.executeQuery();
            while (rs.next()) {
                MedicamentoModelo me = new MedicamentoModelo();
                me.setDescipcion(rs.getString("descipcion"));
                me.setUnidadMedida(rs.getString("unidadMedida"));
                me.setIdMedicamento(rs.getInt("idMedicamento"));
                lista.add(me);
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error en la base de datos: " + e.getMessage())
                    .build();
        }

        if (lista.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Medicamento no encontrado")
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
        String sql = "DELETE FROM Medicamento WHERE idMedicamento=?";

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
                        .entity("{\"message\": \"Medicamento no encontrado\"}")
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
