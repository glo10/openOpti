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
			<form method="POST" action="GestionOpticien" class="form-horizontal"
				id="factureForm" data-toggle="validator">

					<legend>Opticien</legend>
					<div class="form-group">


							<c:if test="${requestScope.mode == 'ajout'}">
								<input type="hidden" name="mode" value="ajout" />
								<div class="form-group">
									<label for="login" class="col-sm-offset-4 col-sm-4">Login</label>
									<input type="email" name="login" id="login" required />
								</div>
							</c:if>
							<c:if test="${requestScope.mode == 'modif'}">
								<div class="form-group">
									<label for="a_mdp" class="col-sm-offset-4 col-sm-4">Ancien
										mot de passe</label> <input type="password" name="a_mdp" id="a_mdp" />
								</div>
								<input type="hidden" name="mode" value="modif" />
							</c:if>
							<c:if test="${not empty error}">
								<label>${error}</label>
							</c:if>
							<div class="form-group">
								<label for="mdp" class="col-sm-offset-4 col-sm-4">Nouveau mot de
									passe</label> <input type="password" name="mdp" id="mdp" required
									onkeyup="check();" />
							</div>

							<div class="form-group">
								<label for="r_mdp" class="col-sm-offset-4 col-sm-4">Repétez
									votre mot de passe</label> <input type="password" id="r_mdp"
									name="r_mdp" onkeyup="check();" />
							</div>
							<c:if test="${requestScope.mode == 'ajout'}">
								<div class="form-group">
									<label for="nom" class="col-sm-offset-4 col-sm-4">Nom</label> <input
										type="text" name="nom" id="nom" />
								</div>
								<div class="form-group">
									<label for="prenom" class="col-sm-offset-4 col-sm-4">Prénom</label>
									<input type="text" name="prenom" id="prenom" required />
								</div>
							</c:if>
							<input type="hidden" name="status" value="ok" /> <span
								id='message'></span>
					</div>
				</fieldset>
				<c:if test="${requestScope.mode == 'ajout'}">
					<fieldset>
						<legend>Droits</legend>
						<select name="fonction" id="fonction" required>
							<option value="" disabled selected>Rôle</option>
							<option value="<c:out value="Opticien"/>"><c:out
									value="opticien" /></option>
							<option value="<c:out value="Directeur"/>"><c:out
									value="directeur" /></option>
						</select>
					</fieldset>
				</c:if>
				<div class="form-group flex-end">
					<button type="submit" id="valider" class="btn btn-success btn-lg">
						<c:if test="${requestScope.mode == 'ajout'}"> Ajouter </c:if>
						<c:if test="${requestScope.mode == 'modif'}"> Modifier </c:if>
						le compte utilisateur
					</button>
				</div>
			</form>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp" />

</body>
<script type="text/javascript">
	var check = function() {
		if (document.getElementById('mdp').value == document
				.getElementById('r_mdp').value) {
			document.getElementById('message').style.color = 'green';
			document.getElementById('message').innerHTML = 'matching';
			document.getElementById('valider').disabled = false;
		} else {
			document.getElementById('message').style.color = 'red';
			document.getElementById('message').innerHTML = 'not matching';
			document.getElementById('valider').disabled = true;
		}
	}
</script>

</html>