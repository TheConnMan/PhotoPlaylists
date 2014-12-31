<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>Photo Playlists</title>
	</head>
	<body>
		<div id="page-body" role="main">
			<sec:ifNotLoggedIn>
				<oauth:connect provider="google" class="ui google plus button" style="float: right;">
					<i class="google plus icon"></i>
					Log In With Google
				</oauth:connect>
			</sec:ifNotLoggedIn>
			<h1>Welcome to Photo Playlists</h1>
		</div>
	</body>
</html>
