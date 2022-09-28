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
<title>Dieta ${dieta.nome}</title>
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
					<h1>Dieta</h1>
					<form id="form-user"
						action="<%=request.getContextPath()%>/ServletDieta" method="get">
						<table class="table  table-borderless">
							<thead>

								<th>NOME</th>
								<th>OBJETIVO</th>
								<th>CALORIAS</th>
								<th>PROTEINAS</th>
								<th>CARBOIDRATOS</th>
								<th>CALORIAS</th>


							</thead>

							<tbody>
								<tr>

									<td>${dieta.nome}</td>
									<td>${dieta.objetivo}</td>
									<td>${dieta.totalCalorias}</td>
									<td>${dieta.totalProteinas}</td>
									<td>${dieta.totalCarboidratos}</td>
									<td>${dieta.totalGorduras}</td>



								</tr>
							</tbody>
						</table>

						<button type="button" onclick="mostrarTodasRefeicoes(1)"
							class="btn btn-success" data-toggle="modal"
							data-target=".bd-example-modal-lg">Adicionar Refeição</button>


					</form>
				</div>
			</main>




			<div class="modal fade bd-example-modal-lg" tabindex="-1"
				role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">

						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Nova Refeição</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>


						<div class="modal-body">


<form>
				  <div class="form-group row">
				    <label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
				    <div class="col-sm-10">
				      <input type="text" readonly class="form-control-plaintext" id="nomeref" value="email@example.com">
				    </div>
				  </div>
				  <div class="form-group row">
				    <label for="inputPassword" class="col-sm-2 col-form-label">Horário</label>
				    <div class="col-sm-10">
				      <input type="password" class="form-control" id="horario" placeholder="Password">
				    </div>
				  </div>
				</form>



							<table class="table table-sm">
								<thead>
									<tr>
										<th scope="col">Nome</th>
										<th scope="col">Calorias</th>
										<th scope="col">Carboidratos</th>
										<th scope="col">Proteinas</th>
										<th scope="col">Gorduras</th>

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


				</div>
			</div>
		</div>





		<footer class="py-4 bg-light mt-auto">
			<div class="container-fluid px-4">
				<div class="d-flex align-items-center justify-content-between small">
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

	<script>
	var p=0;
	function mostrarTodasRefeicoes(paginaatual) {
		var urlAction=document.getElementById("form-user").action;
		var porpagina=5;
		p=paginaatual;
		$.ajax(
				{
					method : "GET",
					url :"ServletAlimento",
					data : "acao=todasrefeicoes&paginaatual="+paginaatual+"&porpagina="+porpagina,
					success : function(response,textStatus,request) {
			
					var json=JSON.parse(response);
					if (json.length>0){
						$("div.modal-body>table >thead").html("<tr>"+
								"<th scope=\"col\">#</th>"+
								"<th scope=\"col\">Nome</th>"+
								"<th scope=\"col\">Calorias</th>"+
								"<th scope=\"col\">Proteinas</th>"+
								"<th scope=\"col\">Carboidratos</th>"+
								"<th scope=\"col\">Gorduras</th>"+
								"<th scope=\"col\">SELECIONAR</th>"+
								


							"</tr>")
					$("ul.pagination").html("");
					document.querySelector("div.modal-body>table >tbody").innerHTML="";
					json.forEach((e)=>{
// 						var botaoremover="<button type=\"button\" onclick=\"excluirRefeicao("+e.id+")\" class=\"btn btn-danger\">EXCLUIR</button>";
						var botaoselecionar="<button type=\"button\" onclick=\"selecionarRefeicao("+e.id+",'"+e.nome+"')\" class=\"btn btn-success\">VER</button>";

						var botaoremover="";
						var botaover="";


						document.querySelector("div.modal-body>table >tbody").innerHTML+="<tr id=\""+e.id+"\"><td>"+e.id+"</td><td>"+e.nome+"</td><td>"+e.calorias+"</td><td>"+e.proteinas+"</td><td>"+e.carboidratos+"</td><td>"+e.gorduras+"</td><td>"+botaoselecionar+"</td></tr>"
					
					})
					var totalPaginas=request.getResponseHeader("totalPaginas");
					for (var i=0;i<totalPaginas;i++){
						if (paginaatual==(i+1)){
							document.querySelector("ul.pagination").innerHTML+="<li onclick=\"mostrarTodasRefeicoes("+(i+1)+")\" class=\"page-item active\"><a class=\"page-link\" href=\"#\">"+(i+1)+"</a></li>"			

						}else{
							document.querySelector("ul.pagination").innerHTML+="<li onclick=\"mostrarTodasRefeicoes("+(i+1)+")\" class=\"page-item\"><a class=\"page-link\" href=\"#\">"+(i+1)+"</a></li>"			

						}
						
					
					
					
					
					}
					
					}else {
						$("div.modal-body>table>thead").html("Você não possui refeições cadastradas.");
					}
					}

				}).fail(function(xhr, status, errorThrown) {
			alert("Error ao buscar usuário por nome" + xhr.responseText);
		});
	}
	$("div.modal-body>form").hide();

	function selecionarRefeicao(id,nome){
		$("div.modal-body>form").show();
		$("#nomeref").val(nome);


	}
	</script>

</body>


</html>
