package projetoalimentos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import dao.DAOGeneric;
import model.ModelAlimento;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
	private final DAOGeneric<ModelAlimento> daoGeneric=new DAOGeneric<>();
    @Test
    public void shouldAnswerWithTrue()
    {
    	List<ModelAlimento> retornaListaEntidadesHql = daoGeneric.retornaListaEntidadesHql("from ModelAlimento m");
    	
    }
}
