package com.madconn.photoplaylists

import grails.transaction.Transactional
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;

@Transactional
class AWSService {

	def grailsApplication
	
	void putPhoto(InputStream photo, String location) {
		String bucket = grailsApplication.config.s3bucket;
		AmazonS3Client s3 = new AmazonS3Client();
		s3.putObject(bucket, location, photo, null);
	}
	
	Map getPhoto(Photo photo) {
		String bucket = grailsApplication.config.s3bucket;
		AmazonS3Client s3 = new AmazonS3Client();
		S3Object obj = s3.getObject(bucket, photo.fileLocation);
		return [stream: obj.getObjectContent(), type: obj.getObjectMetadata().getContentType()];
	}
}
