package srv;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import beanDTO.ConsumidoDTO;
import dao.DAOAlimento;
import dao.DAOConsumido;
import dao.DAOGeneric;
import dao.DAORefeicao;
import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelConsumidoDia;
import model.ModelRefeicao;
import model.ModelRefeicaoConsumida;
import model.ModelUsuario;
import util.Constantes;
import util.Mensagem;

public class ConsumidoService {

	private final DAOConsumido daoConsumido = new DAOConsumido();
	private final DAOGeneric<ModelAlimentoConsumido> daoAlimentoConsumido = new DAOGeneric<ModelAlimentoConsumido>();
	private final DAORefeicao daoRefeicao = new DAORefeicao();
	private final DAOAlimento daoAlimento = new DAOAlimento();
	private final DAOGeneric<ModelRefeicaoConsumida> daoRefeicaoConsumida = new DAOGeneric<>();

	public String consumirAlimento(ModelUsuario userLogado, int quantidade, ModelAlimento alimento, String data) {
		ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(editaData(data), userLogado.getId());

		ModelAlimento alimentoAux = novoAlimento(alimento);

		alimentoAux.consumir(quantidade);

		boolean contem = false;
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

	public ConsumidoDTO removerAlimento(int quantidade,ModelUsuario user,String data,Long id) {
		
		
		ModelAlimentoConsumido alimentoConsumido = (ModelAlimentoConsumido) daoAlimentoConsumido
				.consultarPorId(ModelAlimentoConsumido.class, id);

		alimentoConsumido.setQuantidade(alimentoConsumido.getQuantidade() - quantidade);
		if (alimentoConsumido.getQuantidade() <= 0) {
			daoAlimentoConsumido.deletarPorId(ModelAlimentoConsumido.class, alimentoConsumido.getId());

		} else {
			daoAlimentoConsumido.merge(alimentoConsumido);
		}
		ModelAlimento alimento = ((ModelAlimento) daoAlimento.consultarPorId(ModelAlimento.class,
				alimentoConsumido.getIdAlimento()));
		ModelAlimento novoAlimento = novoAlimento(alimento);
		novoAlimento.consumir(quantidade);
		
		ModelConsumidoDia consumoDia = daoConsumido.consultarConsumoDia(editaData(data), user.getId());
		consumoDia.retirarAlimento(novoAlimento);
		System.out.println(quantidade+" g -Proteinas Retiradas :"+novoAlimento.getProteina()+" - Total de Proteinas : "+consumoDia.getProteinas());
		
		verificaConsumo(consumoDia);
		return gerarDTO(consumoDia);
	}
	private ConsumidoDTO gerarDTO(ModelConsumidoDia c) {
		return new ConsumidoDTO(c.getId(),c.getData(),c.getCalorias()
				,c.getProteinas(),c.getCarboidrato(),c.getGordura());
	}

	public String consumirRefeicao(ModelUsuario userLogado, String data, Long refeicao) {
		ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(editaData(data), userLogado.getId());
		ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, refeicao);
		DAOGeneric<ModelRefeicaoConsumida> daoGeneric = new DAOGeneric<ModelRefeicaoConsumida>();
		String hql = "";
		String retorno;
		if (macros != null) {
			hql = "select count(u) from ModelRefeicaoConsumida u where u.macros.id=" + macros.getId();
		}
		if (macros != null && (daoGeneric.retornaLongHql(hql) == Constantes.VALOR_MAXIMO_REFEICOES_CONSUMIDAS)) {
			retorno = (Mensagem.VALOR_MAXIMO);
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
			retorno = (Mensagem.MENSAGEM_SUCESSO);
		}
		daoConsumido.flush();

		return retorno;
	}

	public ModelConsumidoDia removerRefeicao(Long id, ModelUsuario user, String data) {
		ModelRefeicaoConsumida refeicaoConsumida = (ModelRefeicaoConsumida) daoRefeicaoConsumida
				.consultarPorId(ModelRefeicaoConsumida.class, id);
		ModelRefeicao ref = daoRefeicao.consultarPorId(refeicaoConsumida.getRefeicao().getId());
		daoRefeicaoConsumida.deletarPorId(ModelRefeicaoConsumida.class, refeicaoConsumida.getId());

		ModelConsumidoDia consumoDia = daoConsumido.consultarConsumoDia(editaData(data), user.getId());
		consumoDia.removerRefeicao(ref);
		verificaConsumo(consumoDia);
		return consumoDia;

	}
	public void verificaConsumo(ModelConsumidoDia consumoDia) {
		if (consumoDia.getProteinas().signum()!=1 || consumoDia.getCalorias().signum()!=1  ||
				consumoDia.getCarboidrato().signum()!=1  || consumoDia.getGordura().signum()!=1 ) {
				daoConsumido.deletarPorId(ModelConsumidoDia.class, consumoDia.getId());
			} else {
				daoConsumido.merge(consumoDia);

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

	public ModelAlimento novoAlimento(ModelAlimento alimento) {
		ModelAlimento alimentoAux = new ModelAlimento();
		alimentoAux.setPorcao(alimento.getPorcao());
		alimentoAux.setCaloria(alimento.getCaloria());
		alimentoAux.setProteina(alimento.getProteina());
		alimentoAux.setCarboidrato(alimento.getCarboidrato());
		alimentoAux.setGordura(alimento.getGordura());

		return alimentoAux;
	}

	public String limpar(String data, ModelUsuario user) {
		// TODO Auto-generated method stub
		ModelConsumidoDia consumoDia = daoConsumido.consultarConsumoDia(editaData(data), user.getId());
		String msg = "";

		if (consumoDia != null) {
			consumoDia.setCalorias(new BigDecimal(0));
			consumoDia.setProteinas(new BigDecimal(0));
			consumoDia.setCarboidrato(new BigDecimal(0));
			consumoDia.setGordura(new BigDecimal(0));
			daoConsumido.deletarAlimentoConsumidoPorId(consumoDia.getId());
			String hql = "delete from " + ModelRefeicaoConsumida.class.getCanonicalName() + " m where m.macros.id="
					+ consumoDia.getId();
			daoRefeicaoConsumida.realizarUpdate(hql);
			daoConsumido.deletarPorId(ModelConsumidoDia.class, consumoDia.getId());

			msg = Mensagem.MENSAGEM_SUCESSO;

		}

		return msg;
	}
}
