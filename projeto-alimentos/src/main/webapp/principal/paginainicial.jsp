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
<title>Início</title>
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
					<h2>Consumo Médio</h2>
				</div>
				<div class="container-fluid px-4">

					<div class="row">

						<div class="col-sm-1 col-md-2">
							<div class="card bg-primary text-white mb-4">
								<div class="card-body">Calorias</div>
								<div
									class="card-footer d-flex align-items-center justify-content-between">
									<span id="media-calorias"></span>
									
								</div>
							</div>
						</div>
						<div class="col-sm-3 col-md-2">
							<div class="card bg-warning text-white mb-4">
								<div class="card-body">Proteínas</div>
								<div
									class="card-footer d-flex align-items-center justify-content-between">
									<span id="media-proteinas"></span>
									
								</div>
							</div>
						</div>
				

						<div class="col-sm-3 col-md-2">
							<div class="card bg-success text-white mb-4">
								<div class="card-body">Carboidratos</div>
								<div
									class="card-footer d-flex align-items-center justify-content-between">
																		<span id="media-carboidratos"></span>

								</div>
							</div>
						</div>
						<div class="col-sm-3 col-md-2">
							<div class="card bg-danger text-white mb-4">
								<div class="card-body">Gorduras</div>
								<div
									class="card-footer d-flex align-items-center justify-content-between">
																		<span id="media-gorduras"></span>

								</div>
							</div>
						</div>

						<div class="container-fluid px-4">
						<h2>Histórico</h2>
							<select onchange="mostrarHistorico(1)"
								class="custom-select custom-select-lg mb-3">
								<option value="2">2 por pagina</option>
								<option value="4">4 por pagina</option>
								<option selected value="5">5 por pagina</option>
								<option value="10">10 por pagina</option>

							</select>
							<form action="<%=request.getContextPath()%>/ServletAlimento"
								id="form-macro" method="get">
								<input type="hidden" name="acao" value=""
									id="acaoRelatorioImprimirTipo">
								<button type="button" onclick="imprimirHtml()"
									class="btn btn-dark  btn-sm">Imprimir Histórico</button>


