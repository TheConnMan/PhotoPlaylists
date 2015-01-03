package com.madconn.photoplaylists

import grails.test.mixin.TestFor
import spock.lang.*
import grails.plugin.springsecurity.SpringSecurityService

@TestFor(PlaylistController)
@Mock(Playlist)
class PlaylistControllerSpec extends Specification {

	void "view playlist returns the correct model"() {
		given:
			controller.metaClass.menu { -> [:] }
		
		when:"The view action is executed with a null id"
			controller.view(null)
			
		then:"A 404 error is returned"
			response.status == 404
	}
}
