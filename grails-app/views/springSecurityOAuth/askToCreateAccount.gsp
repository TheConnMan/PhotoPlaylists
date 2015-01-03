<head>
    <meta name="layout" content="semantic"/>
    <title>Create Account </title>
</head>

<body>

<div class='body'>
	<div class="ui segment" style="max-width: 600px; margin: 0 auto; padding: 15px;">
    	<g:link controller="login" action="auth" class="ui button blue basic icon labeled">
    		<i class="icon left arrow"></i>
    		<g:message code="springSecurity.oauth.registration.back" default="Back to login page"/>
    	</g:link>
    	
		<h1 class="ui header"><g:message code="springSecurity.oauth.registration.create.legend" default="Create a new account"/></h1>
    	
	    <g:if test='${ flash.error }'>
	        <div class="ui negative message">
	        	<i class="close icon"></i>
	        	<div class="header">
	        		${ flash.error }
	        	</div>
	        </div>
	    </g:if>
	
	    <h4><g:message code="springSecurity.oauth.registration.link.not.exists" default="No user was found with this account." args="[session.springSecurityOAuthToken?.providerName]"/></h4>

	    <g:hasErrors bean="${createAccountCommand}">
	    <div class="errors">
	        <g:renderErrors bean="${createAccountCommand}" as="list"/>
	    </div>
	    </g:hasErrors>

	    <g:form action="createAccount" method="post" autocomplete="off" class="ui form">
	            <div class="field">
	                <label><g:message code="OAuthCreateAccountCommand.username.label" default="Username"/>:</label>
	                <g:textField name='username' value='${createAccountCommand?.username}'/>
	            </div>
	            <div style="text-align: center;">
	            	<g:submitButton class="ui button primary" name="${message(code: 'springSecurity.oauth.registration.create.button', default: 'Create')}"/>
            	</div>
	    </g:form>
	</div>
</div>

</body>
