package labs.fourpizza.cozinha.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.cozinha.dominio.Recheio;
import labs.fourpizza.dao.PersistenciaUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JdbcRecheiosDaoIT {
	private static final File SCHEMA_SQL_BANCO = new File(
            "src/main/webapp/META-INF/database.sql");

    private Connection conexao;
    private JdbcIngredientesDao ingredientesDao;
    private JdbcRecheiosDao recheiosDao;

    @Before
    public void inicializar() throws Exception {
        conexao = PersistenciaUtil.getConexao();

        // Carrega arquivo com a estrutura das tabelas e executa no
        // banco de dados
        String sql = lerSqlBancoDeDados();
        Statement stmt = conexao.createStatement();
        stmt.executeUpdate(sql);
        
        recheiosDao = new JdbcRecheiosDao(conexao);
        ingredientesDao = new JdbcIngredientesDao(conexao);
    }
    
    @Test
	public void novoRecheioMussarelaValido() throws Exception {
    	Ingrediente mussarela = new Ingrediente("Mussarela", 1.10f);
		ingredientesDao.adicionar(mussarela);
		
		Ingrediente tomate = new Ingrediente("Tomate", 0.80f);
		ingredientesDao.adicionar(tomate);
		
		Recheio recheioMussarela = new Recheio("Mussarela Tradicional");
		recheioMussarela.adicionarIngrediente(mussarela);
		recheioMussarela.adicionarIngrediente(tomate);
		recheiosDao.adicionar(recheioMussarela);
		
		assertNotNull(recheioMussarela.getId());
	}
    
    @Test(expected = IllegalStateException.class)
	public void adicionarRecheio4QueijosInvalido() throws Exception {
    	Recheio quatroQueijos = new Recheio("Quatro Queijos");
		recheiosDao.adicionar(quatroQueijos);
	}

    @Test
	public void buscarRecheio4Queijos() throws Exception {
    	Ingrediente mussarela = new Ingrediente("Mussarela", 1.10f);
		ingredientesDao.adicionar(mussarela);
		
		Ingrediente provolone = new Ingrediente("Provolone", 1.10f);
		ingredientesDao.adicionar(provolone);
		
		Ingrediente parmesao = new Ingrediente("Parmesão", 1.10f);
		ingredientesDao.adicionar(parmesao);
		
		Ingrediente gorgonzola = new Ingrediente("gorgonzola", 1.10f);
		ingredientesDao.adicionar(gorgonzola);
    	
		Recheio quatroQueijos = new Recheio("Quatro Queijos");
		quatroQueijos.adicionarIngrediente(mussarela);
		quatroQueijos.adicionarIngrediente(provolone);
		quatroQueijos.adicionarIngrediente(parmesao);
		quatroQueijos.adicionarIngrediente(gorgonzola);
		
		recheiosDao.adicionar(quatroQueijos);
		
		Recheio buscadoPorID = recheiosDao.buscar(quatroQueijos.getId());
		assertNotNull(buscadoPorID);
		assertEquals(4, buscadoPorID.getIngredientes().size());
		
		Recheio buscadoPorNome = recheiosDao.buscarPorNome("Quatro Queijos");
		assertNotNull(buscadoPorNome);
		assertEquals(4, buscadoPorNome.getIngredientes().size());
	}
    
    @Test
	public void buscarRecheioPorIDQueNaoExiste() throws Exception {
		assertTrue(recheiosDao.buscar(-12L) == null);
	}
    
    @Test
	public void buscarRecheioPorNomeQueNaoExiste() throws Exception {
		assertTrue(recheiosDao.buscarPorNome("Mussarela") == null);
	}
    
    @Test
	public void listandoNenhumRecheio() throws Exception {
    	assertNotNull(recheiosDao.listar());
		assertEquals(0, recheiosDao.listar().size());
	}
    
    @Test
	public void listandoTodosOsRecheios() throws Exception {
    	Ingrediente mussarela = new Ingrediente("Mussarela", 1.10f);
		ingredientesDao.adicionar(mussarela);
		
		Ingrediente provolone = new Ingrediente("Provolone", 1.10f);
		ingredientesDao.adicionar(provolone);
		
		Ingrediente parmesao = new Ingrediente("Parmesão", 1.10f);
		ingredientesDao.adicionar(parmesao);
		
		Ingrediente gorgonzola = new Ingrediente("gorgonzola", 1.10f);
		ingredientesDao.adicionar(gorgonzola);
		
		Recheio doisQueijos = new Recheio("Dois Queijos");
		doisQueijos.adicionarIngrediente(mussarela);
		doisQueijos.adicionarIngrediente(provolone);
		recheiosDao.adicionar(doisQueijos);
		
		Recheio quatroQueijos = new Recheio("Quatro Queijos");
		quatroQueijos.adicionarIngrediente(mussarela);
		quatroQueijos.adicionarIngrediente(provolone);
		quatroQueijos.adicionarIngrediente(parmesao);
		quatroQueijos.adicionarIngrediente(gorgonzola);
		recheiosDao.adicionar(quatroQueijos);
		
		assertEquals(2, recheiosDao.listar().size());
		assertEquals(4, recheiosDao.listar().get(1).getIngredientes().size());
	}
    
    @Test
	public void removendoRecheio() throws Exception {
    	Ingrediente mussarela = new Ingrediente("Mussarela", 1.10f);
		ingredientesDao.adicionar(mussarela);
		
		Recheio recheio = new Recheio("Mussarela");
		recheio.adicionarIngrediente(mussarela);
		
		recheiosDao.adicionar(recheio);
		
		assertEquals(1, recheiosDao.listar().size());
		
		assertNotNull(recheiosDao.remover("Mussarela"));
		assertEquals(0, recheiosDao.listar().size());
	}
    
	private String lerSqlBancoDeDados() throws Exception {
        BufferedReader leitor = new BufferedReader(new FileReader(SCHEMA_SQL_BANCO));

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
