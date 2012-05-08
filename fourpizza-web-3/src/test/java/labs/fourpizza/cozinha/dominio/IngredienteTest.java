package labs.fourpizza.cozinha.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class IngredienteTest {
    @Test
    public void criandoMussarelaValidaComConstrutor() throws Exception {
        Ingrediente mussarela = new Ingrediente("Mussarela", 2.34f);
        assertEquals("Nome do ingrediente deve ser Mussarela", "Mussarela", mussarela.getNome());
        assertEquals(new Float(2.34f), new Float(mussarela.getValorPorPorcao()));
    }
    
    @Test
    public void criandoMussarelaValidaComSetter() throws Exception {
        Ingrediente mussarela = new Ingrediente();
        mussarela.setNome("Mussarela");
        mussarela.setValorPorPorcao(2.34f);
        assertEquals("Nome do ingrediente deve ser Mussarela", "Mussarela", mussarela.getNome());
        assertEquals(new Float(2.34f), new Float(mussarela.getValorPorPorcao()));
    }
    
    @Test
    public void criandoProvoloneInvalido() throws Exception {
        Ingrediente provolone = new Ingrediente(null, 2.34f);
        assertFalse("Ingrediente deve ser inválido", provolone.validar());
    }
    
    @Test
    public void criandoIngredientesIguais() throws Exception {
        Ingrediente presunto = new Ingrediente("Presunto", 3.23f);
        Ingrediente presunto2 = new Ingrediente("Presunto", 4.10f);
        assertEquals("Os presuntos devem ser iguais, pois possuem o mesmo nome.", presunto, presunto2);
    }
}