<table class="table table-striped-columns">
						<thead>
						</thead>
						<tbody>
						</tbody>
					</table>
					<nav aria-label="Page navigation example">
						<ul class="pagination">
						</ul>
					</nav>










								<h2>Gráfico de Macronutrientes Consumidos</h2>

								<input type="hidden" id="acao" name="acao" value=""> <select
									id="macro" class="form-select mb-2"
									aria-label="Default select example">
									<option selected value="todos">Todos</option>
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


					</div>
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

	<script
		src="https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

	<script type="text/javascript">
		var myChart = new Chart(document.getElementById("myChart"));

		mostrarGrafico();
		function mostrarGrafico() {
			var macro = document.getElementById("macro").value;

			var urlAction = document.getElementById("form-macro").action;

			
			
			
			if (macro != null && macro != "") {
				$.ajax(
						{
							method : "get",
							url : urlAction,
							data : "acao=mostrargrafico&macro=" + macro,
							success : function(response, textStatus, xhr) {

								var json = JSON.parse(response);

								var listaMacro;
								var titulo;
								var cor;
								if (macro != 'todos') {
									if (macro == 'calorias') {
										listaMacro = json.listaCalorias;
										titulo = "Calorias";
										cor = "brown";
									}
									if (macro == 'proteina') {
										listaMacro = json.listaProteinas;
										titulo = "Proteinas";
										cor = "blue";

									}
									if (macro == 'carboidrato') {
										listaMacro = json.listaCarboidratos;
										titulo = "Carboidratos";
										cor = "green";

									}
									if (macro == 'gordura') {
										listaMacro = json.listaGorduras;
										titulo = "Gorduras";
										cor = "orangered";

									}
									var vetor = [ {
										label : titulo,
										backgroundColor : cor,
										borderColor : cor,
										data : listaMacro,
									} ];
								} else {
									var vetor = [ {
										label : 'Proteinas',
										backgroundColor : 'blue',
										borderColor : 'blue',
										data : json.listaProteinas,
									}, {
										label : 'Carboidratos',
										backgroundColor : 'red',
										borderColor : 'red',
										data : json.listaCarboidratos,
									}, {
										label : 'Gorduras',
										backgroundColor : 'green',
										borderColor : 'green',
										data : json.listaGorduras,
									}, {
										label : 'Calorias',
										backgroundColor : 'black',
										borderColor : 'black',
										data : json.listaCalorias,
									} ];
								}

								myChart.destroy();

								myChart = new Chart(document
										.getElementById('myChart'), {
									type : 'line',
									data : {
										labels : json.listaData,
										datasets : vetor
									},
									options : {
										scales : {
											x : {
												type : 'time',
												time : {
													unit : 'day'
												}
											}
										}
									}
								});
								console.log(response);
							},
						}).fail(
						function(xhr, status, errothrown) {
							alert("Error ao buscar dados para o gráfico"
									+ xhr.responseText);

						});

			}

		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
// 		Historico --------------------------------------------------------
		var ordem="data";
		var asc="desc";
		document.querySelector(".table > tbody").innerHTML="<div class=\"spinner-border\" role=\"status\">  <span class=\"sr-only\">Loading...</span>  </div>";

		function ordenarData(){
			ordem="data";
			inverterOrdem();
			mostrarHistorico(1);

		}
		function inverterOrdem(){
			if (asc=="asc"){
				asc="desc";
			}else {
				asc="asc";
			}
		}
		 function imprimirHtml(){

		    	document.getElementById("acaoRelatorioImprimirTipo").value="ImprimirRelatorioMacrosPDF";
		    	$("#form-macro").submit();
		    	return false;
		    }
		function ordenarProteinas(){
			ordem="proteinas";
			inverterOrdem();

			mostrarHistorico(1);

		}
		function ordenarCalorias(){
			ordem="calorias";
			inverterOrdem();

			mostrarHistorico(1);

		}
		function ordenarCarboidrato(){
			ordem="carboidrato";
			inverterOrdem();

			mostrarHistorico(1);

		}
		function ordenarGordura(){
			ordem="gordura";
			inverterOrdem();

			mostrarHistorico(1);

		}
		mostrarHistorico(1);
			function mostrarHistorico(paginaatual) {
				var porpagina=document.querySelector("select").value;
				$.ajax({
					method : "get",
					url : window.location.pathname.substring(0, window.location.pathname.indexOf("/",2))+ "/ServletAlimento",
					data : "paginaatual=" + paginaatual + "&porpagina=" + porpagina
							+ "&acao=historico&ordem="+ordem+"&asc="+asc,
					success : function(response, textStatus, xhr) {
						
						console.log(response);
						var json=JSON.parse(response);
						if (json.length!=0){
							$(".table").empty();
							$(".table").append("<thead></thead>");

							$(".table>thead").append("<tr>"+
									"<th onclick=\"ordenarData()\">Data</th>"+
									"<th onclick=\"ordenarCalorias()\">Calorias</th>"+
									"<th onclick=\"ordenarProteinas()\">Proteina</th>"+
									"<th onclick=\"ordenarGordura()\">Gordura</th>"+
									"<th onclick=\"ordenarCarboidrato()\">Carboidrato</th>"+
								"</tr>");
							$(".table").append("<tbody></tbody>");

						json.forEach((e)=>{
							var minhaData=new Date(e.data);
							console.log();
							document.querySelector(".table > tbody").innerHTML+="<tr><td>"+minhaData.getUTCDate()+"/"+(minhaData.getUTCMonth() + 1)+"/"+(minhaData.getUTCFullYear())+"</td>"+"<td>"+e.calorias+" g</td>"+"<td>"+e.proteinas+" g</td>"+"<td>"+e.carboidrato+" g</td>"+"<td>"+e.gordura+" g</td>"+"</tr>";
							
							
							
							
							
							
						})
						
						
						
						var paginacao="";
						var previous="";
						var next="";
						var totalElementos = xhr
						.getResponseHeader("totalPagina");
						console.log("TOTAL : "+totalElementos);
						const quotient = Math.floor(totalElementos/porpagina);
						const remainder = totalElementos % porpagina;
						var totalPagina=quotient;
						if (remainder!=0){
							totalPagina++;
						}
						for (var i=1;i<=totalPagina;i++){
							
							
							if (paginaatual==i){
								
									paginacao+="<li class=\"page-item active\"  onclick=\"mostrarHistorico("+i+")\"><a  class=\"page-link\" >"+i+"</a></li>";
							}
							else {
								paginacao+="<li class=\"page-item\" onclick=\"mostrarHistorico("+i+")\"><a class=\"page-link\"  >"+i+"</a></li>";

							}
						}
						if  (totalPagina>1){
							previous="<li class=\"page-item\" onclick=\"mostrarHistorico("+(paginaatual-1)+")\"><a class=\"page-link\"  ><</a></li>";
							next="<li class=\"page-item\" onclick=\"mostrarHistorico("+(paginaatual+1)+")\"><a class=\"page-link\"  >></a></li>";

							if (paginaatual==1){
								previous="<li class=\"page-item disabled\" ><a class=\"page-link\"  ><</a></li>";

							}
							if (paginaatual==totalPagina){
								next="<li class=\"page-item disabled\" ><a class=\"page-link\"  >></a></li>";

							}

						}
						document.querySelector("ul.pagination").innerHTML=previous+paginacao+next;

						
						
						}else{
							$(".spinner-border").remove();
							
							$("table.table").empty();
							$(".table").append("<thead></thead>");

							$("table.table>thead ").html("<span>O seu histórico está vazio.</span>");
						}
						
					}

				}).fail(function (xhr, status, errorThrown) {
					
				      alert("Error ao buscar usuário por nome" + xhr.responseText);
			    });

			}
			
			mediasMacros();
			function mediasMacros(){
				  var urlAction = document.getElementById("form-macro").action;
					

					  $.ajax({
					      method: "get",
					      url: urlAction,
					      data: "acao=mediamacros",
					      success: function (response, textStatus, xhr) {
					    	 var json=JSON.parse(response);
					    	 $("#media-calorias").html(Number(json[0][0]).toFixed(2)+" Kcal");
					    	 $("#media-proteinas").html(Number(json[0][1]).toFixed(2)+" gramas");
					    	 $("#media-carboidratos").html(Number(json[0][2]).toFixed(2)+" gramas");
					    	 $("#media-gorduras").html(Number(json[0][3]).toFixed(2)+" gramas");

					      },
					    }).fail(function (xhr, status, errorThrown) {
					      alert("Error ao buscar usuário por nome" + xhr.responseText);
					    });
				 
			}
		
	</script>
</body>
</html>
