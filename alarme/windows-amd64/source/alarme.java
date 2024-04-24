/* autogenerated by Processing revision 1292 on 2023-05-05 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import processing.serial.*;
import processing.sound.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class alarme extends PApplet {




Serial port;
SoundFile music;
String mode = "Wait";
int nb[] = {0, 0, 0, 0, 0, 0};
int x;
public void setup()
{
  //printArray(Serial.list());
  /* size commented out by preprocessor */;
  port = new Serial(this, Serial.list()[0],9600);
  music = new SoundFile(this, "04. 24kgoldn - Mood (feat. iann dior).mp3");
}

public void draw()
{
  background(190);
  fill(0, 255, 0);
  ellipse(100, 100, 100, 100);
  fill(236, 231, 16);
  ellipse(250, 100, 100, 100);
  fill(255, 0, 0);
  ellipse(400, 100, 100, 100);
  fill(0, 0, 0);
  textSize(50);
  text(mode, 50, 230);
  if(mousePressed==true && dist(mouseX, mouseY, 100, 100)<50)
  {
    port.write("go\n");
    mode = "GO";
  }
  if(mousePressed==true && dist(mouseX, mouseY, 250, 100)<50)
  {
    port.write("stop\n");
    mode = "STOP";
  }
  if(mousePressed==true && dist(mouseX, mouseY, 400, 100)<50)
  {
    port.write("reset\n");
    mode = "RESET";
    if(music.isPlaying())
    {
      music.stop();
    }
  }
  x = 50;
  for(int i=0; i<6; i++)
  {
    if(i!=0 && i%2==0)
      {
        x += 100;
      }
      else 
      {
        x += 20;
      }
    if(mousePressed==true && dist(mouseX, mouseY, x, 350)<9)
    {
      nb[i]++;
      if(nb[i]>9)
      {
        nb[i] = 0; 
      }
      delay(300);
    }
    if(mousePressed==true && dist(mouseX, mouseY, x, 420)<9)
    {
      nb[i]--;
      if(nb[i]<0)
      {
        nb[i] = 9; 
      }
      delay(300);
    }
    
  }
  if(mousePressed==true && dist(mouseX, mouseY, x+60, 390)<26)
  {
    for(int i=0; i<6; i++)
    {
      port.write(nb[i]+"\n"); 
      delay(50);
    }
  }
  x = 50;
  textSize(40);
  if(mode=="RESET" || mode=="Wait")
  {
    for(int i=0; i<6; i++)
    {
      if(i!=0 && i%2==0)
      {
        x += 50;
        text(" : ", x, 400);
        x += 50;
      }
      else 
      {
        x += 20;
      }
      
      fill(0, 0, 0);
      text(nb[i], x, 400); 
      fill(0, 0, 150);
      ellipse(x+8, 350, 16, 16);
      ellipse(x+8, 420, 16, 16);
    }
    fill(44, 205, 189);
    ellipse(x+60, 390, 50, 50);
    fill(0, 0, 0);
    textSize(20);
    text("OK", x+50, 400);
  }
  
  if(port.available()>0)
  {
    String command = port.readStringUntil('\n');
    if(command!=null && trim(command).equals("time")){
      if(!music.isPlaying()){
        music.play();
      }
    }
  }
  
}


  public void settings() { size(500, 500); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "alarme" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
