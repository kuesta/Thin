// uC-Einstellungen --------------------------------------------------------------------------
#include <18F2550.h>
#fuses  HSPLL,NOWDT,NOPROTECT,NOLVP,NODEBUG,USBDIV,PLL5,CPUDIV1,VREGEN
#use    delay(clock=48000000)						
// Für Bootloader ------------------------------------------------
#build (reset=0x1000, interrupt=0x1008)	// neue Adressen		
#org 0x0,0xfff {}			// res. Bereich

#use I2C(master, sda=PIN_B0, scl=PIN_B1)
// -------------------------------------------------------------------------------------------


// USB ------------------------------------------------------------------------------------------

// USB-Funktions von CCS
#include <usb_cdc.h>

// schreibe Char in USB-Transmitt-Buffer
void usb_putc(char cc) // printf(usb_putc,...)
{
  	while(!usb_cdc_putready());
	delay_ms(1);
	usb_cdc_putc(cc);
}
// USB -------------------------------------------------------------------------------------------


// globale Variable
int8  	In_Char;
int     charOffest=48;
int8    adresses[6] = {0x00, 0x10, 0x11, 0x13, 0x14, 0x15};
// Init der Hardware
struct cmd{
    int modulId;
    int ledId;
    int activ;
    boolean ready;
}cmd;

void HW_Init(){
    output_b(0x01);
    cmd.modulId=0;
    cmd.ledId=0;
    cmd.activ=0;
    cmd.ready=false;
}

void sendCommand(){
    long data=cmd.ledId;
    i2c_start();         // Start condition
    i2c_write(adresses[cmd.modulId]);    // Device address
    i2c_write(data);     // Low byte of command
    i2c_write(data>>8);
    data=cmd.activ;
    i2c_start();
    i2c_write(data);     // Low byte of command
    i2c_write(data>>8);
    i2c_stop();
    cmd.modulId=0;
    cmd.ledId=0;
    cmd.activ=0;
    cmd.ready=false;
    printf(usb_putc,"Command sent!\r\n");
}

void printHelp(){
    printf(usb_putc,"\r\n\Zentraleinheit v1.0\r\n\n");
    printf(usb_putc,"\tUebersicht:\n\r");
    printf(usb_putc,"ModulId:%u (zum Editieren 'm' drücke)\r\n", cmd.modulId);
    printf(usb_putc,"LedId:  %u (zum Editieren 'l' drücke)\r\n", cmd.ledId);
    printf(usb_putc,"Activ?  %u (zum Editieren 'a' drücke)\r\n", cmd.activ);
    printf(usb_putc,"Command: %uX%uX%u\r\n", cmd.modulId,cmd.ledId,cmd.activ);
    printf(usb_putc,"To send the Command, press: 'c'\r\n");
}

void rtUsb(){
  if (usb_cdc_kbhit()){
    In_Char=usb_cdc_getc();
    if (In_Char == 'h'){
      printHelp();
      return;
    }
    if (In_Char == 'c'){
      
        cmd.ready=true;
      printHelp();
      return;
    }
    switch (In_Char){
        case 'm':
            printf(usb_putc,"\tInsert ModulId(0-5):\n\r");
            while(usb_cdc_kbhit()){}
            In_Char=usb_cdc_getc()-charOffest;
            if(In_Char>=0 && In_Char<=5) cmd.modulId=In_Char;
            else cmd.modulId=0;
            printHelp();
            break;
        case 'l':
            while(usb_cdc_kbhit()){}
            printf(usb_putc,"\tInsert LedId(0-5):\n\r");
            In_Char=usb_cdc_getc()-charOffest;
            if(In_Char>=0 && In_Char<=5) cmd.ledId=In_Char;
            else cmd.ledId=0;
            printHelp();
            break;
        case 'a':
            while(usb_cdc_kbhit()){}
            printf(usb_putc,"\tActiv(0/1):\n\r");
            In_Char=usb_cdc_getc()-charOffest;
            if(In_Char==1)cmd.activ=1;
            else cmd.activ=0;
            printHelp();
            break;
    }
  }
}


void main() {
  HW_Init();
  usb_init();
  while(!usb_cdc_connected());
  output_b(0xff); //verbindung ok
  printHelp();
  while (true){
      if(cmd.ready){
          sendCommand();
      }
    rtUsb();
  }
}


