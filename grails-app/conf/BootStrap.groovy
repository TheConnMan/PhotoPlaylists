import java.util.Date;

import com.madconn.photoplaylists.Playlist
import com.madconn.photoplaylists.Role
import com.madconn.photoplaylists.User
import com.madconn.photoplaylists.UserRole

class BootStrap {
	
	def grailsApplication
	def AWSService
	
	def createUser(name, password, role) {
		def me = new User(username: name, password: password, enabled: true).save()
		UserRole.create(me, role, true)
	}

    def init = { servletContext ->
		println 'Bootstrapping'
		def adminRole = new Role(authority: "ROLE_ADMIN").save()
		def userRole = new Role(authority: "ROLE_USER").save()
		createUser('admin', 'theconnman', adminRole)
		createUser('user', 'theconnman', userRole)
		
		User admin = User.findByUsername('admin');
		File file = grailsApplication.mainContext.getResource('images/Orion_Launch.jpg').file;
		String loc = new Date().format('yyyy/MM/dd/HH/mm-ss-') + 'Test.jpg';
		AWSService.putPhoto(file.newInputStream(), loc);
		(1..5).each { i ->
			Playlist p = new Playlist(
				name: 'Playlist ' + i,
				description: 'Description ' + i,
				createdBy: admin,
				createdDate: new Date(),
				lastViewedDate: new Date(),
				lastEditedDate: new Date()
			).save()
			(1..5).each {
				p.addToPhotos(
					name: 'Photo ' + i + ' ' + it,
					description: 'Description ' + it,
					uploadedDate: new Date(),
					uploadedBy: admin,
					lastUpdated: new Date(),
					fileLocation: loc
				)
			}
		}
    }
    def destroy = {
		
    }
}
