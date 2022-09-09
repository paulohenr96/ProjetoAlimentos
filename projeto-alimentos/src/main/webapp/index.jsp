<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">

<title>Bem-Vindo</title>
<style>
 .formulario-login{
 	position:absolute;
 	left: 30%;
 	top:10%;
 	padding:2%;
 	width:500px;
 	border:black 2px solid;
 	border-radius: 10%;
 	
 }
</style>
</head>
<body>

<div class="formulario-login">
<form action="ServletLogin" method="post">
  <div class="row mb-3">
    <label for="inputEmail3" class="col-sm-2 col-form-label">Login</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="login" id="inputEmail1">
    </div>
  </div>
  <div class="row mb-3">
    <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
    <div class="col-sm-10">
      <input type="password" class="form-control" name="senha" id="inputPassword1">
    </div>
  </div>
  
  <button type="submit" class="btn btn-primary">Entrar</button>
</form>

</div>







<jsp:include page="principal/scripts.jsp"/>
</body>
</html>