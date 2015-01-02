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
			render([success: false] as JSON)
		} else {
			render([success: true, id: playlist.id] as JSON)
		}
	}
	
	def createPhoto() {
		String loc = 'Location';
		Photo photo = new Photo(
			name: params['photo-name'],
			description: params['photo-description'],
			uploadedBy: springSecurityService.currentUser,
			uploadedDate: new Date(),
			lastUpdatedDate: new Date(),
			fileLocation: loc
		);
		photo.save();
		if (photo.hasErrors()) {
			render([success: false] as JSON)
		} else {
			render([success: true] as JSON)
		}
	}
	
	Map menu() {
		[playlists: Playlist.findAllByCreatedBy(springSecurityService.currentUser)]
	}
}
