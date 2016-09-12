#include <Wire.h>

#define SLAVE_ADDRESS 0x04
#define RELAY1 7

volatile bool    newData = false;
volatile uint8_t state   = false;

int number = 0;
int state = 0;

void setup() {
    pinMode(RELAY1, OUTPUT);

    Serial.begin(9600); // start serial for output
    // initialize i2c as slave
    Wire.begin(SLAVE_ADDRESS);

    // define callbacks for i2c communication
    Wire.onReceive(receiveData);
    Wire.onRequest(sendData);

    Serial.println("Ready!");
}

void receiveData(int byteCount)
{
    // Read all the bytes; only the last one changes the relay state
    while (byteCount-- > 0)
      number = Wire.read();

    if (state != number) {
      state   = number;
      newData = true;
    }
}

// callback for sending data
void sendData(){
    Wire.write(number);
}

void loop()
{
  if (newData) {
    newData = false; // clear the flag for next time

    if (number == 1){
        digitalWrite(RELAY1, HIGH); // set the LED on
    } else {
        digitalWrite(RELAY1, LOW); // set the LED off
    }

    Serial.print("data received: ");
    Serial.println( number );
  }
}
