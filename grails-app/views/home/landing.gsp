<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Photo Playlists</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<g:javascript src="jquery.min.js" />
        <script src="${resource(dir: 'semantic', file: 'semantic.min.js')}" charset="utf-8"></script>
		<link rel="stylesheet" href="${resource(dir: 'semantic', file: 'semantic.min.css')}" type="text/css">
	</head>
	<body>
		<div class="ui segment">
			<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
		</div>
		<div class="ui segment">
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
