#include <18F2550.h>

#FUSES NOWDT, INTHS, PROTECT ,NODEBUG ,NOLVP 
#use delay(clock=1000000)

#define TASTER     pin_b5
#define LED_1      pin_a0
#define LED_2      pin_a1
#define LED_3      pin_a2
#define LED_4      pin_a3
#define LED_5      pin_a4

#define LOOPS_PER_SECOUND      4

#define MODE_LAST_MODE         1
#define MODE_CONSTANT_LIGHT    0
#define MODE_FLASHING          1
#define MODE_I2C               2 //not used yet

int flashCounter=0;
int mode = 0;

void toggleLeds(){
   output_toggle(LED_1);
   output_toggle(LED_2);
   output_toggle(LED_3);
   output_toggle(LED_4);
   output_toggle(LED_5);
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
	   output_high(LED_1);
       output_high(LED_2);
       output_high(LED_3);
       output_high(LED_4);
       output_high(LED_5);
	 break;

	// flash lightning mode
	 case MODE_FLASHING:
		if(flashCounter==LOOPS_PER_SECOUND){
      	  toggleLeds();
      	  flashCounter=0;
   		}else{
      	  flashCounter++;
   		}
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
