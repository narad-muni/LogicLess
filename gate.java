class gate{
    int gateType=0,noOfInputs=0,i=0;
    boolean out=false;
    String name;
    gate[] input = new gate[4];

void setGate(int gt,int noi){
gateType=gt;
if(gt<2){
    noi=0;
    out=(gateType==1);
}else if(gt<4){
    noi=1;
}else if(gateType==10){
    noi=3;
}
noOfInputs=noi;
}

void setGate(int gt){
    setGate(gt,2);
}

void setGate(int gt,String nam){
    setGate(gt,2);
    name = nam;
}

void setGate(boolean gt){
    out=gt;
    if(gt){
        setGate(1);
    }else{
        setGate(0);
    }
}

void setGate(boolean gt,String nam){
    setGate(gt);
    name=nam;
}

gate(){
    input[0]=null;
    input[1]=null;
    input[2]=null;
    input[3]=null;
}

gate(int gt,int noi){
    input[0]=null;
    input[1]=null;
    input[2]=null;
    input[3]=null;
gateType=gt;
if(gt<2){
    noi=0;
    out=(gateType==1);
}else if(gt<4){
    noi=1;
}else if(gateType==10){
    noi=3;
}
noOfInputs=noi;
}


gate(int gt){
    this(gt,2);
}

void simulate(){
    i++;
    if(i>1){
        setOut();
    }else{
        for(int a=0;a<noOfInputs;a++){
            input[a].simulate();
        }
        setOut();
        i=0;
    }
}

void setOut(){
    if(gateType<2){
        out=(gateType==1);
    }else if(gateType==2){
        out=input[0].out;
    }else if(gateType==3){
        out=!(input[0].out);
    }else if(gateType==4){
        for(int x=0;x<noOfInputs;x++){
            if (x==0){
                out=input[0].out&input[1].out;
                x++;
                continue;
            }
            out=out&input[x].out;
        }
        out=!out;
    }else if(gateType==5){
        for(int x=0;x<noOfInputs;x++){
            if (x==0){
                out=input[0].out|input[1].out;
                x++;
                continue;
            }
            out=out|input[x].out;
        }
        out=!out;
    }else if(gateType==6){
        for(int x=0;x<noOfInputs;x++){
            if (x==0){
                out=input[0].out^input[1].out;
                x++;
                continue;
            }
            out=out^input[x].out;
        }
        out=!out;
    }else if(gateType==7){
        for(int x=0;x<noOfInputs;x++){
            if (x==0){
                out=input[0].out&input[1].out;
                x++;
                continue;
            }
            out=out&input[x].out;
        }
    }else if(gateType==8){
        for(int x=0;x<noOfInputs;x++){
            if (x==0){
                out=input[0].out|input[1].out;
                x++;
                continue;
            }
            out=out|input[x].out;
        }
    }else if(gateType==9){
        for(int x=0;x<noOfInputs;x++){
            if (x==0){
                out=input[0].out^input[1].out;
                x++;
                continue;
            }
            out=out^input[x].out;
        }
    }else if(gateType==10){
        if(input[1].out==true){
            if(input[0].out==true&input[2].out==true){
                out=!out;
            }else if(input[0].out!=false^input[2].out!=false){
                out=!(input[0].out);
            }
        }
    }
}
}
//0 low
//1 high
//2 output
//3 not
//4 nand
//5 nor
//6 xnor
//7 and
//8 or
//9 xor
//10 jk flipflop
