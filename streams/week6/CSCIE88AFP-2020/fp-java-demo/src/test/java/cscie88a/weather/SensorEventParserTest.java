package cscie88a.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SensorEventParserTest {

    @Test
    void test_getSensorEventFromString() throws JsonProcessingException {
        SensorEvent testEventAsObjectIn = createTestEvent();
        String eventAsString = SensorEventParser.getSensorEventAsJsonString(testEventAsObjectIn);
        SensorEvent sensorEventOut = SensorEventParser.getSensorEventFromString(eventAsString);
        assertEquals(testEventAsObjectIn, sensorEventOut);
    }

    @Test
    void test_getSensorEventAsJsonString() throws JsonProcessingException {
        SensorEvent testEventObject = createTestEvent();
        String eventAsString = SensorEventParser.getSensorEventAsJsonString(testEventObject);
        System.out.println("weather event as JSON string: " + eventAsString);
    }

    private SensorEvent createTestEvent(){
        SensorEvent event = new SensorEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventTimestamp(Instant.now().getEpochSecond());
        event.setSensorId("sensorId1");
        event.setWindDirection("S");
        event.setZipCode("02144");
        event.setHumidityPercentage(0.5f);
        event.setWindSpeedInMPH(20L);
        event.setPressureInAtm(1.2f);
        event.setTemperatureInCelcius(25.0f);
        return event;
    }
}