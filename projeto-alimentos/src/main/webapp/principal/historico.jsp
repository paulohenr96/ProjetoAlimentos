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
<title>Histórico</title>
<link href="<%=request.getContextPath()%>/assets/css/styles.css"
	rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
	crossorigin="anonymous"></script>
<style>
th:hover {
	cursor:pointer;
}

</style>

</head>
<body class="sb-nav-fixed">
	<jsp:include page="header.jsp"></jsp:include>
	<div id="layoutSidenav">
		<jsp:include page="navbar.jsp" />
		<div id="layoutSidenav_content">






			<main>
				<div class="container-fluid px-4">
					<h1>Seja Bem-Vindo ao meu Projeto !</h1>





					<select onchange="mostrarHistorico(1)"
						class="custom-select custom-select-lg mb-3">
						<option value="2">2 por pagina</option>
						<option value="4">4 por pagina</option>
						<option selected value="5">5 por pagina</option>
						<option value="10">10 por pagina</option>

					</select>
					
					 <form method="get"
						action="<%=request.getContextPath()%>/ServletAlimento"
						id="form-user">
                        <input type="hidden" name="acao" value="" id="acaoRelatorioImprimirTipo">
                        <button type="button" onclick="imprimirHtml()" class="btn btn-dark">IMPRIMIR</button>
                        
                        </form>

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
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script type="text/javascript">
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
	    	$("#form-user").submit();
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
						$(".table>thead").html("");
						$(".table>thead").append("<tr>"+
								"<th onclick=\"ordenarData()\">Data</th>"+
								"<th onclick=\"ordenarCalorias()\">Calorias</th>"+
								"<th onclick=\"ordenarProteinas()\">Proteina</th>"+
								"<th onclick=\"ordenarGordura()\">Gordura</th>"+
								"<th onclick=\"ordenarCarboidrato()\">Carboidrato</th>"+
							"</tr>");
					
					document.querySelector(".table > tbody").innerHTML="";
					json.forEach((e)=>{
						var minhaData=new Date(e.data);
						console.log();
						document.querySelector(".table > tbody").innerHTML+="<tr><td>"+minhaData.getUTCDate()+"/"+(minhaData.getUTCMonth() + 1)+"/"+(minhaData.getUTCFullYear())+"</td>"+"<td>"+e.calorias+"</td>"+"<td>"+e.proteinas+"</td>"+"<td>"+e.carboidrato+"</td>"+"<td>"+e.gordura+"</td>"+"</tr>";
						
						
						
						
						
						
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
							paginacao+="<li class=\"page-item\" onclick=\"mostrarHistorico("+i+")\"><a class=\"page-link\" href=\"#\" >"+i+"</a></li>";

						}
					}
					if  (totalPagina>1){
						previous="<li class=\"page-item\" onclick=\"mostrarHistorico("+(paginaatual-1)+")\"><a class=\"page-link\" href=\"#\" ><</a></li>";
						next="<li class=\"page-item\" onclick=\"mostrarHistorico("+(paginaatual+1)+")\"><a class=\"page-link\" href=\"#\" >></a></li>";

						if (paginaatual==1){
							previous="<li class=\"page-item disabled\" ><a class=\"page-link\" href=\"#\" ><</a></li>";

						}
						if (paginaatual==totalPagina){
							next="<li class=\"page-item disabled\" ><a class=\"page-link\" href=\"#\" >></a></li>";

						}

					}
					document.querySelector("ul.pagination").innerHTML=previous+paginacao+next;

					
					
					}else{
						$(".spinner-border").remove();
						$("main > div.container-fluid").html("<h2>O seu histórico está vazio.</h2>");
					}
					
				}

			}).fail(function (xhr, status, errorThrown) {
				
			      alert("Error ao buscar usuário por nome" + xhr.responseText);
		    });

		}
		
		
// 		function getContextPath() {
// 			   return window.location.protocol+"//"+ window.location.host+window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
// 			}
// 			alert(getContextPath());
	</script>
</body>
</html>
