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
<style>
.table tr:hover {
	cursor: pointer;
}
</style>


</head>
<body class="sb-nav-fixed">
	<jsp:include page="header.jsp"></jsp:include>
	<div id="layoutSidenav">
		<jsp:include page="navbar.jsp" />
		<div id="layoutSidenav_content">






			<main>

				<div class="container-sm px-4" >

					<form method="post"
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
										type="number" min="0" step=".1" class="form-control" required
										id="porcao" name="porcao"
										placeholder="">
								</div>

								<div class="mb-2">
									<label for="caloria" class="form-label">Caloria</label> <input
										type="number" class="form-control" required id="caloria"
										name="caloria" placeholder="">
								</div>


								<div class="mb-2">
									<label for="proteina" class="form-label">Proteina</label> <input
										type="number" class="form-control" required id="proteina"
										name="proteina" placeholder="">
								</div>


								<div class="mb-2">
									<label for="carboidrato" class="form-label">Carboidrato</label>
									<input type="number" class="form-control" required
										id="carboidrato" name="carboidrato"
										placeholder="">

								</div>

								<div class="mb-2">
									<label for="gordura" class="form-label">Gordura</label> <input
										type="text" class="form-control" required id="gordura"
										name="gordura" placeholder="">

								</div>
							</div>
						</div>
						<button type="button" onclick="mandarFormulario()"
							class="btn btn-success">Adicionar</button>

						<button type="button" onclick="mostrarTodosAlimentos()"
							class="btn btn-primary">Mostrar Todos</button>
						<button type="button" onclick="editarAlimento()"
							class="btn btn-secondary">Editar</button>


					</form>
					<c:if test="${not empty msg }">
						<div style="margin-top: 10px" class="alert alert-info"
							role="alert">${msg}</div>

					</c:if>

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
									<th>Remover</th>

								</tr>

							</thead>
							<tbody>
								<c:forEach items="${todos}" var="ali">
									<tr id="${ali.id}" onclick="pegarAlimento(${ali.id})">
										<td>${ali.id}</td>

										<td>${ali.nome}</td>
										<td>${ali.porcao}</td>
										<td>${ali.caloria }</td>
										<td>${ali.proteina}</td>
										<td>${ali.carboidrato}</td>
										<td>${ali.gordura}</td>
										<td><button type="button" onclick="deletarId(${ali.id})"
												class="btn btn-danger btn-sm">Excluir</button></td>
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
									href="<%=request.getContextPath()%>/ServletAlimento?acao=mostrartodosalimentospaginados&paginaatual=${paginaatual-1}"
									class="page-link">Previous</a>

								</li>

								<c:forEach begin="1" end="${totalpaginas}" step="1" var="p">
									<c:if test="${p==paginaatual}">
										<li class="page-item"><a class="page-link"
											autofocus="autofocus"
											href="<%=request.getContextPath()%>/ServletAlimento?acao=mostrartodosalimentospaginados&paginaatual=${p}">${p}</a></li>
									</c:if>

									<c:if test="${p!=paginaatual}">
										<li class="page-item"><a class="page-link"
											href="<%=request.getContextPath()%>/ServletAlimento?acao=mostrartodosalimentospaginados&paginaatual=${p}">${p}</a></li>
									</c:if>
								</c:forEach>
								<c:if test="${totalpaginas!=paginaatual}">
									<li class="page-item">
								</c:if>
								<c:if test="${totalpaginas==paginaatual}">
									<li class="page-item disabled">
								</c:if>
								<a class="page-link"
									href="<%=request.getContextPath()%>/ServletAlimento?acao=mostrartodosalimentospaginados&paginaatual=${paginaatual+1}">Next</a>

								</li>
							</ul>
						</nav>
					</c:if>
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
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>

	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script type="text/javascript">
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
	
		function mandarFormulario(){
			
			if (verificarFormulario()){
				document.getElementById("form-user").submit();

			}
			
		}
		function mostrarTodosAlimentos() {
			document.getElementById("form-user").method = "get";
			var urlAction = document.getElementById("form-user").action;

			window.location.href = urlAction + "?acao=mostrartodosalimentospaginados&paginaatual=1";

		}
		
		function pegarAlimento(alimento){
			var linha=document.getElementById(alimento);
			document.getElementById("id").value=alimento;

			document.getElementById("nome").value=linha.cells[1].innerHTML;
			document.getElementById("porcao").value=linha.cells[2].innerHTML;
			document.getElementById("caloria").value=linha.cells[3].innerHTML;
			document.getElementById("proteina").value=linha.cells[4].innerHTML;
			document.getElementById("carboidrato").value=linha.cells[5].innerHTML;
			document.getElementById("gordura").value=linha.cells[6].innerHTML;

			
		}
		
		function editarAlimento(){
			if (verificarFormulario()){
			document.getElementById("form-user").method = "get";
			document.getElementById("acao").value = "editar";
			document.getElementById("form-user").submit();
			}
			
		}
		function deletarId(id){
			document.getElementById("form-user").method = "get";
			var urlAction = document.getElementById("form-user").action;

			window.location.href = urlAction + "?acao=deletarId&paginaatual=1&idalimento="+id;

		}
		
		
	</script>
</body>
</html>
