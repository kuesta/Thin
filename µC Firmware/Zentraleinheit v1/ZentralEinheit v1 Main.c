#include <18F2550.h>
#fuses  HSPLL,NOWDT,NOPROTECT,NOLVP,NODEBUG,USBDIV,PLL5,CPUDIV1,VREGEN		// nicht verändern
#use    delay(clock=48000000)												// nicht verändern
// Für Bootloader ------------------------------------------------
#build (reset=0x1000, interrupt=0x1008)	// neue Adressen					// nicht verändern
#org 0x0,0xfff {}						// res. Bereich						// nicht verändern
// -------------------------------------------------------------------------------------------
#use I2C(master, scl=PIN_B0, sda=PIN_B1, fast=100000)

// USB ------------------------------------------------------------------------------------------
// USB-Funktions von CCS
#include "usb_cdc.h"
// schreibe Char in USB-Transmitt-Buffer
void usb_putc(char cc)							// printf(usb_putc,...)
{
  	while(!usb_cdc_putready());
	delay_ms(1);
	usb_cdc_putc(cc);
}
// USB -------------------------------------------------------------------------------------------

// globale Variable
struct cmd{
    int veId,
    ledId,
    red,
    green,
    blue,
    data;
} cmd;
int8    devices[5] = {0x10, 0x11, 0x12, 0x13, 0x14};
char    res='';
int8    itera=0;
char  	data[6]={'m','l','v','r','g','b'};
boolean dataReady=false;

// Aus/Eingabe über USB
void rtUsb(){
    if (usb_cdc_kbhit()){		// Zeichen empfangen?{
        res=usb_cdc_getc();
        switch(res){
            case 'a':
                output_toggle(pin_b7);
                break;
            case 'b':
                output_toggle(pin_6);
                break;
            case 'c':
                output_toggle(pin_5);
                break;
            case 'd':
                output_toggle(pin_4);
                break;
        }
    }
}
void writeI2cData(int address, int i2cData){
    i2c_start();            // Start condition
    i2c_write(address);     // Device address
    i2c_write(i2cData);     // Low byte of command
    i2c_write(i2cData>>8);  // High byte of command
    i2c_stop();             // Stop condition
    dataReady=false;
}

// Hauptprogramm
void main() {
    output_b(0x00);               // output definierenn
    usb_init();                   // usb initialisieren
    delay_ms(10);
    while (true){
        while(!usb_cdc_connected());  // Warte auf PC-USB-Verbindung
                                      // bis mit Hyperterminal oder Applikation verbunden ist
        rtUsb();                      // Usb-Verbindung
        if(dataReady){
            writeI2cData(devices[cmd.veId], cmd.data);
            output_toggle(PIN_B0);
        }
    }
}


