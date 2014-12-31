package com.madconn.photoplaylists

class HomeController {

	def springSecurityService
	
    def index() {
		[playlists: Playlist.findAllByCreatedBy(springSecurityService.currentUser)]
	}

    def landing() { }
}
