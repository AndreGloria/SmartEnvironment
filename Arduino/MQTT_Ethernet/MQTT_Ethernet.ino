#include <SPI.h>
#include <Ethernet.h>
#include <PubSubClient.h>
#include <DHT.h>

#define DHTPIN 2     // what pin we're connected to
#define DHTTYPE DHT11   // DHT 11
DHT dht(DHTPIN, DHTTYPE);

// Update these with values suitable for your network.
byte mac[] = { 0xC8, 0x2A, 0x14, 0x1E, 0xAB, 0xB4 };   //physical mac address
byte ip[] = { 192, 168, 1, 99 };                      // ip in lan (that's what you need to use in your browser. ("192.168.1.178")
byte gateway[] = { 192, 168, 1, 1 };                   // internet access via router
byte subnet[] = { 255, 255, 255, 0 };                  //subnet mask

const char* mqtt_server = "broker.hivemq.com";

EthernetClient ethClient;
PubSubClient client(ethClient);

long lastMsg = 0;
char msg[50];
int value = 0;

int led = 13;

long lastReconnectAttempt = 0;

void setup(){
  Serial.begin(9600);
  Ethernet.begin(mac, ip, gateway, subnet);

  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);

  dht.begin();
}

void loop(){
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
    float t = dht.readTemperature();
    client.publish("gloria/out",String(t).c_str());

    client.loop();
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  // Switch on the LED if an 1 was received as first character
  if ((char)payload[0] == '1') {
    client.publish("gloria/out","ON");
    digitalWrite(led, HIGH);
  } else {
    client.publish("gloria/out","OFF");
    digitalWrite(led, LOW);
  }

}

boolean reconnect() {
  Serial.println("Connecting...");
  if (client.connect("arduinoClient")) {
    Serial.println("...");
    // Once connected, publish an announcement...
    client.publish("gloria/out","led");
    // ... and resubscribe
    client.subscribe("gloria/in");
  } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
    }
  return client.connected();
}
