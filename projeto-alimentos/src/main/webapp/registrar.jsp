<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Registrar</title>
<link href="assets/css/styles.css" rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
	crossorigin="anonymous"></script>
<style>
body {
	font-family: "Times New Roman", Times, serif;
}
</style>
</head>
<body class="bg-primary">
	<div id="layoutAuthentication">
		<div id="layoutAuthentication_content">
			<main>
				<div class="container">
					<div class="row justify-content-center">
						<div class="col-lg-7">
							<div class="card shadow-lg border-0 rounded-lg mt-5">
								<div class="card-header">
									<h3 class="text-center font-weight-light my-4">Crie uma
										Conta</h3>
								</div>
								<div class="card-body">
									<form action="ServletCadastro" id="form-cadastro" method="post">
										<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-floating mb-3 mb-md-0">
													<input class="form-control" required name="nome"
														id="inputFirstName" type="text"
														placeholder="Enter your first name" /> <label
														for="inputFirstName">First name</label>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-floating">
													<input class="form-control" name="sobrenome" required
														id="inputLastName" type="text"
														placeholder="Enter your last name" /> <label
														for="inputLastName">Last name</label>
												</div>
											</div>
										</div>
										<div class="form-floating mb-3" id="divlogin">
											<input onblur="pesquisalogin()" class="form-control"
												name="login" required id="inputLogin" type="text"
												placeholder="login" /> <label for="inputLogin">Login</label>
												<span  id="userinvalido" style="display:none;color:red">Usuário Invalido</span>
											
										</div>
									
										<div class="form-floating mb-3">
											<input class="form-control" name="email" required
												id="inputEmail" type="email" placeholder="name@example.com" />
											<label for="inputEmail">Email address</label>
										</div>
										<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-floating mb-3 mb-md-0">
													<input class="form-control" name="senha" required
														id="senha" type="password" placeholder="Senha " /> <label
														for="senha">Senha</label>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-floating mb-3 mb-md-0">
													<input class="form-control" name="confirmasenha" required
														id="confirmasenha" type="password"
														placeholder="Confirmar Senha" /> <label
														for="confirmasenha">Confirmar Senha</label>
												</div>
											</div>
										</div>
										<div class="alert alert-info" style="display: none;"
											id="mensagem" role="alert"></div>
										<div class="mt-4 mb-0">
											<div class="d-grid">
												<button type="button" onclick="enviarFormulario()"
													class="btn btn-primary btn-block" id="botaoenviar" href="login.html">Criar
													Conta</button>
											</div>
										</div>
									</form>
								</div>
								<div class="card-footer text-center py-3">
									<div class="small">
										<a href="index.jsp">Entrar</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
	<div id="layoutAuthentication_footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	<script src="<%=request.getContextPath()%>/assets/js/scripts.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/js/jquery/jquery.min.js"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="assets/js/scripts.js"></script>
	<script type="text/javascript">
	function pesquisalogin(){
		var urlAction = document.getElementById("form-cadastro").action;
		var data = "login="+$('#inputLogin').val();
		
		$.get(urlAction,data, function(response){ 
		    if (response==='ERRO'){
//     			$('#inputLogin').html("<span class=\"alerta\" style=\"color:red\">Usuário Invalido</span>");	
				$('#userinvalido' ).show();
	            $("#botaoenviar").prop('disabled', true);
				$('#divlogin > .alerta' ).hide();

		    }else {
				$('#userinvalido' ).hide();
				$('#divlogin > .alerta' ).hide();

	            $("#botaoenviar").prop('disabled', false);

		    }
		});


	}
	
	
	
        function verificarFormulario(){
        	var entradas=document.querySelectorAll(".form-floating");
        	var erro=0;
        	entradas.forEach((e)=>{
        		if (e.querySelector(".alerta")!=null){
            		e.querySelector(".alerta").remove();

        		}
        		if (e.querySelector("input").value==""){
        			erro++;
        			e.innerHTML+="<span class=\"alerta\" style=\"color:red\">Campo Vazio</span>";	
        		}
        		
        	});
        	if (erro!=0){
        		return false;
        	}
        	var senha=document.getElementById("senha");
        	var confirmaSenha=document.getElementById("confirmasenha");
        	
        	if (senha.value!=confirmaSenha.value){

        		erro++;
        		confirmaSenha.parentNode.innerHTML+="<span class=\"alerta\" style=\"color:red\">As senhas não coincidem.</span>";
        		return false;
        	}
        	return true;
        }
        function enviarFormulario(){
        	
        	if (verificarFormulario()){
    			ajaxFormulario();
        	}

        }
        
        
    	function ajaxFormulario() {
			
			var urlAction = document.getElementById("form-cadastro").action;
			var data="nome="+$('#inputFirstName').val();
			data+="&";
			data+="sobrenome="+$('#inputLastName').val();
			data+="&";
			data+="login="+$('#inputLogin').val();
			data+="&";
			data+="email="+$('#inputEmail').val();
			data+="&";
			data+="senha="+$('#senha').val();
			data+="&";
			data+="confirmasenha="+$('#confirmasenha').val();
			
			
			$.post(urlAction,data, function(response){ 
			      if (response==='SUCESSO'){
				      alert('Cadastro bem sucedido');
				      $('#form-cadastro').each (function(){
				    	  this.reset();
				    	});
				     mensagemSucesso("mensagem","Cadastro efetuado com sucesso.");
			      }
			      else {
				      $('#mensagem').show();

				      $('#mensagem').html(response);
					     mensagemErro("mensagem",response);


			      }
			});


		}
        
        </script>
</body>
</html>
