package implementacao;

import java.math.BigDecimal;
import java.math.RoundingMode;

import interfaces.Operacoes;
import model.ModelAlimento;
import model.ModelAlimentoRefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;

public class OperacoesImp implements Operacoes {

	@Override
	public ModelRefeicao adicionarAlimentoRefeicao(ModelRefeicao ref, ModelAlimentoRefeicao ali) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelRefeicao removerAlimentoRefeicao(ModelRefeicao ref, ModelAlimentoRefeicao ali) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAlimento consumirAlimento(ModelAlimento ali, double quantidade) {
		// TODO Auto-generated method stub
		BigDecimal k=new BigDecimal((quantidade/ali.getPorcao())).setScale(2,RoundingMode.UP);
		
		
		ali.setProteina(multiplicarBigDecimal(k,ali.getProteina()));
		ali.setCaloria(multiplicarBigDecimal(k, ali.getCaloria()));
		ali.setCarboidrato(multiplicarBigDecimal(k, ali.getCarboidrato()));
		ali.setGordura(multiplicarBigDecimal(k, ali.getGordura()));
		
		return ali;
		
	}

	@Override
	public ModelDieta adicionarRefeicaoDieta(ModelDieta dieta, ModelRefeicao ref) {
		// TODO Auto-generated method stub
		dieta.setTotalCalorias( new BigDecimal (dieta.getTotalCalorias().doubleValue()+ref.getCalorias().doubleValue()));
		dieta.setTotalProteinas(dieta.getTotalProteinas().add(ref.getProteinas()));
		dieta.setTotalCarboidratos(dieta.getTotalCarboidratos().add(ref.getCarboidratos()));
		dieta.setTotalGorduras(dieta.getTotalGorduras().add(ref.getGorduras()));
		return dieta;
	}

	@Override
	public ModelDieta removerRefeicaoDieta(ModelDieta dieta, ModelRefeicao ref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelConsumidoDia consumirRefeicao(ModelConsumidoDia macros, ModelRefeicao ref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelConsumidoDia removerRefeicao(ModelConsumidoDia macros, ModelRefeicao ref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelConsumidoDia consumirAlimento(ModelConsumidoDia macros, ModelAlimento ali) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelConsumidoDia removerAlimento(ModelConsumidoDia macros, ModelAlimento ali) {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal multiplicarBigDecimal(BigDecimal n1, BigDecimal n2) {
		
		
		
		return new BigDecimal (converterDouble(n1.doubleValue()* (n2.doubleValue())));
	}
	
	public double converterDouble (double valor) {
		
		String str=valor+"";
		if (str.contains(".")) {
			String[] split = str.split("\\.");
			String inteiro=split[0];
			String decimal="";
			if (split[1].length()>1) {
				decimal=split[1].substring(0, 2);

			}else {
				decimal=split[1];
			}
			double novovalor=Double.parseDouble(inteiro+"."+decimal);
			valor=novovalor;
		}
		return valor;

	}
	
	
}
