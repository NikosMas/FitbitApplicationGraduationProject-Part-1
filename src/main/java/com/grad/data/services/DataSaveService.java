package com.grad.data.services;

import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grad.auth.services.AccessTokenRequestService;
import com.grad.auth.services.RefreshTokenRequestService;
import com.grad.collections.CollectionEnum;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author nikos_mas
 */

@Service
public class DataSaveService {

	@Autowired
	private ObjectMapper mapperGet;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private AccessTokenRequestService fitbitTokenService;
	private RefreshTokenRequestService refreshTokenService;
	private static String accessToken;
	
	@Autowired
	public DataSaveService(AccessTokenRequestService fitbitTokenService, RefreshTokenRequestService refreshTokenService) {
		this.fitbitTokenService = fitbitTokenService;
		this.refreshTokenService = refreshTokenService;
	}

	public void dataTypeInsert(ResponseEntity<String> responseData, String collection, String filterCollectionName)
			throws IOException, JsonProcessingException {
		JsonNode responseDataBody = mapperGet.readTree(responseData.getBody());
		DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());

		if (collection.equals(CollectionEnum.ACTIVITIES_LIFETIME.getDescription())) {
			mongoTemplate.insert(dataToInsert, collection);
		} else if (filterCollectionName.equals("user")) {
			DBObject filteredValue = (DBObject) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		} else {
			BasicDBList filteredValue = (BasicDBList) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		}
	}

	public HttpEntity<String> getEntity() throws JsonProcessingException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAccessToken());
		return new HttpEntity<String>(headers);
	}

	protected String getAccessToken() throws JsonProcessingException, IOException {
		if (accessToken == null) {
			
			accessToken = fitbitTokenService.token();
			
		}else if (redisTemplate.opsForValue().get("RefreshToken") != null){
			
			accessToken = refreshTokenService.refreshToken();
		}
		return accessToken;
	}
}
