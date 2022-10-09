import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.awt.event.*;

public class LogicLessGUI{

    public static void inputTracker(JRadioButton inputButtonTrack,int gateid){
        isConnected=!isConnected;
        if(isConnected){
            if(inToOut){
                System.out.println("gt["+gateIdTrack+"].input["+Integer.parseInt(buttonTrack)+"]=gt["+gateid+"]");
                gt[gateIdTrack].input[Integer.parseInt(buttonTrack)]=gt[gateid];
            }else{
                System.out.println("gt["+gateid+"].input["+Integer.parseInt(inputButtonTrack.getName())+"]=gt["+gateIdTrack+"]");
                gt[gateid].input[Integer.parseInt(inputButtonTrack.getName())]=gt[gateIdTrack];
            }
        }else{
            buttonTrack=inputButtonTrack.getName();
            gateIdTrack=gateid;
        }
    }

    static int count=0,gateCount=0;
    static JFrame frame = new JFrame();
    static JPanel buttoPanel = new JPanel(new GridLayout(1,8,5,0));
    static wireDrawer ld = new wireDrawer();
    static Boolean inToOut=false , isDrawing=false;
    static Graphics2D grd;
    static Boolean isConnected=true;
    static String buttonTrack;
    static int gateIdTrack;
    static gate []gt = new gate[50];
    static gateGUI []test = new gateGUI[50];
    static Boolean simStatus = false;
    static String truthTableString=""; 

static class gateGUI extends JPanel implements  MouseInputListener{
    JLabel gateLogo;
    JRadioButton outPut;
    JRadioButton in1,in2;
    ButtonGroup bg;    
    int prevCursorX = 0;
    int prevCursorY = 0;
    int objectPosX = 0;
    int objectPosY = 0;
    int outputCount=0;
    String gateNamel;
    Boolean switchPr=false;
    Boolean inSet1=false,inSet2=false;
    Line2D []inputWire = new Line2D[2];
    Line2D []outPutWire = new Line2D[4];
    int gateId;
    gateGUI(){            
        gateLogo = new JLabel();
        outPut = new JRadioButton();
        in1 = new JRadioButton();
        in2 = new JRadioButton();
        bg = new ButtonGroup();
        in1.setBackground(Color.white);
        in2.setBackground(Color.white);
        outPut.setBackground(Color.white);
        in1.setOpaque(false);
        in2.setOpaque(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        in1.addMouseListener(new MouseInputAdapter(){
            public void mouseClicked(MouseEvent e){
                // System.out.println("Connection");
                Point properLocation = new Point();
                properLocation.setLocation(in1.getLocationOnScreen().getX(),in1.getLocationOnScreen().getY()-73);
                if(!inSet1){
                    inputWire[0] = ld.li[count];
                }
                else{
                    inputWire[0].setLine(0,0,0,0);
                    inputWire[0] = ld.li[count];
                }
                if(isDrawing){
                    inToOut=false;
                }else{
                    inToOut=true;
                }
                ld.startLineDraw(properLocation);
                bg.clearSelection();
                inSet1=true;
                inputTracker(in1,gateId);
            }
        });
        in2.addMouseListener(new MouseInputAdapter(){
            public void mouseClicked(MouseEvent e){
                // System.out.println("Connection");
                Point properLocation = new Point();
                properLocation.setLocation(in2.getLocationOnScreen().getX(),in2.getLocationOnScreen().getY()-73);
                if(!inSet2){
                    inputWire[1] = ld.li[count];
                }
                else{
                    inputWire[1].setLine(0,0,0,0);
                    inputWire[1] = ld.li[count];
                }
                if(isDrawing){
                    inToOut=false;
                }else{
                    inToOut=true;
                }
                ld.startLineDraw(properLocation);
                bg.clearSelection();
                inSet2=true;
                inputTracker(in2,gateId);
            }
        });
        outPut.addMouseListener(new MouseInputAdapter(){
                public void mouseClicked(MouseEvent e){
                    // System.out.println("Connection");
                    Point properLocation = new Point();
                    properLocation.setLocation(outPut.getLocationOnScreen().getX()+15,outPut.getLocationOnScreen().getY()-73);
                    outPutWire[outputCount] = ld.li[count];
                    if(isDrawing){
                        inToOut=true;
                    }else{
                        inToOut=false;
                    }
                    ld.startLineDraw(properLocation);
                    bg.clearSelection();
                    outputCount++;
                    inputTracker(outPut,gateId);
                }
        });
        setLocation(0,100);
        setSize(100,70);
        gateLogo.setSize(100,70);
        // gateLogo.setIcon(new ImageIcon("and.png"));
        gateLogo.setLocation(15,0);
        setOpaque(true);
        setBackground(Color.white);
        setLayout(null);
        in1.setBounds(0, 15, 20,20);
        in2.setBounds(0, 35, 20,20);
        outPut.setBounds(80, 25, 20,20);
        bg.add(outPut);
        bg.add(in1);
        bg.add(in2);
        add(outPut);
        add(in1);
        add(in2);
        add(gateLogo);
        setVisible(true);    
        setFocusable(true);
    }
    public void setGate(String gateName){
        gateId=gateCount;
        gateNamel = gateName;
        if(gateName!="input" && gateName!="output"){
            gateLogo.setIcon(new ImageIcon(gateName+".png"));
            outPut.setName("output");
            in1.setName("0");
            in2.setName("1");
        }else{
            gateLogo.setText("   "+gateName);
            if(gateName=="output"){
                in2.setVisible(false);
                outPut.setVisible(false);
                in1.setLocation(0,25);
                in1.setName("0");
            }else{
                in2.setVisible(false);
                in1.setVisible(false);
                outPut.setName("outPut");
            }
        }
    }
    public void mouseDragged(MouseEvent e) {
        int translateX = e.getXOnScreen() - prevCursorX;
        int translateY = e.getYOnScreen() - prevCursorY;
        setLocation(objectPosX + translateX, objectPosY + translateY);
        if(inSet1){
            if(in2.isVisible()){
                inputWire[0].setLine(inputWire[0].getX1(),inputWire[0].getY1(),getX(),getY()-25);
            }else{
                inputWire[0].setLine(inputWire[0].getX1(),inputWire[0].getY1(),getX(),getY()-15);
            }
            ld.callRepaint();
        }if(inSet2){
            inputWire[1].setLine(inputWire[1].getX1(),inputWire[1].getY1(),getX(),getY());
            ld.callRepaint();
        }
        for(int za=0;za<outputCount;za++){
            if(outPutWire[za].getX1()==0 || outPutWire[za].getX2()==0 || outPutWire[za].getY1()==0 || outPutWire[za].getY2()==0){
                outPutWire[za]=null;
                outputCount--;
                ld.callRepaint();
            }else{
                outPutWire[za].setLine(getX()+100,getY()-15,outPutWire[za].getX2(),outPutWire[za].getY2());
                ld.callRepaint();
            }
        }
    }  
    public void mouseMoved(MouseEvent m) {  
    }
    public void mouseExited(MouseEvent m){
    }   
    public void mouseClicked(MouseEvent m){
        switchPr = !(switchPr);
        if(gateNamel=="input"){
            gt[gateId].setGate(switchPr);
            if(switchPr){
                setBackground(Color.green);
            }else{
                setBackground(Color.red);
            }
        }
    }
    public void mousePressed(MouseEvent e){
        prevCursorX = e.getXOnScreen();
        prevCursorY = e.getYOnScreen();
        objectPosX = getX();
        objectPosY = getY();
    }
    public void mouseEntered(MouseEvent m){
    }
    public void mouseReleased(MouseEvent m){
    }
}

static class wireDrawer extends JPanel implements MouseInputListener{
    Line2D []li = new Line2D[50];
    // Vector<Line2D> liVect = new Vector<Line2D>();
    int x=0,y=0,xn=0,yn=0;
            wireDrawer(){
                for(int xyz=0;xyz<50;xyz++){
                    li[xyz] = new Line2D.Float(0,0,0,0);
                    setOpaque(false);
                    // setBackground(Color.yellow);
                }
                addMouseListener(this);
                addMouseMotionListener(this);
                // setBackground(Color.blue);
            }
            public void mouseDragged(MouseEvent m) {
            }  
            public void mouseMoved(MouseEvent m) {
                if(isDrawing){
                    if(!inToOut){
                        li[count].setLine(x,y,(int)m.getX(),(int)m.getY());
                    }else{
                        li[count].setLine((int)m.getX(),(int)m.getY(),x,y);
                    }
                    repaint();
                }
            }
            public void mouseExited(MouseEvent m){
            }   
            public void mouseClicked(MouseEvent m){
            }

