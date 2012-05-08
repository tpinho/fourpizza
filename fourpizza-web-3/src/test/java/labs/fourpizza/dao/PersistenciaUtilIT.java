package labs.fourpizza.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import org.junit.After;
import org.junit.Test;

public class PersistenciaUtilIT {

    private static final String SCHEMA_SQL_BANCO = "src/main/webapp/"
            + "META-INF/database.sql";

    @Test
    public void criandoConexao() throws Exception {
        Connection conexao = PersistenciaUtil.getConexao();
        assertNotNull(conexao);
    }

    @Test
    public void populandoBancoDeDados() throws Exception {
        Connection conexao = PersistenciaUtil.getConexao();

        StringBuilder bancoDadosSql = new StringBuilder();
        String linha = null;

        BufferedReader leitor = new BufferedReader(new FileReader(
                SCHEMA_SQL_BANCO));
        while ((linha = leitor.readLine()) != null) {
            bancoDadosSql.append(linha);
        }

        PersistenciaUtil.popularBanco(bancoDadosSql.toString());

        Statement stmt = conexao.createStatement();
        int total = stmt
                .executeUpdate("INSERT INTO ingredientes(nome, valor_porcao) "
                        + "VALUES('Mussarela', 3.20)");
        assertEquals(1, total);
    }

    @After
    public void encerrar() throws Exception {
        PersistenciaUtil.encerrarConexao();
    }
}
