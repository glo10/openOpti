<!DOCTYPE html>
<html lang="en">
<head>
<title>Gestion des Organismes d'assurance maladie</title>
<meta charset="UTF-8" />
<link href="style/css/bootstrap.min.css" rel="stylesheet" />
<link href="style/style.css" rel="stylesheet" />


<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>

	<div class="container-fluid">
		<c:import url="/jsp_include/menu.jsp" />
		<div>
			<form method="POST" action="GestionOAM" class="form-horizontal"
				id="oamForm" data-toggle="validator">

				<fieldset>
					<legend>Organisme d'Assurance Maladie</legend>

						<c:if test="${requestScope.mode == 'ajout'}">
							<input type="hidden" name="mode" value="ajout" />
						</c:if>
						<c:if test="${requestScope.mode == 'modif'}">
							<input type="hidden" name="mode" value="modif" />
						</c:if>
						<c:if test="${not empty error}">
							<label>${error}</label>
						</c:if>

						<select name="type" id="type" required>
							<option value="" disabled selected>Types d'organisme
								d'assurance maladie</option>
							<option value="<c:out value="mutuelle"/>"
								<c:if test="${requestScope.mode == 'modif'}"> selected </c:if> />
							<c:out value="Mutuelle"></c:out>
							</option>
							<option value="<c:out value="ss"/>"
								<c:if test="${requestScope.mode == 'secu'}"> selected </c:if> />
							<c:out value="Sécurité Sociale" />
							</option>
						</select>


						<div class="form-group">
							<label for="nom" class="col-sm-offset-4 col-sm-4">Saisir
								le nom</label> <input
								<c:if test="${requestScope.mode == 'modif'}"> value = "${requestScope._oam.getNom_t()}"</c:if>
								type="text" name="nom" id="nom" required />
						</div>
						<div class="form-group">
							<label for="adresse" class="col-sm-offset-4 col-sm-4">Saisir
								l'adresse</label> <input type="text" name="adresse" id="adresse"
								<c:if test="${requestScope.mode == 'modif'}"> value = "${requestScope._oam.getAdresse_t()}"</c:if> />
						</div>

						<div class="form-group">
							<label for="cp" class="col-sm-offset-4 col-sm-4">Saisir
								le code postal</label> <input type="text" name="cp" id="cp"
								<c:if test="${requestScope.mode == 'modif'}"> value = "${requestScope._oam.getCp_t()}"</c:if> />
						</div>
						<div class="form-group">
							<label for="ville" class="col-sm-offset-4 col-sm-4">Saisir
								la ville</label> <input type="text" name="ville" id="ville"
								<c:if test="${requestScope.mode == 'modif'}"> value = "${requestScope._oam.getVille_t()}"</c:if> />
						</div>
						<div class="form-group">
							<label for="email" class="col-sm-offset-4 col-sm-4">Saisir
								l'adresse mail</label> <input type="email" name="email" id="email"
								<c:if test="${requestScope.mode == 'modif'}"> value = "${requestScope._oam.getEmail_t()}"</c:if> />
						</div>
						<div class="form-group">
							<label for="telephone" class="col-sm-offset-4 col-sm-4 bfh-phone"
								data-country="Fr">Saisir le numéro de t&eacute;l&eacute;phone</label> <input
								type="text" name="telephone" id="telephone"
								<c:if test="${requestScope.mode == 'modif'}"> value = "${requestScope._oam.getTel_t()}"</c:if> />
						</div>
						<div class="form-group">
							<label for="fax" class="col-sm-offset-4 col-sm-4 bfh-phone"
								data-country="Fr">Saisir le numéro de fax</label> <input
								type="text" name="fax" id="fax"
								<c:if test="${requestScope.mode == 'modif'}"> value = "${requestScope._oam.getFax_t()}"</c:if> />
						</div>

						<c:if test="${requestScope.mode == 'modif'}">
							<input type="hidden" name="id" value="${_oam.getId_t() }" />
							<input type="hidden" name="mode" value="modif" />
							<input type="hidden" name="type" value="${requestScope.type}" />
						</c:if>
						<input type="hidden" name="status" value="ok" /> <span
							id='message'></span>
				</fieldset>


				<div class="form-group flex-end">
					<button type="submit" id="valider" class="btn btn-success btn-lg">
						<c:if test="${requestScope.mode == 'ajout'}"> Ajouter </c:if>
						<c:if test="${requestScope.mode == 'modif'}"> Modifier </c:if>
						l'organisme d'assurance maladie
					</button>
				</div>
			</form>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp" />

</body>

</script>

</html>