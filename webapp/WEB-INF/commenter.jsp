<!DOCTYPE html>
<html lang="fr">
<head>
	<title>Commenter</title>
	<meta charset="UTF-8"/>
	<link href="style/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="style/style.css" rel="stylesheet" type="text/css"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<style>
	
		textarea{
			resize:none;
		}
		
		h3{
			margin-left:15px;
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
		<div>
			<h3>Anciens commentaires</h3>
			<div id="oldComs">
				<c:import url="/jsp_include/commentaires.jsp"/>
			</div>
		</div>
	</div>
	<c:import url="/jsp_include/footer_js.jsp"/>
	<script>
		$(function(){
			$("button[type='submit']").popover({html:true});
			$("input[type='text'],textarea").each(function(){
				$(this).attr('required',true);
			})
			
			
			$("button[type='submit']").on("click",function(e){
				var patternTitre = new RegExp("[a-zA-Z0-9]+(.{1,79})");
				var patternCom = new RegExp("[a-zA-Z0-9]+(.{1,1499})");
				var titre = $("input[type='text']").val();
				var com = $("textarea").val();
				if(patternTitre.test(titre) && patternTitre.test(com)){
					$(this).off('click');
				}
				else{
					e.preventDefault();
				}
			});
		});
	</script>
</body>
</html>