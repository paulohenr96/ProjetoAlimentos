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


					<form id="form-user"
						action="<%=request.getContextPath()%>/ServletUsuario"
						method="post" enctype="multipart/form-data">
						<div class="divfoto">
	<c:if test="${empty user.extensaoFoto}">
				<img alt="imagem user" id="fotoBase64"
								src="<%=request.getContextPath()%>/assets/img/user-1.png"
								width="70px"> <input name="imagem" style="display: none;"
								accept="img/" id="fileFoto" class="form-control"
								class="custom-file-input" type="file">
			</c:if>
			<c:if test="${not empty user.extensaoFoto}">
			
			<img alt="imagem user" id="fotoBase64"
								src="data:image/${user.extensaoFoto};base64,${user.foto}"
								width="70px"> <input name="imagem" style="display: none;"
								accept="img/" id="fileFoto" class="form-control"
								class="custom-file-input" type="file">
			</c:if>

							

						</div>

						<div class="mb-2 col-2">
							<label for="id">ID</label> <input type="text"
								class="form-control" name="id" id="id" value="${user.id}"
								readonly>
						</div>
						<div class="form-group col-4 mb-2 editavel">
							<label for="nome">Nome</label> <input type="text"
								class="form-control" id="nome" name="nome" value="${user.nome}"
								readonly>
						</div>
						<div class="form-group col-4 mb-2 editavel">
							<label for="sobrenome">Sobrenome</label> <input type="text"
								class="form-control" id="sobrenome" name="sobrenome"
								value="${user.sobreNome}" readonly>
						</div>
						<div class="form-group col-4 mb-2 editavel">
							<label for="email">Email</label> <input type="email"
								class="form-control" id="email" name="email"
								value="${user.email}" readonly>
						</div>
						<div class="form-group editavel col-4 mb-2">
							<label for="login">Login</label> <input type="text"
								class="form-control" id="login" name="login"
								value="${user.login}" readonly>
						</div>



					</form>


					<br />

					<p>${msg_atualiza_perfil}</p>
					<button type="button" id="botaoeditar"
						class="btn btn-primary btn-editar" onclick="editarPerfil()">Editar</button>
					<button type="button" id="botaosalvar" class="btn btn-success"
						style="display: none;" onclick="salvar()">Salvar</button>
					<button data-toggle="modal" data-target="#modalsenha" type="button"
						class="btn btn-primary">Alterar Senha</button>


				</div>
			</main>





			<div class="modal" id="modalsenha" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-content ">
						<div class="modal-header">
							<h5 class="modal-title">Altere a senha</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">

							<form id="form-senha"
								action="<%=request.getContextPath()%>/ServletSenha">
								<div class="form-group">
									<label for="senhaantiga" class="col-form-label">Senha
										Antiga:</label> <input type="password" class="form-control"
										id="senhaantiga">
								</div>

								<div class="form-group">
									<label for="senhanova" class="col-form-label">Nova
										Senha:</label> <input type="password" class="form-control"
										id="senhanova">
								</div>

								<div class="form-group">
									<label for="confirmasenha" class="col-form-label">Confirma
										Senha:</label> <input type="password" class="form-control"
										id="confirmasenha">
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								onclick="alterarsenha()">Salvar</button>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Cancelar</button>
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
		function editarPerfil() {

			$(".editavel > input").attr("readonly", false);
			$(".btn-editar").remove();
			$("#fileFoto").show();
			$("#botaosalvar").show();

		}

		function salvar() {
			$("#form-user").submit();
			// 			var urlAction = document.getElementById("form-user").action;
			// 			var data = "nome=" + $('#nome').val();
			// 			data += "&";
			// 			data += "sobrenome=" + $('#sobrenome').val();
			// 			data += "&";
			// 			data += "email=" + $('#email').val();
			// 			data += "&";
			// 			data += "login=" + $('#login').val();
			// 			data += "&";
			// 			data += "imagem=" + $('#fileFoto').val();
			// 			$.post(urlAction, data, function(response) {
			// 				if (response === 'ERRO') {
			// 					alert(response);

			// 				} else {

			// 					location.reload(true);

			// 				}
			// 			});

		}

		function alterarsenha() {
			var urlAction = document.getElementById("form-senha").action;
			var data = "senha=" + $('#senha').val();
			data += "&";
			data += "senhanova=" + $('#senhanova').val();
			data += "&";
			data += "confirmasenha=" + $('#confirmasenha').val();

			$.post(urlAction, data, function(response) {
				if (response === 'ERRO') {
					alert(response);

				} else {
					$('#form-senha').each(function() {
						this.reset();
					});

					location.reload(true);

				}
			});
		}
	</script>
</body>
</html>
