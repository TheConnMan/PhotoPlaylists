package com.madconn.photoplaylists

import grails.test.mixin.TestFor
import org.springframework.mock.web.MockMultipartFile
import spock.lang.*
import grails.plugin.springsecurity.SpringSecurityService
import grails.converters.JSON

@TestFor(PlaylistController)
@Mock([Playlist, User, Photo])
class PlaylistControllerSpec extends Specification {
	
	def populateValidPlaylistParams() {
		params.name = 'Test';
	}
	
	def populateValidPhotoParams() {
		params['photo-name'] = 'Test';
		params['photo-file'] = new MockMultipartFile(
			'Test File',
			'Original Name',
			'image/png',
			(byte[])[]
		);
	}
	
	def createPlaylist() {
		return new Playlist(
			name: 'Test',
			createdBy: getUser(),
			createdDate: new Date(),
			lastViewedDate: new Date(),
			lastEditedDate: new Date()
		)
	}
	
	User getUser() {
		new User(username: 'Test', password: 'pass')
	}

	void "view playlist returns the correct model"() {
		given:
			controller.metaClass.menu { -> [:] };
		
		when:"The view action is executed with a null id"
			controller.view(null);
			
		then:"A 404 error is returned"
			response.status == 404;
		
		when:"The view action is executed with a correct id"
			response.reset();
			Playlist playlist = createPlaylist();
			playlist.save();
			Map result = controller.view(playlist.id);
		
		then:"The correct playlist is returned"
			result.playlist == playlist;
	}
	
	void "create playlist returns correct results"() {
		given:
			controller.springSecurityService = [currentUser: getUser()];
		
		when:"Request contains invalid params"
			controller.createPlaylist();
		
		then:"Success is false"
			!JSON.parse(response.text).success;
		
		when:"Request contains valid params"
			response.reset();
			populateValidPlaylistParams();
			controller.createPlaylist();
		
		then:"Success is true"
			JSON.parse(response.text).success;
			JSON.parse(response.text).id != null;
	}
	
	void "create photo returns correct results"() {
		given:
			controller.springSecurityService = [currentUser: getUser()];
			def mockAWSService = mockFor(AWSService);
			mockAWSService.demand.putPhoto { InputStream i, String loc ->  };
			controller.AWSService = mockAWSService.createMock();
		
		when:"Request contains no params"
			controller.createPhoto();
		
		then:"Success is false"
			!JSON.parse(response.text).success;
			JSON.parse(response.text).error != null;
		
		when:"Request contains valid params"
			response.reset();
			populateValidPhotoParams();
			controller.createPhoto();
		
		then:"Success is true"
			JSON.parse(response.text).success;
	}
}
