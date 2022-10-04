<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<div class="container-sm px-4">
					<h1>Seja Bem-Vindo ao meu Projeto !</h1>
					<form id="form-user"
						action="<%=request.getContextPath()%>/ServletDieta" method="get">
						<input type="hidden" id="acao" value="">
						<div class="mb-2">
							<label for="nome">Nome</label> <input class="form-control"
								name="nome" required id="nome" type="text"
								placeholder="Nome da dieta" />



						</div>
						<div class="mb-2 col-3">

							<select id="obj" class="form-select"
								aria-label="Default select example">
								<option selected>Open this select menu</option>
								<option value="PERDER_MASSA">Emagrecer</option>
								<option value="GANHAR_MASSA">Ganho de Massa</option>
								<option value="MANTER_MASSA">Manter</option>
							</select>

						</div>

						<div class="alimentos"></div>


						<div class="mt-4 mb-0 col-2">
							<div class="d-grid">
								<button type="button" onclick="novaDieta()"
									class="btn btn-primary btn-block">Nova</button>
							</div>
						</div>
					</form>

					<table class="table table-striped">
						<thead>
							
						</thead>
						<tbody></tbody>
					</table>
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



	<script type="text/javascript">

		function novaDieta() {
			var nome = document.querySelector("#nome").value;
			var objetivo = document.querySelector("select").value;

			var urlAction=document.getElementById("form-user").action;
			$.ajax(
					{
						method : "GET",
						url : urlAction,
						data : "acao=novadieta&nome=" + nome+
								"&objetivo="+objetivo,
						success : function(response) {
							var json=JSON.parse(response);

							 document.querySelector("#nome").value="";
							auxMostrarListaDietas(json);
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});

		}
		function auxMostrarListaDietas(json){
			if (json.length==0){
				$("table>thead").html("<th>Sem Dietas.</th>");
				document.querySelector("table >tbody").innerHTML="";

			}
			else{
				$("table>thead").html("");
				$("table>thead").append($("<tr>"+
						"<th scope=\"col\">Nome</th>"+
						"<th scope=\"col\">Objetivo</th>"+

						"<th scope=\"col\">Calorias</th>"+
						"<th scope=\"col\">Proteinas</th>"+
						"<th scope=\"col\">Carboidratos</th>"+
						"<th scope=\"col\">Gorduras</th>"+
						"<th scope=\"col\">Remover</th>"+
						"<th scope=\"col\">Ver</th>"+
					"</tr>"));
			
			document.querySelector("table >tbody").innerHTML="";

			json.forEach((e)=>{
				var botaoremover="<button type=\"button\" onclick=\"removerDieta("+e.id+")\" class=\"btn btn-danger\">EXCLUIR</button>";
				var botaover="<button type=\"button\" onclick=\"verDieta("+e.id+")\" class=\"btn btn-success\">VER</button>";

				document.querySelector("table >tbody").innerHTML+="<tr id=\""+e.id+"\"><td>"+e.nome+"</td><td>"+e.objetivo+"</td><td>"+e.totalCalorias+"</td><td>"+e.totalProteinas+"</td><td>"+e.totalCarboidratos+"</td><td>"+e.totalGorduras+"</td><td>"+botaoremover+"</td><td>"+botaover+"</td></tr>"
			
			})
			}
		}
		function removerDieta(id){
			var urlAction=document.getElementById("form-user").action;

			$.ajax(
					{
						method : "GET",
						url :urlAction,
						data : "acao=removerdieta&id="+id,
						success : function(response) {
								
							var json=JSON.parse(response);
							auxMostrarListaDietas(json);
							
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
		}
		mostrarTodasDietas();
		function mostrarTodasDietas() {
			var urlAction=document.getElementById("form-user").action;
		$("table>thead").html("<div class=\"d-flex justify-content-center\">"+
				  "<div class=\"spinner-border\" role=\"status\">"+
	    "<span class=\"sr-only\">Loading...</span>"+
	  "</div>"+
	"</div>");
			$.ajax(
					{
						method : "GET",
						url :urlAction,
						data : "acao=todasdietas",
						success : function(response) {

						var json=JSON.parse(response);
						
						auxMostrarListaDietas(json);
						
						
						
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
		}
		
		function verDieta(dieta){

				var urlAction = document.getElementById("form-user").action;

				window.location.href = urlAction + "?acao=verdieta&id=" + dieta;
		}
	</script>
</body>
</html>
