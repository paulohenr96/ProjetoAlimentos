package srv;

import java.util.ArrayList;
import java.util.List;

import dao.DAOGeneric;
import dao.DAORefeicao;
import model.ModelAlimento;

public class AlimentoService {

	private final DAOGeneric<ModelAlimento> daoAlimento = new DAOGeneric<>();
	private final DAORefeicao daoRefeicao = new DAORefeicao();

	public List<Object[]> editarAlimento(ModelAlimento alimento) {
		// TODO Auto-generated method stub
		Long tamanho = daoRefeicao.contarRefeicoesComOAlimento(alimento.getId());
		List<Object[]> listaInnerJoin = new ArrayList<Object[]>();
		if (tamanho == 0) {
			daoAlimento.merge(alimento);
		} else {
			listaInnerJoin = daoRefeicao.refeicoesAlimentosInnerJoin(alimento.getId());

		}

		return listaInnerJoin;
	}

}
