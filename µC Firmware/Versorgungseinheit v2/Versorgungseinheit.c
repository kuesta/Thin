#include <18F2550.h>

#FUSES NOWDT, INTHS, PROTECT ,NODEBUG ,NOLVP 
#use delay(clock=1000000)

// PIN DEFINES
#define TASTER     pin_b5
#define LED_1      pin_a0
#define LED_2      pin_a1
#define LED_3      pin_a2
#define LED_4      pin_a3
#define LED_5      pin_a4
#define LED_D_1    pin_c4
#define LED_D_2    pin_c2
#define LED_D_3    pin_c1
#define LED_D_4    pin_c0
#define LED_D_5    pin_a6

//MODE DEFINES
#define MODE_CONSTANT_LIGHT    0
#define MODE_FLASHING          1
#define MODE_RAINBOW           2
#define MODE_I2C               3 //not used yet
//EXTRA DEFINES
#define MODE_LAST_MODE         2
#define LOOPS_PER_SECOUND      4

int flashCounter=0;
int mode = 0;

void toggleLeds(){
   output_toggle(LED_1);
   output_toggle(LED_2);
   output_toggle(LED_3);
   output_toggle(LED_4);
   output_toggle(LED_5);
}

void dataPinChange(unsigned int mask){
	if(mask&0b00001){
	  output_high(LED_D_1);
	}else{
	  output_low(LED_D_1);
	}
	if(mask&0b00010){
	  output_high(LED_D_2);
	}else{
	  output_low(LED_D_2);
	}
	if(mask&0b00100){
	  output_high(LED_D_3);
	}else{
	  output_low(LED_D_3);
	}
	if(mask&0b01000){
	  output_high(LED_D_4);
	}else{
	  output_low(LED_D_4);
	}
	if(mask&0b10000){
	  output_high(LED_D_5);
	}else{
	  output_low(LED_D_5);
	}
} 

#INT_RB
void modeChange(){
   if(!input(TASTER)){
      delay_ms(250); //entprällung des tasters
      if(mode==MODE_LAST_MODE){
         mode=0;
      }else{
         mode++;
      }

   }
}

// called every 26ms
#int_timer1
void timerInt(){
   // output_toggle(pin_b1);
   switch(mode){
	 // constant lightningmode
     case MODE_CONSTANT_LIGHT:
	   dataPinChange(0b00000);
	   output_high(LED_1);
       output_high(LED_2);
       output_high(LED_3);
       output_high(LED_4);
       output_high(LED_5);
	 break;

	// flash lightning mode
	 case MODE_FLASHING:
		dataPinChange(0b00000);
		if(flashCounter==LOOPS_PER_SECOUND){
      	  toggleLeds();
      	  flashCounter=0;
   		}else{
      	  flashCounter++;
   		}
	 break;
	
	// Ranbow
	 case MODE_RAINBOW:
	   dataPinChange(0b11111);
	   output_high(LED_1);
       output_high(LED_2);
       output_high(LED_3);
       output_high(LED_4);
       output_high(LED_5);
	 break;

	 // i2c lightning mode
	 case MODE_I2C:
		// not used yet
	 break;
   }
}

// i2c interrupt activity
// not used yet
//int8 data=0x00;
#INT_SSP
void i2cActivity(){   
}

void main(){
   setup_oscillator(OSC_INTRC|OSC_1MHZ);
   enable_interrupts(int_rb);
   enable_interrupts(int_timer1);
   setup_timer_1(T1_INTERNAL|T1_DIV_BY_1);
   ext_int_edge(H_TO_L);
   port_b_pullups(true);
   enable_interrupts(global);
   
   //init Leds
   output_high(LED_1);
   output_high(LED_2);
   output_high(LED_3);
   output_high(LED_4);
   output_high(LED_5);
   while(true){}
}
