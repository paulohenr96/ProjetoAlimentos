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
							<label for="data" class="form-label">Data</label> <input onchange="dataVazia()"
								type="text" class="form-control" id="data" name="data">


						</div>







					</form>




					<button type="button" onclick="limparMacros()"
						class="btn btn-primary">Limpar</button>

					<button type="button" class="btn btn-primary"
						onclick="exibirModal(1)">Ver Alimentos</button>
					<button type="button" class="btn btn-primary"
						onclick="exibirModalRefs(1)">Ver Refeições</button>

					<br>
					<br>
					<div id="aviso_1"></div>




					<div class="mb-2">
						<label for="nome" class="form-label">Pesquisar</label> <input
							type="text" class="form-control" required id="nome-pesquisa"
							name="nome" placeholder="Digite o nome...">


					</div>

					<div class="mb-2" id="radio-pesquisa">

						<input class="form-check-input" type="radio" id="radio-alimento"
							name="radio-pesquisa" value="alimento" checked> <label
							class="form-check-label" for="radio-alimento">Alimento </label> <input
							class="form-check-input" type="radio" id="radio-refeicao"
							value="refeicao" name="radio-pesquisa"> <label
							class="form-check-label" for="radio-refeicao">Refeição </label>

					</div>
					<button type="button" onclick="pesquisar()" class="btn btn-primary">Pesquisar</button>












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
								<div class="modal-body">
									<table class='table table-striped' id='comi-hoje'></table>

									<nav aria-label="Page navigation example">
										<ul class="pagination" id="paginacaoali">

										</ul>
									</nav>


									<table id='tabelamacros' class="table info-macros"
										style="text-align: center;"></table>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Fechar</button>
								</div>
							</div>
						</div>
					</div>








					<div style="height: 400px" id="tabelapesquisa">
						<nav aria-label="Page navigation example">
							<ul id="paginacao-pagina" class="pagination justify-content-end">

							</ul>
						</nav>

						<table id="tabela-pagina" class="table table-hover">

						</table>

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

		function dataVazia(){
			
			if ($("#data").val!=''){
				mensagemSucesso("aviso_1","Escolha entre os alimentos e refeições.")
			}
		}
	
		function pesquisar() {
			$("#tabelapesquisa").append($(spinner_azul))
			
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
						$("div.spinner-border").remove();
			    		var json = JSON.parse(response);
			    		if (json.length>0){
			    			var totalPaginas= xhr
							.getResponseHeader("totalPagina");	

			    		$("#tabela-pagina").empty();
			    		
			    		$("#tabela-pagina").append("<thead><tr><th>NOME</th><th>PORÇÃO</th><th>CALORIAS</th><th>PROTEINAS</th><th>CARBOIDRATOS</th><th>GORDURAS</th><th>AÇÃO</th></tr></thead>")
			        	$("#tabela-pagina").append("<tbody></tbody>");
			    		json.forEach((e)=>{
			        		var botao=icone("comer.png","pegarAlimento("+e.id+")","Comer");
			    			$("#tabela-pagina > tbody").append("<tr id='"+e.id+"'><td>"+e.nome+"</td><td>"+e.porcao+"</td><td>"+e.caloria+"</td><td>"+e.proteina+"</td><td>"+e.carboidrato+"</td><td>"+e.gordura+"</td><td>"+botao+"</td></tr>")
			    			    		console.log(e.nome);
			    		
			    		});
			    		
			    		
			    		$("#paginacao-pagina").empty();
						paginarTabelas("paginacao-pagina",totalPaginas,paginaatual,"pesquisarAlimento");

			        		
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
			
			limparModal();
			$("#exampleModal").modal('show');

			$("div.modal-body").append("<table class='table' id='tabela-alimentosrefeicao' style='text-align:center;'> </table>");
			$("div.modal-body > table").append("<thead></thead>");
			$("div.modal-body > table > thead").append ("<th scope='col'>#</th>");
			$("div.modal-body > table > thead").append ("<th scope='col'>Nome</th>");
			$("div.modal-body > table > thead").append ("<th scope='col'>Quantidade</th>");
			
			$("div.modal-body > table").append("<tbody></tbody>");
			
			$("#exampleModalLabel").html("Esta refeição contém os seguintes alimentos.");

			
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
						$("div.spinner-border").remove();

			    		var json = JSON.parse(response);
			    		if (json.length>0){
			    			var totalPaginas= xhr
							.getResponseHeader("totalPagina");	
			    		$("#tabela-pagina").empty();
			    		
			    		$("#tabela-pagina").append("<thead><tr><th>NOME</th><th>CALORIAS</th><th>PROTEINAS</th><th>CARBOIDRATOS</th><th>GORDURAS</th><th>AÇÃO<th/></tr></thead>")
			        	$("#tabela-pagina").append("<tbody></tbody>");
			    		json.forEach((e)=>{
			        		var botao=icone("comer.png","addRefeicao("+e.id+")","Comer");
			        		var botaoVerAlimentos=icone("ver.png","verAlimentosRefeicao("+e.id+")","VER");


			    			$("#tabela-pagina > tbody").append("<tr id='"+e.id+"'></tr>");
			    			$("#"+e.id).append("<td>"+e.nome+"</td>")
			    			$("#"+e.id).append("<td>"+e.calorias+"</td>");
			    			$("#"+e.id).append("<td>"+e.proteinas+"</td>");
			    			$("#"+e.id).append("<td>"+e.carboidratos+"</td>");
			    			$("#"+e.id).append("<td>"+e.gorduras+"</td>");
			    			$("#"+e.id).append("<td>"+botao+" "+botaoVerAlimentos+"</td>");

			    			    		
			    		
			    		});
			    		
			    		
			    		$("#paginacao-pagina").empty();

						paginarTabelas("paginacao-pagina",totalPaginas,paginaatual,"pesquisarRefeicao");

			        		
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
			var quantidade=document.getElementById("quantidade");
			var idselecionado = document.getElementById("idselecionado");
			var data=document.getElementById("data");
	        if (idselecionado.value=="" ){
				mensagemErro("aviso_alimento","Nenhum Alimento Selecionado");
				return false;
			}
			if (quantidade.value==""){
				mensagemErro("aviso_alimento","Selecione uma quantidade");
				return false;
			} 
			return true;
		}
		
		function addRefeicao(idrefeicao) {
			  var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
			  if (verificarData()){
				  $.ajax({
				      method: "get",
				      url: urlAction,
				      data: "id=" + idrefeicao +
				      "&acao=adicionarrefeicaomacros"+
				      "&data="+data,
				      success: function (response, textStatus, xhr) {
						if (response=="MAXIMO"){
							mensagemErro("aviso_1","Numero maximo de refeicoes consumidas.")
						}else if (response== "SUCESSO"){
					    	mensagemSucesso("aviso_1","Refeição Adicionada")

						}else {
							mensagemErro("aviso_1","Erro ao adicionar a refeição.");

						}
				    	


				      },
				    }).fail(function (xhr, status, errorThrown) {
				      alert("Error ao adicionar refeicao " + xhr.responseText);
				    });
			  }
			  
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
			    	  
					if (response == 'SUCESSO'){
						$("#quantidade").val("");
				    	mensagemSucesso("aviso_alimento","Alimento adicionado com sucesso.");
					}else if (response == 'MAXIMO') {
						mensagemErro("aviso_alimento","O numero maximo de alimentos para consumo foi atingido");
					} else {
						mensagemErro("aviso_alimento","Erro");

					}
				
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao adicionar novo alimento " + xhr.responseText);
			    });
			  }
			}

		function verificarData(){
			var data=document.getElementById("data");
			 
			  
			  if (data.value=="" || data.value.trim=="" ){
					mensagemErro("aviso_1","Selecione uma data.");
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
						
				    	  mensagemSucesso("aviso_1","Os macros foram zerados para esta data.")							

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
				limparModal	();
				$("#exampleModalLabel").html("Insira a quantidade.");
				$("div.modal-body").append($("<div id='pegaralimento'></div>"))
				 $("#pegaralimento").append($("<div class=\"mb-2\">"+
							"<label for=\"idselecionado\" class=\"form-label\">ID</label>" +
							"<input type=\"text\" readonly=\"readonly\" class=\"form-control\" required "+
							"id=\"idselecionado\" name=\"idselecionado\"></div>"));
				 $("#pegaralimento").append($("<div class=\"mb-2\">"+
							"<label for=\"nomeselecionado\" class=\"form-label\">NOME</label>" +
							"<input type=\"text\" readonly=\"readonly\" class=\"form-control\" required "+
							"id=\"nomeselecionado\" name=\"nomeselecionado\"></div>"));
				 $("#pegaralimento").append($("<div class=\"mb-2\">"+
							"<label for=\"quantidade\" class=\"form-label\">QUANTIDADE</label>" +
							"<input type=\"text\"  class=\"form-control\" required "+
							"id=\"quantidade\" name=\"quantidade\"></div>"));
				 $("#pegaralimento").append($("<br><br>"))
				$("#pegaralimento").append($("<div id='aviso_alimento'></div>"));
				 
				 
				var button="<button type=\"button\" onclick=\"adicionarNovoAlimento()\" class=\"btn btn-warning\">Adicionar</button>";
				 $("#pegaralimento").append($(button));
				document.getElementById("nomeselecionado").value=nome;
				document.getElementById("idselecionado").value=id;

			}else{
				$("#exampleModal").modal('hide');
				
			}
			
		}
		function removerAlimento(id){
			 var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
			  var quantidade=$("#quantidade_remover"+id).val();
			  if (
			    id != null &&
			    quantidade !=null &&
			    id != "" &&
			    quantidade != ""
			  ) {
			    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "id=" + id +
			      "&acao=removeralimentoconsumido&quantidade="+quantidade+
			      "&data="+data,
			      success: function (response, textStatus, xhr) {
						var json = JSON.parse(response);

						
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
				 $("div.modal-body").append("<table id='tabelamacros' class='table info-macros'	style='text-align: center;'></table>")
		    $.ajax({
			      method: "get",
			      url: urlAction,
			      data: "acao=consultarmacros&data="+data,
			      success: function (response, textStatus, xhr) {
						var json = JSON.parse(response);

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
		$('#exampleModal').on('hidden.bs.modal', function () {
			  // refresh current page
			 $("#paginacaoali").empty();

			})
		
		function exibirModal(paginaAtual){
			 var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
				limparModal();
			  $("div.modal-body").append($("<table class='table table-striped' id='comi-hoje'></table>"))
			  
			  document.getElementById("comi-hoje").innerHTML=spinner_azul;
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
								$("#comi-hoje").empty();
								$("#comi-hoje").append("<thead></thead>");
								$("#comi-hoje > thead").append("<th>id</th");
								$("#comi-hoje > thead").append("<th>Nome</th");
								$("#comi-hoje > thead").append("<th>Quantidade</th");
								$("#comi-hoje > thead").append("<th>Remover</th");
								$("#comi-hoje").append("<tbody></tbody>");
								for (let i=0;i<json.length;i++){
									var idinput="quantidade_remover"+json[i].id;
									var botao=icone("deletar.png","removerAlimento("+json[i].id+")","REMOVER");
									var linha ="<tr id=\""+json[i].id+"\"><td>"+json[i].idAlimento+"</td><td>"+normalizar(json[i].nome)+"</td><td>"+json[i].quantidade+"</td><td><input type='number' style='width:75px;' id="+idinput+"  placeholder='qtde'/>"+botao+"</td></tr>"
									$("#comi-hoje > tbody").append(linha)
								}
								var totalPaginas = xhr
								.getResponseHeader("totalPagina");
								paginarTabelas("paginacaoali",totalPaginas,paginaAtual,"exibirModal");
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
		
		
		
		function limparModal(){
			$("#comi-hoje").remove();
			$("#tabelamacros").remove();
			$("#pegaralimento").remove();
			$("#tabela-alimentosrefeicao").remove();
		}
		
		function exibirModalRefs(paginaAtual){
			  var urlAction = document.getElementById("form-user").action;
			  var data=document.getElementById("data").value;
			  console.log(data);
			  limparModal();
			  $("div.modal-body").append($("<table class='table table-striped' id='comi-hoje'></table>"))

			  var spinner="<div class=\"spinner-border text-primary\" role=\"status\">"+
			  "<span class=\"sr-only\">Loading...</span>"+
			  "</div>";
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
									var botao=icone("deletar.png","removerRefeicao("+json[i].id+","+json[i].quantidade+")","REMOVER");

									 var linha ="<tr id=\""+json[i].id+"\"><td>"+json[i].id+"</td><td>"+json[i].refeicao.nome+"</td><td>"+botao+"</td></tr>"
										$("#comi-hoje > tbody").append(linha)
								}
								
								var totalPaginas = xhr
								.getResponseHeader("totalPagina");
								paginarTabelas("paginacaoali",totalPaginas,paginaAtual,"exibirModalRefs");
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

						
						exibirModalRefs(1);
			      },
			    }).fail(function (xhr, status, errorThrown) {
			      alert("Error ao remover refeição " + xhr.responseText);
			    });
			  
		}
		
		
		</script>
</body>
</html>
