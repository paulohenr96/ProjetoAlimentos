package srv;

import java.math.BigDecimal;
import java.util.List;

import dao.DAOGeneric;
import dao.DAORefeicao;
import model.ModelAlimento;
import model.ModelAlimentoRefeicao;
import model.ModelRefeicao;

public class RefeicaoService {

	
	private final DAORefeicao daoRefeicao=new DAORefeicao();
	private final DAOGeneric<ModelAlimentoRefeicao> daoAlimentoRefeicao=new DAOGeneric<ModelAlimentoRefeicao>();
	
	public ModelRefeicao removerAlimento (ModelAlimentoRefeicao ali,ModelRefeicao ref,int quantidadeRetirar) {
		Double quantidadeAtual = ali.getQuantidade();
		Double quantidadeNova = quantidadeAtual - quantidadeRetirar;

		ModelAlimentoRefeicao alimentoAuxiliar = new ModelAlimentoRefeicao();
		alimentoAuxiliar.setAlimento(ali.getAlimento());
		alimentoAuxiliar.setQuantidade(quantidadeRetirar);

		ref.removeralimento(alimentoAuxiliar);

		if (quantidadeNova.intValue() <= 0) {
			daoAlimentoRefeicao.deletarPorId(ModelAlimentoRefeicao.class, ali.getId());
		} else {
			ali.setQuantidade(quantidadeNova);
			ali = daoAlimentoRefeicao.merge(ali);
		}
		
		if (refInvalida(ref) || daoRefeicao.contarAlimentosRefeicao(ref.getId()).intValue() == 0) {
			ref.setCalorias(BigDecimal.ZERO);
			ref.setProteinas(BigDecimal.ZERO);
			ref.setCarboidratos(BigDecimal.ZERO);
			ref.setGorduras(BigDecimal.ZERO);

		}

		return daoRefeicao.merge(ref);
	}
	public ModelRefeicao adicionarAlimento(ModelRefeicao ref,double quantidade,ModelAlimento alimento) {
		boolean contem = false;
		List<ModelAlimentoRefeicao> lista = (List<ModelAlimentoRefeicao>) daoRefeicao
				.consultarAlimentosRefeicao(ref.getId());
		if (lista != null) {
			for (ModelAlimentoRefeicao ali : lista) {
				if (ali.getAlimento().getId().intValue() == alimento.getId().intValue()) {
					ali.setQuantidade(ali.getQuantidade() + quantidade);
					daoAlimentoRefeicao.merge(ali);

					contem = true;
				}
			}
		}

		ModelAlimentoRefeicao ali = new ModelAlimentoRefeicao();
		ali.setAlimento(alimento);
		ali.setQuantidade(quantidade);
		ali.setRefeicao(ref);
		if (!contem) {
			daoAlimentoRefeicao.salvar(ali);

		}
		ref.addAlimento(ali);
		return daoRefeicao.merge(ref);
	}
	
	public void salvar(Long idLogado,String nome) {
		ModelRefeicao refeicao = new ModelRefeicao();

		refeicao.setNome(nome);
		refeicao.setIdUserLogado(idLogado);

		daoRefeicao.salvar(refeicao);
	}
	
	public boolean refInvalida(ModelRefeicao ref) {
		return (valorInvalido(ref.getCalorias()) || valorInvalido(ref.getProteinas())
				|| valorInvalido(ref.getCarboidratos())|| valorInvalido(ref.getGorduras()));
	}
	public  boolean valorInvalido(BigDecimal valor) {
		return (valor.compareTo(BigDecimal.ZERO)==-1) ;
		
	}
	
}
