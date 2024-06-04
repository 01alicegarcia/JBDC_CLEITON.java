package br.unipar;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.unipar.ATIVIDADE_4_JBDC.*;

public class ATIVIDADE_4_JBDCTest {
    public static void main(String[] args) {
        criarTabelaUsuario();

        System.out.println("Inserindo usuário...");
        inserirUsuarioComVerificacao("alice_garcia", "14182001", "Alice Garcia", Date.valueOf("2003-12-27"));
        inserirUsuarioComVerificacao("renanperini12", "785698", "Renan Perini", Date.valueOf("2002-03-13"));
        inserirUsuarioComVerificacao("caramelo", "12345", "Carla Melo", Date.valueOf("1998-05-28"));
        System.out.println("Listando usuários...");
        listarUsuarios();

        System.out.println("Atualizando usuário...");
        atualizarUsuario(1, "AliceGarcia");

        System.out.println("Listando usuários após atualização...");
        listarUsuarios();

        System.out.println("Deletando usuário...");
        deletarUsuario(1);

        System.out.println("Listando usuários após exclusão...");
        listarUsuarios();
    }

    public static void inserirUsuarioComVerificacao(String username, String password, String nome, Date nascimento) {
        try {
            // Verifica se o usuário já existe na tabela
            if (!verificarUsuarioExistente(username)) {
                // Se o usuário não existir, insere o novo usuário
                inserirUsuario(username, password, nome, nascimento);
                System.out.println("Usuário inserido com sucesso!");
            } else {
                System.out.println("Usuário com nome de usuário '" + username + "' já existe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean verificarUsuarioExistente(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        try (PreparedStatement pstmt = connection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
