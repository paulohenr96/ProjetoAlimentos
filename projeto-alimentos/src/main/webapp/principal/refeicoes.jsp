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
<title>Comi Hoje</title>
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


					<h2>Meu Consumo Calórico</h2>

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

					<button type="button" class="btn btn-primary" onclick="exibirModal(1)">Ver
						Alimentos</button>
<button type="button" class="btn btn-primary" onclick="exibirModalRefs(1)">Ver
						Refeições</button>


					<div id="aviso_1"></div>




					<div class="mb-2">
						<label for="nome" class="form-label">Pesquisar</label> <input
							type="text" class="form-control" required id="nome-pesquisa"
							name="nome" placeholder="Digite o nome...">


					</div>

					<div class="mb-2" id="radio-pesquisa">

						<input class="form-check-input" type="radio" id="radio-alimento"
							name="radio-pesquisa" value="alimento" checked> <label
							class="form-check-label"  for="radio-alimento">Alimento </label> <input
							class="form-check-input" type="radio" id="radio-refeicao"
							value="refeicao" name="radio-pesquisa"> <label
							class="form-check-label" for="radio-refeicao">Refeição </label>

					</div>
					<button type="button" onclick="pesquisar()" class="btn btn-dark">Pesquisar</button>


					<table id="tabela-pagina" class="table table-hover">

					</table>
					<nav aria-label="Page navigation example">
						<ul id="paginacao-pagina" class="pagination justify-content-end">

						</ul>
					</nav>










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









								                           <jsp:include page="/footer.jsp"></jsp:include>

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
			
			
			var radio=0;
			if ($("#radio-alimento").is(":checked")){
				radio="alimento";
				pesquisarAlimento(1);
			}else {
				radio="refeicao";
				pesquisarRefeicao(1);
			}

		

		}
		function pesquisarAlimento(paginaatual){
			var urlAction = document.getElementById("form-user").action;
			var nome = document.getElementById("nome-pesquisa").value;



		    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "nome=" + nome  +
			      "&acao=pesquisaralimentos"+
			      "&paginaatual="+paginaatual,
			      success: function (response, textStatus, xhr) {
			    	  
			    		var json = JSON.parse(response);
			    		if (json.length>0){
			    			var totalPaginas= xhr
							.getResponseHeader("totalpagina");	

			    		$("#tabela-pagina").empty();
			    		
			    		$("#tabela-pagina").append("<thead><tr><th>NOME</th><th>PORÇÃO</th><th>CALORIAS</th><th>PROTEINAS</th><th>CARBOIDRATOS</th><th>GORDURAS</th><th>SELECIONAR</th></tr></thead>")
			        	$("#tabela-pagina").append("<tbody></tbody>");
			    		json.forEach((e)=>{
			        		var botao="<button onclick='pegarAlimento("+e.id+")' class='btn btn-success'>Selecionar</button>";
			    			$("#tabela-pagina > tbody").append("<tr id='"+e.id+"'><td>"+e.nome+"</td><td>"+e.porcao+"</td><td>"+e.caloria+"</td><td>"+e.proteina+"</td><td>"+e.carboidrato+"</td><td>"+e.gordura+"</td><td>"+botao+"</td></tr>")
			    			    		
			    		
			    		});
			    		
			    		
			    		$("#paginacao-pagina").empty();
						for (var i=1;i<=totalPaginas;i++){
							
							var n="";
							if (i==paginaatual){
								 n="<li onclick='pesquisarAlimento("+i+")' class='page-item active'> <a  href='#' class='page-link'>"+i+"</a></li> ";

							}else{
								 n="<li onclick='pesquisarAlimento("+i+")' class='page-item '> <a  href='#' class='page-link'>"+i+"</a></li> ";

							}
							$("#paginacao-pagina").append(n);

						}			    	
			        		
			        	}else {
				    		$("#tabela-pagina").empty();
				    		$("#tabela-pagina").append("<th>Nenhum alimento encontrado.</th>");


			        	}
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao pesquisar alimento " + xhr.responseText);
			    });
		}
		
		function verAlimentosRefeicao(idrefeicao){
			
			$("div.modal-body").html("");
			$("#exampleModal").modal('show');

			$("div.modal-body").append("<table class='table' style='text-align:center;'> </table>");
			$("div.modal-body > table").append("<thead></thead>");
			$("div.modal-body > table > thead").append ("<th scope='col'>#</th>");
			$("div.modal-body > table > thead").append ("<th scope='col'>Nome</th>");
			$("div.modal-body > table > thead").append ("<th scope='col'>Quantidade</th>");
			
			$("div.modal-body > table").append("<tbody></tbody>");
			
			
			
			var urlAction=document.getElementById("form-user").action;
			$.ajax({
				method:"get",
				url:urlAction,
				data:"idrefeicao="+idrefeicao+"&acao=alimentosrefeicao",
				success:function(response,textStatus,xhr){
					var json=JSON.parse(response);
					if (json.length==0){
						$(".modal thead").hide();
					}else {
						$(".modal .table").show();
						$("div.modal-body>table>tbody").empty();

						json.forEach((e)=>{
							$("div.modal-body>table>tbody").append("<tr id='"+e.id+"'> </tr>");

							$("#"+e.id).append("<td>"+e.id+"</td>");
							$("#"+e.id).append("<td>"+e.alimento.nome+"</td>");
							$("#"+e.id).append("<td>"+e.quantidade+" gramas</td>");

						

						});
						
						
						

					}
					}
				
			}).fail(function(xhr,status,erroThrown){
				alert("Erro : "+xhr.responseText);
			})
		

		}
		function pesquisarRefeicao(paginaatual){
			var urlAction = document.getElementById("form-user").action;
			var nome = document.getElementById("nome-pesquisa").value;



		    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "nome=" + nome  +
			      "&acao=pesquisarrefeicao"+
			      "&paginaatual="+paginaatual,
			      success: function (response, textStatus, xhr) {
			    	  
			    		var json = JSON.parse(response);
			    		if (json.length>0){
			    			var totalPaginas= xhr
							.getResponseHeader("totalpagina");	

			    		$("#tabela-pagina").empty();
			    		
			    		$("#tabela-pagina").append("<thead><tr><th>NOME</th><th>CALORIAS</th><th>PROTEINAS</th><th>CARBOIDRATOS</th><th>GORDURAS</th><th>SELECIONAR</th><th>VER</th></tr></thead>")
			        	$("#tabela-pagina").append("<tbody></tbody>");
			    		json.forEach((e)=>{
			        		var botao="<button onclick='addRefeicao("+e.id+")' class='btn btn-success'>Adicionar</button>";
			        		var botaoVerAlimentos="<button onclick='verAlimentosRefeicao("+e.id+")' class='btn btn-primary'>Ver Alimentos</button>";

			    			$("#tabela-pagina > tbody").append("<tr id='"+e.id+"'></tr>");
			    			$("#"+e.id).append("<td>"+e.nome+"</td>")
			    			$("#"+e.id).append("<td>"+e.calorias+"</td>");
			    			$("#"+e.id).append("<td>"+e.proteinas+"</td>");
			    			$("#"+e.id).append("<td>"+e.carboidratos+"</td>");
			    			$("#"+e.id).append("<td>"+e.gorduras+"</td>");
			    			$("#"+e.id).append("<td>"+botao+"</td>");
			    			$("#"+e.id).append("<td>"+botaoVerAlimentos+"</td>");

			    			    		
			    		
			    		});
			    		
			    		
			    		$("#paginacao-pagina").empty();

						for (var i=1;i<=totalPaginas;i++){
							
							var n="";
							if (i==paginaatual){
								 n="<li onclick='pesquisarRefeicao("+i+")' class='page-item active'> <a  href='#' class='page-link'>"+i+"</a></li> ";

							}else{
								 n="<li onclick='pesquisarRefeicao("+i+")' class='page-item '> <a  href='#' class='page-link'>"+i+"</a></li> ";

							}
							$("#paginacao-pagina").append(n);

						}			    	
			        		
			        	}else {
				    		$("#tabela-pagina").empty();
				    		$("#tabela-pagina").append("<th>Nenhuma refeição encontrada.</th>");


			        	}
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error pesquisar refeicao " + xhr.responseText);
			    });
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
		
		function addRefeicao(idrefeicao) {
			  var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
			    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "id=" + idrefeicao +
			      "&acao=adicionarrefeicaomacros"+
			      "&data="+data,
			      success: function (response, textStatus, xhr) {
			    	  var json=JSON.parse(response);
			    	  console.log(json);

// 			    	  var ali_n=json.listaAlimentos.length;
// 			    	  var ref_n=json.refeicoes.length;
			    	  $("#aviso_1").empty()
			    	  $("#aviso_1").append("<span style='color:green'>Refeicao adicionada.</span>")
			    	


			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao adicionar refeicao " + xhr.responseText);
			    });
			  }
		
		
		function adicionarNovoAlimento() {
			  var idselecionado = document.getElementById("idselecionado").value;
			  var quantidade=document.getElementById("quantidade").value;
			  var urlAction = document.getElementById("form-user").action;
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
					

			        adicionar();
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao adicionar novo alimento " + xhr.responseText);
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
				    	  $("#aviso_1").empty()

						$("#aviso_1").append("<span style='color:green'>Consumos da data "+data+" foram zerados.</span>")

				      },
				    }).fail(function (xhr, status, errorThrown) {
				      alert("Error ao resetar " + xhr.responseText);
				    });
			  }
			 
		}
		
		function pegarAlimento(id){
			var data=document.getElementById("data");
			
			if (verificarData()){
				$("#exampleModal").modal('show');

				var linha=document.getElementById(id);
				var nome=linha.cells[0].innerHTML;
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

				
				var button="<button type=\"button\" onclick=\"adicionarNovoAlimento()\" class=\"btn btn-warning\">Adicionar</button>";
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

// 						console.log(json);
// 						document.getElementById("data-caloria").innerHTML=json.calorias;
// 						document.getElementById("data-proteina").innerHTML=json.proteinas;
// 						document.getElementById("data-carboidrato").innerHTML=json.carboidrato;
// 						document.getElementById("data-gordura").innerHTML=json.gordura;
						
						exibirModal(1);
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao remover alimento " + xhr.responseText);
			    });
			  }
			}
		
		
		
		function consultarMacros(){
			  var data=document.getElementById("data").value;
				 var urlAction = document.getElementById("form-user").action;

		    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "acao=consultarmacros&data="+data,
			      success: function (response, textStatus, xhr) {
						var json = JSON.parse(response);

//						console.log(json);
//						document.getElementById("data-caloria").innerHTML=json.calorias;
//						document.getElementById("data-proteina").innerHTML=json.proteinas;
//						document.getElementById("data-carboidrato").innerHTML=json.carboidrato;
//						document.getElementById("data-gordura").innerHTML=json.gordura;
						$("div.modal-body").append("<table class=\"table info-macros\" style=\"text-align: center;\"></table>");	
						$("div.modal-body > table.info-macros").append("<thead><th>Calorias</th><th>Proteinas</th><th>Carboidratos</th><th>Gorduras</th></thead>");
						$("div.modal-body > table.info-macros ").append("<tbody></tbody>");
						$("div.modal-body > table.info-macros >tbody").append("<td>"+json.calorias+" kcal</td>");	
						$("div.modal-body > table.info-macros >tbody").append("<td>"+json.proteinas+" g</td>");			      
						$("div.modal-body > table.info-macros >tbody").append("<td>"+json.carboidrato+" g</td>");			      
						$("div.modal-body > table.info-macros >tbody").append("<td>"+json.gordura+" g</td>");			      

						},
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao consultar os macros " + xhr.responseText);
			    });
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

				$("#exampleModalLabel").html("Todos os alimentos consumidos "+data);

				$.ajax({
				      method: "get",
				      url: urlAction,
				      data: "acao=alimentosmodal&paginaatual="+paginaAtual+
				      		"&data="+data,
				      success: function (response, textStatus, xhr) {
							var json = JSON.parse(response);
							if (json.length==0){
								$("#comi-hoje").html("Sem Alimentos");
							}
							else {
								var tabela="";
								var previous="";
								var next="";
								var paginacao="";
								$("#comi-hoje").empty();
								$("#comi-hoje").append("<thead></thead>");
								$("#comi-hoje > thead").append("<th>id</th");

								$("#comi-hoje > thead").append("<th>Nome</th");
								$("#comi-hoje > thead").append("<th>Quantidade</th");
								$("#comi-hoje > thead").append("<th>Tipo</th");
								$("#comi-hoje > thead").append("<th>Remover</th");

								
								
								$("#comi-hoje").append("<tbody></tbody>");
								for (let i=0;i<json.length;i++){
									console.log(i);
									var botao="<button onclick=\"removerAlimento("+json[i].id+","+json[i].quantidade+")\"type=\"button\" class=\"btn btn-danger\">Remover</button>";
									
									
									
									
									
									
									 var linha ="<tr id=\""+json[i].id+"\"><td>"+json[i].idAlimento+"</td><td>"+json[i].nome+"</td><td>"+json[i].quantidade+"</td><td>Alimento</td><td>"+botao+"</td></tr>"
								
										$("#comi-hoje > tbody").append(linha)

								}
								
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
								consultarMacros();

							}
						
							
				      },
				    }).fail(function (xhr, status, errorThrown) {
				      alert("Error ao mostrar o modal" + xhr.responseText);
				    });

			}else {

				$('#exampleModal').modal('hide');
			


			}
			  		}
		
		
		
		
		
		function exibirModalRefs(paginaAtual){
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

				$("#exampleModalLabel").html("Todos as refeicoes consumidas "+data);

				$.ajax({
				      method: "get",
				      url: urlAction,
				      data: "acao=refeicoesmodal&paginaatual="+paginaAtual+
				      		"&data="+data,
				      success: function (response, textStatus, xhr) {
							var json = JSON.parse(response);
							if (json.length==0){
								$("#comi-hoje").html("Sem Refeicoes");
							}
							else {
								var tabela="";
								var previous="";
								var next="";
								var paginacao="";
								$("#comi-hoje").empty();
								$("#comi-hoje").append("<thead></thead>");
								$("#comi-hoje > thead").append("<th>id</th");

								$("#comi-hoje > thead").append("<th>Nome</th");
								$("#comi-hoje > thead").append("<th>Remover</th");

								
								
								$("#comi-hoje").append("<tbody></tbody>");
								for (let i=0;i<json.length;i++){
									console.log(i);
									var botao="<button onclick=\"removerRefeicao("+json[i].id+","+json[i].quantidade+")\"type=\"button\" class=\"btn btn-danger\">Remover</button>";
									
									
									
									
									
									
									 var linha ="<tr id=\""+json[i].id+"\"><td>"+json[i].id+"</td><td>"+json[i].nome+"</td><td>"+botao+"</td></tr>"
								
										$("#comi-hoje > tbody").append(linha)

								}
								
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
								consultarMacros();

							}
						
							
				      },
				    }).fail(function (xhr, status, errorThrown) {
				      alert("Error ao mostrar refeições " + xhr.responseText);
				    });

			}else {

				$('#exampleModal').modal('hide');
			


			}
			  		}
		
		
		
		function removerRefeicao(idrefeicao){
			 var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
			  
			    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "id=" + idrefeicao +
			      "&acao=removerrefeicaoconsumida"+
			      "&data="+data,
			      success: function (response, textStatus, xhr) {
						var json = JSON.parse(response);

//						console.log(json);
//						document.getElementById("data-caloria").innerHTML=json.calorias;
//						document.getElementById("data-proteina").innerHTML=json.proteinas;
//						document.getElementById("data-carboidrato").innerHTML=json.carboidrato;
//						document.getElementById("data-gordura").innerHTML=json.gordura;
						
						exibirModalRefs(1);
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao remover refeição " + xhr.responseText);
			    });
			  
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
