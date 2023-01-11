/*!
	* Start Bootstrap - SB Admin v7.0.5 (https://startbootstrap.com/template/sb-admin)
	* Copyright 2013-2022 Start Bootstrap
	* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
	*/
// 
// Scripts
// 
var spinner_azul="<div class=\"spinner-border text-primary\" role=\"status\">"+
			  "<span class=\"sr-only\">Carregando...</span>"+
			  "</div>";
function mensagemSucesso(id, mensagem) {
	$("#" + id).empty();
	$("#" + id).removeClass();

	$("#" + id).addClass("alert alert-info")


	$("#" + id).show();
	$("#" + id).html(mensagem);

}
function mensagemErro(id, mensagem) {
	$("#" + id).empty();

	$("#" + id).empty();
	$("#" + id).removeClass();
	$("#" + id).addClass("alert alert-danger")

	$("#" + id).html(mensagem);
	$("#" + id).show();


}


function icone(nome, funcao, tooltip) {

	var caminho=getContextPath()+"/assets/img/"+nome;
	if (tooltip == null) {
		tooltip = "";
	}

	return "<image  class='icones' onclick='" + funcao + "' width='24px' src='" + caminho + "' data-toggle='tooltip' data-placement='top' title=" + tooltip + ">";
}
function getContextPath() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
window.addEventListener('DOMContentLoaded', event => {

	// Toggle the side navigation
	const sidebarToggle = document.body.querySelector('#sidebarToggle');
	if (sidebarToggle) {
		// Uncomment Below to persist sidebar toggle between refreshes
		// if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
		//     document.body.classList.toggle('sb-sidenav-toggled');
		// }
		sidebarToggle.addEventListener('click', event => {
			event.preventDefault();
			document.body.classList.toggle('sb-sidenav-toggled');
			localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
		});
	}

});


function limpar(id) {
	$('#' + id).each(function() {
		this.reset();
	});
}


function normalizar(str) {
	return str.normalize('NFD').replace(/\p{Mn}/gu, "");

}


function paginarTabelas(id, totalPaginas, paginaatual, funcao) {
	$("#" + id).empty();

	if (totalPaginas == 1) {


		return;
	}
	var previous = "";
	var next = "";
	if (!document.getElementById(id).classList.contains('justify-content-center')) {
		document.getElementById(id).classList.add("justify-content-center");

	}
	if (totalPaginas > 1) {
		previous = "<li class=\"page-item\" onclick=" + funcao + "(" + (paginaatual - 1) + ")><a class=\"page-link\"  ><</a></li>";
		next = "<li class=\"page-item\" onclick=" + funcao + "(" + (paginaatual + 1) + ")><a class=\"page-link\"  >></a></li>";

		if (paginaatual == 1) {
			previous = "<li class=\"page-item disabled\" ><a class=\"page-link\"  ><</a></li>";

		}
		if (paginaatual == totalPaginas) {
			next = "<li class=\"page-item disabled\" ><a class=\"page-link\"  >></a></li>";

		}

	}
	document.getElementById(id).innerHTML = previous;




	for (var i = 0; i < totalPaginas; i++) {




		if (paginaatual == (i + 1)) {
			document.getElementById(id).innerHTML += "<li onclick=" + funcao + "(" + (i + 1) + ") class=\"page-item active\"><a class=\"page-link\" >" + (i + 1) + "</a></li>"


		} else {
			document.getElementById(id).innerHTML += "<li onclick=" + funcao + "(" + (i + 1) + ") class=\"page-item \"><a class=\"page-link\" >" + (i + 1) + "</a></li>"

		}





	}

	document.getElementById(id).innerHTML += next;

}