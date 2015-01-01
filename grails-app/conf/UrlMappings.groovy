class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
		"/"(controller:'playlist',action:'home')
		"/landing"(controller:'home',action:'landing')
        "/controllers"(view:'/index')
		'/oauth/askToCreateAccount'(controller: 'springSecurityOAuth', action: 'askToCreateAccount')
        "500"(view:'/error')
	}
}
