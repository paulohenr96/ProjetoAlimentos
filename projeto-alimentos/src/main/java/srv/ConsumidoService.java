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
import model.ModelUsuario;
import util.Constantes;
import util.Mensagem;

public class ConsumidoService {

	private final DAOConsumido daoConsumido=new DAOConsumido();
	private final DAOGeneric<ModelAlimentoConsumido> daoAlimentoConsumido = new DAOGeneric<ModelAlimentoConsumido>();
	
	public String consumirAlimento(ModelUsuario userLogado,int quantidade,ModelAlimento alimento,String data) {
		ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(editaData(data), userLogado.getId());


		if (daoConsumido
				.contarTodosAlimentosConsumidos(macros.getId()) == Constantes.VALOR_MAXIMO_ALIMENTOS_CONSUMIDOS) {
			return Mensagem.VALOR_MAXIMO;
		} 
		
		
		boolean contem=false;
		if (macros != null) {
			DAOGeneric<ModelAlimentoConsumido> buscador = new DAOGeneric<ModelAlimentoConsumido>();
			List<ModelAlimentoConsumido> retornaListaEntidades = buscador
					.retornaListaEntidadesHql("from ModelAlimentoConsumido u where u.macros.id=" + macros.getId());
			macros.setListaAlimentos(retornaListaEntidades);
			for (ModelAlimentoConsumido ali : macros.getListaAlimentos()) {
				if (ali.getIdAlimento().intValue() == alimento.getId().intValue()) {
					ali.setQuantidade(ali.getQuantidade() + quantidade);
					daoAlimentoConsumido.merge(ali);
					macros.adicionarAlimento(alimento);
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
				macros.adicionarAlimento(alimento);
				daoConsumido.merge(macros);
				alimentoConsumido.setMacros(macros);

			} else {

				ModelConsumidoDia dia = new ModelConsumidoDia();
				dia.setUsuario(userLogado);
				dia.setData(editaData(data));
				dia.adicionarAlimento(alimento);
				daoConsumido.salvar(dia);
				alimentoConsumido.setMacros(dia);

			}
			daoAlimentoConsumido.salvar(alimentoConsumido);
		}
		
		return Mensagem.MENSAGEM_SUCESSO;
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
