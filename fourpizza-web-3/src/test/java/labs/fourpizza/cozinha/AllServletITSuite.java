package labs.fourpizza.cozinha;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	IngredientesServletIT.class,
	RecheiosServletIT.class
})
public class AllServletITSuite {

}
