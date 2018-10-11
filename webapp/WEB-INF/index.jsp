<!DOCTYPE html>
<html lang="fr">
<head>
	<title>Accueil</title>
	<meta charset="UTF-8"/>
	<link href="style/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="style/style.css" rel="stylesheet" type="text/css"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<style>
		p.msgUser{
			display:none;
		}
		
		.table.table-striped>tbody>tr>td>.glyphicon-eye-open,
		.table.table-striped>tbody>tr>td>.glyphicon-sunglasses{
			position: relative;
			right: 3px;
		}
	</style>
</head>
<body>
	<div class="container-fluid">
		<c:import url="/jsp_include/menu.jsp"/>
		<div>
			<c:forEach items="${sessionScope.msgUser}" var="user">
				<p class="<c:out value='${user.key}'/>"><c:out value="${user.value}"/></p>
			</c:forEach>
			<c:remove var="msgUser" scope="session"/>
		</div>	
		<div>
			<div>
				<form method="POST" action="Accueil" class="form-inline flex-between">
					<div class="form-group">
						<select name="filtre1" id="filtre1" class="form-control form-control-lg">
							<c:if test="${empty sessionScope.filtre1}">
								<option value="" disabled selected>Trier</option>
							</c:if>
							<option value="montant_remb_mut ASC" <c:if test="${!empty sessionScope.filtre1 && sessionScope.filtre1 == 'montant_remb_mut ASC'}"><c:out value="selected"/></c:if>>Montant mutuelle - au +</option>
							<option value="montant_remb_mut DESC" <c:if test="${!empty sessionScope.filtre1 && sessionScope.filtre1 == 'montant_remb_mut DESC'}"><c:out value="selected"/></c:if>>Montant mutuelle + au -</option>
							<option value="date_facture ASC" <c:if test="${!empty sessionScope.filtre1 && sessionScope.filtre1 == 'date_facture ASC'}"><c:out value="selected"/></c:if>>- r&eacute;cente au + r&eacute;cente</option>
							<option value="date_facture DESC" <c:if test="${!empty sessionScope.filtre1 && sessionScope.filtre1 == 'date_facture DESC'}"><c:out value="selected"/></c:if>>+ r&eacute;cente au - r&eacute;cente</option>
						</select>
					</div>
					<div>
						<span class="glyphicon glyphicon-hourglass"></span>&nbsp;
						<span class="badge"><c:out value="${sessionScope.totalAttente}" default="-"/></span>
					</div>
					<div class="form-group">
						<input type="text" name="filtre2" id="filtre2" class="form-control form-control-lg col-sm-12" placeholder="Num&eacute;ro facture ou mutuelle ou s&eacute;cu" <c:if test="${!empty sessionScope.filtre2}"> value="<c:out value='${sessionScope.filtre2}'/>"</c:if>>
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
						<button type="submit" class="btn btn-danger" name="reset" value="sup"><span class="glyphicon glyphicon-remove"></span></button>
					</div>
				</form>
			</div>
			<div>
				<c:choose>
					<c:when test="${listeFacture.size() > 0}">
						<table class="table table-striped">
							<thead>
								<tr class="tableHead">
									<th>N° facture</th>
									<th id="thDf">Date facture&nbsp;</th>
									<th>Date d'envoi</th>
									<th>Total</th>
									<th id="thRc">Remb RC&nbsp;</th>
									<th>Remb RO</th>
									<th>RC</th>
									<th>RO</th>
									<th>Client</th>
									<th>Etat</th>
									<th></th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listeFacture}" var="facture">
									<tr>
										<td>
											<c:choose>
												<c:when  test="${facture.value.libelle_equipement == 'lunettes'}">
													<span class="glyphicon glyphicon-sunglasses"></span>
												</c:when>
												<c:otherwise>
													<span class="glyphicon glyphicon-eye-open"></span>
												</c:otherwise>
											</c:choose>
											<c:out value="${facture.value.num_facture}" default="-"/></td>
										<td><c:out value="${facture.value.date_facture}" default="-"/></td>
										<td><c:out value="${facture.value.date_envoi}"  default="-"/></td>
										<td><c:out value="${facture.value.montant_total}"  default="-"/></td>
										<td><c:out value="${facture.value.montant_remb_mut}"  default="-"/></td>
										<td><c:out value="${facture.value.montant_remb_secu}"  default="-"/></td>
										<td><c:out value="${facture.value.mutuelle}"  default="-"/></td>
										<td><c:out value="${facture.value.secu}"  default="-"/></td>
										<td><c:out value="${facture.value.client}"  default="-"/></td>
										<td><c:out value="${facture.value.etat}"  default="-"/></td>
										<td>
											<c:url value="/Commenter" var="lienCom" scope="request">
												<c:param name="num_facture" value="${facture.key}"/>
											</c:url>
												
											<a href="${lienCom}" role="button" class="btn btn-warning col-sm-offset-3 col-sm-9">
												<span class="glyphicon glyphicon-comment"></span>
											</a>
										</td>
										<td>
											<c:url value="/Facture_detaillee" var="lienDetail" scope="request">
												<c:param name="num_facture" value="${facture.key}"/>
											</c:url>
											<a href="${lienDetail}" role="button" class="btn btn-info col-sm-offset-3 col-sm-9">
												<span class="glyphicon glyphicon-info-sign"></span>
											</a>
										</td>
										<td>
											<c:url value="/Acquitter" var="lienAcquitter" scope="request">
												<c:param name="num_facture" value="${facture.key}"/>
											</c:url>
											<a href="${lienAcquitter}" role="button" class="btn btn-success col-sm-offset-3 col-sm-9">
												<span class="glyphicon glyphicon-euro"></span>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody> 	
						</table>
					</c:when>
					<c:otherwise>
						<p class="alert alert-info">
							Aucune facture en attente de paiement a &eacute;t&eacute; trouv&eacute;e
						</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp"/>
	<script>
		$(function(){
			
			$(".msgUser").each(function(){
				$(this).slideUp(300).delay(100).fadeIn(200);
				$(this).slideDown(300).delay(8000).fadeOut(200);
			});
			
			switch($("#filtre1").val()){
				case 'montant_remb_mut ASC':
					$("#thRc").append("<span class='glyphicon glyphicon-sort-by-order'>");
				break;
				case 'montant_remb_mut DESC':
					$("#thRc").append("<span class='glyphicon glyphicon-sort-by-order-alt'>");
				break;
				case 'date_facture DESC':
					$("#thDf").append("<span class='glyphicon glyphicon-sort-by-order-alt'>");
				break;
				case 'date_facture ASC':
					$("#thDf").append("<span class='glyphicon glyphicon-sort-by-order'>");
				break;
			}
			
			$('#filtre1').on('change',function(){
				$(this).closest('form').submit();
			});
		});
	</script>
</body>
</html>
