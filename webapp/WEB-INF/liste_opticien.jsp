<!DOCTYPE html>
<html lang="en">
<head>
<title>Ajouter une facture</title>
<meta charset="UTF-8" />
<link href="style/css/bootstrap.min.css" rel="stylesheet" />
<link href="style/style.css" rel="stylesheet" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>

	<div class="container-fluid">
		<c:import url="/jsp_include/menu.jsp" />
		<div>
			<p id="require">

			</p>
			<c:if test="${!empty requestCope.addFail}">
				<p class="alert alert-success">
					<c:out value="${requestScope.addFail}" />
				</p>
			</c:if>
			<form method="POST" action="Ajouter_facture" class="form-horizontal"
				id="factureForm">

				<h2>Liste des opticiens</h2>
				<table class="table">
					<thead>
						<tr>
							<th>Nom</th>
							<th>Prénom</th>
							<th>Email</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="opticien" items="${requestScope.opticiens}">
							<tr>
								<td>${opticien.getNom() }</td>
								<td>${opticien.getPrenom() }</td>
								<td>${opticien.getLogin() }</td>
								<td>
									<a class="btn btn-danger" onclick="return confirm('Êtes vous sur de vouloir supprimer cet utilisateur ?')" href="GestionOpticien?qui=${opticien.getLogin()}&mode=del">Supprimer</a>
								</td>
							</tr>
						</c:forEach>

					</tbody>
					<tr>

					</tr>

				</table>



			</form>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp" />
	<script>
		$(function() {
			$("button[type='submit']")
					.on(
							'click',
							function() {
								if ($("select[name='libelle_equipement']")
										.val() == "lunettes") {
									$("select[name='secu']").attr('required',
											true);
									$("input[name='num_lot']").attr('required',
											true);
								} else {
									$("select[name='secu']").removeAttr(
											'required');
									$("input[name='num_lot']").removeAttr(
											'required');
								}
							});

			$("input:required, select:required").each(
					function() {
						$(this).siblings('label').append(
								'<span class="colorRed">&nbsp;*</span>');
					});
		});
	</script>
</body>
</html>