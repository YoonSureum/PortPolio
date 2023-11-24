#include <stdio.h>
#include <wiringPi.h>

#define SW1 22
#define SW2 23
#define SW3 24
#define LED 26
#define SEG_A 0
#define SEG_B 1
#define SEG_C 2
#define SEG_D 3
#define SEG_E 4
#define SEG_F 5
#define SEG_G 6
#define SEG_DP 7

int segPins[] = {SEG_A, SEG_B, SEG_C, SEG_D, SEG_E, SEG_F, SEG_G, SEG_DP};

int main(void)
{
    int i, num, sevenseg[10][8] = {
        {0,0,0,0,0,0,1,1},
        {1,0,0,1,1,1,1,1},
        {0,0,1,0,0,1,0,1},
        {0,0,0,0,1,1,0,1},
        {1,0,0,1,1,0,0,1},
        {0,1,0,0,1,0,0,1},
        {0,1,0,0,0,0,0,1},
        {0,0,0,1,1,1,1,1},
        {0,0,0,0,0,0,0,1},
        {0,0,0,0,1,0,0,1}
    };

    if(wiringPiSetup() == -1)
    {
        return 1;
    }

    pinMode(SW1, INPUT);
    pinMode(SW2, INPUT);
    pinMode(SW3, INPUT);
    pinMode(SEG_A, OUTPUT);
    pinMode(SEG_B, OUTPUT);
    pinMode(SEG_C, OUTPUT);
    pinMode(SEG_D, OUTPUT);
    pinMode(SEG_E, OUTPUT);
    pinMode(SEG_F, OUTPUT);
    pinMode(SEG_G, OUTPUT);
    pinMode(SEG_DP, OUTPUT);
    pinMode(LED, PWM_OUTPUT);
    pwmSetMode(PWM_MODE_MS);
    pwmSetRange(100);

    num = 0;
    int isOn = 1;
    int prevNum = 0;
    int prevIsOn = 1;

    while(1)
    {
        if(digitalRead(SW1) == HIGH)
        {
            num++;
            if(num > 9) 
                num = 0;

            for(i=0; i<8; i++)
            {
                digitalWrite(segPins[i], sevenseg[num][i]);
            }

            if(num == 0) 
            {
                pwmWrite(LED, 0);
            }
            else
            {
                pwmWrite(LED, (num + 1) * 10);
            }
            delay(200);
        }

        if(digitalRead(SW2) == HIGH)
        {
            num--;
            if(num < 0) 
                num = 9; 
            for(i=0; i<8; i++)
            {
                digitalWrite(segPins[i], sevenseg[num][i]);
            }
            if(num == 0) 
            {
                pwmWrite(LED, 0);
            }
            else
            {
                pwmWrite(LED, (num + 1) * 10);
            }
            delay(200);
        }

        if(digitalRead(SW3) == HIGH)
        {
            if(isOn)
            {
                isOn = 0;
                prevNum = num;
                prevIsOn = 1;
                pwmWrite(LED, 0);
                for(i=0; i<8; i++)
                {
                    digitalWrite(segPins[i], HIGH);
                }
                printf("LED and segment lights turned off\n");
            }
            else
            {
                isOn = 1;
                num = prevNum;
                prevIsOn = 0;
                if(num == 0) 
                {
                    pwmWrite(LED, 0);
                }
                else
                {
                    pwmWrite(LED, (num + 1) * 10);
                }
                for(i=0; i<8; i++)
                {
                    digitalWrite(segPins[i], sevenseg[num][i]);
                }
                printf("LED and segment lights turned on\n");
            }
            delay(200);
        }
    }

    return 0;
}
