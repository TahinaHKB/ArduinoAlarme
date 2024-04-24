#include "SevSeg.h"
SevSeg sevseg;
int compteur = 20;
int nb[] = {0, 0, 0, 0, 0, 0};
int pointNb = 0;
bool go = false;
int min, h, s;
int virgule = -1;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  byte numDigits = 4;
  byte digitPins[] = {10, 11, 12, 13};
  byte segmentPins[] = {9, 2, 3, 5, 6, 8, 7, 4};
  bool resistorsOnSegments = true;
  bool updateWithDelaysln = true;
  byte hardwareConfig = COMMON_ANODE;
  sevseg.begin(hardwareConfig, numDigits, digitPins, segmentPins, resistorsOnSegments);
  sevseg.setBrightness(90);
}
long time = millis();
void loop() {
  // put your main code here, to run repeatedly:
  sevseg.setNumber(compteur, virgule);
  sevseg.refreshDisplay();
  if(Serial.available()>0)
  {
    String command = Serial.readStringUntil('\n');
    if(command=="go")
    {
      go = true;
    }
    else if(command=="stop")
    {
      go = false;
    }
    else if(command=="reset")
    {
      go = false;
      compteur = 20;
    }
    else 
    {
      nb[pointNb] = command.toInt();
      Serial.println(nb[pointNb]);
      pointNb++;
      if(pointNb>5)
      {
        pointNb = 0;
      }
    }
  }

  h = nb[0]*10+nb[1];
  min = nb[2]*10 + nb[3];
  s = nb[4]*10+nb[5];

  if(millis()-time>1000)
  {
    if(go==true)
    {
      s--;
      time = millis();
    }

  }

  if(s<0)
  {
    min--;
    s = 59;
  }
  if(min<0)
  {
    h--;
    min = 59;
  }
  if(h<0)
  {
    h = 0;
    min = 0;
    s = 0;
    go = false;
    Serial.println("time");
  }

  if(h!=0)
  {
    compteur = h*100+min;
    virgule = 2;
  }
  else 
  {
    compteur = min*100+s;
    virgule = 2;
    
  }
  if(min==0 && h==0)
  {
    virgule = -1;
  }
  
  nb[0] = h/10;
  nb[1] = h%10;
  nb[2] = min/10;
  nb[3] = min%10;
  nb[4] = s/10;
  nb[5] = s%10;
}
