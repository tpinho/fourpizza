package labs.fourpizza.cozinha.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.dao.DaoException;
import labs.fourpizza.dao.PersistenciaUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JdbcIngredientesDaoIT {
    private static final File SQL_INSTALACAO_BANCO = new File(
            "src/main/webapp/META-INF/database.sql");

    private Connection conexao;

    @Before
    public void inicializar() throws Exception {
        // Carrega driver do banco de dados e cria conexão com banco
        // em memória.
        conexao = PersistenciaUtil.getConexao();

        // Carrega arquivo com a estrutura das tabelas e executa no
        // banco de dados
        String sql = lerSqlBancoDeDados();
        Statement stmt = conexao.createStatement();
        stmt.executeUpdate(sql);
    }

    @Test
    public void criandoTabelaDeIngredientes() throws Exception {
        Statement stmt = conexao.createStatement();
        int totalAdicionado = stmt.executeUpdate("INSERT INTO "
                + "ingredientes(nome,valor_porcao) "
                + "values(('Mussarela', 3.45), ('Calabreza', 2.10))");
        assertEquals(2, totalAdicionado);
    }
    
    @Test
    public void adicioandoIngredienteValido() throws Exception {
        JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        dao.adicionar(new Ingrediente("Mussarela", 2.45f));
        
        Statement stmt = conexao.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM ingredientes");
        
        resultSet.next();
        assertEquals("Mussarela", resultSet.getString("nome"));
        assertEquals(new Float(2.45f), new Float(resultSet.getFloat("valor_porcao")));
    }
    
    @Test(expected=DaoException.class)
    public void adicionandoIngredienteSemNome() throws Exception {
        JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        dao.adicionar(new Ingrediente(null, 2.45f));
    }
    
    @Test(expected=DaoException.class)
    public void adicionandoIngredienteMesmoNome() throws Exception {
        JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        dao.adicionar(new Ingrediente("Calabreza", 2.45f));
        dao.adicionar(new Ingrediente("Calabreza", 2.45f));
    }
    
    @Test
	public void buscandoIngredientePorID() throws Exception {
    	JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        Ingrediente calabreza = new Ingrediente("Calabreza", 2.45f);
        dao.adicionar(calabreza);
        Ingrediente persistido = dao.buscar(calabreza.getId());
        assertEquals(calabreza, persistido);
        assertEquals(new Float(2.45f), new Float(calabreza.getValorPorPorcao()));
	}
    
    @Test
	public void buscandoIngredienteIDErrado() throws Exception {
    	JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        Ingrediente calabreza = new Ingrediente("Calabreza", 2.45f);
        dao.adicionar(calabreza);
        assertTrue(dao.buscar(-2L) == null);
	}
    
    @Test
    public void buscandoIngredientePorNome() throws Exception {
        JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        Ingrediente calabreza = new Ingrediente("Calabreza", 2.45f);
        dao.adicionar(calabreza);
        Ingrediente persistido = dao.buscarPorNome("Calabreza");
        assertEquals(calabreza, persistido);
    }
    
    @Test
	public void buscandoIngredienteNomeErrado() throws Exception {
    	JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        Ingrediente calabreza = new Ingrediente("Calabreza", 2.45f);
        dao.adicionar(calabreza);
        assertTrue(dao.buscarPorNome("Cala") == null);
	}
    
    @Test
	public void listandoTodosIngredientes() throws Exception {
    	JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
        Ingrediente calabreza = new Ingrediente("Calabreza", 2.45f);
        dao.adicionar(calabreza);
        
        Ingrediente mussarela = new Ingrediente("Mussarela", 2.45f);
        dao.adicionar(mussarela);
        
        List<Ingrediente> ingredientes = dao.listar();
        assertEquals(2, ingredientes.size());
        
        assertEquals(calabreza, ingredientes.get(0));
        assertEquals(mussarela, ingredientes.get(1));
	}
    
    @Test
	public void removendoIngrediente() throws Exception {
    	JdbcIngredientesDao dao = new JdbcIngredientesDao(conexao);
    	
        Ingrediente calabreza = new Ingrediente("Calabreza", 2.45f);
        dao.adicionar(calabreza);
        
        Ingrediente mussarela = new Ingrediente("Mussarela", 2.45f);
        dao.adicionar(mussarela);
        
        List<Ingrediente> ingredientes = dao.listar();
        assertEquals(2, ingredientes.size());
        
        assertEquals(calabreza.getNome(), dao.remover(calabreza.getNome()).getNome());
        
        assertEquals(1, dao.listar().size());
	}

    private String lerSqlBancoDeDados() throws Exception {
        BufferedReader leitor = new BufferedReader(new FileReader(SQL_INSTALACAO_BANCO));

        StringBuilder bancoDadosSql = new StringBuilder();
        String linha = null;
        while ((linha = leitor.readLine()) != null) {
            bancoDadosSql.append(linha);
        }
        return bancoDadosSql.toString();
    }
    
    @After
    public void encerrar() throws Exception {
        PersistenciaUtil.encerrarConexao();
        PersistenciaUtil.apagarBancoDados();
    }
}
