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

					<table class="table table-borderless">
						<thead>
							<tr>
								<th>Caloria</th>
								<th>Proteina</th>
								<th>Carboidrato</th>
								<th>Gordura</th>
							</tr>
						</thead>
						<tr>
							<td id="data-caloria">${macros.calorias}</td>
							<td id="data-proteina">${macros.proteinas}</td>
							<td id="data-carboidrato">${macros.carboidrato}</td>
							<td id="data-gordura">${macros.gordura}</td>
						</tr>
					</table>

					<h1>Seja Bem-Vindo ao meu Projeto !</h1>

					<form id="form-user"
						action="<%=request.getContextPath()%>/ServletAlimento"
						method="get">
						<input type="hidden" id="acao" name="acao" value="">

						<div class="mb-2">
							<label for="data" class="form-label">Data</label> <input
								type="text" class="form-control" id="data" name="data">


						</div>







					</form>




					<button type="button" onclick="limparMacros()"
						class="btn btn-primary">Limpar</button>

					<button type="button" class="btn btn-info" onclick="exibirModal(1)">Ver
						Alimentos</button>








					<div class="mb-2">
						<label for="nome" class="form-label">Pesquisar</label> <input
							type="text" class="form-control" required id="nome" name="nome"
							placeholder="Digite o nome...">


					</div>


					<button type="button" onclick="pesquisar()" class="btn btn-dark">Pesquisar</button>


					<c:if test="${not empty todos}">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>ID</th>
									<th>Nome</th>
									<th>Porcao</th>
									<th>Calorias</th>
									<th>P</th>
									<th>C</th>
									<th>G</th>
									<th>VER</th>


								</tr>

							</thead>
							<tbody>
								<c:forEach items="${todos}" var="ali">
									<tr id="${ali.id}">
										<td>${ali.id}</td>

										<td>${ali.nome}</td>
										<td>${ali.porcao}</td>
										<td>${ali.caloria }</td>
										<td>${ali.proteina}</td>
										<td>${ali.carboidrato}</td>
										<td>${ali.gordura}</td>
										<td><button onclick="pegarAlimento(${ali.id})"
												class="btn btn-success">VER</button></td>

										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<nav aria-label="Page navigation example">
							<ul class="pagination justify-content-end">

								<c:if test="${paginaatual!=1 }">
									<li class="page-item ">
								</c:if>
								<c:if test="${paginaatual==1 }">
									<li class="page-item disabled">
								</c:if>
								<a
									href="<%=request.getContextPath()%>/ServletAlimento?acao=pesquisaralimentos&paginaatual=${paginaatual-1}"
									class="page-link">Previous</a>

								</li>

								<c:forEach begin="1" end="${totalpaginas}" step="1" var="p">
									<c:if test="${p==paginaatual}">
										<li class="page-item"><a class="page-link"
											autofocus="autofocus"
											href="<%=request.getContextPath()%>/ServletAlimento?acao=pesquisaralimentos&paginaatual=${p}">${p}</a></li>
									</c:if>

									<c:if test="${p!=paginaatual}">
										<li class="page-item"><a class="page-link"
											href="<%=request.getContextPath()%>/ServletAlimento?acao=pesquisaralimentos&paginaatual=${p}">${p}</a></li>
									</c:if>
								</c:forEach>
								<c:if test="${totalpaginas!=paginaatual}">
									<li class="page-item">
								</c:if>
								<c:if test="${totalpaginas==paginaatual}">
									<li class="page-item disabled">
								</c:if>
								<a class="page-link"
									href="<%=request.getContextPath()%>/ServletAlimento?acao=pesquisaralimentos&paginaatual=${paginaatual+1}">Next</a>

								</li>
							</ul>
						</nav>
					</c:if>










					<div class="modal fade" id="exampleModal" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">New message</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body"></div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
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

	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script src="https://npmcdn.com/flatpickr/dist/l10n/pt.js"></script>
	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>
	<script type="text/javascript">
	
	flatpickr("#data", {"locale":"pt", dateFormat: "d-m-Y",maxDate: "today"});

	
	
		function pesquisar() {
			var urlAction = document.getElementById("form-user").action;
			var nome = document.getElementById("nome").value;
			  alert(urlAction);

			if (nome == "") {
				window.location.href = urlAction
						+ "?acao=pesquisaralimentos&paginaatual=1";
			} else {
				window.location.href = urlAction
						+ "?acao=pesquisaralimentos&paginaatual=1&nome=" + nome;
			}

		}
		
		function verificarFormulario(){
			var erro=0;
			  var quantidade=document.getElementById("quantidade");
			  var idselecionado = document.getElementById("idselecionado");
				var data=document.getElementById("data");
			  if (document.querySelector(".alerta")!=null){
				  document.querySelector(".alerta").remove();  
			  }
			  
			  if (idselecionado.value=="" ){
				idselecionado.parentNode.innerHTML+="<span class=\"alerta\" style=\"color:red\">Por favor Selecione Um alimento na Lista abaixo.</span>";
				return false;
			}
			
			if (quantidade.value==""){
				
				erro++;
				quantidade.parentNode.innerHTML+="<span class=\"alerta\" style=\"color:red\">Insira uma Quantidade</span>";
				return false;
			} 
			
			
			return true;
			
		}
		function mostrarAlimentoConsumido() {
			  var idselecionado = document.getElementById("idselecionado").value;
			  var quantidade=document.getElementById("quantidade").value;
			  var urlAction = document.getElementById("form-user").action;
			  alert(urlAction);
			  var data=document.getElementById("data").value;
			  if (verificarFormulario()) {
			    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "id=" + idselecionado +
			      "&acao=alimentoconsumido"+
			      "&quantidade="+quantidade+
			      "&data="+data,
			      success: function (response, textStatus, xhr) {
			    	  
			    		var json = JSON.parse(response);
						document.getElementById("data-caloria").innerHTML=json.calorias;
						document.getElementById("data-proteina").innerHTML=json.proteinas;
						document.getElementById("data-carboidrato").innerHTML=json.carboidrato;
						document.getElementById("data-gordura").innerHTML=json.gordura;

						console.log(json);
			        adicionar();
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao buscar usuário por nome" + xhr.responseText);
			    });
			  }
			}

		function verificarData(){
			var data=document.getElementById("data");
			  if (document.querySelector(".alerta")!=null){
				  document.querySelector(".alerta").remove();  
			  }
			  
			  if (data.value=="" || data.value.trim=="" ){
					data.parentNode.innerHTML+="<span class=\"alerta\" style=\"color:red\">Por favor, selecione uma data.</span>";
					flatpickr("#data", {"locale":"pt", dateFormat: "d-m-Y",maxDate: "today"});
					return false;
				}
			  return true;
		}
		function limparMacros(){
			  var urlAction = document.getElementById("form-user").action;
				
			  var data=document.getElementById("data").value;

			  if (verificarData()){
				  $.ajax({
				      method: "get",
				      url: urlAction,
				      data: "acao=limparmacros"+
				      "&data="+data,
				      success: function (response, textStatus, xhr) {
				    	  
							document.getElementById("data-caloria").innerHTML=0;
							document.getElementById("data-proteina").innerHTML=0;
							document.getElementById("data-carboidrato").innerHTML=0;
							document.getElementById("data-gordura").innerHTML=0;

				      },
				    }).fail(function (xhr, status, errorThrown) {
				      alert("Error ao buscar usuário por nome" + xhr.responseText);
				    });
			  }
			 
		}
		
		function pegarAlimento(id){
			var data=document.getElementById("data");
			
			if (verificarData()){
				$("#exampleModal").modal('show');

				var linha=document.getElementById(id);
				var id=linha.cells[0].innerHTML;
				var nome=linha.cells[1].innerHTML;
				$("div.modal-body").html("");
				$("#exampleModalLabel").html("Insira a quantidade.");
				 $("div.modal-body").append($("<div class=\"mb-2\">"+
							"<label for=\"idselecionado\" class=\"form-label\">ID</label>" +
							"<input type=\"text\" readonly=\"readonly\" class=\"form-control\" required "+
							"id=\"idselecionado\" name=\"idselecionado\"></div>"));
				 $("div.modal-body").append($("<div class=\"mb-2\">"+
							"<label for=\"nomeselecionado\" class=\"form-label\">NOME</label>" +
							"<input type=\"text\" readonly=\"readonly\" class=\"form-control\" required "+
							"id=\"nomeselecionado\" name=\"nomeselecionado\"></div>"));
				 $("div.modal-body").append($("<div class=\"mb-2\">"+
							"<label for=\"quantidade\" class=\"form-label\">QUANTIDADE</label>" +
							"<input type=\"text\"  class=\"form-control\" required "+
							"id=\"quantidade\" name=\"quantidade\"></div>"));

				
				var button="<button type=\"button\" onclick=\"mostrarAlimentoConsumido()\" class=\"btn btn-warning\">Adicionar</button>";
				 $("div.modal-body").append($(button));
				document.getElementById("nomeselecionado").value=nome;
				document.getElementById("idselecionado").value=id;

			}else{
				$("#exampleModal").modal('hide');
				
			}
			
		}
		function removerAlimento(id,quantidade){
			 var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
			  if (
			    id != null &&
			    
			    id != ""
			  ) {
			    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "id=" + id +
			      "&acao=removeralimentoconsumido&quantidade="+quantidade+
			      "&data="+data,
			      success: function (response, textStatus, xhr) {
						var json = JSON.parse(response);

						console.log(json);
						document.getElementById("data-caloria")=nnerHTML=json.calorias;
						document.getElementById("data-proteina").innerHTML=json.proteinas;
						document.getElementById("data-carboidrato").innerHTML=json.carboidrato;
						document.getElementById("data-gordura").innerHTML=json.gordura;
						
						exibirModal(1);
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao buscar usuário por nome" + xhr.responseText);
			    });
			  }
			}

	
		
		function exibirModal(paginaAtual){
			 var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
				$("div.modal-body").html("");
				console.log(data);
			  var spinner="<div class=\"spinner-border text-primary\" role=\"status\">"+
			  "<span class=\"sr-only\">Loading...</span>"+
			  "</div>";
			  $("div.modal-body").append($("<table class='table table-striped' id='comi-hoje'></table>"))
			  $("div.modal-body").append($("<nav aria-label=\"Page navigation example\"><ul id=\"paginacao-modal\" class=\"pagination\"></ul></nav>"))
			  document.getElementById("comi-hoje").innerHTML=spinner;
			if (verificarData()){
				$('#exampleModal').modal('show');

				$("#exampleModalLabel").html("Todos os alimentos consumidos em :"+data);

				$.ajax({
				      method: "get",
				      url: urlAction,
				      data: "acao=alimentosmodal&paginaatual="+paginaAtual+
				      		"&data="+data,
				      success: function (response, textStatus, xhr) {
							var json = JSON.parse(response);
							if (json.length==0){
								document.getElementById("comi-hoje").innerHTML="<tr>Sem Alimentos</tr>";
							}
							else {
								var tabela="";
								var previous="";
								var next="";
								var paginacao="";
								var titulo="<thead><tr><th>#</th><th>Nome</th><th>Quantidade</th></tr></thead>";

								for (let i=0;i<json.length;i++){
									console.log(i);
									var botao="<button onclick=\"removerAlimento("+json[i].id+","+json[i].quantidade+")\"type=\"button\" class=\"btn btn-danger\">Danger</button>";

									 tabela +="<tr id=\""+json[i].id+"\"><td>"+json[i].idAlimento+"</td><td>"+json[i].nome+"</td><td>"+json[i].quantidade+"</td><td>"+botao+"</td></tr>"
								}
								document.getElementById("comi-hoje").innerHTML=titulo+tabela;
								
								
								var totalElementos = xhr
								.getResponseHeader("totalPagina");
								const quotient = Math.floor(totalElementos/5);
								const remainder = totalElementos % 5;
								var totalPagina=quotient;
								if (remainder!=0){
									totalPagina++;
								}
								for (var i=1;i<=totalPagina;i++){
									
									
									if (paginaAtual==i){
										
											paginacao+="<li class=\"page-item active\"  onclick=\"exibirModal("+i+")\"><a  class=\"page-link\" >"+i+"</a></li>";
									}
									else {
										paginacao+="<li class=\"page-item\" onclick=\"exibirModal("+i+")\"><a class=\"page-link\" href=\"#\" >"+i+"</a></li>";

									}
								}
								if  (totalPagina>1){
									previous="<li class=\"page-item\" onclick=\"exibirModal("+(paginaAtual-1)+")\"><a class=\"page-link\" href=\"#\" ><</a></li>";
									next="<li class=\"page-item\" onclick=\"exibirModal("+(paginaAtual+1)+")\"><a class=\"page-link\" href=\"#\" >></a></li>";

									if (paginaAtual==1){
										previous="<li class=\"page-item disabled\" ><a class=\"page-link\" href=\"#\" ><</a></li>";

									}
									if (paginaAtual==totalPagina){
										next="<li class=\"page-item disabled\" ><a class=\"page-link\" href=\"#\" >></a></li>";

									}

								}
								document.getElementById("paginacao-modal").innerHTML=previous+paginacao+next;
							}
							
						
							
				      },
				    }).fail(function (xhr, status, errorThrown) {
				      alert("Error ao buscar usuário por nome" + xhr.responseText);
				    });

			}else {

				$('#exampleModal').modal('hide');
			


			}
			  		}
		function adicionar(){
			var nome=document.getElementById("nomeselecionado").value;
			var id=document.getElementById("idselecionado").value;
			var quantidade=document.getElementById("quantidade").value;
			var lista=document.getElementById("lista-alimentos");
			var botao="<button onclick=\"removerAlimento("+id+","+quantidade+")\"type=\"button\" class=\"btn btn-danger\">Danger</button>";

			var string="<tr id=\"comido"+id+"\"><td>"+id+"</td><td>"+nome+"</td><td>"+quantidade+"</td><td>"+botao+"</td></tr>";
			
			lista.innerHTML+=string;

		}
		
		
		</script>
</body>
</html>
