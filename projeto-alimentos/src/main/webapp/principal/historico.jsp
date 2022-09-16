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
                        
                        
                        
                        
                        
                        
                                          <table class="table table-striped-columns">
  				<thead><tr><th>Data</th><th>Calorias</th><th>Proteina</th><th>Gordura</th><th>Carboidrato</th></tr></thead>
  
  					<c:forEach items="${lista}" var="macros">
  					<tr>
  					<td>${macros.data}</td>
  					<td>${macros.calorias}</td>
  					<td>${macros.proteinas}</td>
  					<td>${macros.gordura}</td>
  					  					<td>${macros.carboidrato}</td>
  					
  					
  					</tr>
  					
  					</c:forEach>
					</table>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<%=request.getContextPath() %>/assets/js/scripts.js"></script>
    </body>
</html>
