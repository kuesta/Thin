#include <18F2550.h>

#FUSES NOWDT, INTHS, PROTECT, NODEBUG, NOLVP 

#define MODE		T2_DIV_BY_1
#define PERIOD		64
#define POSTSCALE	1

// PIN DEFINES
#define LED_R      	    pin_c0 //Red LED
#define LED_G           pin_c1 //Green LED
#define LED_B           pin_c2 // Blue LED

#define MIX_PERIOD		2000
#define RED_SCALE		1400
#define GREEN_SCALE		800
#define BLUE_SCALE		850

void outputMask(int8 mask){
	if(mask&0b0001){
		output_high(LED_R);
	}else{
		output_low(LED_R);
	}
	if(mask&0b0010){
		output_high(LED_G);
	}else{
		output_low(LED_G);
	}
	if(mask&0b0100){
		output_high(LED_B);
	}else{
		output_low(LED_B);
	}
}

int stepper=0;
int8 mask=0b0000;
#INT_TIMER2
void t2int(){
	stepper++;
	if(stepper==RED_SCALE) mask+=0b0001;
	if(stepper==GREEN_SCALE) mask+=0b0010;
	if(stepper==BLUE_SCALE) mask+=0b0100;
	outputMask(mask);
	if(stepper==MIX_PERIOD) mask=0x00;
}

//inits
void init(){
   setup_oscillator(OSC_INTRC|OSC_8MHZ);
   setup_timer_2(MODE,PERIOD,POSTSCALE);
   enable_interrupts(INT_TIMER2);
   enable_interrupts(global);
}

void main(){
   	init();
   	while(true){}
}