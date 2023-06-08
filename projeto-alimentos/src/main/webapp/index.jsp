<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Login - SB Admin</title>
        <link href="<%=request.getContextPath() %>/assets/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="bg-primary">
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-5">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Login</h3></div>
                                    <div class="card-body">
                                        <form action="<%=request.getContextPath()%>/ServletLogin" id="form-user" method="post">
                                            
                                            <div class="form-floating mb-3">
                                                <input class="form-control" id="usuario" type="text" name="login" placeholder="Insira o usuário" />
                                                <label for="usuario">Usuário</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="senha" id="senha" type="password" placeholder="Password" /> <label
												for="inputPassword">Senha</label>
										</div>
                                            <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                                <button type="button" onclick="enviarFormulario()" class="btn btn-primary">Entrar</button>
                                            </div>
                                            <c:if test="${not empty msg }">
                                            
                                            <br>
                                            <div class="alert alert-danger" id="alerta_cadastro" role="alert">
												${msg}											
											</div>
											</c:if>
                                           <input type="hidden" value="<%=request.getParameter("url")%>"
			name="url">
                                        </form>
                                    </div>
                                    <div class="card-footer text-center py-3">
                                        <div class="small"><a href="<%=request.getContextPath()%>/registrar.jsp">Não possui uma conta ? Cadastre-se !</a></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
                                       <jsp:include page="footer.jsp"></jsp:include>
            
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<%=request.getContextPath() %>/assets/js/scripts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery-ui/jquery-ui.min.js "></script>
        
        <script type="text/javascript">
        
        function enviarFormulario(){
        	var entradas=document.querySelectorAll(".form-floating");
        	var erro=0;
			entradas.forEach((e)=>{
				if (e.querySelector(".alerta")!=null){
					e.querySelector(".alerta").remove();
				}
				if (e.querySelector("input").value==""){
					e.innerHTML+="<div class=\"alerta\"><span style=\"color:red;\">Campo vazio.</span></div>";
				
					erro++;
				}
			})
			mensagemErro("alerta_cadastro","Preencha todos os campos.");
			if (erro==0){
				document.querySelector("button").innerHTML="<span class=\"spinner-border spinner-border-sm\" role=\"status\" aria-hidden=\"true\"></span>Entrando...";
				$("button").addClass("disabled");
				document.getElementById("form-user").submit();
			}
        }
        
        
        </script>
    </body>
</html>
