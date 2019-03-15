#include <Adafruit_NeoPixel.h>

const int PIN_STRIP = 2;
const int N_LEDS = 60;

const int PIN_FAN_1 = 5;
const int PIN_FAN_2 = 6;
const int PIN_VIB_1 = 9;
const int PIN_VIB_2 = 10;

const int DEVICE_TYPE_FAN = 81;
const int DEVICE_TYPE_VIB = 82;

boolean cmdComplete = false;
String command = ""; 

Adafruit_NeoPixel strip = Adafruit_NeoPixel(N_LEDS, PIN_STRIP, NEO_GRB + NEO_KHZ800);

void setup() {
  Serial.begin(9600);
  pinMode(PIN_FAN_1, OUTPUT); 
  pinMode(PIN_FAN_2, OUTPUT); 
  pinMode(PIN_VIB_1, OUTPUT); 
  pinMode(PIN_VIB_2, OUTPUT); 
  analogWrite(PIN_FAN_1, 0);
  analogWrite(PIN_FAN_2, 0);
  analogWrite(PIN_VIB_1, 0);
  analogWrite(PIN_VIB_2, 0);
  strip.begin();
  strip.show();
}

void loop() {
  // L* LED control
  // LICRGB. turn on LED at index I, until C, where R = red, G = green, B = blue  
  // F* fan control
  // FIX. fan device I with intensity X (0 until 255)  
  // V* vibration control
  // VIX. vibrator device I with intensity X (0 until 255)  
  
  if (cmdComplete){
    //Serial.println(command);
    switch(command[0]) {    
      case 'L':
        processLed();
       break;
      case 'F':
        processPwm(DEVICE_TYPE_FAN);
      break;
      case 'V': 
        processPwm(DEVICE_TYPE_VIB);
      break;
      default : break;
    }
    cmdComplete = false;
    command = "";
  }
}

void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read(); 
    if (inChar == '.') {
      cmdComplete = true;
    } else {
      command += inChar;
    }
  }
}

void processPwm(int deviceType){
  int deviceIndex, intensity = 0; 
  deviceIndex = command[1]; 
  intensity = command[2];
  if (intensity < 0)
    intensity = intensity + 256;
  else if (intensity >0 && intensity < 80)
      intensity = 80;
  if (deviceType == DEVICE_TYPE_FAN){
    //Serial.println("F I=" + (String)deviceIndex + " X="+ (String)intensity);
    if (deviceIndex == 1)
      analogWrite(PIN_FAN_1, intensity);
    else if (deviceIndex == 2)
      analogWrite(PIN_FAN_2, intensity);
  } 
  else if (deviceType == DEVICE_TYPE_VIB){
    //Serial.println("V I=" + (String)deviceIndex + " X="+ (String)intensity);
    if (intensity > 153)
      intensity = 153;
    if (deviceIndex == 1)
      analogWrite(PIN_VIB_1, intensity);
    else if (deviceIndex == 2)
      analogWrite(PIN_VIB_2, intensity);
  }
}

void processLed(){
  if (command.length() == 6){
    int ledIndex, count, red, green, blue = 0; 
    ledIndex = command[1]; 
    count = command[2];
    red = command[3];
    green = command[4];
    blue = command[5];
    if (red < 0)
      red = red + 256;
    if (green < 0)
      green = green + 256;
    if (blue < 0)
      blue = blue + 256;
    //Serial.println("L I=" + (String)ledIndex + " C=" + (String)count + " R=" + (String)red + " G=" + (String)green + " B=" + (String)blue);
    for (int i=0; i<count;i++){
      strip.setPixelColor(i + ledIndex, strip.Color(red,green,blue));
    }
    strip.show();
  }
}