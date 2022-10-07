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
<title>Perfil</title>
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
					<h2>Perfil</h2>


					<form id="form-user" action="<%=request.getContextPath() %>/ServletUsuario" method="post">

						<div class="mb-2 col-2">
							<label for="id">ID</label> <input type="text"
								class="form-control" name="id" id="id" value="${user.id}" readonly>
						</div>
						<div class="form-group col-4 mb-2">
							<label for="nome">Nome</label> <input type="text"
								class="form-control" id="nome" name="nome"  value="${user.nome}" readonly>
						</div>
						<div class="form-group col-4 mb-2">
							<label for="sobrenome">Sobrenome</label> <input type="text"
								class="form-control" id="sobrenome" name="sobrenome"  value="${user.sobreNome}" readonly>
						</div>
						<div class="form-group col-4 mb-2">
							<label for="email">Email</label> <input type="email"
								class="form-control" id="email" name="email"  value="${user.email}" readonly>
						</div>
						<div class="form-group col-4 mb-2">
							<label for="login">Login</label> <input type="text"
								class="form-control" id="login" name="login"  value="${user.login}" readonly>
						</div>
						
						<div class="form-group col-4 mb-2">
							<label for="senha">Senha</label> <input type="password"
								class="form-control" id="senha" name="senha"  value="${user.senha}" readonly>
						</div>
						
						
					</form>
											<button type="button" class="btn btn-primary" onclick="editarPerfil()">Editar</button>
					

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

	<jsp:include page="javascript-files.jsp"></jsp:include>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>
	
	<script type="text/javascript">
	
	function editarPerfil(){

		$(".form-group > input").attr("readonly",false);
		$("button").remove();
		$("main > div.container-fluid").append($("<button type=\"button\" class=\"btn btn-success\" onclick=\"salvar()\">Salvar</button>"));
	}
	
	function salvar(){
		
		$("#form-user").submit();
		return false;
	
	}
	</script>
</body>
</html>
