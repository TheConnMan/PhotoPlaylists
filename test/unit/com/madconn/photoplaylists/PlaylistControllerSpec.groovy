package com.madconn.photoplaylists

import grails.test.mixin.TestFor
import spock.lang.*
import grails.plugin.springsecurity.SpringSecurityService
import grails.converters.JSON

@TestFor(PlaylistController)
@Mock([Playlist, User])
class PlaylistControllerSpec extends Specification {
	
	def populateValidParams() {
		params.name = 'Test';
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
		
		when:"request contains invalid params"
			controller.createPlaylist();
		
		then:"Success is false"
			!JSON.parse(response.text).success;
		
		when:"request contains valid params"
			response.reset();
			populateValidParams();
			controller.createPlaylist();
		
		then:"Success is true"
			JSON.parse(response.text).success;
			JSON.parse(response.text).id != null;
	}
}
