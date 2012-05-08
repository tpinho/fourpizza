package labs.fourpizza.cozinha.dao.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	JdbcIngredientesDaoIT.class,
	JdbcRecheiosDaoIT.class
})
public class AllJdbcDaoITSuite {

}
