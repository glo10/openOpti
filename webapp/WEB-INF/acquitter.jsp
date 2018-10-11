<!DOCTYPE html>
<html lang="fr">
<head>
	<title>Acquitter</title>
	<meta charset="UTF-8"/>
	<link href="style/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="style/style.css" rel="stylesheet"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<style>
		.flex-center{
			margin: 20px 0px;
		}
		
		h3{
			margin-left:15px;
		}
		
		textarea{
			resize:none;
		}
		
		.pdLeft{
			padding-left : 15px;
		}
		
		form{
			margin-left:-15px;
		}
		
		.col-sm-6:first-child{
			padding-left:0px;
		}
		
		.col-sm-6:last-child{
			padding-right:0px;
		}
		
		#oldComs{
			max-height: 200px;
  			overflow: auto;
		}
		
		#dateSecu,#dateMutuelle{
			display:none;
		}
		
	</style>
</head>
<body>
	<div class="container-fluid">
		<c:import url="/jsp_include/menu.jsp"/>
		<div class="flex-center">
			<div class="col-sm-6">
				<h3>Informations</h3>
				<c:import url="/jsp_include/informations_facture.jsp"/>
			</div>
			<div class="col-sm-6">
				<c:import url="/jsp_include/formCommenter.jsp"/>
			</div>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp"/>
	<script>
		$(function(){
			var btnSubmit = $("button[type='submit']");
			var etat = $("input[name='etat']").val();
			var equipement = $("input[name='equipement']").val();
			switch(etat){
				case "remb secu ok":
					$("input#secu").parent().remove();
					$("input#mutuelle_et_secu").parent().remove();
					$("label#dateSecu").parent().remove();
				break;
				case "remb mut ok":
					$("input#mutuelle").parent().remove();
					$("input#mutuelle_et_secu").parent().remove();
					$("label#dateMutuelle").parent().remove();
				break;
			}
			
			switch(equipement){
				case "lentilles NR":
					$("input#secu").parent().remove();
					$("input#mutuelle_et_secu").parent().remove();
					$("label#dateSecu").parent().remove();
				break;
			}
			
			$(btnSubmit).attr("data-content","<p style='text-align:justify'>Pour soumettre votre demande vous devez choisir le type de remboursement parmi les 3 choix proposés.</br><br/>La saisie du commentaire est facultative cependant si l'un des champs titre ou commentaire est saisi alors les deux champs doivent être obligatoirement renseignées.</p>");
			$(btnSubmit).attr("data-placement","left");
			$(btnSubmit).popover({html:true});
			$('form.form-horizontal').attr("action","Acquitter");
			$(btnSubmit).text("Acquitter");
			$(btnSubmit).append("&nbsp;<span class='glyphicon glyphicon-euro'></span>");
			$('h3#newCom').append("(optionel)");
			$("input[type='radio'][name='typeRemboursement']").on("change", function() {
				switch($(this).val()){
					case "secu":
						$("#dateSecu").css({"display":"inline"});
						$("input[name='date_paiement_secu']").attr('type','date');
						$("input[name='date_paiement_secu']").attr("required",true);
						
						$("input[name='date_paiement_mutuelle']").attr('type','hidden');
						$("#dateMutuelle").css({"display":"none"});
						
					break;
					case "mutuelle":
						$("#dateMutuelle").css({"display":"inline"});
						$("input[name='date_paiement_mutuelle']").attr('type','date');
						$("input[name='date_paiement_mutuelle']").attr("required",true);
						
						$("input[name='date_paiement_secu']").attr('type','hidden');
						$("#dateSecu").css({"display":"none"});
						
					break;
					case "mutuelle_et_secu":
						$("#dateSecu").css({"display":"inline"});
						$("#dateMutuelle").css({"display":"inline"});
						
						$("input[name='date_paiement_mutuelle']").attr('type','date');
						$("input[name='date_paiement_secu']").attr('type','date');
						
						$("input[name='date_paiement_secu']").attr("required",true);
						$("input[name='date_paiement_mutuelle']").attr("required",true);
					break;
				}
			});
			
			$(btnSubmit).on('click',function(e){
				var titre = $("input[name='titre']").val();
				var com = $("textarea[name='textCom']").val();
				//e.preventDefault();
				var radio = $("input[type='radio'][name='typeRemboursement']");
				
				console.log("radio vaut " + radio);
				if(titre.length > 0 || com.length > 0){
					//-----PARTIE A MUTUALISER DANS UN FICHIER POUR JS EXTERNE EN FONCTION POUR L'UTILISER DANS LES PAGES acquitter.jsp et commenter.jsp--//
					$("input[type='text'],textarea").each(function(){
						$(this).attr('required',true);
					});
					
					var patternTitre = new RegExp("[a-zA-Z0-9]+(.{1,79})");
					var patternCom = new RegExp("[a-zA-Z0-9]+(.{1,1499})");
					
					titre = $("input[type='text']").val();
					com = $("textarea").val();
					if(patternTitre.test(titre) && patternTitre.test(com)){
						$(this).off('click');
					}
					else{
						e.preventDefault();
					}
					//----FIN PARTIE A MUTUALISER---//
				}
			});
		});
	</script>
</body>
</html>