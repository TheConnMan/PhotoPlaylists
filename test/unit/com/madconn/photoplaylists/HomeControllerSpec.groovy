package com.madconn.photoplaylists

import com.madconn.photoplaylists.HomeController;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(HomeController)
class HomeControllerSpec extends Specification {

    void "test index page"() {
		when:"Index is requested"
			controller.index();
		
		then:"Response 200"
			response.status == 200;
    }

    void "test landing page"() {
		when:"Landing is requested"
			controller.landing();
		
		then:"Response 200"
			response.status == 200;
    }
}
