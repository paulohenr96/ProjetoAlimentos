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
<title>Refeições</title>
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



			<input type="hidden" id="caminhocontexto"
				value="<%=request.getContextPath()%>" />



			<main>
				<div class="container-sm px-4">
					<h2>Refeições</h2>
					<form id="form-user" method="get"
						action="<%=request.getContextPath()%>/ServletAlimento">
						<input type="hidden" id="acao" value="">
						<div class="mb-2">
							<label for="nome">Nova Refeição</label> <input
								class="form-control" name="nome" required id="nome" type="text"
								placeholder="Nome da refeição" />
						</div>

						<div class="alimentos"></div>


						<div class="mt-4 mb-0 col-3">
							<div class="d-grid">
								<button type="button" onclick="novaRefeicao()"
									class="btn btn-primary btn-block">Criar</button>
							</div>
						</div>
						<br><br>
						<div class="alert alert-info" style="display: none;" id="mensagem"
							role="alert"></div>
					</form>
					<div>
						<nav aria-label="Page navigation example">
							<ul class="pagination" id="paginacaorefeicoes">

							</ul>
						</nav>
						<table class="table table-striped">
							<thead>

							</thead>
							<tbody></tbody>
						</table>
					</div>



				</div>
			</main>










			<jsp:include page="/footer.jsp"></jsp:include>

		</div>
	</div>

	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>


	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

	<script type="text/javascript">
	document.querySelector(".table > tbody").innerHTML="<div class=\"spinner-border\" role=\"status\">  <span class=\"sr-only\">Loading...</span>  </div>";

		function novoAlimento() {
			document.querySelector("div.alimentos").innerHTML += "<br/>";
			document.querySelector("div.alimentos").innerHTML += "<div class=\"row\">  <div class=\"col\">  <input type=\"text\" class=\"form-control\" placeholder=\"First name\" aria-label=\"First name\">	  </div>  <div class=\"col\">   <input type=\"text\" class=\"form-control\" placeholder=\"Last name\" aria-label=\"Last name\">	  </div>	</div>";

		}
		function novaRefeicao() {
			var nome = document.querySelector("#nome").value;
			var urlAction=document.getElementById("form-user").action;
				
			if ($("#nome").val()!="" && $("#nome").val().trim!=""){
				document.querySelector("#nome").value="";

			
			$.ajax(
					{
						method : "GET",
						url : urlAction,
						data : "acao=novarefeicao&nome=" + nome,
						success : function(response) {
							mostrarTodasRefeicoes(1);
							
							if (response == "SUCESSO"){
								mensagemSucesso("mensagem","Refeição adicionada com sucesso.")

							}else {
								mensagemErro("mensagem","Você já atingiu o valor máximo de refeições.");
							}
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
			
			}else {

				mensagemErro("mensagem","Insira um nome para a refeição.")
			}
		}
		
		var p=1;
		mostrarTodasRefeicoes(1);
		function mostrarTodasRefeicoes(paginaatual) {
			var urlAction=document.getElementById("form-user").action;
			var porpagina=5;
			var caminho=$("#caminhocontexto").val();

			p=paginaatual;
			$.ajax(
					{
						method : "GET",
						url :urlAction,
						data : "acao=todasrefeicoes&paginaatual="+paginaatual+"&porpagina="+porpagina,
						success : function(response,textStatus,request) {
				
						var json=JSON.parse(response);
						if (json.length>0){
							$("table >thead").html("<tr>"+
									"<th scope=\"col\">#</th>"+
									"<th scope=\"col\">Nome</th>"+
									"<th scope=\"col\">Calorias</th>"+
									"<th scope=\"col\">Proteinas</th>"+
									"<th scope=\"col\">Carboidratos</th>"+
									"<th scope=\"col\">Gorduras</th>"+
									"<th scope=\"col\">AÇÃO</th>"+


								"</tr>")
						$("ul.pagination").html("");
						document.querySelector("table >tbody").innerHTML="";
						json.forEach((e)=>{
							
			        		var botaoremover=icone("deletar.png","excluirRefeicao("+e.id+")","Remover");
			        		var botaover=icone("ver.png","verRefeicao("+e.id+")","ver");

							document.querySelector("table >tbody").innerHTML+="<tr id=\""+e.id+"\"><td>"+e.id+"</td><td>"+e.nome+"</td><td>"+e.calorias+"</td><td>"+e.proteinas+"</td><td>"+e.carboidratos+"</td><td>"+e.gorduras+"</td><td>"+botaoremover+" "+botaover+"</td></tr>"
						
						})
								var totalPaginas=request.getResponseHeader("totalPagina");

						paginarTabelas('paginacaorefeicoes',totalPaginas,paginaatual,"mostrarTodasRefeicoes");
						}else {
							$("table>thead").html("Você não possui refeições cadastradas.");
							$("table>tbody").empty();
							$("ul.pagination").empty();

						}
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
			
		
		}
		
		function excluirRefeicao(idrefeicao){
			
			var urlAction=document.getElementById("form-user").action;
			
			
			$.ajax(
					{
						method : "GET",
						url :urlAction,
						data : "acao=removerrefeicao&idrefeicao="+idrefeicao,
						success : function(response) {

						
						$("#"+idrefeicao).remove();
						mostrarTodasRefeicoes(p);
						mensagemSucesso("mensagem","Refeição removida com sucesso ! .")
						
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
		}
		
		function verRefeicao(refeicao){

				var urlAction = document.getElementById("form-user").action;

				window.location.href = urlAction + "?acao=consultarrefeicao&idrefeicao=" + refeicao;
		}
	</script>

</body>
</html>
