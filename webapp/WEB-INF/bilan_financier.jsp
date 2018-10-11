<!DOCTYPE html>
<html lang="fr">
<head>
	<title>Bilan financier</title>
	<meta charset="UTF-8"/>
	<link href="style/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="style/style.css" rel="stylesheet"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<style>
		.glyphicon-flag{
			font-weight:bold;
		}
		
		.form-group{
			margin: 20px 0px ! important;
		}
		
		.table-striped>tbody>tr>td{
			padding: 8px !important;
		}
		
		.table.table-striped>thead>tr>th:first-child{
			padding-left: 0px !veryimportant;
			text-indent:0px !important;
		}
		
		input[type="date"],.btn.btn-success{
			padding:6px 12px;
			
		}
		
		input[type="date"]{
			background-color: #fff;
			border-radius: 4px;
			border: 1px solid #ccc;
		}
	</style>
</head>
<body>
	<div class="container-fluid">
		<c:import url="/jsp_include/menu.jsp"/>
		<div>
			<form method="POST" action="Bilan" class="form-horizontal flex-between">
			
					<div class="form-group">
						<select name="filtre" id="filtre" class="form-control form-control-lg">
						
							<c:if test="${empty sessionScope.filtre}">
								<option value="" disabled selected>Trier</option>
							</c:if>
							<option value="nom_mutuelle ASC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'nom_mutuelle ASC'}"><c:out value="selected"/></c:if>>Nom A->Z</option>
							<option value="nom_mutuelle DESC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'nom_mutuelle DESC'}"><c:out value="selected"/></c:if>>Nom Z->A</option>
							
							<option value="total_encaissee ASC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'total_encaissee ASC'}"><c:out value="selected"/></c:if>>Montant total encaissé - au + élévé</option>
							<option value="total_encaissee DESC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'total_encaissee DESC'}"><c:out value="selected"/></c:if>>Montant total encaissé + au - élévé</option>
							
							<option value="total_a_encaisser ASC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'total_a_encaisser ASC'}"><c:out value="selected"/></c:if>>Montant total à encaisser - au + élévé</option>
							<option value="total_a_encaisser DESC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'total_a_encaisser DESC'}"><c:out value="selected"/></c:if>>Montant total encaissé + au - élévé</option>
							
							<option value="total_dossier ASC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'total_dossier ASC'}"><c:out value="selected"/></c:if>>Total dossier - au + élévé</option>
							<option value="total_dossier DESC" <c:if test="${!empty sessionScope.filtre && sessionScope.filtre == 'total_dossier DESC'}"><c:out value="selected"/></c:if>>Total dossier + au - élévé</option>
						
						</select>
					</div>
					
					<div class="form-group">
						<label for="exportDeb">Exporter les données de la date du </label>
						<input type="date" name="exportDeb" id="exportDeb" pattern="<c:out value='${requestScope.patternDate}'/>"/>
						<label for="exportFin">au</label>
						<input type="date" name="exportFin" id="exportFin" pattern="<c:out value='${requestScope.patternDate}'/>"/>
						<button type="submit" class="btn btn-success btn-lg"><span class="glyphicon glyphicon-export"></span></button>
					</div>
					
			</form>
		</div>
		<div>
			<table class="table table-striped">
			
				<thead>
					<tr>
					
						<th>Nom de l'organisme de santé</th>
						<th>Nombre de dossier</th>
						<th>Panier moyen</th>
						<th>total CA encaissé</th>
						<th>Total CA à encaisser</th>
						<th>Total CA</th>
						<th>Taux concrétisation(%)</th>
					
					</tr>
				</thead>
				
				<tbody>
						<c:forEach items="${requestScope.reporting}" var ="mut">
							<tr>
								<c:forEach var ="i" begin="0" end="6" step="1">
									<td><c:out value="${mut.value[i]}"/></td>
								</c:forEach>
							</tr>
						</c:forEach>
				</tbody>
				
			</table>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp"/>
	<script>
		$(function(){
			$("tbody>tr>td:last-child").each(function(){
				if($(this).html() < 33){
					$(this).append("&nbsp;<span class='glyphicon glyphicon-flag'></span>");
					$(this).find('.glyphicon-flag').css({"color":"#c9302c"});
				}
				else if($(this).html() >=33 && $(this).html() < 66){
					$(this).append("&nbsp;<span class='glyphicon glyphicon-flag'></span>");
					$(this).find('.glyphicon-flag').css({"color":"#ec971f"});
				}
				else if($(this).html() >= 66){
					$(this).append("&nbsp;<span class='glyphicon glyphicon-flag'></span>");
					$(this).find('.glyphicon-flag').css({"color":"#449d44"});
				}
			});
			
			$('#filtre').on('change',function(){
				$(this).closest('form').submit();
			});
		});
	</script>
</body>
</html>