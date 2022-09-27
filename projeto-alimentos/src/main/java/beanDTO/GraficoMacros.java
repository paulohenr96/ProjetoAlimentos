package beanDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraficoMacros {
	List <Date> listaData=new ArrayList<Date>();
	List <BigDecimal> listaCalorias=new ArrayList<BigDecimal>();
	List <BigDecimal> listaProteinas=new ArrayList<BigDecimal>();
	List <BigDecimal> listaCarboidratos=new ArrayList<BigDecimal>();
	List <BigDecimal> listaGorduras=new ArrayList<BigDecimal>();
	public List<Date> getListaData() {
		return listaData;
	}
	public void setListaData(List<Date> listaData) {
		this.listaData = listaData;
	}
	public List<BigDecimal> getListaCalorias() {
		return listaCalorias;
	}
	public void setListaCalorias(List<BigDecimal> listaCalorias) {
		this.listaCalorias = listaCalorias;
	}
	public List<BigDecimal> getListaProteinas() {
		return listaProteinas;
	}
	public void setListaProteinas(List<BigDecimal> listaProteinas) {
		this.listaProteinas = listaProteinas;
	}
	public List<BigDecimal> getListaCarboidratos() {
		return listaCarboidratos;
	}
	public void setListaCarboidratos(List<BigDecimal> listaCarboidratos) {
		this.listaCarboidratos = listaCarboidratos;
	}
	public List<BigDecimal> getListaGorduras() {
		return listaGorduras;
	}
	public void setListaGorduras(List<BigDecimal> listaGorduras) {
		this.listaGorduras = listaGorduras;
	}


}
