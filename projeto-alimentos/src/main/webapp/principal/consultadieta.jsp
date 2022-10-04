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
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" />
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
					<button class="btn btn-link hBack" type="button">VOLTAR</button>
					
					<h1>Dieta</h1>
					<form id="form-user"
						action="<%=request.getContextPath()%>/ServletDieta" method="get">

						<input type="hidden" id="id-dieta" name="iddieta" value="${dieta.id}">
						<input type="hidden" id="acao" name="acao" value="">
						
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



						<button type="button" onclick="adicionandoRef()"
							class="btn btn-primary">Adicionar Refeição</button>


					</form>


					<div class="mb-2 ">
						<label for="nome-novo" class="col-sm-2 col-form-label">Nome</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="nome-novo"
								value="Nome">
						</div>
					</div>
					<div class="mb-2">
						<label for="horario-novo" class="col-sm-2 col-form-label">Horário</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="horario-novo"
								placeholder="Horario">
						</div>
					</div>

					<button type="button" onclick="salvarRefs()"
						class="btn btn-primary" id="btn-salvar">Salvar</button>



					<button type="button" onclick="imprimirDieta()"
						class="btn btn-primary" id="btn-salvar">Imprimir</button>


					<div class="refeicoes">
						<table class="table  table-borderless">
							<thead>



							</thead>

							<tbody>
							</tbody>
						</table>
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
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script>
		flatpickr("#horario", {
			enableTime : true,
			noCalendar : true,
			dateFormat : "H:i",
			time_24hr : true
		});
		flatpickr("#horario-novo", {
			enableTime : true,
			noCalendar : true,
			dateFormat : "H:i",
			time_24hr : true
		});
		$(".hBack").on("click", function(e){
		    e.preventDefault();
		    window.history.back();
		});

		var p = 0;

		$("div.modal-body>form").hide();

		function selecionarRefeicao(id, nome) {
			$("div.modal-body>form").show();
			$("#nomeref").val();

		}

		
		function imprimirDieta(){
			document.getElementById("acao").value="imprimirdieta";
			
	    	$("#form-user").submit();
	    	return false;

			
		}
		function salvarRefs() {
			var aux = $("#horario-novo").val();
			var hora = aux.replace(":", "-");
			var nome = $("#nome-novo").val();
			var id = $("#id-dieta").val();
			$("btn-salvar").addClass("disabled");
			var urlAction = document.getElementById("form-user").action;
			$.ajax(
					{
						
						method : "GET",
						url : urlAction,
						data : "acao=novaref&hora=" + hora + "&nome=" + nome
								+ "&id=" + id,
						success : function(response) {
							alert(response);
							
							
							var json=JSON.parse(response);
							
							montarTabelaRefeicoes(json);

							$("btn-salvar").removeClass("disabled");

						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
		}
		mostrarRefs();
		function mostrarRefs(){
			var id = $("#id-dieta").val();
	
			
			var urlAction = document.getElementById("form-user").action;
			$.ajax(
					{
						
						method : "GET",
						url : urlAction,
						data : "acao=mostrarrefeicoes&id=" + id,
						success : function(response) {
							alert(response);
							
							
							var json=JSON.parse(response);
							montarTabelaRefeicoes(json);
							
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao buscar usuário por nome" + xhr.responseText);
			});
			
		}
		
	var ordem;
	function montarTabelaRefeicoes(json){
		
		$("div.refeicoes>table>thead").html("");
		$("div.refeicoes>table>tbody").html("");
		if (json.length==0){
			$("div.refeicoes>table>thead").append("<th>Sem refeições cadastradas.</th>");
		}else{
			
		
			var thead="<th>REFEICAO</th> 	<th>HORARIO</th> <th>CALORIAS</th><th>PROTEINAS</th><th>CARBOIDRATOS</th><th>GORDURAS</th>	<th>VER</th>	<th>REMOVER</th>";
			json.sort(compare);
			$("div.refeicoes>table>thead").append(thead);
			json.forEach((e)=>{
				var botaoremover="<button type='button' onclick='deletar("+e.id+")' class='btn btn-danger'>REMOVER</button>";
				var botaover="<button type='button' onclick='ver("+e.id+")' class='btn btn-success'>VER</button>";
				$("div.refeicoes > table > tbody").append("<tr id=\""+e.id+"\"><td>"+e.nome+"</td><td>"+e.horario+"</td><td>"+e.calorias+"</td><td>"+e.proteinas+"</td><td>"+e.carboidratos+"</td><td>"+e.gorduras+"</td><td>"+botaover+"</td><td>"+botaoremover+"</td></tr>")
			})
			
		}
		
		
	}
	function deletar(idrefeicao){
		var urlAction=document.getElementById("form-user").action;
		var id=document.getElementById("id-dieta").value;
		$.ajax({
			method:"get",
			url:urlAction,
			data:"iddieta="+id+"&acao=removerrefeicao&idrefeicao="+idrefeicao,
			success:function(response,textStatus,xhr){
				document.getElementById(idrefeicao).remove();
				if ($("div.refeicoes>table>tbody").children().length==0){
					montarTabelaRefeicoes("");
				}
			}
		}).fail(function(xhr,status,erroThrown){
			alert("Erro : "+xhr.responseText);
		})
		
	}
	function compare(a, b) {
		  // Use toUpperCase() to ignore character casing
		  const bandA = a.horario;
		  const bandB = b.horario;

		  let comparison = 0;
		  if (bandA > bandB) {
		    comparison = 1;
		  } else if (bandA < bandB) {
		    comparison = -1;
		  }
		  return comparison;
		}
	
	function ver(id){
		
		

		var urlAction = "ServletAlimento";

		window.location.href = urlAction + "?acao=consultarrefeicao&idrefeicao=" + id;
	}
		
	</script>

</body>


</html>
