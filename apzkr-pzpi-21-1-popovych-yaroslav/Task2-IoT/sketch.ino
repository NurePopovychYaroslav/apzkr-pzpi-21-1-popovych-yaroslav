#include <ESP32Servo.h>
#include <LiquidCrystal_I2C.h>
#include <Keypad.h>


#include <HTTPClient.h>
#include "WiFi.h"


#define servoPin 15

Servo servo;

LiquidCrystal_I2C lcd(0x27, 16, 2);

const byte ROWS = 4;
const byte COLS = 4;

char keys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'c'},
  {'*', '0', '#', 'D'},
};
byte rowPins[ROWS] = {23, 19, 18, 5};
byte colPins[COLS] = {17, 16, 4, 2};

bool isDoorLocked = true;
String password = "";

byte maxPasswordLength = 6;
byte currentPasswordLength = 0;

String doorId;

Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS);

void setup() {
  Serial.begin(115200);

  Serial.begin(115200);
  Serial.println("Enter Door ID: ");
  while (!Serial.available()); // Wait for input
  doorId = Serial.readStringUntil('\n');
  
  servo.attach(servoPin);
  servo.write(10);
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("Door LOCK System");
  delay(2000);
  lcd.clear();
}

void loop() {
  lcd.setCursor(3, 0);
  isDoorLocked ? lcd.print("DOOR LOCKED") : lcd.print("DOOR OPEN");

  char key = keypad.getKey();
  Serial.println(key);
  if (key != NO_KEY) {
    delay(100);

    if (key == '#') {
      resetPassword();
    } else if (key == '*') {
      isDoorLocked = !isDoorLocked;
      isDoorLocked ? doorLocked() : doorOpen();
    } else {
      processNumberKey(key);
    }
  }
}

void processNumberKey(char key) {
  if (currentPasswordLength < maxPasswordLength) {
    lcd.setCursor(currentPasswordLength + 5, 1);
    lcd.print("*");
    password+= key;
    currentPasswordLength++;
  }
}

void doorLocked() {
  if (checkAccess(password)) {
    servo.write(10);
    displayMessage("Password Correct", "LOCKED");
  } else {
    displayMessage("Wrong Password", "Try Again");
  }
  resetPassword();
}

void doorOpen() {
  if (checkAccess(password)) {
    servo.write(180);
    displayMessage("Password Correct", "UNLOCKED");
  } else {
    displayMessage("Wrong Password", "Try Again");
  }
  resetPassword();
}

void resetPassword() {
  password = "";
  currentPasswordLength = 0;
  lcd.clear();
  lcd.setCursor(0, 0);
}

void displayMessage(const char *line1, const char *line2) {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(line1);
  lcd.setCursor(3, 1);
  lcd.print(line2);
  lcd.clear();
}

bool checkAccess(String id) {
  String api = "http://localhost:8080/api/v1/student/" + id + "/door/" + doorId + "/available";
  
  wifiConnect();

  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("API: " + api);

    HTTPClient http;
    http.begin(api);
    http.addHeader("Content-Type", "application/json");

    int httpResponseCode = http.GET();

    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.println(httpResponseCode);
      Serial.println(response);

      if (response == "true") {
        return true;
      } else {
        return false;
      }
    } else {
      Serial.print("Error on sending POST: ");
      Serial.println(httpResponseCode);
      return false;
    }

    http.end();
    
  }
}

void wifiConnect() {
  int n = WiFi.scanNetworks();
  Serial.println("Scan done!");
  if (n == 0) {
    Serial.println("No networks found.");
  } else {
    Serial.println();
    Serial.print(n);
    Serial.println(" networks found");
    for (int i = 0; i < n; ++i) {
      Serial.println(WiFi.SSID(i));
      if (WiFi.SSID(i) == "Wokwi-GUEST") {
        Serial.begin(9600);
        Serial.print("Connecting to WiFi");
        WiFi.begin("Wokwi-GUEST", "", 6);
        while (WiFi.status() != WL_CONNECTED) {
          delay(100);
          Serial.print(".");
        }
        Serial.println(" Connected!");
      }
      delay(10);
    }
  }
  Serial.println("");
}