package com.madconn.photoplaylists

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PhotoController {
	
	def AWSService
	def springSecurityService
	
    def scaffold = true
	
	def view(Long id) {
		Photo photo = Photo.get(id);
		if (photo) {
			Map obj = AWSService.getPhoto(photo);
			render file: obj.stream, contentType: obj.type
		} else {
			response.status = 404;
		}
	}
	
	def browse() {
		[allPhotos: Photo.findAllByUploadedBy(springSecurityService.currentUser)]
	}
}
