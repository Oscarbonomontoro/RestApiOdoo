package DAO;

import modelos.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DAO.ConexionDB.getConnection;

public class StateDAO {

    public static List<State> obtenerEstados() throws SQLException {
        List<State> estados = new ArrayList<>();

        String query = "SELECT id, country_id, create_uid, write_uid, name, code, create_date, write_date FROM res_country_state ORDER BY name";

        try (Connection conexion = getConnection();
             Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                State estado = new State(
                        resultSet.getInt("id"),
                        resultSet.getInt("country_id"),
                        resultSet.getInt("create_uid"),
                        resultSet.getInt("write_uid"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getTimestamp("create_date"),
                        resultSet.getTimestamp("write_date")
                );
                estados.add(estado);
            }
        }

        return estados;
    }

    public static int obtenerProximoId() {
        int id = 1; //Comenzamos con 1 para evitar IDs inválidos
        try (Connection conn = getConnection()) {
            String sql = "SELECT MAX(id) FROM RES_COUNTRY_STATE";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1) + 1; //Incrementamos el ID +1
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el próximo ID: " + e.getMessage());
        }
        return id;
    }

    public static void insertarEstado(State estado) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO RES_COUNTRY_STATE (id, country_id, name, code, create_uid, write_uid) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, estado.getId());
            stmt.setInt(2, estado.getCountry_id());
            stmt.setString(3, estado.getName());
            stmt.setString(4, estado.getCode());
            stmt.setInt(5, estado.getCreate_uid());
            stmt.setInt(6, estado.getWrite_uid());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar el estado: " + e.getMessage());
        }
    }

    public static void actualizarEstado(State estado) {
        String sql = "UPDATE RES_COUNTRY_STATE SET country_id = ?, name = ?, code = ?, write_uid = ? WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, estado.getCountry_id());
            pstmt.setString(2, estado.getName());
            pstmt.setString(3, estado.getCode());
            pstmt.setInt(4, estado.getWrite_uid());
            pstmt.setInt(5, estado.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado: " + e.getMessage());
        }
    }

    public static void eliminarEstado(int id) {
        String sql = "DELETE FROM res_country_state WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Estado eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún estado con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el estado: " + e.getMessage());
        }
    }
}
//si
