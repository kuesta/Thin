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
int  	data=0x00;
boolean dataReady=false;

// Aus/Eingabe über USB
void rtUsb(){
    if (usb_cdc_kbhit()){											// Zeichen empfangen?{
        data=get_int_usb();
        cmd.veId=(data&0x1E000000)>>25;
        cmd.data=data&0x1FFFFFF;
        dataReady=true;
output_b(data);
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


