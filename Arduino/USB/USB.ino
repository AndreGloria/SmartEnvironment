#include "DHT.h"

#define RELAY1 7
#define DHTPIN 2
#define DHTTYPE DHT11

String action = "";
String port = "";
String value = "";
String mode = "mod1";
boolean stringCompleted = false;  // whether the string is complete
boolean actionPart = true;
boolean portPart = false;
boolean valuePart = false;

int led1 = 6;
int mod1 = 11;
int mod2 = 10;
int mod3 = 9;

long lastSendAttempt = 0;
long lastSendAttempt2 = 0;

bool change = true;

DHT dht(DHTPIN, DHTTYPE);

void setup() {
    pinMode(RELAY1, OUTPUT);
    pinMode(led1, OUTPUT);
    pinMode(mod1, OUTPUT);
    pinMode(mod2, OUTPUT);
    pinMode(mod3, OUTPUT);
    digitalWrite(mod1, HIGH);
    
    dht.begin();

    Serial.begin(9600); // start serial for output
}

void loop(){
  if(stringCompleted){
    if(action == "action"){
      if(value == "high")
        digitalWrite(port.toInt(), HIGH);
      if(value == "low")
        digitalWrite(port.toInt(), LOW);
    } else {
      if(action == "mode"){
        if(port == "mod1"){ 
            digitalWrite(mod1, HIGH);
            digitalWrite(mod2, LOW);
            digitalWrite(mod3, LOW);
            mode = "mod1";
        } else {
          if(port == "mod2"){ 
            digitalWrite(mod2, HIGH);
            digitalWrite(mod1, LOW);
            digitalWrite(mod3, LOW);
            mode = "mod2";
          } else {
            if(port == "mod3"){  
              digitalWrite(mod3, HIGH);
              digitalWrite(mod2, LOW);
              digitalWrite(mod1, LOW);
              mode = "mod3";
            }
          }
        }
      }
    }
    action = "";
    port = "";
    value = "";
    stringCompleted = false;
  }

    if(mode == "mod1"){ 
        normal();
    } else {
      if(mode == "mod2"){ 
        maintenance();
      } else {
        if(mode == "mod3"){  
          hibernation();
        }
      }
    }
}

void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    if (inChar == '\n') {
      stringCompleted = true;
      portPart = false;
      valuePart = false;
      actionPart = true;
    } else {
      if(inChar == ':'){
        actionPart = false;
        valuePart = false;
        portPart = true;
      } else {
        if(inChar == ','){
          actionPart = false;
          portPart = false;
          valuePart = true;
        } else {
          // add it to the inputString:
          if(actionPart)
            action += inChar;
          if(portPart)
            port += inChar;
          if(valuePart)
            value += inChar;
        }
      }
    }
  }
}

void normal(){
  long now = millis();
  if (now - lastSendAttempt > 10000) {
    lastSendAttempt = now;

    float h = dht.readHumidity();
    float t = dht.readTemperature();
    Serial.print("sensor:");
    Serial.print("hum1,");
    Serial.println(h);
    Serial.print("sensor:");
    Serial.print("temp1,");
    Serial.println(t);
  }
  if (now - lastSendAttempt2 > 5000) {
    lastSendAttempt2 = now;
    
    change = !change;
  }
  
  if(change){
    digitalWrite(RELAY1, HIGH);
  } else {
    digitalWrite(RELAY1, LOW);
  }
}

void maintenance(){
  long now = millis();
  if (now - lastSendAttempt > 10000) {
    lastSendAttempt = now;

    float h = dht.readHumidity();
    float t = dht.readTemperature();
    Serial.print("sensor:");
    Serial.print("hum1,");
    Serial.println(h);
    Serial.print("sensor:");
    Serial.print("temp1,");
    Serial.println(t);
  }
  if (now - lastSendAttempt2 > 10000) {
    lastSendAttempt2 = now;

    change = !change;
  }
  
  if(change){
    digitalWrite(RELAY1, HIGH);
  } else {
    digitalWrite(RELAY1, LOW);
  }
  
}

void hibernation(){
   digitalWrite(RELAY1, LOW);
}

