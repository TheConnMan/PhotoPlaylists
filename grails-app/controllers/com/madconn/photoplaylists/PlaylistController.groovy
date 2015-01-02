package com.madconn.photoplaylists

import static org.springframework.http.HttpStatus.*

import java.util.Map;

import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PlaylistController {
	
	def springSecurityService

	def scaffold = true
	
	def home() {
		[menu: menu()]
	}
	
	def view(Long id) {
		Playlist playlist = Playlist.get(id);
		[menu: menu(), playlist: playlist]
		
	}
	
	def createPlaylist() {
		Playlist playlist = new Playlist(
			name: params.name,
			description: params.description,
			createdBy: springSecurityService.currentUser,
			createdDate: new Date(),
			lastViewedDate: new Date(),
			lastEditedDate: new Date()
		);
		playlist.save();
		if (playlist.hasErrors()) {
			render([false: true] as JSON)
		} else {
			render([success: true, id: playlist.id] as JSON)
		}
	}
	
	Map menu() {
		[playlists: Playlist.findAllByCreatedBy(springSecurityService.currentUser)]
	}
}
