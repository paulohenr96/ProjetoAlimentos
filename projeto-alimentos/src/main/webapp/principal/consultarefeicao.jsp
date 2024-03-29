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
<title>Consulta Refeição</title>
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

					
					<h1>Detalhes da refeição</h1>



					<form id="form-user" method="get"
						action="<%=request.getContextPath()%>/ServletAlimento">
						<input type="hidden" id="acao" value="">
						<div class="row mb-2">
							<div class="col">
								<label for="idrefeicao">ID</label> <input class="form-control"
									name="idrefeicao" required readonly="readonly" id="idrefeicao"
									value="${ref.id}" type="text" placeholder="ID da refeição" />
							</div>

							<div class="col">
								<label for="nomerefeicao">Refeicao</label> <input
									class="form-control" name="nomerefeicao" required
									id="nomerefeicao" value="${ref.nome}" readonly="readonly"
									type="text"  />
							</div>

							<div class="col">
								<label for="caloriatotal">Calorias</label> <input
									class="form-control" name="caloriatotal" required
									id="caloriatotal" value="${ref.calorias}" readonly="readonly"
									type="text"  />
							</div>
							<div class="col">
								<label for="proteinatotal">Proteinas</label> <input
									class="form-control" name="proteinatotal" required
									id="proteinatotal" value="${ref.proteinas}" readonly="readonly"
									type="text"  />
							</div>
							<div class="col">
								<label for="carboidratototal">Carboidratos</label> <input
									class="form-control" name="carboidratototal" required
									id="carboidratototal" value="${ref.carboidratos}"
									readonly="readonly" type="text"  />
							</div>
							<div class="col">
								<label for="gorduratotal">Gorduras</label> <input
									class="form-control" name="gorduratotal" required
									id="gorduratotal" value="${ref.gorduras}" readonly="readonly"
									type="text"  />
							</div>
						</div>


						<div class="alimentos"></div>

						<div class="row mb-2">
							<div class="col">
								<input type="text" class="form-control" readonly="readonly"
									placeholder="ID Alimento" id="id" aria-label="First name">
							</div>
							<div class="col">
								<input type="text" class="form-control" readonly="readonly"
									placeholder="Nome" aria-label="First name" id="nome">
							</div>
							<div class="col">
								<input type="text" class="form-control" id="quantidade"
									placeholder="Quantidade" aria-label="Last name">
							</div>

						</div>

						<div class="mb-2">

							<button type="button" onclick="adicionarAlimento()"
								class="btn btn-primary ">Adicionar Alimento</button>
								<button type="button" onclick="imprimirRefeicao()"
								class="btn btn-primary ">Imprimir Refeicao</button>
							<button type="button" class="btn btn-success "
								data-toggle="modal" data-target="#exampleModal"
								onclick="verAlimentosRefeicao()">Ver Todos os Alimentos</button>

						</div>

					</form>
