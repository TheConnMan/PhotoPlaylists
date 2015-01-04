<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>Photo Playlists</title>
	</head>
	<body>
		<div class="ui segment">
			<h1>${ playlist.name }</h1>
			<p>Created on ${ playlist.createdDate.format('M/d/yyyy') } by ${ playlist.createdBy }</p>
			<p>${ playlist.description }</p>
			<g:render template="/layouts/photoList" model="[photos: playlist.photos, rowSize: 'five']" />
		</div>
	</body>
</html>