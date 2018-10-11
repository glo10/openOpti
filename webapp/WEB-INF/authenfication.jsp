<!DOCTYPE html>
<html lang="fr">
<head>
<title>Accueil</title>
<meta charset="UTF-8" />
<link href="style/css/bootstrap.min.css" rel="stylesheet" />
<link href="style/style.css" rel="stylesheet" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>
	<div class="container-fluid">
		<h1>Bienvenue sur OpenOpti</h1>

		<form method="POST" action="Authentification"
			class="center col-md-offset-2 col-sm-4">
			

			<legend>Authentification</legend>
			<div class="form-group">
				<input id="login" placeholder="Identifiant" type="email"
					class="form-control" name="login">

			</div>


			<div class="form-group">
				<input id="pwd" type="password" placeholder="Mot de Passe"
					class="form-control" name="mdp">
			</div>

			<div class="form-group">

				<button  type="submit" class="btn btn-block btn-primary">
					S'authentifier</button>
			</div>

		</form>
	</div>
</body>
</html>
