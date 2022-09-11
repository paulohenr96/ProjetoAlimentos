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
<link href="<%=request.getContextPath()%>/assets/css/styles.css" rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
	crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
	<jsp:include page="header.jsp"></jsp:include>
	<div id="layoutSidenav">
		<jsp:include page="navbar.jsp" />
		<div id="layoutSidenav_content">






			<main>

				<div class="container-sm" style="width: 80%">
					<form method="post" action="<%=request.getContextPath()%>/ServletAlimento" id="form-user">
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
						<button type="submit" class="btn btn-success"
							>Adicionar</button>
						<button type="button" onclick="mostrarTodosAlimentos()" class="btn btn-primary">Mostrar Todos</button>
						
					</form>

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
			var urlAction=document.getElementById("form-user").action;

			window.location.href = urlAction + "?acao=mostrartodosalimentos";

		
		}
	</script>
</body>
</html>
