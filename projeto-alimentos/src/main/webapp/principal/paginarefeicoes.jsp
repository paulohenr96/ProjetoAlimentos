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
					<form id="form-user" method="get"
						action="<%=request.getContextPath()%>/ServletAlimento">
						<input type="hidden" id="acao" value="">
						<div class="mb-2">
							<label for="nome">Nome</label> <input class="form-control"
								name="nome" required id="nome" type="text"
								placeholder="Nome da refeição" />
						</div>

						<div class="alimentos"></div>


						<div class="mt-4 mb-0 col-3">
							<div class="d-grid">
								<button type="button" onclick="novaRefeicao()"
									class="btn btn-primary btn-block">Nova</button>
							</div>
						</div>
					</form>

					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Nome</th>
								<th scope="col">Calorias</th>
								<th scope="col">Proteinas</th>
								<th scope="col">Carboidratos</th>
								<th scope="col">Gorduras</th>
								<th scope="col">Remover</th>
								<th scope="col">Ver</th>


							</tr>
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
		function novoAlimento() {
			document.querySelector("div.alimentos").innerHTML += "<br/>";
			document.querySelector("div.alimentos").innerHTML += "<div class=\"row\">  <div class=\"col\">  <input type=\"text\" class=\"form-control\" placeholder=\"First name\" aria-label=\"First name\">	  </div>  <div class=\"col\">   <input type=\"text\" class=\"form-control\" placeholder=\"Last name\" aria-label=\"Last name\">	  </div>	</div>";

		}
		function novaRefeicao() {
			var nome = document.querySelector("#nome").value;
			var urlAction=document.getElementById("form-user").action;
			$.ajax(
					{
						method : "GET",
						url : urlAction,
						data : "acao=novarefeicao&nome=" + nome,
						success : function(response) {
							alert(response);
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});

		}
		mostrarTodasRefeicoes();
		function mostrarTodasRefeicoes() {
			var urlAction=document.getElementById("form-user").action;
	
			$.ajax(
					{
						method : "GET",
						url :urlAction,
						data : "acao=todasrefeicoes",
						success : function(response) {

						var json=JSON.parse(response);
						
						json.forEach((e)=>{
							var botaoremover="<button type=\"button\" class=\"btn btn-danger\">EXCLUIR</button>";
							var botaover="<button type=\"button\" onclick=\"verRefeicao("+e.id+")\" class=\"btn btn-success\">VER</button>";

							document.querySelector("table >tbody").innerHTML+="<tr><td>"+e.id+"</td><td>"+e.nome+"</td><td>"+e.calorias+"</td><td>"+e.proteinas+"</td><td>"+e.carboidratos+"</td><td>"+e.gorduras+"</td><td>"+botaoremover+"</td><td>"+botaover+"</td></tr>"
						
						})
						
						
						
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
		}
		
		function verRefeicao(refeicao){
				alert(refeicao);

				var urlAction = document.getElementById("form-user").action;

				window.location.href = urlAction + "?acao=consultarrefeicao&idrefeicao=" + refeicao;
		}
	</script>
</body>
</html>
