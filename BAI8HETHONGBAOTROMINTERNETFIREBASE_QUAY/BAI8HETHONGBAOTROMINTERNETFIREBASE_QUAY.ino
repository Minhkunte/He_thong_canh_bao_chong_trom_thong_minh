//FirebaseESP8266.h must be included before ESP8266WiFi.h
#include <ESP8266WiFi.h>
#include <SPI.h>
#include <MFRC522.h>
#include <ArduinoJson.h>
#include "FirebaseESP8266.h"  // Install Firebase ESP8266 library

#define FIREBASE_HOST "lehoangson-5d344-default-rtdb.asia-southeast1.firebasedatabase.app/" //Without http:// or https:// schemes
#define FIREBASE_AUTH "R7ASxz3due7Ws2gbGKL1dKskcGpLBzyIlXpeT0vs"
#define WIFI_SSID "abcd"
#define WIFI_PASSWORD "10120623"

int relay=2;//D4
int cb=4;   //D2
int leddo=0; //D3
int ledxanh=5; //D1
int PinRST = 16; //D0
int PinSDA = 15; //D8
int UID[4], i;
bool baodong;
int ID1[4] = {161, 23, 245, 38};
int ID2[4] = {162, 230, 222, 33};


MFRC522 rfid(PinSDA, PinRST); // Khai báo đối tượng rfid

//Define FirebaseESP8266 data object
FirebaseData firebaseData;
//FirebaseJson json;

String fireStatus = "";// biến chứa trạng thái của đèn LED
String path = "He thong chong trom/";// Đường dẫn tới trường LED trong cấu trúc dữ liệu trên firebase

void setup()
{

  Serial.begin(115200);
  pinMode(relay,OUTPUT);
  pinMode(leddo,OUTPUT);
  pinMode(ledxanh,OUTPUT);
  pinMode(cb,INPUT);
  SPI.begin(); // Khởi động thư viện SPI
  rfid.PCD_Init(); // Khởi động RFID
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  Serial.print(WIFI_SSID);

  while(WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }

  Serial.println();
  Serial.print("Conneted to ");
  Serial.println(WIFI_SSID);

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);// Kết nối tới firebase
  Firebase.reconnectWiFi(true);
}

void sensorUpdate(){
  int h = digitalRead(cb);
  Serial.print("cb:");
  Serial.print(h);
}

void Docfirebase(){
    Firebase.getString(firebaseData, path + "HeThong");
    fireStatus = firebaseData.stringData();
        if(fireStatus == "OFF"){
          baodong = false;
        }
        else if(fireStatus == "ON"){
          baodong = true;
        }
}

void RFID() {
  if ( ! rfid.PICC_IsNewCardPresent()) // Đọc thẻ lần 1
    {
      return;
      }
    if ( ! rfid.PICC_ReadCardSerial()) // Đọc toàn bộ dữ liệu của thẻ
    {
      return;
      }
      
      Serial.print("\nUID của thẻ: ");
      for (byte i = 0; i < rfid.uid.size; i++)
      {
        Serial.print(rfid.uid.uidByte[i] < 0x10 ? " 0" : " "); // Các số 1 chữ số thì thêm số 0 ở trước
        UID[i] = rfid.uid.uidByte[i];
        Serial.print(UID[i]); // 

        if(rfid.uid.size - 1 == i) {
          if(UID[i] == ID1[i]){
            baodong = !baodong;
         }
        }
      }
      Serial.println(" ");
}

void Relay(){
  int gtcb = digitalRead(cb);
        if(!baodong) {
          Firebase.setString(firebaseData, path + "HeThong", "OFF");
          digitalWrite(ledxanh, LOW);
          if(gtcb == 1) {
            digitalWrite(relay, LOW);
            digitalWrite(leddo, LOW);
            Firebase.setString(firebaseData, path + "Còi", "OFF");
          }
            else {
              digitalWrite(relay, LOW);
              digitalWrite(leddo, LOW);
              Firebase.setString(firebaseData, path + "Còi", "OFF");
            }
         }
         
         if(baodong){
          Firebase.setString(firebaseData, path + "HeThong", "ON");
          digitalWrite(ledxanh, HIGH);
          if(gtcb == 1) {
            Firebase.setString(firebaseData, path + "Còi", "ON");
            digitalWrite(relay, HIGH);
            digitalWrite(leddo, HIGH);
            }
          else {
            Firebase.setString(firebaseData, path + "Còi", "OFF");
            digitalWrite(relay, LOW);
            digitalWrite(leddo, LOW);
            }
          }     
        rfid.PICC_HaltA(); // Tạm ngừng đọc thẻ
        rfid.PCD_StopCrypto1(); // Ngừng sử dụng đối tượng rfid
}


void loop() {
    sensorUpdate();
    RFID();
    Relay();
    Docfirebase();
 }
