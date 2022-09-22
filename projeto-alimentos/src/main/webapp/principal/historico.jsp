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





<select onchange="mostrarHistorico(1)" class="custom-select custom-select-lg mb-3">
  <option value="2">2 por pagina</option>
  <option value="4">4 por pagina</option>
  <option selected value="5">5 por pagina</option>
    <option value="10">10 por pagina</option>
  
</select>

					<table class="table table-striped-columns">
						<thead>
							<tr>
								<th>Data</th>
								<th>Calorias</th>
								<th>Proteina</th>
								<th>Gordura</th>
								<th>Carboidrato</th>
							</tr>
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
	
	mostrarHistorico(1);
		function mostrarHistorico(paginaatual) {
			var porpagina=document.querySelector("select").value;
			$.ajax({
				method : "get",
				url : window.location.pathname.substring(0, window.location.pathname.indexOf("/",2))+ "/ServletAlimento",
				data : "paginaatual=" + paginaatual + "&porpagina=" + porpagina
						+ "&acao=historico",
				success : function(response, textStatus, xhr) {
				
					console.log(response);
					var json=JSON.parse(response);
					document.querySelector(".table > tbody").innerHTML="";
					json.forEach((e)=>{
						console.log();
						document.querySelector(".table > tbody").innerHTML+="<tr><td>"+new Date(e.data).toDateString()+"</td>"+"<td>"+e.calorias+"</td>"+"<td>"+e.proteinas+"</td>"+"<td>"+e.carboidrato+"</td>"+"<td>"+e.gordura+"</td>"+"</tr>";
						
						
						
						
						
						
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

				}

			}).fail(function (xhr, status, errorThrown) {
			      alert("Error ao buscar usuário por nome" + xhr.responseText);
		    });

		}
		
		
		function getContextPath() {
			   return window.location.protocol+"//"+ window.location.host+window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
			}
			alert(getContextPath());
	</script>
</body>
</html>
