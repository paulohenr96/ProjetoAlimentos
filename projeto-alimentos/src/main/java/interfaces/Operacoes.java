package interfaces;

import java.math.BigDecimal;

import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelAlimentoRefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;

public interface Operacoes {

	public ModelRefeicao adicionarAlimentoRefeicao(ModelRefeicao ref,ModelAlimentoRefeicao ali);
	public ModelRefeicao removerAlimentoRefeicao (ModelRefeicao ref,ModelAlimentoRefeicao ali);
	public ModelAlimento consumirAlimento (ModelAlimento ali, double quantidade);
	public ModelDieta adicionarRefeicaoDieta ( ModelDieta dieta,ModelRefeicao ref);
	public ModelDieta removerRefeicaoDieta(ModelDieta dieta,ModelRefeicao ref);
	public ModelConsumidoDia consumirRefeicao(ModelConsumidoDia macros,ModelRefeicao ref);
	public ModelConsumidoDia removerRefeicao(ModelConsumidoDia macros,ModelRefeicao ref);
	public ModelConsumidoDia consumirAlimento(ModelConsumidoDia macros,ModelAlimento ali);
	public ModelConsumidoDia removerAlimento(ModelConsumidoDia macros,ModelAlimento ali);
	
}
