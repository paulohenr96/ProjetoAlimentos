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
<title>Sidenav Light - SB Admin</title>
<link href="<%=request.getContextPath()%>/assets/css/styles.css"
	rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
	crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
	<jsp:include page="header.jsp"></jsp:include>
	<div id="layoutSidenav">
		<jsp:include page="navbar.jsp" />
		<div id="layoutSidenav_content">






			<main>
				<div class="container-fluid px-4">
					<h1>Seja Bem-Vindo ao meu Projeto !</h1>

					<form action="<%=request.getContextPath()%>/ServletAlimento"
						id="form-macro"
						 method="get">
						
						
						
						
						<input type="hidden" id="acao" name="acao" value=""> <select
							id="macro" class="form-select"
							aria-label="Default select example">
							<option selected>Open this select menu</option>
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
				</div>
			</main>










			<footer class="py-4 bg-light mt-auto">
				<div class="container-fluid px-4">
					<div
						class="d-flex align-items-center justify-content-between small">
						<div class="text-muted">Copyright &copy; Your Website 2022</div>
						<div>
							<a href="#">Privacy Policy</a> &middot; <a href="#">Terms
								&amp; Conditions</a>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</div>





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

			alert(urlAction);
			console.log("Valor macro=" + macro);
			if (macro != null && macro != "") {
				$.ajax({
					method : "get",
					url : urlAction,
					data : "acao=mostrargrafico&macro=" + macro,
					success : function(response, textStatus, xhr) {

						var json=JSON.parse(response);
						
						console.log(json);
						var listaMacro;
						if (macro=='calorias'){
							listaMacro=json.listaCalorias;
						}
						if (macro=='proteina'){
							listaMacro=json.listaProteinas;
						}
						if (macro=='carboidrato'){
							listaMacro=json.listaCarboidratos;
						}
						if (macro=='gordura'){
							listaMacro=json.listaGorduras;
						}
						myChart.destroy();
						
						myChart = new Chart(
							    document.getElementById('myChart'),
							    {
								    type: 'line',
								    data:  {
									    labels: json.listaData,
									    datasets: [{
									      label: 'Gráfico de Média Salarial por Perfil',
									      backgroundColor: 'rgb(255, 99, 132)',
									      borderColor: 'rgb(255, 99, 132)',
									      data: listaMacro,
									    }]
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
