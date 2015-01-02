<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>Photo Playlists</title>
	</head>
	<body>
		<g:render template="/layouts/menu" model="[menu: menu]" />
		<div>
			<h1>${ playlist.name }</h1>
		<div class="ui five cards">
		<g:each in="${ playlist.photos }">
			<div class="ui card">
				<div class="image" id="my-image">
					<img src="${ it.fileLocation }" />
					<div class="ui dimmer">
						<div class="content">
							<div class="center" style="text-align: left; vertical-align: top; padding: 10px">
								<h2>${ it.name }</h2>
								<p>${ it.description }</p>
								<p class="date">Uploaded on ${ it.uploadedDate.format('MM/dd/yyyy') } by ${ it.uploadedBy }</p>
							</div>
						</div>
					</div>
				</div>
				<div class="ui bottom attached button">
      				<i class="edit icon"></i>
     				 Edit
    			</div>
  			</div>
		</g:each>
		</div>
	</div>
	<script> 
		$(function() {
			$('.ui.card .image')
			  .dimmer({
			    on: 'hover'
			  })
			;	
		})
	</script>
	</body>
</html>