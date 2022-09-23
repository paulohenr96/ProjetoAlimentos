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
<title>Sidenav Light - SB Admin</title>
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
					<h1>Seja Bem-Vindo ao meu Projeto !</h1>
					<div class="form-floating mb-3">
						<input class="form-control" name="nome" required id="nome"
							type="text" placeholder="Nome da refeição" /> <label for="nome">Nome</label>
					</div>
					
					<div class="alimentos"></div>
					
					
					<div class="mt-4 mb-0">
						<div class="d-grid">
							<button type="button" onclick="novaRefeicao()" class="btn btn-primary btn-block">Nova
								Refeição</button>
						</div>
					</div>
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
	
	function novoAlimento(){
		document.querySelector("div.alimentos").innerHTML+="<br/>";
		document.querySelector("div.alimentos").innerHTML+="<div class=\"row\">  <div class=\"col\">  <input type=\"text\" class=\"form-control\" placeholder=\"First name\" aria-label=\"First name\">	  </div>  <div class=\"col\">   <input type=\"text\" class=\"form-control\" placeholder=\"Last name\" aria-label=\"Last name\">	  </div>	</div>";
		
	}
	function novaRefeicao(){
		var nome=document.querySelector("#nome").value;
		$.ajax({
			method:"GET",
			url:window.location.pathname.substring(0, window.location.pathname.indexOf("/",2))+ "/ServletAlimento",
			data:"acao=novarefeicao&nome="+nome,
			success:function(response){
				alert(response);	
			}
			
			
		}).fail(function (xhr, status, errorThrown) {
		      alert("Error ao buscar usuário por nome" + xhr.responseText);
	    });
		
		
	}
	
	</script>
</body>
</html>
