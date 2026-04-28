package entities;

import db.DB;
import entitiesException.DomainException;
import java.sql.*;

public class Eventos {

    private Connection conn;

    public Eventos() {
    }

    public void addEvento(Evento evento) {
        conn = DB.pegarConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO eventos "
                            + "(nome, data, hora, local, responsavel) "
                            + "VALUES "
                            + "(?, ?, ?, ? ,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, evento.getNome());
            st.setDate(2, new java.sql.Date(evento.getData().getTime()));
            st.setString(3, evento.getHora());
            st.setString(4, evento.getLocal());
            st.setString(5, evento.getResponsavel());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    evento.setId(id);
                    System.out.println("\033[32mEvento adicionado com sucesso!\033[m");
                }
            } else {
                throw new DomainException("/033[31mErro inesperado! Nenhuma linha afetada.\033[m");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.fecharStatement(st);
        }
    }

    public void listarTudo() {
        conn = DB.pegarConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "select * from eventos "
                            + "ORDER BY id");
            rs = st.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("nome") + " | " + rs.getDate("data") + " | " + rs.getString("hora") + " | " + rs.getString("local") + " | " + rs.getString("responsavel"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.fecharResultSet(rs);
            DB.fecharStatement(st);
        }
    }

    public void editarEvento(Evento evento){
        conn = DB.pegarConnection();
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE eventos "
                    + "SET nome = ?, data = ?, hora = ?, local = ?, responsavel = ? "
                    + "WHERE id = ?");
            st.setString(1, evento.getNome());
            st.setDate(2, new java.sql.Date(evento.getData().getTime()));
            st.setString(3, evento.getHora());
            st.setString(4, evento.getLocal());
            st.setString(5, evento.getResponsavel());
            st.setInt(6, evento.getId());

            st.executeUpdate();
            System.out.println("\033[32mEvento atualizado com sucesso!\033[m");


        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.fecharStatement(st);
        }
    }

    public void removeEvento(int id) {
        conn = DB.pegarConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE from eventos where id = ?");
            st.setInt(1, id);

            st.executeUpdate();
            System.out.println("\033[32mEvento excluído com sucesso!\033[m");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.fecharStatement(st);
        }
    }

    public void filtarPorData(Date inicio, Date fim){
        conn = DB.pegarConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM eventos WHERE data >= ? AND data < ?"
            );
            st.setDate(1, inicio);
            st.setDate(2, fim);

            rs = st.executeQuery();

            while (rs.next()){
                System.out.println(rs.getInt("id") + " | " + rs.getString("nome") + " | " + rs.getDate("data") + " | " + rs.getString("hora") + " | " + rs.getString("local") + " | " + rs.getString("responsavel"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            DB.fecharResultSet(rs);
            DB.fecharStatement(st);
        }
    }

    public Evento encontrarId(int id){
        conn = DB.pegarConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * from eventos "
                    + "WHERE id = ? ");
            st.setInt(1, id);

            rs = st.executeQuery();
            if(rs.next()){
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setData(rs.getDate("data"));
                evento.setHora(rs.getString("hora"));
                evento.setResponsavel(rs.getString("responsavel"));
                return evento;
            }
            else{
                throw new DomainException("\033[31mErro: Id não encontrado!\033[m");
            }
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        } finally {
            DB.fecharResultSet(rs);
            DB.fecharStatement(st);
        }
    }
}

