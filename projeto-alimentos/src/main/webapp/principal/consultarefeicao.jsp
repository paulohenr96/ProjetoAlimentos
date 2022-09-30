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
				<div class="container-fluid px-4">
				
									<button class="btn btn-link hBack" type="button">VOLTAR</button>
				
					<h1>Seja Bem-Vindo ao meu Projeto !</h1>



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
									type="text" placeholder="Nome da refeição" />
							</div>

							<div class="col">
								<label for="caloriatotal">Calorias</label> <input
									class="form-control" name="caloriatotal" required
									id="caloriatotal" value="${ref.calorias}" readonly="readonly"
									type="text" placeholder="Nome da refeição" />
							</div>
							<div class="col">
								<label for="proteinatotal">Proteinas</label> <input
									class="form-control" name="proteinatotal" required
									id="proteinatotal" value="${ref.proteinas}" readonly="readonly"
									type="text" placeholder="Nome da refeição" />
							</div>
							<div class="col">
								<label for="carboidratototal">Carboidratos</label> <input
									class="form-control" name="carboidratototal" required
									id="carboidratototal" value="${ref.carboidratos}"
									readonly="readonly" type="text" placeholder="Nome da refeição" />
							</div>
							<div class="col">
								<label for="gorduratotal">Gorduras</label> <input
									class="form-control" name="gorduratotal" required
									id="gorduratotal" value="${ref.gorduras}" readonly="readonly"
									type="text" placeholder="Nome da refeição" />
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
							<button type="button" class="btn btn-secondary ">Secondary</button>
							<button type="button" class="btn btn-success "
								data-toggle="modal" data-target="#exampleModal"
								onclick="verAlimentosRefeicao()">Ver Todos os Alimentos</button>

						</div>

					</form>

					<div class="row mb-2">

						<div class="col">
							<input type="text" class="form-control" placeholder="Nome"
								aria-label="Last name">
						</div>
						<div class="col">
							<button type="button" class="btn btn-secondary "
								onclick="mostrarListaAlimentos(1)">BUSCAR</button>

						</div>

					</div>

					<table class="table todos">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Nome</th>
								<th scope="col">Porcao</th>
								<th scope="col">Calorias</th>

								<th scope="col">P</th>
								<th scope="col">C</th>
								<th scope="col">G</th>
								<th scope="col">Selecionar</th>


							</tr>
						</thead>
						<tbody class="todos-alimentos">
						</tbody>

					</table>
					<nav aria-label="Page navigation example">
						<ul class="pagination">

						</ul>
					</nav>
				</div>
			</main>



			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true" >
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">${ref.nome}</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" style="height:400px;overflow:scroll">
						
						<table class="table" >
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Nome</th>
								<th scope="col">Quantidade</th>

								<th scope="col">Remover</th>


							</tr>
						</thead>
						<tbody class="alimentos-refeicao" >
						</tbody>

					</table>
						
						</div>
						<div class="modal-footer">
							
						</div>
					</div>
				</div>
			</div>






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
	$(".hBack").on("click", function(e){
	    e.preventDefault();
	    window.history.back();
	});
	$("table.todos").hide();
	
		function mostrarListaAlimentos(paginaatual){
			var urlAction = document.getElementById("form-user").action;
			var porpagina=5;
			$.ajax({
				method:"get",
				url:urlAction,
				data:"porpagina="+porpagina+"&acao=pesquisaralimentorefeicao&paginaatual="+paginaatual,
				success:function(response,textStatus,xhr){
					$("table.todos").show();

					var json=JSON.parse(response);
					var total=xhr.getResponseHeader("totalPagina");

					var quociente=Math.floor(total/porpagina);
					var paginas=quociente;
					
					if (total%porpagina!=0){
						paginas++;
					}
					var tbody = document.querySelector("tbody.todos-alimentos");
					tbody.innerHTML="";
					
					json.forEach((e)=>{
						
						
// 						document.querySelector("table > tbody").innerHTML+="<tr><td>"+e.id+"</td><td>"+e.nome+"</td><td>"+e.porcao+"</td><td>"+e.caloria+"</td><td>"+e.proteina+"</td><td>"+e.carboidrato+"</td><td>"+e.gordura+"</td></tr>"
						
						
						var linha=tbody.insertRow();
						
						linha.insertCell().innerHTML=e.id;
						linha.insertCell().innerHTML=e.nome;
						linha.insertCell().innerHTML=e.porcao;
						linha.insertCell().innerHTML=e.caloria;
						linha.insertCell().innerHTML=e.proteina;
						linha.insertCell().innerHTML=e.carboidrato;
						linha.insertCell().innerHTML=e.gordura;
						var button=$("<button>").addClass("btn btn-success").attr("type","button").html("Selecionar").click(function (){
							document.querySelector("#id").value=e.id;
							document.querySelector("#nome").value=e.nome;

						});
						linha.insertCell().append(button[0]);


						
					});
					$("ul.pagination").empty();
					if (paginaatual>1){
// 						document.querySelector("ul.pagination").innerHTML="<li class=\"page-item\"><a class=\"page-link\" onclick=\"mostrarListaAlimentos("+(paginaatual-1)+")\" href=\"#\">Anterior</a></li>";
						var anterior=$("<li>").addClass("page-item");
						var link=$("<a>").addClass("page-link").attr("href","#").attr("onclick","mostrarListaAlimentos("+(paginaatual-1)+")").html("Anterior").click(function (){
								alert("Oi");
						});
						console.log(link[0]);
						anterior.append(link[0])
						$("ul.pagination").append(anterior[0]);
						
				
					}else{
						document.querySelector("ul.pagination").innerHTML="<li class=\"page-item disabled\"><a class=\"page-link\" onclick=\"mostrarListaAlimentos("+(paginaatual-1)+")\" href=\"#\">Anterior</a></li>";

					}
				
					for (var i=1;i<=paginas;i++){
						document.querySelector("ul.pagination").innerHTML+="<li class=\"page-item\"><a class=\"page-link\" onclick=\"mostrarListaAlimentos("+i+")\" href=\"#\">"+i+"</a></li>";

					}
					if (paginaatual<paginas){
						document.querySelector("ul.pagination").innerHTML+="<li class=\"page-item\"><a class=\"page-link\" onclick=\"mostrarListaAlimentos("+(paginaatual+1)+")\" href=\"#\">Proxima</a></li>";

					}else{
						document.querySelector("ul.pagination").innerHTML+="<li class=\"page-item disabled\"><a class=\"page-link\" onclick=\"mostrarListaAlimentos("+(paginaatual+1)+")\" href=\"#\">Proxima</a></li>";

					}
					
					
				}
			}).fail(function (xhr, status, errorThrown) {
			      alert("Error ao buscar usuário por nome" + xhr.responseText);
		    });
			
		}
		function verificarFormulario(){
			var id=$("#id");
			var quantidade=$("#quantidade");
			if ( $.trim(id.val())==''){
				alert(id.html());
				$("p.alerta").remove();
				var aviso=$("<p>").addClass("alerta").css("color","red").html("selecione um elemento");
				$("#id").parent().append(aviso[0]);
				
				return false;
			}else if ($.trim(quantidade.val())==""){
				$("p.alerta").remove();
				var aviso=$("<p>").addClass("alerta").css("color","red").html("insira uma quantidade");
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
			$.ajax({
				method:"get",
				url:urlAction,
				data:"idrefeicao="+idrefeicao+"&acao=alimentosrefeicao",
				success:function(response,textStatus,xhr){
					var json=JSON.parse(response);
					if (json.length==0){
						tbody.innerHTML="LISTA VAZIA";
						$(".modal .table").hide();
					}else {
						$(".modal .table").show();

						json.forEach((e)=>{
							var linha=tbody.insertRow();
							var button=$("<button>").addClass("btn btn-danger").attr("type","button").html("REMOVER").click(function(){
								
								
								$.ajax({
									method:"get",
									url:urlAction,
									data:"idalimento="+e.id+"&acao=removeralimentorefeicao&idrefeicao="+idrefeicao,
									success:function(response,textStatus,xhr){
										linha.remove();
									}
								}).fail(function(xhr,status,erroThrown){
									alert("Erro : "+xhr.responseText);
								})
								
								
							});
							linha.insertCell().innerHTML=e.id;
							linha.insertCell().innerHTML=e.alimento.nome;
							linha.insertCell().innerHTML=e.quantidade;
							linha.insertCell().append(button[0]);

							console.log(e);


						});
						
						
						

					}
					}
				
			}).fail(function(xhr,status,erroThrown){
				alert("Erro : "+xhr.responseText);
			})
			
			
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
						console.log(json);
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
	</script>
</body>
</html>