            public void callRepaint(){
                repaint();
            }

            public void startLineDraw(Point pas){
                // pas.setLocation(pas.getX()+pos.getX(),pas.getY()+pos.getY());
                isDrawing=!isDrawing;
                if(isDrawing){
                    li[count].setLine(pas.getX(),pas.getY(),pas.getX(),pas.getY());
                    x=(int)pas.getX();
                    y=(int)pas.getY();
                }else{
                    if(!inToOut){
                        li[count].setLine(x,y,(int)pas.getX(),(int)pas.getY());
                    }else{
                        li[count].setLine((int)pas.getX(),(int)pas.getY(),x,y);
                    }
                    // li[count].setLine(x,y,(int)pas.getX(),(int)pas.getY());
                    // System.out.println("Drawn From "+x+","+y+" to "+pas.getX()+","+pas.getY());
                    count++;
                }
                repaint();
            }
            public void mousePressed(MouseEvent e){
            }
            public void mouseEntered(MouseEvent m){
            }
            public void mouseReleased(MouseEvent m){
            }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        grd = (Graphics2D)g;
        for(int zy = 0;zy<count;zy++){
            grd.draw(li[zy]);
        }
        grd.draw(li[count]);
        grd.dispose();
    }
}

public static void generateTruthTable(gate gatesArray[]){
    truthTableString="";
    int numberOfInputs=0,numberOfOutputs=0,s;
    for(int i=0;i<gateCount;i++){
    if(gatesArray[i].gateType==2){
        numberOfOutputs++;
    }
    else if(gatesArray[i].gateType==0 || gatesArray[i].gateType==1){
    numberOfInputs++;
    }
    }
    
    int[] arrInputs=new int[numberOfInputs];
    int[] arrOutputs=new int[numberOfOutputs];
    
    s = numberOfInputs;
    numberOfInputs=0;
    numberOfOutputs=0;
    
    for(int i=0;i<gateCount;i++){
    if(gatesArray[i].gateType==2){
        arrOutputs[numberOfOutputs]=i;
        numberOfOutputs++;
    }
    else if(gatesArray[i].gateType==0 || gatesArray[i].gateType==1){
    arrInputs[numberOfInputs]=i;
    numberOfInputs++;
    }
    }
    numberOfInputs=s;
    
    int []arr = new int[s];
        for(int i=0;i<s;i++){arr[i]=0;}
        //truthTableString+="   ");
        for(int l=0;l<numberOfInputs;l++){
                   truthTableString+="  Input";
            }
           truthTableString+="  ";
            for(int l=0;l<numberOfOutputs;l++){
                   truthTableString+="  Output";
            }
            truthTableString+="\n";
        for(int j=0;j<java.lang.Math.pow(2,s);j++){
    
            for(int l=0;l<numberOfInputs;l++){
                    gatesArray[arrInputs[l]].setGate(arr[l],gatesArray[arrInputs[l]].name);
                    
            }
            for(int l=0;l<numberOfOutputs;l++){
                    gatesArray[arrOutputs[l]].simulate();
            }
       truthTableString+="   ";
            for(int l=0;l<numberOfInputs;l++){
                   truthTableString+=(gatesArray[arrInputs[l]].out?1:0)+"    |    ";
            }
            for(int l=0;l<numberOfOutputs;l++){
                   truthTableString+=(gatesArray[arrOutputs[l]].out?1:0)+"    |    ";
            }
            truthTableString+="\n";
    
            arr[0]++;
            for(int k=0;k<s-1;k++){
                if(arr[k]%2==0 && arr[k]!=0){
                    arr[k+1]++;
                    arr[k]%=2;
                }
            }
        }
    }

