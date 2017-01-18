package com.grad.data.req;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * sleep-data-request class. 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitSleepData {
	
	private static final String URI_TIME_IN_BED = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/";
	private static final String URI_MINUTES_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/";
	private static final String URI_MINUTES_AWAKE = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/";
	private static final String URI_TO_FALL_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/";
	private static final String URI_AFTER_WAKE_UP = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/";
	private static final String URI_EFFICIENCY = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/";
	
	private static final String SLEEP_EFFICIENCY = "sleep_efficiency";
	private static final String SLEEP_MINUTES_TO_FALL_ASLEEP = "sleep_minutesToFallAsleep";
	private static final String SLEEP_MINUTES_AFTER_WAKE_UP = "sleep_minutesAfterWakeUp";
	private static final String SLEEP_MINUTES_AWAKE = "sleep_minutesAwake";
	private static final String SLEEP_MINUTES_ASLEEP = "sleep_minutesAsleep";
	private static final String SLEEP_TIME_IN_BED = "sleep_timeInBed";
	
	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json"
															,"2016-03-01/2016-05-31.json"
															,"2016-06-01/2016-08-31.json"
															,"2016-09-01/2016-11-30.json");
	@Autowired
	private RestTemplate restTemplateGet;
	
	@Autowired
	private FitbitDataSave fdata;

	public void sleep() throws JsonProcessingException, IOException  {
		
		for(String temp : months){
			ResponseEntity<String> dataTimeInBed = restTemplateGet.exchange(URI_TIME_IN_BED + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataTimeInBed, SLEEP_TIME_IN_BED);
			ResponseEntity<String> dataMinutesAsleep = restTemplateGet.exchange(URI_MINUTES_ASLEEP + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataMinutesAsleep, SLEEP_MINUTES_ASLEEP);
			ResponseEntity<String> dataMinutesAwake = restTemplateGet.exchange(URI_MINUTES_AWAKE + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataMinutesAwake, SLEEP_MINUTES_AWAKE);
			ResponseEntity<String> dataAfterWakeup = restTemplateGet.exchange(URI_AFTER_WAKE_UP + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataAfterWakeup, SLEEP_MINUTES_AFTER_WAKE_UP);
			ResponseEntity<String> dataToFallAsleep = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataToFallAsleep, SLEEP_MINUTES_TO_FALL_ASLEEP);
			ResponseEntity<String> dataEfficiency = restTemplateGet.exchange(URI_EFFICIENCY + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataEfficiency, SLEEP_EFFICIENCY);
       }
	}
}