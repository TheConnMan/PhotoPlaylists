package com.madconn.photoplaylists

class HomeController {

	def springSecurityService
	
    def index() {
		[menu: menu()]
	}

    def landing() { }
	
	Map menu() {
		[playlists: Playlist.findAllByCreatedBy(springSecurityService.currentUser)]
	}
}
