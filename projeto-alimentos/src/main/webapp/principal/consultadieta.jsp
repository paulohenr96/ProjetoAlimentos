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
					
					<h1>Dieta</h1>
					<form id="form-user"
						action="<%=request.getContextPath()%>/ServletDieta" method="get">
<input type="hidden" value="<%=request.getContextPath()%>" id="caminho"/>
						<input type="hidden" id="id-dieta" name="iddieta" value="${dieta.id}">
						<input type="hidden" id="acao" name="acao" >
						
						<table class="table  table-borderless" style="content-align:center;">
							<thead style="text-align:center;">

								<th>NOME</th>
								<th>OBJETIVO</th>
								<th>CALORIAS</th>
								<th>PROTEINAS</th>
								<th>CARBOIDRATOS</th>
								<th>CALORIAS</th>


							</thead>

							<tbody>
								<tr style="text-align:center;">

									<td id="nome_dieta"></td>
									<td id="objetivo_dieta"></td>
									<td id="totalcalorias_dieta"></td>
									<td id="totalproteinas_dieta"></td>
									<td id="totalcarboidratos_dieta"></td>
									<td id="totalgorduras_dieta"></td>



								</tr>
							</tbody>
						</table>

					

					</form>

					<h2>Adicione Refeições !</h2>
					<div id="campo_nome" class="mb-2 ">
						<label for="nome-novo" class="col-sm-2 col-form-label">Nome</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="nome-novo"
								>
						</div>
						<span style='color:red;'></span>
					</div>
					<div id="campo_horario" class="mb-2">
						<label for="horario-novo" class="col-sm-2 col-form-label">Horário</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="horario-novo"
								placeholder="Horario">
						</div>
						<span style='color:red;'></span>
					</div>
						<div id="alerta_dieta"></div>

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







									                           <jsp:include page="/footer.jsp"></jsp:include>

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
			if ($("div.refeicoes>table>tbody").children().length==0){
				mensagemErro("alerta_dieta","Você não possui refeições cadastradas nesta dieta, por isso o relatório está indisponível. Cadastre refeições na dieta.")
			}else{
				document.getElementById("acao").value="imprimirdieta";
				var urlAction = document.getElementById("form-user").action;
				
		    	$("#form-user").submit();
		    	return false;

			}
			
			
		}
		function salvarRefs() {
			var aux = $("#horario-novo").val();
			var hora = aux.replace(":", "-");
			var nome = $("#nome-novo").val();
			var id = $("#id-dieta").val();
			$("btn-salvar").addClass("disabled");
			var urlAction = document.getElementById("form-user").action;
			verificarForm();
			if (verificarForm()){
				$.ajax(
						{
							
							method : "GET",
							url : urlAction,
							data : "acao=novaref&hora=" + hora + "&nome=" + nome
									+ "&id=" + id,
							success : function(response) {
								if (response=="SUCESSO"){
									 $("#nome-novo").val("");

										
										mostrarRefs();
										$("btn-salvar").removeClass("disabled");
										mensagemSucesso("alerta_dieta","Refeição salva com sucesso !");
										dadosDieta();
								}
								else {
									mensagemErro("alerta_dieta","Você atingiu o numero máximo de refeicoes !");
								}
							

							}

						}).fail(function(xhr, status, errorThrown) {
					alert("Error ao salvar a refeicao" + xhr.responseText);
				});
			}
		
		}
		function verificarForm(){
			var nome = $("#nome-novo").val();
			var aux = $("#horario-novo").val();
			var hora = aux.replace(":", "-");
			$("span").empty();
			if (nome=="" || nome.trim==""){
				$("#campo_nome > span").append("Insira o nome");
				return false;

			}if (hora==""){
				$("#campo_horario > span").append("Insira o horario");
				return false;

			}
			
			
			return true;
			
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
							
							
							var json=JSON.parse(response);
							montarTabelaRefeicoes(json);
							
						}

					}).fail(function(xhr, status, errorThrown) {
				alert("Error ao mostrar refeicoes" + xhr.responseText);
			});
			
		}
		
	var ordem;
	var vazia=true;
	function montarTabelaRefeicoes(json){
		
		$("div.refeicoes>table>thead").html("");
		$("div.refeicoes>table>tbody").html("");
		if (json.length==0){
			$("div.refeicoes>table>thead").append("<th>Sem refeições cadastradas.</th>");
		}else{
			
			vazia=false;
			var thead="<th>REFEICAO</th> 	<th>HORARIO</th> <th>CALORIAS</th><th>PROTEINAS</th><th>CARBOIDRATOS</th><th>GORDURAS</th>		<th>AÇÃO</th>";
			json.sort(compare);
			$("div.refeicoes>table>thead").append(thead);
			json.forEach((e)=>{
				var botaoremover=icone("deletar.png","deletar("+e.id+")","Remover");
				var botaover=icone("ver.png","ver("+e.id+")","Ver");
				$("div.refeicoes > table > tbody").append("<tr id=\""+e.id+"\"><td>"+e.nome+"</td><td>"+e.horario+"</td><td>"+e.calorias+"</td><td>"+e.proteinas+"</td><td>"+e.carboidratos+"</td><td>"+e.gorduras+"</td><td>"+botaover+"  "+botaoremover+"</td></tr>")
			})
			
		}
		
		
	}
	dadosDieta();
	function dadosDieta (){
		var id=document.getElementById('id-dieta').value;
		var urlAction=document.getElementById("form-user").action;

		
		$.get(urlAction,"acao=entidadedieta&id="+id,function(response){
				var json=JSON.parse(response);
				$("#totalproteinas_dieta").html(json[0].totalProteinas);
				$("#totalgorduras_dieta").html(json[0].totalGorduras);
				$("#totalcarboidratos_dieta").html(json[0].totalCarboidratos);
				$("#totalcalorias_dieta").html(json[0].totalCalorias);
				$("#nome_dieta").html(json[0].nome);
				$("#objetivo_dieta").html(json[0].objetivo);

				console.log(json[0].objetivo);
		})
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
				dadosDieta();

				mensagemSucesso("alerta_dieta","Refeição Removida Com Sucesso.");
			}
		}).fail(function(xhr,status,erroThrown){
			alert("Erro ao deletar : "+xhr.responseText);
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
