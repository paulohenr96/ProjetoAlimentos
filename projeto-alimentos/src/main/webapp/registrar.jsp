<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Register - SB Admin</title>
        <link href="assets/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="bg-primary">
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-7">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Create Account</h3></div>
                                    <div class="card-body">
                                        <form action="ServletCadastro" id="form-user" method="post">
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <div class="form-floating mb-3 mb-md-0">
                                                        <input class="form-control" required  name="nome" id="inputFirstName" type="text" placeholder="Enter your first name" />
                                                        <label for="inputFirstName" >First name</label>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-floating">
                                                        <input class="form-control"  name="sobrenome" required id="inputLastName" type="text" placeholder="Enter your last name" />
                                                        <label for="inputLastName">Last name</label>
                                                    </div>
                                                </div>
                                            </div>
                                             <div class="form-floating mb-3">
                                                <input class="form-control" name="login" required id="inputEmail" type="text" placeholder="login" />
                                                <label for="inputEmail" >Login</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="email" required id="inputEmail" type="email" placeholder="name@example.com" />
                                                <label for="inputEmail" >Email address</label>
                                            </div>
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <div class="form-floating mb-3 mb-md-0">
                                                        <input class="form-control" name="senha" required id="senha" type="password" placeholder="Create a password" />
                                                        <label for="senha" >Senha</label>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-floating mb-3 mb-md-0">
                                                        <input class="form-control" name="confirmasenha" required id="confirma-senha" type="password" placeholder="Confirm password" />
                                                        <label for="confirma-senha" >Confirm Password</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="mt-4 mb-0">
                                                <div class="d-grid"><button type="button" onclick="enviarFormulario()" class="btn btn-primary btn-block" href="login.html">Create Account</button></div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="card-footer text-center py-3">
                                        <div class="small"><a href="index.jsp">Entrar</a></div>
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
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="assets/js/scripts.js"></script>
        <script type="text/javascript">
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
        	var confirmaSenha=document.getElementById("confirma-senha");
        	
        	if (senha.value!=confirmaSenha.value){

        		erro++;
        		confirmaSenha.parentNode.innerHTML+="<span class=\"alerta\" style=\"color:red\">As senhas não coincidem.</span>";
        		return false;
        	}
        	return true;
        }
        function enviarFormulario(){
        	
        	if (verificarFormulario()){
        		document.getElementById("form-user").submit();
        	}
        }
        </script>
    </body>
</html>
