package com.madconn.photoplaylists

import static org.springframework.http.HttpStatus.*

import java.util.Map;
import org.springframework.web.multipart.commons.CommonsMultipartFile

import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PlaylistController {
	
	def springSecurityService
	def AWSService

	def scaffold = true
	
	def home() { }
	
	def view(Long id) {
		Playlist playlist = Playlist.get(id);
		if (playlist) {
			[playlist: playlist]
		} else {
			response.status = 404;
		}
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
			render([success: false, error: 'Please choose a unique playlist name.'] as JSON)
		} else {
			render([success: true, id: playlist.id] as JSON)
		}
	}
	
	def createPhoto() {
		Collection<Long> playlistIds = params.keySet().grep { it.startsWith('playlist-') }.collect { it.split('-')[1].toLong() }
		if (params['multiple-photos-on'] == 'on') {
			boolean success = true;
			Collection<String> files = params.keySet().grep { it.startsWith('file-photo-') };
			Map result = [:];
			files.each{
				result = createSinglePhoto(params[it], params[it].getOriginalFilename(), '', playlistIds);
				success = success && result.success;
			}
			render([success: success, error: result.error] as JSON)
		} else {
			if (!params['photo-file'].getOriginalFilename() || !params['photo-name']) {
				render([success: false, error: 'Please choose a file and a name.'] as JSON);
			} else {
				Map result = createSinglePhoto(params['photo-file'], params['photo-name'], params['photo-description'], playlistIds);
				render(result as JSON)
			}
		}
	}
	
	private Map createSinglePhoto(CommonsMultipartFile multipartFile, String name, String description, Collection<Long> playlistIds) {
		String loc = new Date().format('yyyy/MM/dd/HH/mm-ss-') + multipartFile.getOriginalFilename();
		AWSService.putPhoto(multipartFile.getInputStream(), loc);
		Photo photo = new Photo(
			name: name,
			description: description,
			uploadedBy: springSecurityService.currentUser,
			uploadedDate: new Date(),
			lastUpdatedDate: new Date(),
			fileLocation: loc
		);
		photo.save();
		if (photo.hasErrors()) {
			[success: false, error: 'Please choose a unique photo name.']
		} else {
			Collection<Playlist> playlists = playlistIds.collect { Playlist.get(it); };
			playlists.each {
				it.addToPhotos(photo);
				it.save();
			}
			[success: true]
		}
	}
	
	def editPhoto() {
		Photo photo = Photo.get(params.id);
		if (!photo || !params.name) {
			render([success: false] as JSON)
		} else {
			photo.name = params.name;
			photo.description = params.description;
			photo.save();
			if (photo.hasErrors()) {
				render([success: false] as JSON)
			} else {
				render([success: true] as JSON)
			}
		}
	}
}
