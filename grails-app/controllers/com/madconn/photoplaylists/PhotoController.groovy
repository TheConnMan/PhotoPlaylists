package com.madconn.photoplaylists

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PhotoController {

    def scaffold = true
}