<form id="form-refeicao" method="get"
						action="<%=request.getContextPath()%>/ServletRefeicao">
						<input type="hidden" id="acao2" name="acao" value="">
						<input type="hidden" name="id" value="${ref.id}">
						
						</form>
					<div class="row mb-2">

						<div class="col">
							<input type="text" class="form-control" placeholder="Nome"
								aria-label="Last name" id="nome-pesquisa">
						</div>
						<div class="col">
							<button type="button" class="btn btn-secondary "
								onclick="mostrarListaAlimentos(1)">BUSCAR</button>

						</div>

					</div>
					
					<br/>
										<br/>
					<hr>
					<div style="height:400px;">
						<nav aria-label="Page navigation example">
							<ul class="pagination" id="paginacao">

							</ul>
						</nav>
						<table class="table todos">



						</table>
						<nav aria-label="Page navigation example">
							<ul class="pagination" id="paginacao">

							</ul>
						</nav>
					</div>

				</div>
			</main>



			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Alimentos da Refeição ${ref.nome}</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" style="height: 400px; overflow: scroll">

							<table class="table modalalimentos">
								<thead>
									<tr>
										<th scope="col">#</th>
										<th scope="col">Nome</th>
										<th scope="col">Quantidade</th>

										<th scope="col">Remover</th>


									</tr>
								</thead>
								<tbody class="alimentos-refeicao">
								</tbody>

							</table>

						</div>
						<div class="modal-footer"></div>
					</div>
				</div>
			</div>






			<jsp:include page="/footer.jsp"></jsp:include>

		</div>
	</div>

	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

	<script type="text/javascript">
	$(".hBack").on("click", function(e){
	    e.preventDefault();
	    window.history.back();
	});
	$("table.todos").hide();
	
		function mostrarListaAlimentos(paginaatual){
			var urlAction = document.getElementById("form-user").action;
			var nome = document.getElementById("nome-pesquisa").value;

			var porpagina=5;
			$.ajax({
				method:"get",
				url:urlAction,
				data:"porpagina="+porpagina+"&acao=pesquisaralimentorefeicao&paginaatual="+paginaatual+"&nome="+nome,
				success:function(response,textStatus,xhr){
					var json=JSON.parse(response);

					if (json.length>0){
						
					
					$("table.todos").show();
					$("table.todos").empty();

					$("table.todos").append("<thead></thead>");
					$("table.todos >thead").append("<tr></tr>");
					$("table.todos >thead >tr").append("<th scope='col'>#</th>");
					$("table.todos >thead >tr").append("<th scope='col'>Nome</th>");
					$("table.todos >thead >tr").append("<th scope='col'>Porção</th>");
					$("table.todos >thead >tr").append("<th scope='col'>Calorias</th>");
					$("table.todos >thead >tr").append("<th scope='col'>P</th>");
					$("table.todos >thead >tr").append("<th scope='col'>C</th>");
					$("table.todos >thead >tr").append("<th scope='col'>G</th>");
					$("table.todos >thead >tr").append("<th scope='col'>Selecionar</th>");

					$("table.todos").append("<tbody class='todos-alimentos'></tbody>");

					

					var totalPaginas=xhr.getResponseHeader("totalPagina");

					var tbody = document.querySelector("tbody.todos-alimentos");
					tbody.innerHTML="";
					
					json.forEach((e)=>{
						
						
						
						
						var linha=tbody.insertRow();
						linha.setAttribute("id",e.id);
						
						linha.insertCell().innerHTML=e.id;
						linha.insertCell().innerHTML=e.nome;
						linha.insertCell().innerHTML=e.porcao;
						linha.insertCell().innerHTML=e.caloria;
						linha.insertCell().innerHTML=e.proteina;
						linha.insertCell().innerHTML=e.carboidrato;
						linha.insertCell().innerHTML=e.gordura;
						var button = icone("selecionar.png","pegarAlimento("+e.id+")","Selecionar");
						linha.insertCell().innerHTML=button;

						
					});
					$("ul.pagination").empty();
					paginarTabelas("paginacao", totalPaginas, paginaatual, "mostrarListaAlimentos");
					
					
					
				}else {
					$("table.todos").show();
					$("table.todos").empty();
					$("table.todos").append("<th>Sem alimentos cadastrados</th>")
				}
				}
			
			}).fail(function (xhr, status, errorThrown) {
			      alert("Error ao mostrar lista de alimentos " + xhr.responseText);
		    });
			
		}
		function pegarAlimento(id){
			var linha=document.getElementById(id);

			document.querySelector("#id").value=linha.cells[0].innerHTML;
			document.querySelector("#nome").value=linha.cells[1].innerHTML;
			verificarFormulario();

		}
		function verificarFormulario(){
			var id=$("#id");
			var quantidade=$("#quantidade");
			if ( $.trim(id.val())==''){
				$("p.alerta").remove();
				var aviso=$("<p>").addClass("alerta").css("color","red").html("Selecione um elemento");
				$("#id").parent().append(aviso[0]);
				
				return false;
			}else if ($.trim(quantidade.val())==""){
				$("p.alerta").remove();
				var aviso=$("<p>").addClass("alerta").css("color","red").html("Insira uma quantidade");
				$("#quantidade").parent().append(aviso[0]);
				
				return false;
			}else if (isNaN(quantidade.val())){
				$("p.alerta").remove();
				var aviso=$("<p>").addClass("alerta").css("color","red").html("Quantidade Invalida");
				$("#quantidade").parent().append(aviso[0]);
				
				return false;
			}
			$("p.alerta").remove();

			return true;
		}
		function verAlimentosRefeicao(){
			var urlAction=document.getElementById("form-user").action;
			var idrefeicao=document.getElementById("idrefeicao").value;
			var tbody=document.querySelector("tbody.alimentos-refeicao");
			$("table.modalalimentos").empty();
			$("table.modalalimentos").show();

			$.ajax({
				method:"get",
				url:urlAction,
				data:"idrefeicao="+idrefeicao+"&acao=alimentosrefeicao",
				success:function(response,textStatus,xhr){
					var json=JSON.parse(response);
					console.log("Response =>>>"+response);
					if (json.length==0 || response.trim==='null'){
						$("table.modalalimentos").append("<thead><th>Lista Vazia</th></thead>");
						infoRefeicao();
						alert("Lista de Alimentos Vazias");
					}else {
						

						$("table.modalalimentos").append("<thead></thead>");
						$("table.modalalimentos >thead").append("<tr></tr>");
						$("table.modalalimentos >thead >tr").append("<th scope='col'>#</th>");
						$("table.modalalimentos >thead >tr").append("<th scope='col'>Nome</th>");
						$("table.modalalimentos >thead >tr").append("<th scope='col'>QUANTIDADE</th>")
						$("table.modalalimentos >thead >tr").append("<th scope='col'>REMOVER</th>")

						$("table.modalalimentos").append("<tbody class='alimentos-refeicao'></tbody>");

						var tbody=document.querySelector("tbody.alimentos-refeicao");

						
						
						json.forEach((e)=>{
							var idinput='quantidade_remover'+e.id;
							var linha=tbody.insertRow();
							var button=icone("deletar.png","removerAlimentoRefeicao("+e.id+")");
							linha.insertCell().innerHTML=e.id;
							linha.insertCell().innerHTML=e.alimento.nome;
							linha.insertCell().innerHTML=e.quantidade;
							linha.insertCell().innerHTML="<input type='number' placeholder='quantidade' id="+idinput+" style='width:75px;'/>"+button;


						});
						
						
						
						infoRefeicao();

					}
					}
				
			}).fail(function(xhr,status,erroThrown){
				alert("Erro : "+xhr.responseText);
			})
			
			
		}
		function infoRefeicao(){
			
			
			var urlAction=document.getElementById("form-user").action;
			var idrefeicao=document.getElementById("idrefeicao").value;
			
			$.ajax({
				method:"get",
				url:urlAction,
				data:"idrefeicao="+idrefeicao+"&acao=informacaodarefeicao",
				success:function(response,textStatus,xhr){
					var json=JSON.parse(response);
					$("#proteinatotal").attr("value",json.proteinas);
					$("#caloriatotal").attr("value",json.calorias);
					$("#gorduratotal").attr("value",json.gorduras);
					$("#carboidratototal").attr("value",json.carboidratos);
					console.log(json);
				}
			}).fail(function(xhr,status,erroThrown){
				alert("Erro : "+xhr.responseText);
			})
			
			
		}
		function removerAlimentoRefeicao(id){
			var urlAction=document.getElementById("form-user").action;

			var idrefeicao=document.getElementById("idrefeicao").value;
			var quantidade=$("#quantidade_remover"+id).val();
			if (quantidade != null && quantidade != ""){
				
				$.ajax({
					method:"get",
					url:urlAction,
					data:"quantidade="+quantidade+"&idalimento="+id+"&acao=removeralimentorefeicao&idrefeicao="+idrefeicao,
					success:function(response,textStatus,xhr){
						verAlimentosRefeicao();
						
					}
				}).fail(function(xhr,status,erroThrown){
					alert("Erro : "+xhr.responseText);
				});
				
			}else {
				alert("Insira uma quantidade para remover.")
			}
				
			
		}
		
		
		function adicionarAlimento(){
			if (verificarFormulario()){
				
				var id=document.getElementById("id").value;
				var quantidade=document.getElementById("quantidade").value;
				var urlAction=document.getElementById("form-user").action;
				var idrefeicao=document.getElementById("idrefeicao").value;
				$.ajax({
					method:"get",
					url:urlAction,
					data:"idrefeicao="+idrefeicao+"&acao=adicionaralimentorefeicao&id="+id+"&quantidade="+quantidade,
					success:function(response,textStatus,xhr){
						var json=JSON.parse(response);
						$("#proteinatotal").attr("value",json.proteinas);
						$("#caloriatotal").attr("value",json.calorias);
						$("#gorduratotal").attr("value",json.gorduras);
						$("#carboidratototal").attr("value",json.carboidratos);

					}
				}).fail(function(xhr,status,erroThrown){
					alert("Erro : "+xhr.responseText);
				})
				
			}
		
		}
		
		function imprimirRefeicao(){
			var idrefeicao=document.getElementById("idrefeicao").value;
			var data="acao=imprimir"+"&"+"id="+idrefeicao;
			var urlAction=getContextPath()+"/ServletRefeicao";
			
			document.getElementById("acao2").value="imprimir";
			
			$("#form-refeicao").submit();
			return false;
			
		}
	</script>
</body>
</html>