    public static void main(String[] args) {
        frame.setLayout(null);
        ld.setBounds(0,50,1366,768);
        // ld.setOpaque(true);  
        // ld.setBackground(Color.red);
        JButton andGateButton = new JButton("AND gate");
        JButton orGateButton = new JButton("OR gate");
        JButton notGateButton = new JButton("NOT gate");
        JButton xorGateButton = new JButton("XOR gate");
        JButton nandGateButton = new JButton("NAND gate");
        JButton norGateButton = new JButton("NOR gate");
        JButton simulateButton = new JButton("Simulate");
        JButton inpuButton = new JButton("Input");
        JButton outpuButton = new JButton("Output");
        JButton TruthTable = new JButton("TTable");
        JFrame truthTablFrame = new JFrame("Truth Table");
        JTextPane table = new JTextPane();
        for(int a=0;a<50;a++){
            test[a] = new gateGUI();
            test[a].setVisible(false);
            frame.add(test[a]);
        }
        andGateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    test[gateCount].setVisible(true);
                    test[gateCount].setGate("and");
                    gt[gateCount] = new gate(7);
                    gateCount++;
            }  
            });  
        orGateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    test[gateCount].setGate("or");
                    test[gateCount].setVisible(true);
                    gt[gateCount] = new gate(8);
                    gateCount++;
            }  
            });  
        notGateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    test[gateCount].setVisible(true);
                    test[gateCount].setGate("not");
                    test[gateCount].in2.setVisible(false);
                    test[gateCount].in1.setLocation(0,25);
                    gt[gateCount] = new gate(3);
                    gateCount++;
            }  
            });  
        xorGateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    test[gateCount].setGate("xor");
                    test[gateCount].setVisible(true);
                    gt[gateCount] = new gate(9);
                    gateCount++;
            }  
            });  
        nandGateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    test[gateCount].setVisible(true);
                    test[gateCount].setGate("nand");
                    gt[gateCount] = new gate(4);
                    gateCount++;
            }  
            });  
        norGateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    test[gateCount].setGate("nor");
                    test[gateCount].setVisible(true);
                    gt[gateCount] = new gate(5);
                    gateCount++;
            }  
            });
        outpuButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    test[gateCount].setGate("output");
                    test[gateCount].setVisible(true);
                    test[gateCount].setBackground(Color.red);
                    gt[gateCount] = new gate(2);
                    gateCount++;
            }  
            });
        inpuButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                    test[gateCount].setGate("input");
                    test[gateCount].setVisible(true);
                    test[gateCount].setBackground(Color.red); 
                    gt[gateCount] = new gate(0);      
                    gateCount++;
            }  
            });
        Timer timer = new Timer(100,new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                for(int zas=0;zas<gateCount;zas++){
                    if(gt[zas].gateType==2){
                        try{
                            gt[zas].simulate();
                        }catch(Exception ebc){
                        }
                    }
                        if(gt[zas].out){
                            test[zas].setBackground(Color.green);
                        }else{
                            test[zas].setBackground(Color.red);
                        }
                }
            }  
            });
        simulateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                simStatus = !simStatus;
                if(simStatus){
                    timer.start();
                    simulateButton.setText("Stop");
                }else{
                    timer.stop();
                    simulateButton.setText("Simulate");
                }
            }  
            });
        TruthTable.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                generateTruthTable(gt);
                table.setBounds(0,0,500,500);
                truthTablFrame.setBounds(50,50,500,500);
                table.setText(truthTableString);
                truthTablFrame.add(table);
                System.out.print(truthTableString);
                truthTablFrame.setVisible(true);
                truthTablFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }  
            });
        buttoPanel.add(andGateButton);
        buttoPanel.add(orGateButton);
        buttoPanel.add(notGateButton);
        buttoPanel.add(xorGateButton);
        buttoPanel.add(nandGateButton);
        buttoPanel.add(norGateButton);
        buttoPanel.add(inpuButton);
        buttoPanel.add(outpuButton);
        buttoPanel.add(simulateButton);
        buttoPanel.add(TruthTable);
        buttoPanel.setSize(1366,40);
        frame.add(buttoPanel);
        frame.add(ld);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);
    }
}