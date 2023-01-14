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
<title>Alimentos</title>
<link href="<%=request.getContextPath()%>/assets/css/styles.css"
	rel="stylesheet" />
	
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
	crossorigin="anonymous"></script>
<style>

td,th{
width:10px;
text-align: center;

}
</style>


</head>
<body class="sb-nav-fixed">
	<jsp:include page="header.jsp"></jsp:include>
	<div id="layoutSidenav">
		<jsp:include page="navbar.jsp" />
		<div id="layoutSidenav_content">



<input type="hidden" id="caminhocontexto" value="<%=request.getContextPath()%>"/>
			<main>

				<div class="container-sm px-4">

					<form method="get"
						action="<%=request.getContextPath()%>/ServletAlimento"
						id="form-user">
						<input type="hidden" id="acao" name="acao" value="">

						<h2>Alimentos</h2>
						<div class="mb-2 col-2">
							<label for="id" class="form-label">ID</label> <input type="text"
								readonly="readonly" class="form-control" id="id" name="id"
								placeholder="ID">
						</div>
						<div class="dados-inseridos col-5 ">

							<div class="mb-2">
								<label for="nome" class="form-label">Nome</label> <input
									type="text" class="form-control" id="nome" name="nome"
									placeholder="">
							</div>
							<div class="numeros">
								<div class="mb-2">
									<label for="porcao" class="form-label">Porção</label> <input
										type="number" min="0" step=".1" class="form-control" 
										id="porcao" name="porcao" placeholder="">
								</div>

								<div class="mb-2">
									<label for="caloria" class="form-label">Caloria</label> <input
										type="number" class="form-control"  id="caloria"
										name="caloria" placeholder="">
								</div>


								<div class="mb-2">
									<label for="proteina" class="form-label">Proteina</label> <input
										type="number" class="form-control"  id="proteina"
										name="proteina" placeholder="">
								</div>


								<div class="mb-2">
									<label for="carboidrato" class="form-label">Carboidrato</label>
									<input type="number" class="form-control" 
										id="carboidrato" name="carboidrato" placeholder="">

								</div>

								<div class="mb-2">
									<label for="gordura" class="form-label">Gordura</label> <input
										type="text" class="form-control"  id="gordura"
										name="gordura" placeholder="">

								</div>
							</div>
						</div>
						<button type="button" onclick="mandarFormulario();"
							class="btn btn-primary">Adicionar</button>
							

						<button type="button" onclick="editarAlimento();"
							class="btn btn-primary">Editar</button>
							<button type="button" onclick="limpar('form-user');verificarFormulario();mensagemSucesso('mensagem','Formulario limpo !')"
							class="btn btn-primary">Limpar</button>
							
							<br><br>
				<div class="alert alert-info" style="display: none;"
											id="mensagem" role="alert"></div>
			<div style= "height:500px">
						<ul id="paginacacadastroalimento" class="pagination  justify-content-end">

						</ul>
					<table  id="tabelaalimentoscadastro" class="table table-hover">

					</table>
			</div>

					</form>



				</div>
			</main>
			<jsp:include page="/footer.jsp"></jsp:include>

		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>

	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script type="text/javascript">
