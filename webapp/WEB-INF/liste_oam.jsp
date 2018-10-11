<!DOCTYPE html>
<html lang="en">
<head>
<title>Listes OAM</title>
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

				<h2>Liste des organismes d'assurances maladies</h2>
				<a href="#tMuts" class="btn btn-info" data-toggle="collapse">Afficher
					Mutuelles</a>
				<div id="tMuts" class="collapse">
					<table class="table">
						<thead>
							<tr>
								<th>Nom</th>
								<th>Adresse</th>
								<th>Code Postal</th>
								<th>Ville</th>
								<th>Email</th>
								<th>Téléphone</th>
								<th>Fax</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="mutuelle" items="${requestScope.mutuelles}">
								<tr>
									<td>${mutuelle.getNom_t() }</td>
									<td>${mutuelle.getAdresse_t() }</td>
									<td>${mutuelle.getCp_t() }</td>
									<td>${mutuelle.getVille_t() }</td>
									<td>${mutuelle.getEmail_t() }</td>
									<td>${mutuelle.getTel_t() }</td>
									<td>${mutuelle.getFax_t() }</td>
									<td><a
										onclick="return confirm('Êtes vous sur de vouloir supprimer cet utilisateur ?')"
										href="GestionOAM?oam=mutuelle&qui=${mutuelle.getId_t()}&mode=del"
										class="btn btn-danger">Supprimer</a></td>
									<td><a
										href="GestionOAM?oam=mutuelle&qui=${mutuelle.getId_t()}&mode=modif"
										class="btn btn-info">Modifier</a></td>
								</tr>
							</c:forEach>

						</tbody>
						<tr>

						</tr>

					</table>
				</div>
				<a href="#tSecus" class="btn btn-info" data-toggle="collapse">Afficher
					Sécus</a>
				<div id="tSecus" class="collapse">
					<table class="table">
						<thead>
							<tr>
								<th>Nom</th>
								<th>Adresse</th>
								<th>Code Postal</th>
								<th>Ville</th>
								<th>Email</th>
								<th>Téléphone</th>
								<th>Fax</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="secu" items="${requestScope.secus}">
								<tr>
									<td>${secu.getNom_t() }</td>
									<td>${secu.getAdresse_t() }</td>
									<td>${secu.getCp_t() }</td>
									<td>${secu.getVille_t() }</td>
									<td>${secu.getEmail_t() }</td>
									<td>${secu.getTel_t() }</td>
									<td>${secu.getFax_t() }</td>
									<td><a
										onclick="return confirm('Êtes vous sur de vouloir supprimer cet utilisateur ?')"
										href="GestionOAM?oam=secu&qui=${secu.getId_t()}&mode=del"
										class="btn btn-danger">Supprimer</a></td>
									<td><a
										href="GestionOAM?oam=secu&qui=${secu.getId_t()}&mode=modif"
										class="btn btn-info">Modifier</a></td>
								</tr>
							</c:forEach>
						</tbody>
						<tr>
						</tr>
					</table>
				</div>

			</form>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp" />
	<script>

	</script>
</body>
</html>