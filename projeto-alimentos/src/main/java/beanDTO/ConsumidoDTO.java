package beanDTO;

import java.math.BigDecimal;
import java.util.Date;

public class ConsumidoDTO {

	private Long id;
	private BigDecimal calorias = new BigDecimal(0);
	private BigDecimal proteinas = new BigDecimal(0);
	private BigDecimal carboidrato = new BigDecimal(0);
	private BigDecimal gordura = new BigDecimal(0);
	private Date data;
	
	public ConsumidoDTO(Date data,BigDecimal calorias, BigDecimal proteinas, BigDecimal carboidrato, BigDecimal gordura) {
		super();
		this.calorias = calorias;
		this.proteinas = proteinas;
		this.carboidrato = carboidrato;
		this.gordura = gordura;
		this.data=data;
		
	}
	public ConsumidoDTO(Long id,Date data,BigDecimal calorias, BigDecimal proteinas, BigDecimal carboidrato, BigDecimal gordura) {
		super();
		this.calorias = calorias;
		this.proteinas = proteinas;
		this.carboidrato = carboidrato;
		this.gordura = gordura;
		this.data=data;
		this.id=id;
		
	}
	public BigDecimal getCalorias() {
		return calorias;
	}

	public void setCalorias(BigDecimal calorias) {
		this.calorias = calorias;
	}

	public BigDecimal getProteinas() {
		return proteinas;
	}

	public void setProteinas(BigDecimal proteinas) {
		this.proteinas = proteinas;
	}

	public BigDecimal getCarboidrato() {
		return carboidrato;
	}

	public void setCarboidrato(BigDecimal carboidrato) {
		this.carboidrato = carboidrato;
	}

	public BigDecimal getGordura() {
		return gordura;
	}

	public void setGordura(BigDecimal gordura) {
		this.gordura = gordura;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
