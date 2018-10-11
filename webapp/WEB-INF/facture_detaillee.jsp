<!DOCTYPE html>
<html lang="fr">
<head>
	<title>Facture  détaillée</title>
	<meta charset="UTF-8"/>
	<link href="style/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="style/style.css" rel="stylesheet"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<style>
		.col-sm-4,.col-sm-8{
			max-height:600px;
			overflow:auto;
		}
		
		.flex-between{
			margin: 20px 0px;
		}
	</style>
</head>
<body>
	
	<div class="container-fluid">
		<c:import url="/jsp_include/menu.jsp"/>
		<div class="flex-between" id="fDetail">
			<div class="col-sm-4">
				<h3>Informations</h3>
				<ul class="list-group">
					<c:forEach items="${factureDetaillee}" var="facture">
						<li class="list-group-item">
							<c:out value="${facture.key}"/>&nbsp;:&nbsp;<c:out value="${facture.value}" default="-"/>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-sm-8">
				<h3>Commentaires</h3>
				<c:import url="/jsp_include/commentaires.jsp"></c:import>
			</div>
		</div>
		<footer class="flex-end">
			<div class="mg">
				<c:url value="/Commenter" var="lienCom" scope="request">
					<c:param name="num_facture" value="${requestScope.num_facture}"/>
				</c:url>
				<a href="${lienCom}" role="button" class="btn btn-lg btn-warning">
					Commenter <span class="glyphicon glyphicon-comment"></span>
				</a>
			</div>
			<div class="mg">
				<c:url value="/Acquitter" var="lienAcquitter" scope="request">
					<c:param name="num_facture" value="${requestScope.num_facture}"/>
				</c:url>
				&nbsp;
				<a href="${lienAcquitter}" role="button" class="btn btn-lg btn-success">
					Acquitter <span class="glyphicon glyphicon-euro"></span>
				</a>
			</div>
		</footer>
		<c:import url="/jsp_include/footer_js.jsp"/>
	</div>

</body>
</html>