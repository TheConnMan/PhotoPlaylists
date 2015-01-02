package com.madconn.photoplaylists

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PhotoController {
	
	def AWSService

    def scaffold = true
	
	def view(Long id) {
		Photo photo = Photo.get(id);
		Map obj = AWSService.getPhoto(photo);
		render file: obj.stream, contentType: obj.type
	}
}
