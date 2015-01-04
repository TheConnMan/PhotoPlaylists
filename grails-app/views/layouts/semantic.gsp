<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Photo Playlists"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<g:javascript src="jquery.min.js" />
        <script src="${resource(dir: 'semantic', file: 'semantic.min.js')}" charset="utf-8"></script>
        <script src="${resource(dir: 'js', file: 'sweet-alert.min.js')}" charset="utf-8"></script>
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="stylesheet" href="${resource(dir: 'semantic', file: 'semantic.min.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'sweet-alert.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'photoplaylists.css')}" type="text/css">
		<g:layoutHead/>
		<g:javascript library="application"/>		
		<r:layoutResources />
	</head>
	<body>
		<sec:ifLoggedIn>
			<g:render template="/layouts/menu" model="[menu: menu]" />
			<div class="menu-content">
				<div class="ui segment">
					<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
				</div>
				<g:layoutBody/>
			</div>
		</sec:ifLoggedIn>
		<sec:ifNotLoggedIn>
			<div class="ui segment">
				<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
			</div>
			<g:layoutBody/>
		</sec:ifNotLoggedIn>
		<div class="footer"></div>
		<r:layoutResources />
	</body>
</html>
