<!DOCTYPE html>
<html lang="en">
<head>
	<title>Ajouter une facture</title>
	<meta charset="UTF-8"/>
	<link href="style/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="style/style.css" rel="stylesheet"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<style>
		legend{
			padding-left:0px;
		}
		
		#require{
			font-weight : bold;
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
			<p id="require">Champs obligatoires <span class="colorRed">*</span></p>
			<form method="POST" action="Ajouter_facture" class="form-horizontal" id="factureForm" autocomplete="off">
			
				<fieldset>
					<legend>S&eacute;curit&eacute; sociale</legend>
					<div class="form-group">
						<label for="secu" class="col-sm-offset-4 col-sm-4">Centre d'assurance maladie (s&eacute;cu)</label>
						<select name="secu" id="secu">
							<option value="" disabled selected>Choisir ici ou cr&eacute;er un nouveau</option>
							<c:forEach items="${listSecu}" var="secu">
								<option value="<c:out value="${secu.key}"/>"><c:out value="${secu.value.nom_t}"/></option>
							</c:forEach>
						</select>
						<a href="GestionOAM?mode=ajout" class="btn btn-default" role="button"><span class="glyphicon glyphicon-plus"></span></a>
					</div>
					<div class="form-group">
						<label for="num_lot" class="col-sm-offset-4 col-sm-4">Num&eacute;ro lot t&eacute;l&eacute;transmission</label>
						<input type="number" min="1" step="1" name="num_lot" id="num_lot" pattern="<c:out value='${requestScope.lot}'/>" value="<c:out value='${sessionScope.values.num_lot}'/>"/>
					</div>
				</fieldset>
				
				<fieldset>
					<legend>Mutuelle</legend>
					<div class="form-group">
						<label for="mutuelle" class="col-sm-offset-4 col-sm-4">Mutuelle</label>
						<select name="mutuelle" id="mutuelle" required>
							<option value="" disabled selected>Choisir ici ou cr&eacute;er un nouveau</option>
							<c:forEach items="${listMut}" var="mut">
								<option value="<c:out value="${mut.key}"/>"><c:out value="${mut.value.nom_t}"/></option>
							</c:forEach>
						</select>
						<a href="GestionOAM?mode=ajout" class="btn btn-default" role="button"><span class="glyphicon glyphicon-plus"></span></a>
					</div>
					<div class="form-group">
						<label for="num_contrat" class="col-sm-offset-4 col-sm-4">Num&eacute;ro contrat</label>
						<input type="text" name="num_contrat"  id="num_contrat" pattern="<c:out value='${requestScope.numero}'/>" value="<c:out value='${sessionScope.values.numero_contrat}'/>"/>
					</div>
					<div class="form-group">
						<label for="num_adherent" class="col-sm-offset-4 col-sm-4">Num&eacute;ro adh&eacute;rent</label>
						<input type="text" name="num_adherent" id="num_adherent" pattern="<c:out value='${requestScope.numero}'/>" value="<c:out value='${sessionScope.values.num_adherent}'/>"/>
					</div>
					<div class="form-group">
						<label for="date_deb" class="col-sm-offset-4 col-sm-4">Date d&eacute;but contrat</label>
						<input type="date" name="date_deb"  id="date_deb" pattern="<c:out value='${date}'/>" value="<c:out value='${sessionScope.values.date_deb}'/>" required/>
					</div>
					<div class="form-group">
						<label for="date_fin" class="col-sm-offset-4 col-sm-4">Date fin contrat</label>
						<input type="date" name="date_fin" id="date_fin" pattern="<c:out value='${date}'/>" value="<c:out value='${sessionScope.values.date_fin}'/>" required/>
					</div>
				</fieldset>
				
				<fieldset>
					<legend>Client</legend>
					<div class="form-group">
						<label for="nom_client" class="col-sm-offset-4 col-sm-4">Nom</label>
						<input type="text" name="nom_client" id="nom_client" pattern="<c:out value='${requestScope.identite}'/>" value="<c:out value='${sessionScope.values.nom_client}'/>" required/>
					</div>
					<div class="form-group">
						<label for="prenom_client" class="col-sm-offset-4 col-sm-4">Pr&eacute;nom</label>
						<input type="text" name="prenom_client" id="prenom_client" pattern="<c:out value='${requestScope.identite}'/>" value="<c:out value='${sessionScope.values.prenom_client}'/>" required/>
					</div>
					<div class="form-group">
						<label for="date_naissance" class="col-sm-offset-4 col-sm-4">Date de naissance</label>
						<input type="date" name="date_naissance" id="date_naissance" pattern="<c:out value='${requestScope.date}'/>" value="<c:out value='${sessionScope.values.date_naissance}'/>" required/>
					</div>
					<div class="form-group">
						<label for="num_secu" class="col-sm-offset-4 col-sm-4">Num&eacute;ro de s&eacute;curit&eacute; sociale</label>
						<input type="text" name="num_secu" id="num_secu" maxlength="15" pattern="<c:out value='${requestScope.secu}'/>" value="<c:out value='${sessionScope.values.num_secu}'/>" required/>
					</div>
					<div class="form-group">
						<label for="adresse" class="col-sm-offset-4 col-sm-4">Adresse</label>
						<input type="text" name="adresse" id="adresse" pattern="<c:out value='${requestScope.adresse}'/>" value="<c:out value='${sessionScope.values.adresse}'/>"/>
					</div>
					<div class="form-group">
						<label for="ville" class="col-sm-offset-4 col-sm-4">Ville</label>
						<input type="text" name="ville" id="ville" pattern="<c:out value='${requestScope.adresse}'/>" value="<c:out value='${sessionScope.values.ville}'/>"/>
					</div>
					<div class="form-group">
						<label for="cp_client" class="col-sm-offset-4 col-sm-4">Code postal</label>
						<input type="text" name="cp_client" id="cp_client" pattern="<c:out value='${requestScope.cp_client}'/>" value="<c:out value='${sessionScope.values.cp_client}'/>"/>
					</div>
					<div class="form-group">
						<label for="email" class="col-sm-offset-4 col-sm-4">Email</label>
						<input type="email" name="email" id="email" pattern="<c:out value='${requestScope.email}'/>" value="<c:out value='${sessionScope.values.email_client}'/>"/>
					</div>
					<div class="form-group">
						<label for="tel" class="col-sm-offset-4 col-sm-4">T&eacute;l&eacute;phone</label>
						<input type="text" name="tel" id="tel" pattern="<c:out value='${requestScope.tel}'/>" value="<c:out value='${sessionScope.values.tel_client}'/>"/>
					</div>
				</fieldset>
				
				<fieldset>
					<legend>Informations g&eacute;n&eacute;rales</legend>
					<div class="form-group">
						<label for="num_facture" class="col-sm-offset-4 col-sm-4">Num&eacute;ro de la facture</label>
						<input type="text" name="num_facture" id="num_facture" pattern ="<c:out value='${requestScope.numero}'/>" value="<c:out value='${sessionScope.values.num_facture}'/>" required/>
					</div>
					<div class="form-group">
						<label for="date_facture" class="col-sm-offset-4 col-sm-4">Date de la facture</label>
						<input type="date" name="date_facture" id="date_facture" pattern="<c:out value='${requestScope.date}'/>" value="<c:out value='${sessionScope.values.date_facture}'/>" required/>
					</div>
					<div class="form-group">
						<label for="date_envoi" class="col-sm-offset-4 col-sm-4">Date d'envoi de la facture à la mutuelle</label>
						<input type="date" name="date_envoi" id="date_envoi" pattern="<c:out value='${requestScope.date}'/>" value="<c:out value='${sessionScope.values.date_envoi}'/>" required/>
					</div>
					<div class="form-group">
						<label for="libelle_equipement" class="col-sm-offset-4 col-sm-4">&eacute;quipement</label>
						<select name="libelle_equipement" id="libelle_equipement" required>
							<option value="" disabled selected>Choisir ici</option>
							<option value="lunettes">Lunettes</option>
							<option value="lentilles NR">Lentilles NR</option>
							<option value="lentilles RSS">Lentilles RSS</option>
						</select>
					</div>
				</fieldset>
				
				<fieldset>
					<legend>Prix</legend>
					<div class="form-group">
						<label for="montant_total" class="col-sm-offset-4 col-sm-4">Montant total</label>
						<input type="number" step="0.01" name="montant_total" id="montant_total" pattern="<c:out value='${requestScope.prix}'/>" value="<c:out value='${sessionScope.values.montant_total}'/>" required/>
					</div>
					<div class="form-group">
						<label for="montant_remb_mut" class="col-sm-offset-4 col-sm-4">Montant remboursement mutuelle</label>
						<input type="number" step="0.01" name="montant_remb_mut" id="montant_remb_mut" pattern="<c:out value='${requestScope.prix}'/>" value="<c:out value='${sessionScope.values.montant_remb_mut}'/>" required/>
					</div>
					<div class="form-group">
						<label for="montant_remb_secu" class="col-sm-offset-4 col-sm-4">Montant remboursement S&eacute;curit&eacute; sociale</label>
						<input type="number" step="0.01" name="montant_remb_secu" id="montant_remb_secu" pattern="<c:out value='${requestScope.prix}'/>" value="<c:out value='${sessionScope.values.montant_remb_secu}'/>" required/>
					</div>
				</fieldset>
				
				<div class="form-group flex-end">
					<button type="submit" class="btn btn-success btn-lg">Ajouter la facture</button>
				</div>
				
			</form>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp"/>
	<script>
		$(function(){
			//Rend disabled les champs concernant la s&eacute;curit&eacute; sociale lorsque l'&eacute;quipement n'est pas rembours&eacute; par la s&eacute;curit&eacute; sociale(lentilles NR)
			$("select[name='libelle_equipement']").on("change",function(){
				if($("select[name='libelle_equipement'] option:selected").val() == "lentilles NR"){
					$("select[name='secu']").val("");
					$("input[name='num_lot']").val("");
					$("input[name='montant_remb_secu']").val("");
					
					$("select[name='secu']").attr('disabled',true);
					$("input[name='num_lot']").attr('disabled',true);
					$("input[name='montant_remb_secu']").attr('disabled',true);
				}
				else{
					$("select[name='secu']").removeAttr('disabled',true);
					$("input[name='num_lot']").removeAttr('disabled',true);
					$("input[name='montant_remb_secu']").removeAttr('disabled',true);
				}
			});
			
			
			
			//Gestion des champs requis  à la soumission du formulaire quand la facture n'est pas de l'&eacute;quipement lentilles NR autrement dit lunettes ou lentilles RSS
			$("button[type='submit']").on('click',function(e){
				//A REVOIR GERER LA COHERENCE DES MONTANTS
				/*var montant_total = $("input[name='montant_total']").val();
				var montant_secu = $("input[name='montant_remb_secu']").val();
				var montant_mut = $("input[name='montant_remb_mut']").val();
				
				if( ($(montant_secu) > $(montant_total)) || ($(montant_mut) > $(montant_total)) || ($(montant_secu) +  $(montant_mut) > $(montant_total)) ){
					e.preventDefault();
					alert("Les montants saisis ne sont pas coh&eacute;rents entre elles");
				}
				else{
					$("button[type='submit']").off('click');
				}*/
				
				if($("select[name='libelle_equipement']").val() !== "lentilles NR"){
					$("select[name='secu']").attr('required',true);
					$("input[name='num_lot']").attr('required',true);
				}
				else{
					$("select[name='secu']").removeAttr('required');
					$("input[name='num_lot']").removeAttr('required');
				}
			});
			
			//Ajoute un ast&eacute;rix à tous les champs qui ont un required
			$("input:required, select:required").each(function(){
					$(this).siblings('label').append('<span class="colorRed">&nbsp;*</span>');
			});
			
		});
	</script>
</body>
</html>