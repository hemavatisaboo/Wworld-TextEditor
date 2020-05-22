//W-World, a text editor
import java.io.*;
import javax.print.attribute.*;
import java.awt.print.*;
import javax.swing.UIManager.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;
import java.util.*;

public class Wworld extends JFrame 
{
	Font f1;
	Color c1;
	JLabel ff,ft,fs;
	JButton ok,cancel,choose;
	JTextField pre;
	String f_style[]={"regular","bold","italic","boldItalic"};
	JComboBox font_family1,font_size1,font_style;
	String f_size1[]={"8","9","10","11","12","14","16","18","20","22","24","26","28","36","46","72"};
	
	int style;
	String family,si;
	JLabel mpos,cap,num,scroll;
   	PrinterJob job = PrinterJob.getPrinterJob();
	PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
   	Clipboard cBoard=getToolkit().getSystemClipboard();
   	JTextArea tArea;
   	JList ll;
   	JTextField t,tr;
   	Dimension d=new Dimension(5,5);
  	JCheckBoxMenuItem tbar,wordwrap,stbar,fbar;
	JToolBar t1,t2,t3;
   	String fName;
   	JButton b,b1,b2,yes,no,can,count;
   	JDialog j,j1,j2,j3;
    JButton cut,copy,paste,new1,open,save,print,find,print_pre,undo,redo;
   	JScrollPane p;
   	JPanel jp;
 	JPopupMenu pop;
 	JMenuItem u,c,co,pa,s,de,f;
 	//undo related
 	protected UndoManager undoManager = new UndoManager();
 	Component selectedComponent;
	//toolbar related
	JToggleButton bold,italic,underline,up,low,center,align_left,align_right;
	JButton tcolor,bcolor;
	JComboBox font_family,font_size;
	String fonts[];
	String f_size[]={"8","9","10","11","12","14","16","18","20","22","24","26","28","36","46","72"};
	//Constructor	
 	public Wworld()
   	{
   		Image img=Toolkit.getDefaultToolkit().getImage("F:/Image/wworld.png");
	    setIconImage(img);
	    tArea =new JTextArea();
	    add(tArea);
	    //undo coding
	    tArea.getDocument().addUndoableEditListener( new UndoableEditListener()
 		{
 			public void undoableEditHappened(UndoableEditEvent e)
          	{
            	undoManager.addEdit(e.getEdit());
	        }
        });
		jp=new JPanel();
		jp.setLayout(new GridLayout(2,1));
		//dialog for find
		JLabel l=new JLabel("Find What:");
		JLabel l1=new JLabel("Replace With:");
		t=new JTextField(15);
		tr=new JTextField(15);
		b=new JButton("Find");
		b1=new JButton("Replace");
		b2=new JButton("Cancel");
		j=new JDialog((Frame)null,"FIND",false);
		Container dialogContentPane=j.getContentPane();
		dialogContentPane.setLayout(new FlowLayout());
		dialogContentPane.add(l);
		dialogContentPane.add(t);
		dialogContentPane.add(l1);
		dialogContentPane.add(tr);
		dialogContentPane.add(b);
		dialogContentPane.add(b1);
		dialogContentPane.add(b2);
		b.addActionListener(new find_option());
		b1.addActionListener(new find_option());
		b2.addActionListener(new find_option());

	     //file menu
	    JMenuBar mBar=new JMenuBar();
	    JMenu fileMenu =new JMenu("File");
		fileMenu.setMnemonic('f');
	    JMenuItem nOption=new JMenuItem("New");
	    JMenuItem oOption=new JMenuItem("Open");
	    JMenuItem sOption=new JMenuItem("Save");
	    JMenuItem sa=new JMenuItem("Save As");
	    JMenuItem prOption=new JMenuItem("Print");
 	    JMenuItem pgsOption=new JMenuItem("Page setup");
 	    JMenuItem exOption=new JMenuItem("Exit");

	    nOption.addActionListener(new Ne_option());
	    nOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
	    fileMenu.add(nOption);

	    oOption.addActionListener(new Ope_option());
	    oOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
	    fileMenu.add(oOption);

	    sOption.addActionListener(new Sav_option());
	    sOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
	    fileMenu.add(sOption);
	    fileMenu.add(sa);
	    sa.addActionListener(new Sav_option());
		fileMenu.addSeparator();

        //pgsOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.F3));
        fileMenu.add(pgsOption);
        pgsOption.addActionListener(new pageset());
        
		//fileMenu.add(ppwOption);
		
		prOption.addActionListener(new print(tArea));
        prOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
        fileMenu.add(prOption);
		fileMenu.addSeparator();
         
        exOption.addActionListener(new Clos_option());
		fileMenu.add(exOption);
		exOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK));

        mBar.add(fileMenu);

        //Edit menu
        JMenu editMenu=new JMenu("Edit");
        editMenu.setMnemonic('e');
        
        JMenuItem undoOption=new JMenuItem("Undo");
	    JMenuItem redoOption=new JMenuItem("Redo");
        JMenuItem cutOption=new JMenuItem("Cut");
        JMenuItem copyOption=new JMenuItem("Copy");
        JMenuItem pasteOption=new JMenuItem("Paste");
        JMenuItem findOption=new JMenuItem("Find");
        JMenuItem replaceOption=new JMenuItem("Replace");

        undoOption.addActionListener(new Undome());
        undoOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK));
		editMenu.add(undoOption);

		redoOption.addActionListener(new Redome());
       	redoOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_MASK));
       	editMenu.add(redoOption);
		editMenu.addSeparator();
		
      	cutOption.addActionListener(new Cu_option());
       	cutOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
       	editMenu.add(cutOption);

        copyOption.addActionListener(new Cop_option());
		copyOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		editMenu.add(copyOption);

        pasteOption.addActionListener(new Past_option());
		pasteOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
		editMenu.add(pasteOption);
		editMenu.addSeparator();
		
		findOption.addActionListener(new find_option());
		findOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK));
		editMenu.add(findOption);

 		replaceOption.addActionListener(new find_option());
        replaceOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));
        editMenu.add(replaceOption);

        mBar.add(editMenu);

        //View Menu
        JMenu view=new JMenu("View");
        mBar.add(view);
        view.setMnemonic('v');
        
        tbar=new JCheckBoxMenuItem("ToolBar",true);
        stbar=new JCheckBoxMenuItem("StatusBar",true);
        fbar=new JCheckBoxMenuItem("FormatBar",true);
         
        tbar.addItemListener(new t_option());
        view.add(tbar);
        
		stbar.addItemListener(new st_option());
        view.add(stbar);
        
        fbar.addItemListener(new f_option());
        view.add(fbar);
         
		//Format Menu
        JMenu format=new JMenu("Format");
        mBar.add(format);
        format.setMnemonic('o');
        
        wordwrap=new JCheckBoxMenuItem("WordWrap");
        format.add(wordwrap);
        wordwrap.addItemListener(new wwrap());

        JMenuItem font=new JMenuItem("Font");
		format.add(font);
		font.addActionListener(new font_opt());
       
       	//Insert Menu
       	JMenu insert=new JMenu("Insert");
        insert.setMnemonic('i');
        mBar.add(insert);

        JMenuItem dt=new JMenuItem("Date");
        insert.add(dt);
        dt.addActionListener(new date());
        
        JMenuItem tm=new JMenuItem("Time");
        tm.addActionListener(new time());
        insert.add(tm);

		//Help Menu
		JMenu help=new JMenu("Help");
		mBar.add(help);
		help.setMnemonic('h');
     	JMenuItem ht=new JMenuItem("Help Topics");
		help.add(ht);
		ht.addActionListener(new help_topic());
		JMenuItem ab=new JMenuItem("About W-World");
		help.add(ab);
		ab.addActionListener(new about());
		
        setJMenuBar(mBar);
		
		//popup menu
        MouseListener mouseListener = new MouseAdapter( )
		{
			public void mousePressed(MouseEvent e) { checkPopup(e); }
			public void mouseClicked(MouseEvent e) { checkPopup(e); }
			public void mouseReleased(MouseEvent e) { checkPopup(e); }
			private void checkPopup(MouseEvent e)
			{
				if (e.isPopupTrigger( ))
				{
					selectedComponent = tArea;
					pop.show(tArea, e.getX(), e.getY( ));
				}
			}
		};
		pop = new JPopupMenu("POPMENU");
		pop.add(u=new JMenuItem("Undo"));
		pop.addSeparator();
		u.addActionListener(new Undome());
		pop.add(c=new JMenuItem("Cut"));
		c.addActionListener(new Cu_option());
		pop.add(co=new JMenuItem("Copy"));
		co.addActionListener(new Cop_option());
		pop.add(pa=new JMenuItem("Paste"));
		pop.addSeparator();
		pa.addActionListener(new Past_option());
		pop.add(de=new JMenuItem("Delete"));
		de.addActionListener(new delete());
		pop.addSeparator();
		pop.add(s=new JMenuItem("Select all"));
		s.addActionListener(new selectAll());
		pop.addSeparator();
		pop.add(f=new JMenuItem("Font"));
		f.addActionListener(new font_opt());
		tArea.addMouseListener(mouseListener);
		setVisible(true);

		//toolbar
		setLayout(new BorderLayout());
		t1=new JToolBar();
		t1.setFloatable(true);
		new1=new JButton(new ImageIcon("Image/new.gif"));
		new1.setToolTipText("New");
		t1.add(new1);
		new1.addActionListener(new Ne_option());
		t1.addSeparator();

		open=new JButton(new ImageIcon("Image/open.gif"));
		open.setToolTipText("Open");
		t1.add(open);
		open.addActionListener(new Ope_option());
		t1.addSeparator();

		save=new JButton(new ImageIcon("Image/save.gif"));
		save.setToolTipText("Save");
		t1.add(save);
		save.addActionListener(new Sav_option());
		t1.addSeparator();
		
		print=new JButton(new ImageIcon("Image/icon_printer.gif"));
		print.setToolTipText("Print");
		t1.add(print);
		print.addActionListener(new print(tArea));
		t1.addSeparator();

		cut=new JButton(new ImageIcon("Image/cut.gif"));
		cut.setToolTipText("Cut");
		t1.add(cut);
		cut.addActionListener(new Cu_option());
		t1.addSeparator();

		copy=new JButton(new ImageIcon("Image/copy.gif"));
		copy.setToolTipText("Copy");
		t1.add(copy);
		copy.addActionListener(new Cop_option());
		t1.addSeparator();

		paste=new JButton(new ImageIcon("Image/bpaste.gif"));
		paste.setToolTipText("Paste");
		t1.add(paste);
		paste.addActionListener(new Past_option());
		t1.addSeparator();
		
		find=new JButton(new ImageIcon("Image/find.gif"));
		find.setToolTipText("Find");
		t1.add(find);
		find.addActionListener(new find_option());
		t1.addSeparator();
			
		undo=new JButton(new ImageIcon("Image/undo.gif"));
		undo.setToolTipText("Undo");
		t1.add(undo);
		undo.addActionListener(new Undome());
		t1.addSeparator();

		redo=new JButton(new ImageIcon("Image/redo.gif"));
		redo.setToolTipText("Redo");
		t1.add(redo);
		redo.addActionListener(new Redome());
		t1.addSeparator();
		jp.add(t1);
				
		//format bar
		t2=new JToolBar();
		t2.setFloatable(true);
		t2.setRollover(true);
		GraphicsEnvironment g=GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts=g.getAvailableFontFamilyNames();
		
		font_family=new JComboBox(fonts);
		t2.add(font_family);
		font_family.addItemListener(new fontlisten());
		t2.addSeparator(d);

		font_size=new JComboBox(f_size);
		t2.add(font_size);
		font_size.addItemListener(new fontlisten());
		t2.addSeparator(d);


		bold=new JToggleButton(new ImageIcon("Image/bold.gif"));
		bold.setToolTipText("Bold");
		t2.add(bold);
		bold.addItemListener(new fontlisten());
		t2.addSeparator(d);

		italic=new JToggleButton(new ImageIcon("Image/italic.gif"));
		italic.setToolTipText("Italic");
		t2.add(italic);
		italic.addItemListener(new fontlisten());
		t2.addSeparator(d);

		/*underline=new JToggleButton(new ImageIcon("F:/Image/underline.gif"));
		underline.setToolTipText("Underline");
		t2.add(underline);
		underline.addItemListener(new fontlisten());
		t2.addSeparator(d);*/
		
		
		tcolor=new JButton(new ImageIcon("Image/tcolor.gif"));
		tcolor.setToolTipText("Text Color");
		t2.add(tcolor);
		tcolor.addActionListener(new color_listen());
		
		bcolor=new JButton(new ImageIcon("Image/bgcolor.gif"));
		bcolor.setToolTipText("Background Color");
		t2.add(bcolor);
		bcolor.addActionListener(new color_listen());

		up=new JToggleButton(new ImageIcon("Image/up.gif"));
		up.setToolTipText("Upper Case");
		t2.add(up);
		up.addItemListener(new fontlisten());
		
		low=new JToggleButton(new ImageIcon("Image/low.gif"));
		low.setToolTipText("Lower Case");
		t2.add(low);
		low.addItemListener(new fontlisten());
		
		align_left=new JToggleButton(new ImageIcon("Image/left.gif"));
		align_left.setToolTipText("Align Left");
		t2.add(align_left);
		align_left.addItemListener(new fontlisten());
		t2.addSeparator(d);
		
		align_right=new JToggleButton(new ImageIcon("Image/right.gif"));
		align_right.setToolTipText("Align Right");
		t2.add(align_right);
		align_right.addItemListener(new fontlisten());
		t2.addSeparator(d);

		jp.add(t2);
		add(tArea);
		
		//save dialog at closing
		JLabel l5=new JLabel("Do you want to save changes?");
		yes=new JButton("Yes");
		no=new JButton("No");
		can=new JButton("Cancel");
		j2=new JDialog((Frame)null,"SAVE",false);
		Container dialogContentPane11=j2.getContentPane();
		dialogContentPane11.setLayout(new FlowLayout());
		dialogContentPane11.add(l5);
		dialogContentPane11.add(yes);
		dialogContentPane11.add(no);
		dialogContentPane11.add(can);
		no.addActionListener(new Close_option());
		yes.addActionListener(new Sav_option());
		can.addActionListener(new cancel_option());
		
		//status bar		
        t3=new JToolBar();
        count=new JButton("Count");
        count.addActionListener(new counts());
        t3.add(count);
        t3.addSeparator();
        mpos=new JLabel();
        tArea.addMouseMotionListener(new MouseAdapter()
        {
        	public void mouseMoved(MouseEvent e)
        	{
        		mpos.setText(e.getX()+","+e.getY());
        	}
        });
		t3.add(mpos);
		t3.addSeparator();
		cap=new JLabel();
		num=new JLabel();
		scroll=new JLabel();
		tArea.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				Toolkit t=Toolkit.getDefaultToolkit();
				boolean tr=t.getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				if(tr==true)
					cap.setText("CAP");
				else
					cap.setText("");
				boolean nt=t.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
				if(nt==true)
					num.setText("NUM");
				else
					num.setText("");
				boolean scr=t.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
				if(scr==true)
					scroll.setText("SCRL");
				else
					scroll.setText("");
				
			}
		});
				
		t3.add(cap);		
		t3.addSeparator();
		t3.add(num);		
		t3.addSeparator();
		t3.add(scroll);		
		t3.addSeparator();
		
		add(t3,BorderLayout.SOUTH);
        validate();
        pack();
        
		p=new JScrollPane(tArea);
    	add(p);
		add(jp,"North");
		
		//font dialog
		JLabel ff=new JLabel(" Font family:");
		JLabel ft=new JLabel(" Font Style:");
		JLabel fs=new JLabel(" Font Size:");
		JLabel fc=new JLabel(" Font Color:");  
		choose=new JButton("Choose..");
		ok=new JButton("OK");
		cancel=new JButton("Cancel");
		pre=new JTextField(5);
		pre.setText("ABCD abcd");
		pre.setEditable(false);
		font_family1=new JComboBox(fonts);
		font_size1=new JComboBox(f_size);
		font_style=new JComboBox(f_style);
		j3=new JDialog((Frame)null,"Font",false);
		Container dialog=j3.getContentPane();
		dialog.setLayout(new GridLayout(4,3,10,15));
		dialog.add(ff);
		dialog.add(ft);
		dialog.add(fs);
		dialog.add(font_family1);
		dialog.add(font_style);
		dialog.add(font_size1);
		dialog.add(fc);
		dialog.add(choose);
		dialog.add(pre);
		dialog.add(ok);
		dialog.add(cancel);
		
		font_family1.addItemListener(new font_item());
		font_size1.addItemListener(new font_item());
		font_style.addItemListener(new font_item());
		
		choose.addActionListener(new font_opt());
		ok.addActionListener(new font_opt());
		cancel.addActionListener(new font_opt());
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				j2.setBounds(400,125,225,100);
    			j2.show();
			}
		});
		
		font_family.setSelectedItem("Times New Roman");
		font_size.setSelectedItem("12");
		font_family1.setSelectedItem("Times New Roman");
		font_size1.setSelectedItem("12");
		font_style.setSelectedItem("regular");
		tArea.setForeground(Color.black);
		tArea.setBackground(Color.white);
		tArea.requestFocus();	
		setTitle("W-World");
	}
	class font_item implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			Font textFont = tArea.getFont();
			style=Font.PLAIN;
			family=textFont.getName();
			int size=textFont.getSize();
			family=(String)font_family1.getSelectedItem();
			si=(String)font_size1.getSelectedItem();
			size=Integer.parseInt(si);
			String st=(String)font_style.getSelectedItem();
		
			if(st.equals("regular"))
				style=Font.PLAIN;
			else if(st.equals("bold"))
				style=Font.BOLD;
			else if(st.equals("italic"))
				style=Font.ITALIC;
			else if(st.equals("boldItalic"))
				style=Font.BOLD|Font.ITALIC;
			f1=new Font(family,style,size);
			pre.setFont(f1);
		}
	}
	class font_opt implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			j3.setBounds(500,100,400,250);
    		j3.show();
			if(e.getSource()==ok)
    		{
    			tArea.setFont(f1);
    			font_family.setSelectedItem(family);
				font_size.setSelectedItem(si);
				if(style==Font.BOLD)
				{
					bold.setSelected(true);
					italic.setSelected(false);
				}
				if(style==Font.ITALIC)
				{
					italic.setSelected(true);
					bold.setSelected(false);
				}
				if(style==3)
				{
					bold.setSelected(true);
					italic.setSelected(true);
				}
				if(style==Font.PLAIN)
				{
					bold.setSelected(false);
					italic.setSelected(false);
				}
				tArea.setForeground(c1);
    			j3.dispose();
       		}
    	 	if(e.getSource()==cancel)
    		{
				j3.dispose();
			}
			if(e.getSource()==choose)
    		{
				JColorChooser c2=new JColorChooser();
				c1=c2.showDialog(null,"color choooser",Color.green);
				pre.setForeground(c1);

			}
    	}
    }
	class fontlisten implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
		    Font textFont = tArea.getFont();
			int style=Font.PLAIN;
			family=textFont.getName();
			int size=textFont.getSize();
			String s=tArea.getText();
			String s1=s.toUpperCase();
			String s2=s.toLowerCase();
			if(bold.isSelected()==true)
			{
				style=Font.BOLD;
				font_style.setSelectedItem("bold");
			}
			if(italic.isSelected()==true)
			{
				style=Font.ITALIC;
				font_style.setSelectedItem("italic");
			}
			if(bold.isSelected()==true && italic.isSelected()==true)
			{
				style=Font.BOLD|Font.ITALIC;
				font_style.setSelectedItem("boldItalic");
			}
			if(bold.isSelected()==false && italic.isSelected()==false)
			{
				font_style.setSelectedItem("regular");
			}
				
			if(align_left.isSelected()==true)
			{
				tArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			}
			if(align_right.isSelected()==true)
			{
				tArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			}
			if(up.isSelected()==true)
			{
				tArea.setText(s1);
				low.setSelected(false);
			}
			if(low.isSelected()==true)
			{
				tArea.setText(s2);
				up.setSelected(false);
			}
			family=(String)font_family.getSelectedItem();
			si=(String)font_size.getSelectedItem();
			size=Integer.parseInt(si);
			tArea.setFont(new Font(family,style,size));
			font_family1.setSelectedItem(family);
			font_size1.setSelectedItem(si);
		}
	}
	class color_listen implements ActionListener
	{
		public void actionPerformed(ActionEvent cc)
		{
			if(cc.getSource()==tcolor)
			{
				JColorChooser c1=new JColorChooser();
				Color c=c1.showDialog(null,"color choooser",Color.green);
				tArea.setForeground(c);
			}
			if(cc.getSource()==bcolor)
			{
				JColorChooser c1=new JColorChooser();
				Color c=c1.showDialog(null,"color choooser",Color.green);
				tArea.setBackground(c);
			}
		}	
	}
	class Ne_option implements ActionListener
    {
		public void actionPerformed(ActionEvent ne)
		{
			tArea.setText(" ");
			font_family.setSelectedItem("Times New Roman");
			font_size.setSelectedItem("12");
			font_family1.setSelectedItem("Times New Roman");
			font_size1.setSelectedItem("12");
			font_style.setSelectedItem("regular");
			tArea.setForeground(Color.black);
			tArea.setBackground(Color.white);
			fName=null;
			setTitle("W-World");
		}
	}
	class Close_option implements ActionListener
	{
		public void actionPerformed(ActionEvent close_o)
		{
			System.exit(0);
		}
	}
 	class Clos_option implements ActionListener
 	{
		public void actionPerformed(ActionEvent close_o)
		{
			j2.setBounds(400,125,225,100);
    		j2.show();
        }
	 }
	class cancel_option implements ActionListener
 	{
		public void actionPerformed(ActionEvent close_o)
		{
			j2.dispose();
			setVisible(true);
		}
 	}
  	class Cu_option implements ActionListener
	{
		public void actionPerformed(ActionEvent cut_o)
		{
			String sText=tArea.getSelectedText();
			StringSelection sSelection =new StringSelection(sText);
			cBoard.setContents(sSelection,sSelection);
			tArea.replaceRange(" ",tArea.getSelectionStart(),tArea.getSelectionEnd());
		}
	}
	class Undome implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
	 	{
        	try 
        	{
          		undoManager.undo();
        	}
        	catch (CannotUndoException cre) 
        	{
          		//cre.printStackTrace();
        	}
    	}
	}
	class Redome implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
        	try
        	{
          		undoManager.redo();
        	}
        	catch (CannotUndoException cre) 
        	{
          		//cre.printStackTrace();
        	}
      	}
	}
	class Cop_option implements ActionListener
	{
		public void actionPerformed(ActionEvent copy_o)
		{
			String sText=tArea.getSelectedText();
			StringSelection cString =new StringSelection(sText);
			cBoard.setContents(cString,cString);
		}
	}
	class Past_option implements ActionListener
	{
		public void actionPerformed(ActionEvent paste_o)
		{
			Transferable ctransfer=cBoard.getContents(Wworld.this);
			try
			{
				String sText=(String)ctransfer.getTransferData(DataFlavor.stringFlavor);
				tArea.replaceRange(sText,tArea.getSelectionStart(),tArea.getSelectionEnd());
			}
			catch (Exception exc)
			{
				System.out.println("NOT A STRING FLAVOR");
			}
		}
	}
	class wwrap implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			if(wordwrap.getState()==true)
			{
				tArea.setLineWrap(true);
				tArea.setWrapStyleWord(true);
			}
			else
			{
				tArea.setLineWrap(false);
				tArea.setWrapStyleWord(false);
			}
		}
	}
	class t_option implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if(tbar.getState()==true)
			{
				t1.setVisible(true);
			}
			else
			t1.setVisible(false);
		}
	}
	class f_option implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if(fbar.getState()==true)
			{
			t2.setVisible(true);
			}
			else
				t2.setVisible(false);
		}
	}
	class st_option implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if(stbar.getState()==true)
			{
				t3.setVisible(true);
			}
			else
				t3.setVisible(false);
		}
	}
	class counts implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			j1=new JDialog((Frame)null,"COUNT",false);
			JLabel lcount=new JLabel("  Line Count:    "+tArea.getLineCount());
			JLabel ccount=new JLabel("  Char Count:    "+tArea.getText().length());
			Container dpane=j1.getContentPane();
			dpane.setLayout(new FlowLayout());
			dpane.add(lcount);
			dpane.add(ccount);
			j1.setBounds(150,100,150,100);
			j1.show();
		}
	}
	class find_option implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			j.setBounds(400,125,300,125);
    		j.show();
    		if(e.getSource()==b)
    		{
    			String s=tArea.getText().toLowerCase();
    			String s2=t.getText().toLowerCase();
       			int location=s.indexOf(s2);
       			if(location==(-1))
    				JOptionPane.showMessageDialog((Component)null,"No matches found","Find",JOptionPane.WARNING_MESSAGE);
				else
    				tArea.select(location,location+s2.length());
    			  			  			    				
   	    	}
    		if(e.getSource()==b1)
    		{
    			String s=tArea.getText().toLowerCase();
    			String s2=t.getText().toLowerCase();
    			int loc=s.indexOf(s2);
    			if(loc==(-1))
    				JOptionPane.showMessageDialog((Component)null,"No matches found for given text","Find",JOptionPane.WARNING_MESSAGE);
				else
    			{
    				tArea.select(loc,loc+s2.length());
   					tArea.replaceRange(tr.getText(),tArea.getSelectionStart(),tArea.getSelectionEnd());
   				}
 		   	}
    		if(e.getSource()==b2)
    		{
				j.dispose();
			}
	    }
	}
	class date implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String str=String.format("%1$td %1$tB %1$tY ",new Date());
			tArea.append(str);
		}
	}
	class time implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String st=String.format("%1$tI: %1$tM: %1$tS %1$tp ",new Date());
			tArea.append(st);
		}
	}
	class delete implements ActionListener
	{
    	public void actionPerformed(ActionEvent e)
     	{
        	String selection = tArea.getSelectedText();
         	tArea.replaceRange("", tArea.getSelectionStart(),
         	tArea.getSelectionEnd());
     	}
	}
	class selectAll implements ActionListener
	{
    	public void actionPerformed(ActionEvent e)
		{
        	tArea.selectAll();
		}
	}
	class Ope_option implements ActionListener
   	{
    	public void actionPerformed(ActionEvent e)
      	{	
        	int chooserStatus;
		   	JFileChooser chooser = new JFileChooser();
         	chooserStatus = chooser.showOpenDialog(null);
         	if (chooserStatus == JFileChooser.APPROVE_OPTION)
         	{
            	File selectedFile = chooser.getSelectedFile();
            	fName = selectedFile.getPath();
            	if (!openFile(fName))
            	{
               		JOptionPane.showMessageDialog(null,"Error reading " +fName, "Error",JOptionPane.ERROR_MESSAGE);
            	}
         	}
      	}
      	boolean openFile(String filename)
      	{
        	boolean success;
         	String inputLine, editorString = "";
         	FileReader freader;
         	BufferedReader inputFile;
	        try
    	    {
	            freader = new FileReader(filename);
    	       	inputFile = new BufferedReader(freader);
	            inputLine = inputFile.readLine();
	            if(inputLine.equals("W-World"))
	            {
	            	inputLine = inputFile.readLine();
	            	String ffo=inputLine;
	            	inputLine = inputFile.readLine();
	            	int sto=Integer.parseInt(inputLine);
	            	inputLine = inputFile.readLine();
	            	int so=Integer.parseInt(inputLine);
	            	tArea.setFont(new Font(ffo,sto,so));
	            
	            	inputLine = inputFile.readLine();
	            	int rgbfo=Integer.parseInt(inputLine);
	            	Color fco=new Color(rgbfo);
	            	tArea.setForeground(fco);
	            	inputLine = inputFile.readLine();
	            	int rgbo=Integer.parseInt(inputLine);
	            	Color bco=new Color(rgbo);
	            	tArea.setBackground(bco);
	            	inputLine = inputFile.readLine();
	         	}
	         	else
	         	{
						tArea.setForeground(Color.black);
						tArea.setBackground(Color.white);         	 	
	         	 		inputLine = inputFile.readLine();
	         	}
    	        while (inputLine != null)
        	    {
            		editorString = editorString +inputLine + "\n";
               		inputLine = inputFile.readLine();
            	}
            	tArea.setText(editorString);
	            inputFile.close();
    	        success = true;
        	 }
         	catch (IOException e)
         	{
            	success = false;
         	}
        	return success;
      	}
   	}
	class Sav_option implements ActionListener
   	{
   		public void actionPerformed(ActionEvent e)
   		{
   			int chooserStatus;
	        if (e.getActionCommand() == "Save As" || fName == null)
    	    {
        	    JFileChooser chooser = new JFileChooser();
            	chooserStatus = chooser.showSaveDialog(null);
            	if (chooserStatus == JFileChooser.APPROVE_OPTION)
            	{
		         	File selectedFile =
                    chooser.getSelectedFile();
	               	fName = selectedFile.getPath();
	               	
            	}
         	}
	       if (!saveFile(fName))
         	{
            	JOptionPane.showMessageDialog(null,"Error saving " +fName,"Error",JOptionPane.ERROR_MESSAGE);
         	}
         	j2.dispose();
        }
        boolean saveFile(String fName)
      	{
        	boolean success;
        	String editorString;
        	FileWriter fwriter;
        	PrintWriter outputFile;
			try
         	{
            // Open the file.
            int index=fName.lastIndexOf('.')+1;
            //.lastindexOf('.');
            	if((fName.substring(index).toLowerCase()).equals("txt"))
            	{
            		fwriter = new FileWriter(fName);
            		outputFile = new PrintWriter(fwriter);
            		Font f=tArea.getFont();
            		String ff=f.getFamily();
            		int st=f.getStyle();
            		int s=f.getSize();
            		Color fc=tArea.getForeground();
            		int rgbf=fc.getRGB();
            		Color bc=tArea.getBackground();
            		int rgb=bc.getRGB();
            		editorString = "W-World"+"\n"+ff+"\n"+st+"\n"+s+"\n"+rgbf+"\n"+rgb+"\n"+tArea.getText();
            		outputFile.print(editorString);
            		outputFile.close();
            		success = true;
            	}
            	else
            	{
            		fwriter = new FileWriter(fName);
            		outputFile = new PrintWriter(fwriter);
            		editorString=tArea.getText();
            		outputFile.print(editorString);
            		outputFile.close();
            		success=true;
 
            }
         }
         		catch (IOException e)
         		{
             		success = false;
         		}
         return success;
      }
   		
   	}
	class print implements ActionListener,Printable
	{
		JTextArea frameToPrint;
			
	 	public int print(Graphics g, PageFormat pf, int page) throws PrinterException 
    	{
			if (page > 0) 
        	{ /* We have only one page, and 'page' is zero-based */
            	return NO_SUCH_PAGE;
    		}
			
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
       	 	Graphics2D g2d = (Graphics2D)g;
        	g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        //g.drawString("Test the print dialog!", 100, 100);
        	frameToPrint.printAll(g);

        /* tell the caller that this page is part of the printed document */
        	return PAGE_EXISTS;
    	}
     	public void actionPerformed(ActionEvent e)
     	{
			try 
        	{
            	String cn = UIManager.getSystemLookAndFeelClassName();
            	UIManager.setLookAndFeel(cn); // Use the native L&F
        	}
         	catch (Exception cnf) 
         	{
        	}
        	PageFormat pf = job.pageDialog(aset);
        	job.setPrintable(this);
        	boolean ok = job.printDialog();
        	if (ok)
         	{
            	try 
            	{
                	job.print(aset);
            	}
             	catch (PrinterException ex) 
             	{
             		/* The job did not successfully complete */
            	}
        	}
        
   		}
   		public print(JTextArea tArea) 
		{
        	frameToPrint = tArea;
    	}
	}
	class pageset implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			PageFormat pf = job.pageDialog(aset);
    	}
	}
	class help_topic implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			Container c=getContentPane();
			JFrame fr=new JFrame();
			fr.setVisible(true);
			fr.setSize(600,600);
			fr.setLocation(400,20);
			JTextArea tA=new JTextArea();
			fr.add(tA);
			tA.setLineWrap(true);
			tA.setWrapStyleWord(true);
			JScrollPane p1=new JScrollPane(tA,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			tA.setFont(new Font("Times New Roman",Font.PLAIN,16));	
		
			String  s="FILE Menu-->\n"+"???     Create new document\n"+"Select File Menu and then click on New Option\n\n"+
			"???     Open existing document\n"+"Select File Menu and then click on Open Option\n"+
			"Choose file to open from Dialog box appeared on screen and Click OK.\n\n"+
			"???     Save the document\n"+"Select File Menu and then click on Save Option\n"+
			"Specify the path and then name of file to save and Click SAVE\n\n"+"???     Print the document\n"+
			"Select File Menu and then click on Print Option\nSpecify the Page setup on upcoming windowand Click OK\n"+
			"Then Choose Printer and select properties on next Window and click PRINT\n\n"+"EDIT Menu\n"+"???    Use cut, copy, paste\n "+
			"To Cut the required text, so that you can move it to another location, Select The required text then"+
			"Click on Edit menu and Select Cut Option\n To copy the text (make dupicate) Click on Edit menu and Select Copy Option\n"+
			"To Paste the Text Cut or Copied, take Cursor to position on which that text is required"+
			"and then Click on Edit menu and Select Paste Option\n\n"+"???     Use Undo and Redo\n "+
			"To undo your last action, click the Edit menu then click Undo.\n To Redo your last action, click the Edit menu and click Redo\n\n"+
			"???       Find and Replace\n"+"Click On Edit Menu and select Find option to Find any words\nThen write "+
			"word to find in textfield after Find what: label and click find\n Word if present in document will be selected or"+
			" No matches found message box will be present\n To replace any word in document write word to be replaced in Find What:"+
			"textfield and write word with we want to replace in Replace with textfield\n\n"+"INSERT Menu-->\n"+
			"???       Insert current date and time in a document"+"\nClick Insert and Select Date for date and Time for Time\n\n"+
			"VIEW Menu-->\n"+"???      display or hide toolbar,formatbar and statusbar\n "+"To display these "+
			"bars keep checkable on\n To hide them keep checkable off\n\n"+"FORMAT Menu-->\n"+"???      Use of Wordwrap\n"+
			" If you do not want to read or write text by scrolling horizontally, keep checkable on"+
			"the wordwrap option in Format Menu, it will give you facility to read text without horizontal scrolling\n\n"+
			"???      Give Font to Text"+"\n Select Font option from Format Menu. Font Dialog box will appear"+
			"Choose font family,font style, font size and Font color form that and Press OK. Selected Font will be "+
			"your text font now"+"\nYou can aso use Format bar for that purpose"+
			"\n\n\nShortcuts can be used:\n1.New		Ctrl+N\n2.Open		Ctrl+O\n3.Save		Ctrl+S\n4.Exit"+
			"		Alt+F4\n5.Cut		Ctrl+X\n6.Copy		Ctrl+C\n7.Paste		Ctrl+V\n8.Find		Ctrl+F"+
			"\n9.Replace		Ctrl+H\n10.Undo		Ctrl+Z\n11.Redo		Ctrl+Y\n12.Print		Ctrl+P";
			tA.setText(s);
			tA.setEditable(false);
			fr.add(p1);
		}
	}
	class helpab extends JPanel
	{
		public void paint(Graphics g)
		{
			setBackground(new Color(223,223,223));
			Font f=new Font("Times New Roman",Font.ITALIC,50);
			g.setFont(f);
			g.setColor(new Color(182,39,243));
			g.drawString("W-WORLD",150,65);
			g.setColor(Color.black);
			g.drawLine(100,75,450,75);
			Image image=Toolkit.getDefaultToolkit().getImage("F:\\Image\\wword.png");
	    	g.drawImage(image,100,70,50,50,this);
	    	Font f1=new Font("Arial",Font.BOLD,20);
	    	g.setFont(f1);
			g.drawString("Developed by,",175,100);
		 	Font f2=new Font("Arial",Font.BOLD,15);
		    g.setFont(f2);
			g.drawString("Hemavati M. Sabu",205,130);
			g.drawString("Minal S. Chaudhari",205,160);
			g.drawString("Damini S. Rote",205,190);
			g.drawString("Reena R. Shinde",205,220);
		}
	}
	class about extends JFrame implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFrame helps=new JFrame();
			helps.setVisible(true);
			helps.setSize(550,260);
			helps.setResizable(false);
			Container cp=helps.getContentPane();
			cp.add(new helpab());	
		}
	}
	public static void main (String[]args)
	{
		JFrame nFrame=new Wworld();
		nFrame.setSize(700,650);
		nFrame.setVisible(true);
	}
}
