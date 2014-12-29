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
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="stylesheet" href="${resource(dir: 'semantic', file: 'semantic.min.css')}" type="text/css">
		<g:layoutHead/>
		<g:javascript library="application"/>		
		<r:layoutResources />
	</head>
	<body>
		<div class="ui segment">
			<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
		</div>
		<div style="max-width: 1000px; margin: 0 auto;">
			<div class="ui menu">
				<g:link controller="home" class="item">
					<i class="home icon"></i> Home
				</g:link>
				<sec:ifLoggedIn>
					<div class="right menu">
						<div class="ui dropdown item">
							<sec:username/> <i class="icon dropdown"></i>
							<div class="menu">
								<a class="item"><i class="edit icon"></i> Edit Profile</a>
								<a class="item"><i class="globe icon"></i> Choose Language</a>
								<a class="item"><i class="settings icon"></i> Account Settings</a>
							</div>
						</div>
					</div>
				</sec:ifLoggedIn>
			</div>
			<g:layoutBody/>
		</div>
		<div class="footer" role="contentinfo"></div>
		<r:layoutResources />
	</body>
</html>
