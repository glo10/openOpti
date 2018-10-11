<form class="form-horizontal" action="Commenter" method="POST" autocomplete="off">
	<c:if test="${not empty url}">
	<div class="form-group">
		<input type="radio" name="typeRemboursement" value="secu" id="secu" required/><label for="secu">&nbsp;Acquitter le remboursement de la Sécurité sociale</label>
	</div>
	<div class="form-group">
		<input type="radio" name="typeRemboursement" value="mutuelle" id="mutuelle" required/><label for="mutuelle">&nbsp;Acquitter le remboursement de la mutuelle</label>
	</div>
	<div class="form-group">
		<input type="radio" name="typeRemboursement" value="mutuelle_et_secu" id="mutuelle_et_secu" required/><label for="mutuelle_et_secu">&nbsp;Acquitter le remboursement de la sécurité sociale et de la mutuelle</label>
	</div>
	<div class="form-group">
		<label for="date_secu" id="dateSecu">Saisir la date de paiement de la sécurité sociale</label>
		<input type="hidden" name="date_paiement_secu" pattern="<c:out value='${requestScope.date}'/>"/>
	</div>
	<div class="form-group">
		<label for="date_mutuelle" id="dateMutuelle">Saisir la date de paiement de la mutuelle</label>
		<input type="hidden" name="date_paiement_mutuelle" pattern="<c:out value='${requestScope.date}'/>" />
	</div>
	</c:if>
	<h3 id="newCom">Nouveau commentaire</h3>
	<div class="form-group">
		<input type="hidden" name="etat" value="<c:out value='${requestScope.etat}'/>"/>
		<input type="hidden" name="equipement" value="<c:out value='${requestScope.equipement}'/>"/>
		<input type="hidden" name="num_facture" value="<c:out value='${requestScope.num_facture}'/>"/>
		<input class="col-sm-12 input-lg" type="text" maxlength="80" id="titre" name="titre" placeholder="Titre du commentaire"/>
	</div>
	<div class="form-group">
		<textarea class="col-sm-12 input-lg" name="textCom" maxlength="1500" placeholder="Votre commentaire ici..."></textarea>
	</div>
	<div class="form-group">
		<button type="submit" class="col-sm-12 btn btn-success btn-lg" data-container="body" data-toggle="popover" data-title="Champs obligatoires" data-placement="top" data-content="Vous devez remplir obligatoirement  un titre et un commentaire.<br>Au moins 2 caractères pour chaque champs.">Ajouter un commentaire</button>
	</div>
</form>