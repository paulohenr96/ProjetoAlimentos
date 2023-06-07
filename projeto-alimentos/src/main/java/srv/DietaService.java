package srv;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dao.DAODieta;
import dao.DAORefeicao;
import model.ModelAlimento;
import model.ModelAlimentoRefeicao;
import model.ModelDieta;
import model.ModelRefeicao;
import model.ModelUsuario;
import util.Constantes;
import util.Mensagem;

public class DietaService {
	private final DAODieta daoDieta=new DAODieta();
	private final DAORefeicao daoRefeicao=new DAORefeicao();
	
	
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
		refeicao.addAlimento(alimentoAuxiliar);
		
		dieta.removerRefeicao(refeicao);
		verificaDieta(dieta);
	}
	
	public String novaDieta(String nome, String objetivo,ModelUsuario user) {
		// TODO Auto-generated method stub
		Long total = daoDieta.contarDietas(user.getId());
		String msg="";
		if (total == Constantes.VALOR_MAXIMO_DIETAS) {
			msg=(Mensagem.MENSAGEM_ERRO);

		} else {
			ModelDieta dieta = new ModelDieta();
			dieta.setNome(nome);
			dieta.setObjetivo(objetivo);
			dieta.setIdUsuario(user.getId());

			daoDieta.salvar(dieta);
			msg=(Mensagem.MENSAGEM_SUCESSO);

		}
		
		return msg;
	}

	public void deletarDieta(Long id) {
		// TODO Auto-generated method stub
		ModelDieta dieta = (ModelDieta) daoDieta.consultarPorId(ModelDieta.class, id);
		
		dieta.getListaRefeicoes().forEach((e) -> {
			daoRefeicao.removerAlimentoRefeicao(e.getId());
		});
		daoDieta.removerTodasRefeicaoDieta(id);
		daoDieta.deletarPorId(ModelDieta.class, id);
	}

	public String adicionarRefeicao(Long idDieta,Long idLogado,String nome, String horario) {
		// TODO Auto-generated method stub
		String hora = horario.replace("-", ":");
		ModelDieta dieta = (ModelDieta) daoDieta.consultarDietaPorId(idDieta, idLogado);
		Long total = daoDieta.contarRefeicoesDieta(dieta.getId());
		String msg="";
		if (total == Constantes.VALOR_MAXIMO_REFEICOES) {
			msg=(Mensagem.MENSAGEM_ERRO);
		} else {
			ModelRefeicao ref = new ModelRefeicao();

			ref.setNome(nome);

			try {
				ref.setHorario(new SimpleDateFormat("HH:mm").parse(hora));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ref.setDieta(dieta);
			daoRefeicao.salvar(ref);
			daoDieta.merge(dieta);
			
			msg=(Mensagem.MENSAGEM_SUCESSO);
		}
		
		return msg;
		
	}

	public void removerRefeicao(Long idDieta,Long idLogado,Long idRefeicao) {
		// TODO Auto-generated method stub
		
		ModelDieta dieta = (ModelDieta) daoDieta.consultarDietaPorId(idDieta, idLogado);
		ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, idRefeicao);
		dieta.removerRefeicao(ref);

		verificaDieta(dieta);
		daoRefeicao.removerAlimentoRefeicao(idRefeicao);
		daoRefeicao.deletarPorId(ModelRefeicao.class, idRefeicao);

	}
	
	public void verificaDieta(ModelDieta dieta) {
		if (dietaInvalida(dieta) ) {
			dieta.setTotalCalorias(BigDecimal.ZERO);
			dieta.setTotalProteinas(BigDecimal.ZERO);
			dieta.setTotalCarboidratos(BigDecimal.ZERO);
			dieta.setTotalGorduras(BigDecimal.ZERO);
		}
		daoDieta.merge(dieta);

	}
	public boolean dietaInvalida(ModelDieta dieta) {
		return (valorInvalido(dieta.getTotalCalorias()) || valorInvalido(dieta.getTotalProteinas())
				|| valorInvalido(dieta.getTotalCarboidratos())|| valorInvalido(dieta.getTotalGorduras()));
			
	
	}
	public  boolean valorInvalido(BigDecimal valor) {
		return (valor.compareTo(BigDecimal.ZERO)==-1) ;
		
	}
}
