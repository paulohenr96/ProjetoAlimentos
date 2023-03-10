package srv;

import java.math.BigDecimal;

import dao.DAODieta;
import model.ModelAlimento;
import model.ModelAlimentoRefeicao;
import model.ModelDieta;
import model.ModelRefeicao;

public class DietaService {
	private final DAODieta daoDieta=new DAODieta();
	
	
	
	public void adicionarAlimentoRefeicao(ModelDieta dieta,double quantidade, ModelAlimento alimento ) {
		ModelRefeicao refaux = new ModelRefeicao();
		
		ModelAlimentoRefeicao aliaux = new ModelAlimentoRefeicao();
		aliaux.setAlimento(alimento);
		aliaux.setQuantidade(quantidade);
		
		refaux.addAlimento(aliaux);
		dieta.adicionarRefeicao(refaux);
		dieta.setListaRefeicoes(daoDieta.todasRefsDieta(dieta.getId()));
		daoDieta.merge(dieta);
	}
	
	public void removerAlimentoRefeicao(ModelRefeicao ref,ModelAlimentoRefeicao ali,int quantidadeRetirar) {
		
		ModelAlimentoRefeicao alimentoAuxiliar = new ModelAlimentoRefeicao();
		alimentoAuxiliar.setAlimento(ali.getAlimento());
		alimentoAuxiliar.setQuantidade(quantidadeRetirar);
		
		
		ModelRefeicao refeicao= new ModelRefeicao();
		ModelDieta dieta = ref.getDieta();
		System.out.println(dieta);
		refeicao.addAlimento(alimentoAuxiliar);
		
		dieta.removerRefeicao(refeicao);
		
		DAODieta daoDieta = new DAODieta();
		ModelDieta merge = daoDieta.merge(dieta);
	}
	
//	public ModelDieta removeralimento(ModelAlimentoRefeicao ali,ModelDieta dieta) {
//		// TODO Auto-generated method stub
//		ModelAlimento alimento=new ModelAlimento();
//		alimento.setPorcao(ali.getAlimento().getPorcao());
//		alimento.setCaloria(ali.getAlimento().getCaloria());
//		alimento.setProteina(ali.getAlimento().getProteina());
//		alimento.setCarboidrato(ali.getAlimento().getCarboidrato());
//		alimento.setGordura(ali.getAlimento().getGordura());
//		
//		alimento.consumir(ali.getQuantidade());
//		
//		dieta.setTotalCalorias(dieta.getTotalCalorias().subtract(alimento.getCaloria()));
//		dieta.setTotalProteinas(dieta.getTotalProteinas().subtract(alimento.getProteina()));
//		dieta.setTotalCarboidratos(dieta.getTotalCarboidratos().subtract(alimento.getCarboidrato()));
//		dieta.setTotalGorduras(dieta.getTotalGorduras().subtract(alimento.getGordura()));
//
//
//
////		calorias = new BigDecimal(calorias.doubleValue() - (alimento.getCaloria().doubleValue()));
////		proteinas = new BigDecimal(proteinas.doubleValue() - (alimento.getProteina().doubleValue()));
////		carboidratos = new BigDecimal(carboidratos.doubleValue() - (alimento.getCarboidrato().doubleValue()));
////		gorduras = new BigDecimal(gorduras.doubleValue() - (alimento.getGordura().doubleValue()));
//
//	}

}
