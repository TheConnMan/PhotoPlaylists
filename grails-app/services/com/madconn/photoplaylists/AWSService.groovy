package com.madconn.photoplaylists

import grails.transaction.Transactional
import com.amazonaws.services.s3.AmazonS3Client;

@Transactional
class AWSService {

	def grailsApplication
	
	void putPhoto(InputStream photo, String location) {
		String bucket = grailsApplication.config.s3bucket;
		AmazonS3Client s3 = new AmazonS3Client();
		s3.putObject(bucket, location, photo, null);
	}
}
