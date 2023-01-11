<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Gráfico</title>
<link href="<%=request.getContextPath()%>/assets/css/styles.css"
	rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
	crossorigin="anonymous"></script>






			
					<h2>Gráfico de consumo nutricional</h2>

					<form action="<%=request.getContextPath()%>/ServletAlimento"
						id="form-macro"
						 method="get">
						
						
						
						
						<input  type="hidden" id="acao" name="acao" value=""> <select
							id="macro" class="form-select mb-2"
							aria-label="Default select example">
							<option selected>Escolha o macronutriente</option>
							<option value="calorias">Calorias</option>

							<option value="proteina">Proteina</option>
							<option value="gordura">Gordura</option>
							<option value="carboidrato">Carboidrato</option>
						</select>

						<button type="button" onclick="mostrarGrafico()"
							class="btn btn-dark">Pesquisar</button>




																<div>
																	<canvas id="myChart"></canvas>
																</div>

					</form>










	





	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

	<script src="https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>

	<script type="text/javascript">
	
	var myChart=new Chart(document.getElementById("myChart"));

	
		function mostrarGrafico() {
			var macro = document.getElementById("macro").value;

			var urlAction = document.getElementById("form-macro").action;

			if (macro != null && macro != "") {
				$.ajax({
					method : "get",
					url : urlAction,
					data : "acao=mostrargrafico&macro=" + macro,
					success : function(response, textStatus, xhr) {
						
						var json=JSON.parse(response);
						if (json.listaData.length>0){
							alert("Aqui");
							var listaMacro;
							var titulo;
							var cor;
							if (macro=='calorias'){
								listaMacro=json.listaCalorias;
								titulo="Calorias";
								cor="brown";
							}
							if (macro=='proteina'){
								listaMacro=json.listaProteinas;
								titulo="Proteinas";
								cor="blue";

							}
							if (macro=='carboidrato'){
								listaMacro=json.listaCarboidratos;
								titulo="Carboidratos";
								cor="green";

							}
							if (macro=='gordura'){
								listaMacro=json.listaGorduras;
								titulo="Gorduras";
								cor="orangered";
								

							}
							myChart.destroy();
							
							myChart = new Chart(
								    document.getElementById('myChart'),
								    {
									    type: 'line',
									    data:  {
										    labels:json.listaData  ,
										    datasets: [{
										      label: 'Proteinas',
										      backgroundColor: 'blue',
										      borderColor: 'blue',
										      data: json.listaProteinas,
										    },
										    {
											      label: 'Gorduras',
											      backgroundColor: 'orangered',
											      borderColor: 'orangered',
											      data: json.listaGorduras,
											},
											{
											      label: 'Carboidratos',
											      backgroundColor: 'green',
											      borderColor: 'green',
											      data: json.listaCarboidratos,
											}
										    
										    
										    ]
										  },
									    options: {
									        scales: {
									            x: {
									              type: 'time',
									              time:{
									            	  unit:'day'
									              }
									            }
									          }}
									  }
								  );
							console.log(response);
						}else {
							alert("Vazio")
							$("#myChart").hide();
						}
						
					},
				}).fail(
						function(xhr, status, errothrown) {
							alert("Error ao buscar dados para o gráfico"
									+ xhr.responseText);

						});

			}

		}
	</script>
</body>
</html>
