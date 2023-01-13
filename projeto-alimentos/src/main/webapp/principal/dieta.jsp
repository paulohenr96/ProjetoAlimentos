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
<title>Dietas</title>
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
					<h2>Minhas Dietas</h2>
					<form id="form-user"
						action="<%=request.getContextPath()%>/ServletDieta" method="get">
						<input type="hidden" id="acao" value="">
						<div class="mb-2">
							<label for="nome">Nome</label> <input class="form-control"
								name="nome" required id="nome" type="text"
								placeholder="Nome da dieta" /> <span id="nome-vazio"
								class="alerta"></span>



						</div>
						<div class="mb-2 col-3">

							<select id="obj" class="form-select"
								aria-label="Default select example">
								<option selected value="">Open this select menu</option>
								<option value="Perder Peso">Emagrecer</option>
								<option value="Ganhar Peso">Ganho de Massa</option>
								<option value="Manter Peso">Manter</option>
							</select> <span id="select-vazio" class="alerta"></span>

						</div>

						<div class="alimentos"></div>


						<div class="mt-4 mb-0 col-2">
							<div class="d-grid">
								<button type="button" onclick="novaDieta()"
									class="btn btn-primary btn-block">Nova</button>
							</div>

						</div>
						<span class="sucesso"></span>

					</form>
					<div id="todas_dietas" style="height:400px;">
						<ul id="paginacaodietas" class='pagination'></ul>

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
		function verificar(){
			var nome = document.querySelector("#nome").value;
			var objetivo = document.querySelector("select").value;
			$(".alerta").html("");

			if ($("#nome").val()==""){
				$("#nome-vazio").html("Insira um nome para a dieta.").attr("style","color:red");
			}
			if ($("select").val()==""){
				$("#select-vazio").html("Selecione o objetivo da dieta.").attr("style","color:red");
			}
			if (nome!="" && nome.trim!="" && objetivo!=""){
				return true;
			}
			
			return false;
		}
		function novaDieta() {
			
			if (verificar()){
				var nome = document.querySelector("#nome").value;
				var objetivo = document.querySelector("select").value;

				var urlAction=document.getElementById("form-user").action;
				$.ajax(
						{
							method : "GET",
							url : urlAction,
							data : "acao=novadieta&nome=" + nome+
									"&objetivo="+objetivo,
							success : function(response,textStatus,xhr) {

								 document.querySelector("#nome").value="";
								mostrarTodasDietas(1);

								$(".sucesso").html("Dieta Adicionada ").attr("style","color:green;font-weight:bold");

							}

						}).fail(function(xhr, status, errorThrown) {
					alert("Error criar nova dieta " + xhr.responseText);
				});
			}
			

		}
		function auxMostrarListaDietas(json){
			if (json.length==0){
				$("table>thead").html("<th>Sem Dietas.</th>");
				document.querySelector("table >tbody").innerHTML="";
				$(".sucesso").html("");

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
						"<th scope=\"col\">AÇÃO</th>"+
					"</tr>"));
			
			document.querySelector("table >tbody").innerHTML="";

			json.forEach((e)=>{
				var botaoremover=icone("deletar.png","removerDieta("+e.id+")","Remover");
				var botaover=icone("ver.png","verDieta("+e.id+")","VER");

				document.querySelector("table >tbody").innerHTML+="<tr id=\""+e.id+"\"><td>"+e.nome+"</td><td>"+e.objetivo+"</td><td>"+e.totalCalorias+"</td><td>"+e.totalProteinas+"</td><td>"+e.totalCarboidratos+"</td><td>"+e.totalGorduras+"</td><td>"+botaoremover+""+botaover+"</td></tr>"
			
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
								
							mostrarTodasDietas(1);


							$(".sucesso").html("Dieta Removida ").attr("style","color:green;font-weight:bold");

						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Erro ao remover dieta " + xhr.responseText);
			});
		}
		mostrarTodasDietas(1);
		function mostrarTodasDietas(paginaatual) {
			var porpagina=5;
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
						data : "acao=todasdietaspaginadas&paginaatual="+paginaatual,
						success : function(response,textStatus,xhr) {
						console.log(response);
						var json=JSON.parse(response);
						
						auxMostrarListaDietas(json);
						var totalPaginas=xhr.getResponseHeader("totalPagina")
						paginarTabelas("paginacaodietas", totalPaginas, paginaatual, "mostrarTodasDietas");
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Erro ao mostrar as dietas " + xhr.responseText);
			});
		}
		
	
		
		function verDieta(dieta){

				var urlAction = document.getElementById("form-user").action;

				window.location.href = urlAction + "?acao=verdieta&id=" + dieta;
		}
	</script>
</body>
</html>
