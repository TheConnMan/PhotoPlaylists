package com.madconn.photoplaylists

import static org.springframework.http.HttpStatus.*

import java.util.Map;

import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PlaylistController {
	
	def springSecurityService

	def scaffold = true
	
	def home() {
		[menu: menu()]
	}
	
	@Secured(["ROLE_USER", "ROLE_ADMIN"])
	def view(Long id) {
		Playlist playlist = Playlist.get(id);
		println playlist.photos
		[menu: menu(), playlist: playlist]
		
	}
	
	@Secured(["ROLE_USER", "ROLE_ADMIN"])
	Map menu() {
		[playlists: Playlist.findAllByCreatedBy(springSecurityService.currentUser)]
	}
}
