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



</head>
<body class="sb-nav-fixed">
	<jsp:include page="header.jsp"></jsp:include>
	<div id="layoutSidenav">
		<jsp:include page="navbar.jsp" />
		<div id="layoutSidenav_content">






			<main>

				<div class="container-sm" style="width: 80%">
					<form method="post"
						action="<%=request.getContextPath()%>/ServletAlimento"
						id="form-user">
						<input type="hidden" id="acao" name="acao" value="">

						<h1>Alimentos</h1>

						<div class="mb-2">
							<label for="formGroupExampleInput" class="form-label">Nome</label>
							<input type="text" class="form-control"
								id="formGroupExampleInput" name="nome"
								placeholder="Example input placeholder">
						</div>
						<div class="mb-2">
							<label for="formGroupExampleInput2" class="form-label">Porção</label>
							<input type="text" class="form-control" required
								id="formGroupExampleInput2" name="porcao"
								placeholder="Another input placeholder">
						</div>
						<div class="mb-2">
							<label for="formGroupExampleInput2" class="form-label">Caloria</label>
							<input type="text" class="form-control" required
								id="formGroupExampleInput2" name="caloria"
								placeholder="Another input placeholder">
						</div>
						<div class="mb-2">
							<label for="formGroupExampleInput2" class="form-label">Proteina</label>
							<input type="text" class="form-control" required
								id="formGroupExampleInput2" name="proteina"
								placeholder="Another input placeholder">
						</div>
						<div class="mb-2">
							<label for="formGroupExampleInput2" class="form-label">Carboidrato</label>
							<input type="text" class="form-control" required
								id="formGroupExampleInput2" name="carboidrato"
								placeholder="Another input placeholder">
						</div>
						<div class="mb-2">
							<label for="formGroupExampleInput2" class="form-label">Gordura</label>
							<input type="text" class="form-control" required
								id="formGroupExampleInput2" name="gordura"
								placeholder="Another input placeholder">
						</div>
						<button type="submit" class="btn btn-success">Adicionar</button>
						<button type="button" onclick="mostrarTodosAlimentos()"
							class="btn btn-primary">Mostrar Todos</button>

					</form>
					<!-- 					<div class="card mb-4"> -->
					<!-- 						<div class="card-header"> -->
					<!-- 							<i class="fas fa-table me-1"></i> DataTable Example -->
					<!-- 						</div> -->
					<!-- 						<div class="card-body"> -->
					<!-- 							<table id="datatablesSimple"> -->
					<!-- 								<thead> -->
					<!-- 									<tr> -->
					<!-- 										<th>Nome</th> -->
					<!-- 										<th>Porcao</th> -->
					<!-- 										<th>Calorias</th> -->
					<!-- 										<th>P</th> -->
					<!-- 										<th>C</th> -->
					<!-- 										<th>G</th> -->
					<!-- 									</tr> -->

					<!-- 								</thead> -->

					<!-- 								<tbody> -->
					<%-- 									<c:forEach items="${todos}" var="ali"> --%>
					<%-- 										<tr  onclick="pegarAlimento(${ali.id})" > --%>
					<%-- 											<td>${ali.nome}</td> --%>
					<%-- 											<td>${ali.porcao}</td> --%>
					<%-- 											<td>${ali.caloria }</td> --%>
					<%-- 											<td>${ali.proteina}</td> --%>
					<%-- 											<td>${ali.carboidrato}</td> --%>
					<%-- 											<td>${ali.gordura}</td> --%>
					<!-- 										</tr> -->
					<%-- 									</c:forEach> --%>
					<!-- 								</tbody> -->
					<!-- 							</table> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
			<c:if test="${not empty todos}">
					<table class="table table-hover">
						<thead>
							<tr>

								<th>Nome</th>
								<th>Porcao</th>
								<th>Calorias</th>
								<th>P</th>
								<th>C</th>
								<th>G</th>

							</tr>

						</thead>
						<tbody>
							<c:forEach items="${todos}" var="ali">
								<tr onclick="pegarAlimento(${ali.id})">
									<td>${ali.nome}</td>
									<td>${ali.porcao}</td>
									<td>${ali.caloria }</td>
									<td>${ali.proteina}</td>
									<td>${ali.carboidrato}</td>
									<td>${ali.gordura}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<nav aria-label="Page navigation example">
						<ul class="pagination justify-content-end">
							<li class="page-item disabled"><a class="page-link">Previous</a>
							</li>
							<c:forEach begin="1" end="${totalpaginas}" step="1" var="p">
								<c:if test="${p==paginaatual}">
								<li class="page-item"><a class="page-link" autofocus="autofocus" href="<%=request.getContextPath()%>/ServletAlimento?acao=mostrartodosalimentospaginados&paginaatual=${p}">${p}</a></li>
								</c:if>
								
								<c:if test="${p!=paginaatual}">
								<li class="page-item"><a class="page-link"  href="<%=request.getContextPath()%>/ServletAlimento?acao=mostrartodosalimentospaginados&paginaatual=${p}">${p}</a></li>
								</c:if>
							</c:forEach>
							<li class="page-item"><a class="page-link" href="#">Next</a>
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
		function mostrarTodosAlimentos() {
			document.getElementById("form-user").method = "get";
			var urlAction = document.getElementById("form-user").action;

			window.location.href = urlAction + "?acao=mostrartodosalimentospaginados&paginaatual=1";

		}
		
		function pegarAlimento(alimento){
			alert(alimento);
		}
	</script>
</body>
</html>
