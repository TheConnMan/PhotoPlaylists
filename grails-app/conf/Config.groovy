// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

def loc = ['../UserConfig.groovy', 'webapps/ROOT/Jenkins.groovy'].grep { new File(it).exists() }.first();
def localConfig = new ConfigSlurper(grailsSettings.grailsEnv).parse(new File(loc).toURI().toURL())

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.excludes = ['/WEB-INF/**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}

 
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
		s3bucket = 'photoplaylists-dev'
    }
    devdeploy {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://photoplaylists.theconnman.com"
		s3bucket = 'photoplaylists'
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://photoplaylists.theconnman.com"
		s3bucket = 'photoplaylists'
    }
}

// log4j configuration
log4j = {
    appenders {
		'null' name: 'stacktrace'
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n'), threshold: org.apache.log4j.Level.INFO
		file name: 'file', layout:pattern(conversionPattern: '%d{dd-MM-yyyy HH:mm:ss} %p %c{2} - %m%n'), file: './Log.log', threshold: org.apache.log4j.Level.DEBUG
    }
	root {
		org.apache.log4j.Level.OFF
	}
	
	environments {
		development {
			info	stdout:	['com.madconn.photoplaylists', 'grails.app.conf'], additivity: false
		}
	}
    debug	file:	['com.madconn.photoplaylists', 'grails.app.conf'], additivity: false
	off 'org.grails.plugin.resource.ResourceMeta'
}

grails.app.context = '/'

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.madconn.photoplaylists.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.madconn.photoplaylists.UserRole'
grails.plugin.springsecurity.authority.className = 'com.madconn.photoplaylists.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':								['permitAll'],
	'/index':							['permitAll'],
	'/index.gsp':						['permitAll'],
	'/**/js/**':						['permitAll'],
	'/**/css/**':						['permitAll'],
	'/**/images/**':					['permitAll'],
	'/**/favicon.ico':					['permitAll'],
	'/oauth/**':						['permitAll'],
	'/springSecurityOAuth/**':			['permitAll'],
	'/home/landing':					['permitAll'],
	'/playlist':						['ROLE_ADMIN'],
	'/playlist/show/**':				['ROLE_ADMIN'],
	'/playlist/edit/**':				['ROLE_ADMIN'],
	'/playlist/**':						['ROLE_USER', 'ROLE_ADMIN'],
	'/photo':							['ROLE_ADMIN'],
	'/photo/show/**':					['ROLE_ADMIN'],
	'/photo/edit/**':					['ROLE_ADMIN'],
	'/photo/**':						['ROLE_USER', 'ROLE_ADMIN']
]

grails.plugin.springsecurity.auth.loginFormUrl = '/landing'

def baseURL = grails.serverURL ?: "http://localhost:${System.getProperty('server.port', '8080')}"
oauth {
	debug = true
	providers {
		google {
			api = org.grails.plugin.springsecurity.oauth.GoogleApi20
			key = localConfig.photoplaylists.oauth.google.key
			secret = localConfig.photoplaylists.oauth.google.secret
			successUri = '/oauth/google/success'
			failureUri = '/oauth/google/failure'
			callback = "${baseURL}/oauth/google/callback"
			scope = 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email'
		}
	}
}
// Added by the Spring Security OAuth plugin:
grails.plugin.springsecurity.oauth.domainClass = 'com.madconn.photoplaylists.OAuthID'
grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri = '/oauth/askToCreateAccount'
