<ul class="list-group">
	<c:set var="num_facture" value="${factureDetaillee['Num�ro']}" scope="request"></c:set>
	<li class="list-group-item">Num�ro de facture&nbsp;:&nbsp;<c:out value="${requestScope.num_facture}"/></li>
	<li class="list-group-item">Date de la facture&nbsp;:&nbsp;<c:out value="${factureDetaillee['Date']}"/></li>
	<li class="list-group-item">�quipement&nbsp;:&nbsp;<c:out value="${factureDetaillee['�quipement']}"/></li>
	<li class="list-group-item">Mutuelle&nbsp;:&nbsp;<c:out value="${factureDetaillee['Mutuelle']}"/></li>
	<li class="list-group-item">S�curit� sociale&nbsp;:&nbsp;<c:out value="${factureDetaillee['S�curit� sociale']}"/></li>
	<li class="list-group-item">Client(e)&nbsp;:&nbsp;<c:out value="${factureDetaillee['Nom']}"/>&nbsp;<c:out value="${factureDetaillee['Pr�nom']}"/></li>
	<li class="list-group-item">Num�ro s�curit� sociale&nbsp;:&nbsp;<c:out value="${factureDetaillee['Num�ro de s�curit� sociale']}"/></li>
	<li class="list-group-item">�tat&nbsp;:&nbsp;<c:out value="${factureDetaillee['�tat']}"/></li>
</ul>