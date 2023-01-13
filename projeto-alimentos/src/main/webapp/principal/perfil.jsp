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
	<input type="hidden" value="nao" id="temfoto"/>
				<img alt="imagem user" id="fotoBase64"
								src="<%=request.getContextPath()%>/assets/img/user-1.png"
								width="70px"> 
			</c:if>
			<c:if test="${not empty user.extensaoFoto}">
			<input type="hidden" value="sim" id="temfoto"/>
			<img alt="imagem user" id="fotoBase64"
								src="data:image/${user.extensaoFoto};base64,${user.foto}"
								width="70px"> 
			</c:if>

							<input name="imagem" style="display: none;"
								accept="img/" id="fileFoto" class="form-control edicao"
								class="custom-file-input" type="file" onchange="visualizarImage('fotoBase64', 'fileFoto')">

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

					<button type="button" id="botaoeditar"
						class="btn btn-primary btn-editar" onclick="editarPerfil()">Editar</button>
					<button type="button" id="botaosalvar" class="btn btn-success edicao"
						style="display: none;" onclick="salvar()">Salvar</button>
					<button data-toggle="modal" data-target="#modalsenha" id="botaoalterarsenha" type="button"
						class="btn btn-primary ">Alterar Senha</button>
						
						<button  style="display:none;" onclick="colocaFotoPadrao()" id="botaoremoverfoto" type="button"
						class="btn btn-danger edicao">Remover Foto</button>

<button   id="botaocancelar" type="button"
						class="btn btn-primary edicao" onclick="location.reload(true);" style="display:none;">Cancelar</button>
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
										Antiga:</label> <input required type="password" class="form-control"
										id="senhaantiga">
								</div>

								<div class="form-group">
									<label for="senhanova" class="col-form-label">Nova
										Senha:</label> <input required type="password" class="form-control"
										id="senhanova">
								</div>

								<div class="form-group">
									<label for="confirmasenha" required class="col-form-label">Confirma
										Senha:</label> <input type="password" class="form-control"
										id="confirmasenha">
								</div>
							</form>
							<br><br>
							<div id="alerta_senha"></div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								onclick="alterarsenha()">Salvar</button>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal" onclick="$('#alerta_senha').hide()">Cancelar</button>
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
		checarFoto();
		function checarFoto(){
			if (document.getElementById("temfoto").value=='sim'){
			}
			else if (document.getElementById("temfoto").value=='nao'){
				$("#botaoremoverfoto").addClass('disabled');
			}
		}
		function editarPerfil() {

			$(".editavel > input").attr("readonly", false);
			$(".btn-editar").remove();
// 			$("#fileFoto").show();
// 			$("#botaosalvar").show();
// 			$("#botaoremoverfoto").show();
// 			$("#botaoalterarsenha").show();
// 			$("#botaocancelar").show();
			$(".edicao").show();


		}

		function salvar() {
			if (removerfoto){
				removerFoto();
			}
			
			$("#form-user").submit();
			
		}
		
		function alterarsenha() {
			if (!verificarFormulario()){
				return;
			}
			var urlAction = document.getElementById("form-senha").action;
			var data = "senhaantiga=" + $('#senhaantiga').val();
			data += "&";
			data += "senhanova=" + $('#senhanova').val();
			data += "&";
			data += "confirmasenha=" + $('#confirmasenha').val();

			$.post(urlAction, data, function(response) {
				
				if (response==1) {
					mensagemSucesso("alerta_senha","Senha alterada com sucesso.")					
// 					location.reload(true);
					limpar('form-senha');

				}
				else {
					mensagemErro("alerta_senha",response);
				}
			});
		}
		
		function verificarFormulario(){
			if ($('#senhaantiga').val().length==0){
				mensagemErro("alerta_senha","Senha antiga vazia.");
				return false;
			}
			if ($('#senhanova').val().length==0){
				mensagemErro("alerta_senha","Insira a nova senha.");
				return false;
			}
			if ($('#confirmasenha').val().length==0){
				mensagemErro("alerta_senha","Confirme a senha.");
				return false;
			}
			return true;
		}
		function visualizarImage(foto, file) {
			var preview = document.getElementById(foto);
			var file = document.getElementById(file).files[0];

			var reader = new FileReader();
			reader.onloadend = function() {
				preview.src = reader.result;

			};

			if (file) {
				reader.readAsDataURL(file);
			} else {
				preview.src = '';
			}
		}
		
		var removerfoto=false;
		function removerFoto(){
			
			var id=document.getElementById("id").value;
			var urlAction=document.getElementById("form-user").action;
			
			$.get(urlAction,"acao=removerimagem",function(response){
				var preview = document.getElementById('fotoBase64');
				preview.src=getContextPath()+"/assets/img/user-1.png";

			})
		}
		
		function colocaFotoPadrao(){
			
			removerfoto=true;
			var preview = document.getElementById('fotoBase64');

			preview.src=getContextPath()+"/assets/img/user-1.png";

		}
	</script>
</body>
</html>
