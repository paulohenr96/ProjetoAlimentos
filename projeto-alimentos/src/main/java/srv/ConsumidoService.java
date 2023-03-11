package srv;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.DAOConsumido;
import dao.DAOGeneric;
import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelConsumidoDia;
import model.ModelRefeicao;
import model.ModelRefeicaoConsumida;
import model.ModelUsuario;
import util.Constantes;
import util.Mensagem;

public class ConsumidoService {

	private final DAOConsumido daoConsumido=new DAOConsumido();
	private final DAOGeneric<ModelAlimentoConsumido> daoAlimentoConsumido = new DAOGeneric<ModelAlimentoConsumido>();
	private final DAOGeneric<ModelRefeicao> daoRefeicao=new DAOGeneric<ModelRefeicao>();
	
	
	
	public String consumirAlimento(ModelUsuario userLogado,int quantidade,ModelAlimento alimento,String data) {
		ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(editaData(data), userLogado.getId());

		
		ModelAlimento alimentoAux=new ModelAlimento();
		alimentoAux.setPorcao(alimento.getPorcao());
		alimentoAux.setCaloria(alimento.getCaloria());
		alimentoAux.setProteina(alimento.getProteina());
		alimentoAux.setCarboidrato(alimento.getCarboidrato());
		alimentoAux.setGordura(alimento.getGordura());
		
		alimentoAux.consumir(quantidade);
		
		
		
		boolean contem=false;
		if (macros != null) {
			
			if (daoConsumido
					.contarTodosAlimentosConsumidos(macros.getId()) == Constantes.VALOR_MAXIMO_ALIMENTOS_CONSUMIDOS) {
				return Mensagem.VALOR_MAXIMO;
			} 
			
			DAOGeneric<ModelAlimentoConsumido> buscador = new DAOGeneric<ModelAlimentoConsumido>();
			List<ModelAlimentoConsumido> retornaListaEntidades = buscador
					.retornaListaEntidadesHql("from ModelAlimentoConsumido u where u.macros.id=" + macros.getId());
			macros.setListaAlimentos(retornaListaEntidades);
			for (ModelAlimentoConsumido ali : macros.getListaAlimentos()) {
				if (ali.getIdAlimento().intValue() == alimento.getId().intValue()) {
					ali.setQuantidade(ali.getQuantidade() + quantidade);
					daoAlimentoConsumido.merge(ali);
					macros.adicionarAlimento(alimentoAux);
					daoConsumido.merge(macros);
					contem = true;
					break;
				}
			}
		}

		if (!contem) {
			ModelAlimentoConsumido alimentoConsumido = new ModelAlimentoConsumido();

			alimentoConsumido.setIdAlimento(alimento.getId());
			alimentoConsumido.setNome(alimento.getNome());
			alimentoConsumido.setQuantidade(quantidade);

			if (macros != null) {
				macros.adicionarAlimento(alimentoAux);
				daoConsumido.merge(macros);
				alimentoConsumido.setMacros(macros);

			} else {

				ModelConsumidoDia dia = new ModelConsumidoDia();
				dia.setUsuario(userLogado);
				dia.setData(editaData(data));
				dia.adicionarAlimento(alimentoAux);
				daoConsumido.salvar(dia);
				alimentoConsumido.setMacros(dia);

			}
			daoAlimentoConsumido.salvar(alimentoConsumido);
		}
		
		return Mensagem.MENSAGEM_SUCESSO;
	}
	
	public String consumirRefeicao(ModelUsuario userLogado,String data,Long refeicao) {
		ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(editaData(data), userLogado.getId());
		ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, refeicao);
		DAOGeneric<ModelRefeicaoConsumida> daoGeneric = new DAOGeneric<ModelRefeicaoConsumida>();
		String hql = "";
		if (macros != null) {
			hql = "select count(u) from ModelRefeicaoConsumida u where u.macros.id=" + macros.getId();
		}
		if (macros != null && (daoGeneric.retornaLongHql(hql) == Constantes.VALOR_MAXIMO_REFEICOES_CONSUMIDAS)) {
			return (Mensagem.VALOR_MAXIMO);
		} else {
			if (macros != null) {

				macros.adicionarRefeicao(ref);
				daoConsumido.merge(macros);

			} else {

				macros = new ModelConsumidoDia();
				macros.setUsuario(userLogado);
				macros.setData(editaData(data));
				macros.adicionarRefeicao(ref);
				daoConsumido.salvar(macros);

				macros = daoConsumido.consultarConsumoDia(editaData(data), userLogado.getId());

			}
			ModelRefeicaoConsumida refeicaoNova = new ModelRefeicaoConsumida();

			refeicaoNova.setMacros(macros);
			refeicaoNova.setRefeicao(ref);

			daoGeneric = new DAOGeneric<ModelRefeicaoConsumida>();
			daoGeneric.salvar(refeicaoNova);

			return (Mensagem.MENSAGEM_SUCESSO);
		}
	}
	public Date editaData(String format) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//		String format = dateFormat.format(data);
		Date data2 = null;
		try {
			data2 = dateFormat.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data2;
	}
}
