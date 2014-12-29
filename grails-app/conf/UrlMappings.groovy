class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
		"/"(controller:'home')
        "/controllers"(view:'/index')
		'/oauth/askToCreateAccount'(controller: 'springSecurityOAuth', action: 'askToCreateAccount')
        "500"(view:'/error')
	}
}
