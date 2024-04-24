import processing.serial.*;
import processing.sound.*;

Serial port;
SoundFile music;
String mode = "Wait";
int nb[] = {0, 0, 0, 0, 0, 0};
int x;
void setup()
{
  //printArray(Serial.list());
  size(500, 500);
  port = new Serial(this, Serial.list()[0],9600);
  music = new SoundFile(this, "04. 24kgoldn - Mood (feat. iann dior).mp3");
}

void draw()
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
