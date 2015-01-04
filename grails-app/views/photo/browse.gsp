<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>Photo Playlists</title>
	</head>
	<body>
		<div class="ui segment">
			<h1>My Photos</h1>
			<g:render template="/layouts/photoList" model="[photos: allPhotos, rowSize: 'five']" />
		</div>
	</body>
</html>