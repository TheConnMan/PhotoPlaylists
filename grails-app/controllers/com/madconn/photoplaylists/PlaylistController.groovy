package com.madconn.photoplaylists

import static org.springframework.http.HttpStatus.*

import java.util.Map;

import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PlaylistController {
	
	def springSecurityService
	def AWSService

	def scaffold = true
	
	def home() {
		[menu: menu()]
	}
	
	def view(Long id) {
		Playlist playlist = Playlist.get(id);
		if (playlist) {
			[menu: menu(), playlist: playlist]
		} else {
			response.status = 404;
		}
	}
	
	def createPlaylist() {
		Playlist playlist = new Playlist(
			name: params.name,
			description: params.description,
			createdBy: springSecurityService.currentUser(),
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
		String loc = new Date().format('yyyy/MM/dd/HH/mm-ss-') + params['photo-file'].getOriginalFilename();
		AWSService.putPhoto(params['photo-file'].getInputStream(), loc);
		Photo photo = new Photo(
			name: params['photo-name'],
			description: params['photo-description'],
			uploadedBy: springSecurityService.currentUser(),
			uploadedDate: new Date(),
			lastUpdatedDate: new Date(),
			fileLocation: loc
		);
		photo.save();
		if (photo.hasErrors()) {
			render([success: false] as JSON)
		} else {
			Collection<Playlist> playlists = Playlist.findAllByCreatedBy(springSecurityService.currentUser());
			playlists.each {
				if (params['playlist-' + it.id] == 'on') {
					it.addToPhotos(photo);
					it.save();
				}
			}
			render([success: true] as JSON)
		}
	}
	
	Map menu() {
		[playlists: Playlist.findAllByCreatedBy(springSecurityService.currentUser())]
	}
}
