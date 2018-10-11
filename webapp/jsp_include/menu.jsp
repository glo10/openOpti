<nav class="flex-between">
	<ul class="nav nav-tabs">
		<li role="presentation"><a href="Accueil">Accueil</a></li>
		<li role="presentation"><a href="Ajouter_facture">Ajouter une
				facture</a></li>
		<li role="presentation"><a href="GestionOAM?mode=ajout">Ajouter
				un organisme d'assurance maladie</a></li>

	</ul>
	<ul class="nav nav-tabs">
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
			aria-expanded="false"> <span class="glyphicon glyphicon-user">&nbsp;</span>Mon
				espace personnel <span class="caret"></span>

		</a>
			<ul class="dropdown-menu">
				<li role="presentation"><a href="GestionOpticien?mode=modif">Modifier
						mot de passe</a></li>
				<li role="presentation"><a href="Deconnexion">Se
						deconnecter</a></li>
				<c:if test="${sessionScope.opticien.getFonction() == 'directeur'}">
					<li role="presentation"><a href="GestionOpticien?mode=ajout">Ajouter
							un compte opticien</a></li>
					<li role="presentation"><a href="Opticiens">Gestion des
							opticiens</a></li>
					<li role="presentation"><a href="Liste_OAM">Gestion
							Sécu/Mutuelle</a></li>
					<li role="presentation"><a href="Bilan">Consulter le bilan
							financier</a></li>
				</c:if>
			</ul></li>
	</ul>
</nav>