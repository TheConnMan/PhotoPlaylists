<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="semantic"/>
		<title>Photo Playlists</title>
	</head>
	<body>
		<div class="ui left inverted vertical sidebar menu">
			<div class="header item">
				<i class="list icon"></i>
				My Playlists
			</div>
			<g:each in="${ playlists }">
				<a class="item">
					${ it.name }
				</a>
			</g:each>
			<a class="item">
				<i class="plus icon"></i>
				Create A New Playlist
			</a>
			<div class="header item">
				<i class="photo icon"></i>
				My Photos
			</div>
			<a class="item">
				Browse
			</a>
			<a class="item">
				<i class="plus icon"></i>
				Add A New Photo
			</a>
		</div>
		<div>
			<sec:ifNotLoggedIn>
				<oauth:connect provider="google" class="ui google plus button" style="float: right;">
					<i class="google plus icon"></i>
					Log In With Google
				</oauth:connect>
			</sec:ifNotLoggedIn>
			<h1>Welcome to Photo Playlists</h1>
		</div>
		<script>
			$(function() {
				$('.left.sidebar').sidebar('toggle');
			})
		</script>
	</body>
</html>