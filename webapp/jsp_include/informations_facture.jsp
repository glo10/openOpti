<ul class="list-group">
	<c:set var="num_facture" value="${factureDetaillee['Numéro']}" scope="request"></c:set>
	<li class="list-group-item">Numéro de facture&nbsp;:&nbsp;<c:out value="${requestScope.num_facture}"/></li>
	<li class="list-group-item">Date de la facture&nbsp;:&nbsp;<c:out value="${factureDetaillee['Date']}"/></li>
	<li class="list-group-item">Équipement&nbsp;:&nbsp;<c:out value="${factureDetaillee['Équipement']}"/></li>
	<li class="list-group-item">Mutuelle&nbsp;:&nbsp;<c:out value="${factureDetaillee['Mutuelle']}"/></li>
	<li class="list-group-item">Sécurité sociale&nbsp;:&nbsp;<c:out value="${factureDetaillee['Sécurité sociale']}"/></li>
	<li class="list-group-item">Client(e)&nbsp;:&nbsp;<c:out value="${factureDetaillee['Nom']}"/>&nbsp;<c:out value="${factureDetaillee['Prénom']}"/></li>
	<li class="list-group-item">Numéro sécurité sociale&nbsp;:&nbsp;<c:out value="${factureDetaillee['Numéro de sécurité sociale']}"/></li>
	<li class="list-group-item">État&nbsp;:&nbsp;<c:out value="${factureDetaillee['État']}"/></li>
</ul>