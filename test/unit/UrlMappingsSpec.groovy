import com.madconn.photoplaylists.HomeController
import com.madconn.photoplaylists.PlaylistController
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(UrlMappings)
@Mock([HomeController, PlaylistController])
class UrlMappingsSpec extends Specification {

	void "test forward mappings"() {
		expect:
			assertUrlMapping("/", controller: 'playlist', action: "home")
			assertUrlMapping("/landing", controller: 'home', action: "landing")
	}
}