// 	$.mobile.ajaxLinksEnabled = false; 

	function verificarFormulario(){
		var regex=/^[0-9]*(\.[0-9]{0,2})?$/;
		const container = document.querySelectorAll(".numeros .mb-2");
		const todos = document.querySelectorAll(".dados-inseridos .mb-2");
		var erro=0;
		console.log(document.querySelectorAll(".dados-inseridos .mb-2"));
		todos.forEach ((e)=>{
			if (e.querySelector("div.alerta")!=null){
				e.querySelector("div.alerta").remove();

			}
			if (e.querySelector("input").value==""){
				e.innerHTML+="<div class=\"alerta\"><span style=\"color:red;\">Campo Vazio.</span></div>";
				erro++;
			}
		});
		
		
		
		if (erro>0){
			return false;
		}
		
		
		
		container.forEach((e)=> {
			
			if(!regex.test(e.querySelector("input").value)){
				e.innerHTML+="<div class=\"alerta\"><span style=\"color:red;\">Apenas duas casas decimais.</span></div>";
				erro++;
			}
			else{
				
				
				if (e.querySelector("div.alerta")!=null){
					e.querySelector("div.alerta").remove();

				}
				
			}
			});
		
		
		
		
		if (erro>0){
			return false;
		}
		
		return true;
		
	}

	
	exibirElementos(1);
	function exibirElementos(paginaatual){
		var caminho=$("#caminhocontexto").val();
		var urlAction=document.getElementById("form-user").action;
		var data="acao=alimentoscadastrados";
		data+="&";
		data+="paginaatual="+paginaatual;
		$.get(urlAction,data,function(response,textStatus,xhr){
			var json=JSON.parse(response);
			if (json.length>0){
    			var totalPaginas= xhr
				.getResponseHeader("totalPagina");	
    		$("#tabelaalimentoscadastro").empty();
    		
    		$("#tabelaalimentoscadastro").append("<thead><tr><th>NOME</th><th>PORÇÃO</th><th>CALORIAS</th><th>PROTEINAS</th><th>CARBOIDRATOS</th><th>GORDURAS</th><th>Ação</th></tr></thead>")
        	$("#tabelaalimentoscadastro").append("<tbody></tbody>");
    		json.forEach((e)=>{
        		var botao=icone("deletar.png","deletarId("+e.id+")","Remover");
				var botao2=icone("editar.png","pegarAlimento("+e.id+")","Editar");//     			var botao2="<input type='image' width='24px' src='"+caminho+"/assets/img/editar.png'/>";
        		$("#tabelaalimentoscadastro > tbody").append("<tr  id='"+e.id+"'><td>"+e.nome+"</td><td>"+e.porcao+"</td><td>"+e.caloria+"</td><td>"+e.proteina+"</td><td>"+e.carboidrato+"</td><td>"+e.gordura+"</td><td>"+botao+botao2+"</td></tr>")
    		
    		});
			limpar("form-user");

    		$("#paginacacadastroalimento").empty();

			paginarTabelas("paginacacadastroalimento",totalPaginas,paginaatual,"exibirElementos");

        		
        	}else {
	    		$("#tabelaalimentoscadastro").empty();
	    		$("#tabelaalimentoscadastro").append("<th>Nenhum alimento encontrado.</th>");


        	}
		});
	}
	
	
		function mandarFormulario(){
			var urlAction=document.getElementById("form-user").action;
			var id=$("#id").val();
			if ( id.length !=0 ){
				mensagemErro("mensagem","Este alimento já está cadastrado.")
				
			}
			else if (!verificarFormulario()){
				mensagemErro('mensagem','Preencha o formulario corretamente.')
			}
			else {
				var data="acao=novoalimento&nome="+$("#nome").val();
				data+="&";
		        data+="porcao="+$("#porcao").val();
				data+="&";
				data+="caloria="+$("#caloria").val();
				data+="&";
				data+="proteina="+$("#proteina").val();
				data+="&";
				data+="carboidrato="+$("#carboidrato").val();
				data+="&";
				data+="gordura="+$("#gordura").val();
				$.get(urlAction,data,function(response){
					if (response == "SUCESSO"){
						limpar('form-user');
						mensagemSucesso("mensagem","Alimento adicionado com sucesso !");
						exibirElementos(1);
					}else {
						mensagemErro("mensagem","Numero maximo de alimentos atingido.");
					}
					
				});
			}
			
		}
	
		
		function pegarAlimento(alimento){
			var linha=document.getElementById(alimento);
			document.getElementById("id").value=alimento;
			mensagemSucesso('mensagem','Edite o Alimento');
			document.getElementById("nome").value=linha.cells[0].innerHTML;
			document.getElementById("porcao").value=linha.cells[1].innerHTML;
			document.getElementById("caloria").value=linha.cells[2].innerHTML;
			document.getElementById("proteina").value=linha.cells[3].innerHTML;
			document.getElementById("carboidrato").value=linha.cells[4].innerHTML;
			document.getElementById("gordura").value=linha.cells[5].innerHTML;

			verificarFormulario();

		}
		
		function editarAlimento(){
			if (verificarFormulario()){
			var urlAction=document.getElementById("form-user").action;
			
			var data="acao=editar&nome="+$("#nome").val();
			data+="&";
	        data+="porcao="+$("#porcao").val();
	        data+="&";
	        data+="id="+$("#id").val();
			data+="&";
			data+="caloria="+$("#caloria").val();
			data+="&";
			data+="proteina="+$("#proteina").val();
			data+="&";
			data+="carboidrato="+$("#carboidrato").val();
			data+="&";
			data+="gordura="+$("#gordura").val();
			$.get(urlAction,data,function(response){
				
				var json=JSON.parse(response);
				console.log(json);
				if (json.length==0){
					mensagemSucesso('mensagem','Alimento Editado Com Sucesso');
					
				}
				else{
					
					
					var mensagem="<b>Não foi possivel realizar a ação por que o alimento está sendo usado.</b>";
				
					for (var i=0;i<json.length;i++){
						mensagem+=("<ul> Refeição : "+json[i][0]+"  ("+json[i][1]+"x)</ul>");
					}
					mensagemErro('mensagem',mensagem);
					
				}
			
				limpar('form-user');
			});
			
			
			}
		}
	
		function deletarId(id){
// 			document.getElementById("form-user").method = "get";
          if(confirm("Deseja remover  ?")){
        	  var urlAction = document.getElementById("form-user").action;

  			var data="acao=deletarid&paginaatual=1&idalimento="+id;
  			$.get(urlAction,data,function(response){
  				var json=JSON.parse(response);
  				if (json.length==0){
  					mensagemSucesso("mensagem","Alimento removido do cadastro.")
  	  				exibirElementos(1);

  				}else {
  					erroAcao(json);
  				}
  				
  			});
          }
			
		}
		
		
		
		function erroAcao(json){
			var mensagem="<b>Não foi possivel realizar a ação por que o alimento está sendo usado.</b>";
			
			for (var i=0;i<json.length;i++){
				mensagem+=("<ul> Refeição : "+json[i][0]+"  ("+json[i][1]+"x)</ul>");
			}
			mensagemErro('mensagem',mensagem);
		}
		
		
		
		
		
		
		
		
		
		
		
		
	
	</script>
</body>
</html>
