#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <DHT.h>

#define DHTPIN D4     // what pin we're connected to
#define DHTTYPE DHT11   // DHT 11
DHT dht(DHTPIN, DHTTYPE);

// Update these with values suitable for your network.

const char* ssid = "gloria";
const char* password = "2DA4CE0FC2";
const char* mqtt_server = "broker.hivemq.com";

WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[50];
int value = 0;

long lastReconnectAttempt = 0;
long lastSendAttempt = 0;

String sensors[] = {"temp1", "hum1"}; 
int sensorCount = 2;

void setup() {
  pinMode(D0, OUTPUT);     // Initialize the BUILTIN_LED pin as an output
  Serial.begin(9600);
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void setup_wifi() {

  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  String message = "";
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    message += (char)payload[i];
  }
  Serial.println();
  Serial.println(message);

  /*// Switch on the LED if an 1 was received as first character
  if ((char)payload[0] == '1') {
    client.publish("client/out","ON");
    digitalWrite(BUILTIN_LED, LOW);   // Turn the LED on (Note that LOW is the voltage level
    // but actually the LED is on; this is because
    // it is acive low on the ESP-01)
  } else {
    client.publish("client/out","OFF");
    digitalWrite(BUILTIN_LED, HIGH);// Turn the LED off by making the voltage HIGH
  }*/
  
  if (message == "update") {
    String out = "update:";
    for(int i = 0; i < sensorCount - 1; i++){
      out += sensors[i];
      out += ",";
    }
    out += sensors[sensorCount - 1];
    client.publish("client/in",out.c_str());
  }
}

/*void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP8266Client")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("client/out", "hello world");
      // ... and resubscribe
      client.subscribe("client/in");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}*/

boolean reconnect() {
  if (client.connect("arduinoClient")) {
    // Once connected, publish an announcement...
    //client.publish("client/in","led");
    // ... and resubscribe
    client.subscribe("client/out");
  }
  return client.connected();
}

void loop() {

  /*if (!client.connected()) {
    reconnect();
  }
  client.loop();*/
  if (!client.connected()) {
    long now = millis();
    if (now - lastReconnectAttempt > 5000) {
      lastReconnectAttempt = now;
      // Attempt to reconnect
      if (reconnect()) {
        lastReconnectAttempt = 0;
      }
    }
  } else {
    // Client connected
    long now = millis();
    if (now - lastSendAttempt > 5000) {
      lastSendAttempt = now;

      float t = dht.readTemperature();
      String out = "sensor:temp1," + String(t);
      client.publish("client/in",out.c_str());
  
      float h = dht.readHumidity();
      String out2 = "sensor:hum1," + String(h);
      client.publish("client/in",out2.c_str());
    }

    client.loop();
  }
}
