package cscie97.smartcity.model;

/* The Sensor class generates events that are processed by the Virtual IoT Devices. There are 4 types of sensors,
namely microphones, CO2 meters, cameras, and thermometers. Microphones are able to convert speech to text, and both
the microphone and camera sensors use AI to automatically identify the subject person. The CO2 Sensor may generate
the events measuring the total "parts per million" (denoted "ppm", and used to measure the concentration of a
contaminant in soils and sediments), similarly, the thermometer may report the current ambient temperature, or the
temperature of an individual person. There is additionally, a speaker sensor to help broadcast events to the devices. */
public enum SensorType{
    microphone, // A microphone sensor intended to process vocal interactions
    camera, // A camera sensor intended to process visual interactions
    thermometer, // A thermometer sensor intended to process thermal fluctuations
    co2meter, // A CO2 meter sensor intended to process air quality changes
    speaker, // A speaker sensor intended to broadcast events out to devices
    gps // A GPS system intended to determine the current location of the device
}
