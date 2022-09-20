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
        <title>Sidenav Light - SB Admin</title>
        <link href="<%=request.getContextPath() %>/assets/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
      	<jsp:include page="header.jsp"></jsp:include>
        <div id="layoutSidenav">
         <jsp:include page="navbar.jsp"/>   
            <div id="layoutSidenav_content">
            
            
            
            
            
            
                <main>
                    <div class="container-fluid px-4">
                        <h1>Seja Bem-Vindo ao meu Projeto !</h1>
                        
                        
                        
                        <form method="get"
						action="<%=request.getContextPath()%>/ServletAlimento"
						id="form-user">
                        <input type="hidden" name="acao" value="" id="acaoRelatorioImprimirTipo">
                        <button type="button" onclick="imprimirHtml()" class="btn btn-dark">IMPRIMIR</button>
                        
                        </form>
                    </div>
                </main>
                
               
                
                
                
                
                
                
                
                
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2022</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        
        	<jsp:include page="javascript-files.jsp"></jsp:include>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<%=request.getContextPath() %>/assets/js/scripts.js"></script>
    
    <script type="text/javascript">
    
    function imprimirHtml(){

    	document.getElementById("acaoRelatorioImprimirTipo").value="ImprimirRelatorioMacrosPDF";
    	$("#form-user").submit();
    	return false;
    }
    </script>
    
    </body>
</html>
