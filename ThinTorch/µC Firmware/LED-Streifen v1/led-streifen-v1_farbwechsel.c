#include <18F2550.h>

#FUSES NOWDT, INTHS, PROTECT ,NODEBUG ,NOLVP 
#use delay(clock=1000000)

// PIN DEFINES
#define LED_R      	    pin_c0 //Red LED
#define LED_G           pin_c1 //Green LED
#define LED_B           pin_c2 // Blue LED
#define LOOPS_TO_NEXT	4      // defines the 1secound intervall

//inits
void init(){
   setup_oscillator(OSC_INTRC|OSC_1MHZ);
   enable_interrupts(int_timer1);
   setup_timer_1(T1_INTERNAL|T1_DIV_BY_1);
   port_b_pullups(true);
   enable_interrupts(global);
}

//Bitmask for the output
void rgbOutput(int mask){
	//looking for the red-led bit
	if(mask&0b0001){
		output_high(LED_R);
	}else{
		output_low(LED_R);
	}
	//looking for the green-led bit
	if(mask&0b0010){
		output_high(LED_G);
	}else{
		output_low(LED_G);
	}
//looking for the blue-led bit
	if(mask&0b0100){
		output_high(LED_B);
	}else{
		output_low(LED_B);
	}
}

int flashCounter=0;
int state=7;
#int_timer1
void timerInt(){
	if(flashCounter==LOOPS_TO_NEXT){
		  flashCounter=0;
		  if(state==7){	
		    state=1;
		    rgbOutput(state);
		  }else{	
		    state++;
		    rgbOutput(state);
		  }
	}else{
		flashCounter++;
	}
}

void main(){
   init();
   while(true){}
}