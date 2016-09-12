#define RELAY1 7

String action = "";
String port = "";
String value = "";
boolean stringCompleted = false;  // whether the string is complete
boolean actionPart = true;
boolean portPart = false;
boolean valuePart = false;

void setup() {
    pinMode(RELAY1, OUTPUT);

    Serial.begin(9600); // start serial for output
}

void loop(){
  if(stringCompleted){
    if(action == "action"){
      if(value == "high")
        digitalWrite(port.toInt(), HIGH);
      if(value == "low")
        digitalWrite(port.toInt(), LOW);
    }
    Serial.println(action);
    Serial.println(port);
    Serial.println(value);
    action = "";
    port = "";
    value = "";
    stringCompleted = false;
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
