package com.madconn.photoplaylists

import grails.test.mixin.*
import spock.lang.*

@TestFor(PhotoController)
@Mock(Photo)
class PhotoControllerSpec extends Specification {
	
	def createPhoto() {
		return new Photo(
			name: 'Test',
			uploadedBy: getUser(),
			uploadedDate: new Date(),
			lastUpdated: new Date(),
			lastViewed: new Date(),
			fileLocation: 'Location'
		)
	}
	
	User getUser() {
		new User(username: 'Test', password: 'pass')
	}

	void "view photo returns the correct model"() {
		given:
			def mockAWSService = mockFor(AWSService);
			mockAWSService.demand.getPhoto { Photo p -> [stream: new ByteArrayInputStream(), type: 'image/png']; }
			controller.AWSService = mockAWSService.createMock();
		
		when:"The view action is executed with a null id"
			controller.view(null);
			
		then:"A 404 error is returned"
			response.status == 404;
		
		when:"The view action is executed with a correct id"
			response.reset();
			Photo photo = createPhoto();
			photo.save();
			Map result = controller.view(photo.id);
		
		then:"The correct playlist is returned"
			response.contentType.contains('image/png');
	}
}
